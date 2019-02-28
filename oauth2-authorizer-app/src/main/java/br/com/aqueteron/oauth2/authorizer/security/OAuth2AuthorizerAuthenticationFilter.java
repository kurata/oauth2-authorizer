package br.com.aqueteron.oauth2.authorizer.security;

import br.com.aqueteron.oauth2.authorizer.api.ErrorApiEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.stereotype.Component;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Component
public class OAuth2AuthorizerAuthenticationFilter implements ContainerRequestFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(OAuth2AuthorizerAuthenticationFilter.class);

    private static final Set<ApiResourceSecurityDefinition> RESOURCE_SECURITY_DEFINITIONS;

    static {
        RESOURCE_SECURITY_DEFINITIONS = new HashSet<>();
        RESOURCE_SECURITY_DEFINITIONS.add(new ApiResourceSecurityDefinition("GET", "user", "read"));
        RESOURCE_SECURITY_DEFINITIONS.add(new ApiResourceSecurityDefinition("POST", "user", "write"));
    }

    private TokenStore tokenStore;

    @Autowired
    public OAuth2AuthorizerAuthenticationFilter(final TokenStore tokenStore) {
        this.tokenStore = tokenStore;
    }

    @Override
    public void filter(final ContainerRequestContext requestContext) {
        String requestMethod = requestContext.getMethod();
        String requestPath = requestContext.getUriInfo().getPath();

        if (isAuthenticate(requestMethod, requestPath)) {
            // Get the HTTP Authorization header from the request
            String authorizationHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);

            // Check if the HTTP Authorization header is present and formatted correctly
            if (authorizationHeader == null) {
                requestContext.abortWith(buildErrorResponse(Status.UNAUTHORIZED, "Authorization header must be provided."));
            } else {
                if (!authorizationHeader.startsWith("Bearer")) {
                    requestContext.abortWith(buildErrorResponse(Status.UNAUTHORIZED, "Bearer token must be provided in authorization header."));
                } else {
                    // Extract the token from the HTTP Authorization header
                    String token = authorizationHeader.substring("Bearer".length()).trim();

                    try {
                        // Validate the token
                        OAuth2AccessToken accessToken = this.tokenStore.readAccessToken(token);
                        if (!havePermition(requestMethod, requestPath, accessToken.getScope())) {
                            requestContext.abortWith(buildErrorResponse(Status.UNAUTHORIZED, "The token provided don't have the scope required."));
                        }
                    } catch (Exception e) {
                        LOGGER.warn(e.getMessage(), e);
                        requestContext.abortWith(Response.status(Status.UNAUTHORIZED).build());
                    }
                }
            }
        }
    }

    private Response buildErrorResponse(final Status status, final String message) {
        return Response.status(status).entity(new ErrorApiEntity(status, message)).build();
    }

    private Boolean havePermition(final String method, final String path, final Set<String> scope) {
        Optional<ApiResourceSecurityDefinition> apiResourceSecurityDefinition = RESOURCE_SECURITY_DEFINITIONS.parallelStream()
                .filter(resource -> resource.getMethod().equals(method) &&
                        path.matches(resource.getPathRegex()))
                .findFirst();
        if (apiResourceSecurityDefinition.isPresent()) {
            return scope.contains(apiResourceSecurityDefinition.get().getScope());
        }
        return Boolean.FALSE;
    }

    private Boolean isAuthenticate(final String method, final String path) {
        Optional<ApiResourceSecurityDefinition> apiResource = RESOURCE_SECURITY_DEFINITIONS.parallelStream()
                .filter(resource -> resource.getMethod().equals(method) && path.equals(resource.getPath()))
                .findFirst();
        return apiResource.isPresent();
    }

}
