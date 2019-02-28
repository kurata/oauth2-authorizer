package br.com.aqueteron.oauth2.authorizer.security;

import br.com.aqueteron.oauth2.authorizer.security.OAuth2AuthorizerAuthenticationFilter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.TokenStore;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.UriInfo;

import java.util.HashSet;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class OAuth2AuthorizerAuthenticationFilterTest {

    @Mock
    private TokenStore tokenStoreMock;

    @InjectMocks
    private OAuth2AuthorizerAuthenticationFilter filter;

    @Test
    public void filter() {
        UriInfo uriInfoMock = mock(UriInfo.class);
        when(uriInfoMock.getPath()).thenReturn("user");
        String authorizationHeader = "Bearer token";

        ContainerRequestContext requestMock = mock(ContainerRequestContext.class);
        when(requestMock.getMethod()).thenReturn("GET");
        when(requestMock.getUriInfo()).thenReturn(uriInfoMock);
        when(requestMock.getHeaderString(eq(HttpHeaders.AUTHORIZATION))).thenReturn(authorizationHeader);

        this.filter.filter(requestMock);

        verify(this.tokenStoreMock).readAccessToken("token");
    }

    @Test
    public void filterHeaderNull() {
        UriInfo uriInfoMock = mock(UriInfo.class);
        when(uriInfoMock.getPath()).thenReturn("user");

        ContainerRequestContext requestMock = mock(ContainerRequestContext.class);
        when(requestMock.getMethod()).thenReturn("GET");
        when(requestMock.getUriInfo()).thenReturn(uriInfoMock);
        when(requestMock.getHeaderString(eq(HttpHeaders.AUTHORIZATION))).thenReturn(null);

        this.filter.filter(requestMock);

        verify(requestMock).abortWith(any());
    }

    @Test
    public void filterNotBearerToken() {
        UriInfo uriInfoMock = mock(UriInfo.class);
        when(uriInfoMock.getPath()).thenReturn("user");
        String authorizationHeader = "Not bearer token";

        ContainerRequestContext requestMock = mock(ContainerRequestContext.class);
        when(requestMock.getMethod()).thenReturn("GET");
        when(requestMock.getUriInfo()).thenReturn(uriInfoMock);
        when(requestMock.getHeaderString(eq(HttpHeaders.AUTHORIZATION))).thenReturn(authorizationHeader);

        this.filter.filter(requestMock);

        verify(requestMock).abortWith(any());
    }

    @Test
    public void filterNotAthenticatePostUser() {
        UriInfo uriInfoMock = mock(UriInfo.class);
        when(uriInfoMock.getPath()).thenReturn("user");

        ContainerRequestContext requestMock = mock(ContainerRequestContext.class);
        when(requestMock.getMethod()).thenReturn("POST");
        when(requestMock.getUriInfo()).thenReturn(uriInfoMock);
        when(requestMock.getHeaderString(eq(HttpHeaders.AUTHORIZATION))).thenReturn("Bearer token");

        OAuth2AccessToken accessTokenMock = mock(OAuth2AccessToken.class);
        Set<String> scopes = new HashSet<>();
        scopes.add("write");
        when(accessTokenMock.getScope()).thenReturn(scopes);

        when(this.tokenStoreMock.readAccessToken(eq("token"))).thenReturn(accessTokenMock);

        this.filter.filter(requestMock);
    }

    @Test
    public void filterNotAthenticatePostUserInvalidScope() {
        UriInfo uriInfoMock = mock(UriInfo.class);
        when(uriInfoMock.getPath()).thenReturn("user");

        ContainerRequestContext requestMock = mock(ContainerRequestContext.class);
        when(requestMock.getMethod()).thenReturn("POST");
        when(requestMock.getUriInfo()).thenReturn(uriInfoMock);
        when(requestMock.getHeaderString(eq(HttpHeaders.AUTHORIZATION))).thenReturn("Bearer token");

        OAuth2AccessToken accessTokenMock = mock(OAuth2AccessToken.class);
        Set<String> scopes = new HashSet<>();
        scopes.add("read");
        when(accessTokenMock.getScope()).thenReturn(scopes);

        when(this.tokenStoreMock.readAccessToken(eq("token"))).thenReturn(accessTokenMock);

        this.filter.filter(requestMock);

        verify(requestMock).abortWith(any());
    }

    @Test
    public void filterNotAthenticatePostUserInvalidToken() {
        UriInfo uriInfoMock = mock(UriInfo.class);
        when(uriInfoMock.getPath()).thenReturn("user");

        ContainerRequestContext requestMock = mock(ContainerRequestContext.class);
        when(requestMock.getMethod()).thenReturn("POST");
        when(requestMock.getUriInfo()).thenReturn(uriInfoMock);
        when(requestMock.getHeaderString(eq(HttpHeaders.AUTHORIZATION))).thenReturn("token");

        this.filter.filter(requestMock);

        verify(requestMock).abortWith(any());
    }

    @Test
    public void filterNotAthenticatePostUserNotToken() {
        UriInfo uriInfoMock = mock(UriInfo.class);
        when(uriInfoMock.getPath()).thenReturn("user");

        ContainerRequestContext requestMock = mock(ContainerRequestContext.class);
        when(requestMock.getMethod()).thenReturn("POST");
        when(requestMock.getUriInfo()).thenReturn(uriInfoMock);
        when(requestMock.getHeaderString(eq(HttpHeaders.AUTHORIZATION))).thenReturn(null);

        this.filter.filter(requestMock);

        verify(requestMock).abortWith(any());
    }

    @Test
    public void filterNotAthenticateOptions() {
        UriInfo uriInfoMock = mock(UriInfo.class);
        when(uriInfoMock.getPath()).thenReturn("path");

        ContainerRequestContext requestMock = mock(ContainerRequestContext.class);
        when(requestMock.getMethod()).thenReturn("OPTIONS");
        when(requestMock.getUriInfo()).thenReturn(uriInfoMock);

        this.filter.filter(requestMock);
    }
}
