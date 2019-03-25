package br.com.aqueteron.oauth2.authorizer.client;

import br.com.aqueteron.oauth2.authorizer.model.UserApiEntity;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.jwt.crypto.sign.RsaVerifier;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class OAuth2AuthorizerClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(OAuth2AuthorizerClient.class);

    private static final String VALIDATE_TOKEN_PATH = "oauth/token_key";

    private static final String USER_RESROUCE_PATH = "api/user";

    private static final String USER_WITH_ID_RESOURCE_PATH = "api/user/%s";

    private static final String CLIENT_PROTOCOL_EXCEPTION_MESSAGE = "Error while class the http request.";

    private static final String IO_EXCEPTIONS_MESSAGE = "Error while reading the http response.";

    private static final String URI_SYNTAX_EXCEPTION = "Error while creating http request.";

    private static final Integer OK = 200;

    private static final Integer CREATED = 201;

    private final HttpClient httpClient;

    private final ObjectMapper objectMapper;

    private final URI hostUri;

    private final ClientInfo clientInfo;

    private AccessToken accessToken;

    private RsaVerifier rsaVerifier;

    @Autowired
    public OAuth2AuthorizerClient(final ServerInfo serverInfo, final ClientInfo clientInfo) throws URISyntaxException {
        LOGGER.debug(String.format("Starting OAuth2AuthorizerClient with %s://%s:%d", serverInfo.getScheme(), serverInfo.getHost(), serverInfo.getPort()));
        this.httpClient = HttpClients.createDefault();
        this.objectMapper = new ObjectMapper();
        this.hostUri = new URIBuilder()
                .setScheme(serverInfo.getScheme())
                .setHost(serverInfo.getHost())
                .setPort(serverInfo.getPort())
                .build();
        this.clientInfo = clientInfo;
        this.rsaVerifier = null;
        this.accessToken = null;
    }

    OAuth2AuthorizerClient(final HttpClient httpClient, final ObjectMapper objectMapper, final URI hostUri, final RsaVerifier rsaVerifier, final AccessToken accessToken) {
        this.httpClient = httpClient;
        this.objectMapper = objectMapper;
        this.hostUri = hostUri;
        this.rsaVerifier = rsaVerifier;
        this.accessToken = accessToken;
        this.clientInfo = null;
    }

    RsaVerifier getRsaVerifier() {
        if (this.rsaVerifier == null) {
            this.rsaVerifier = new RsaVerifier(this.getTokenKey().getPublicKey());
        }
        return this.rsaVerifier;
    }

    public AccessToken getAccessToken() {
        if (this.accessToken == null) {
            Optional<AccessToken> loadResult = loadAccessToken(this.clientInfo.getId(), this.clientInfo.getSecret(), this.clientInfo.getScope());
            if (loadResult.isPresent()) {
                this.accessToken = loadResult.get();
            } else {
                this.accessToken = new AccessToken();
            }
        }
        return this.accessToken;
    }

    public TokenKey getTokenKey() {
        try {
            URI getFromToUri = new URIBuilder(this.hostUri).setPath(VALIDATE_TOKEN_PATH).build();
            HttpResponse httpResponse = this.httpClient.execute(new HttpGet(getFromToUri));
            if (200 == httpResponse.getStatusLine().getStatusCode()) {
                InputStream inputStream = httpResponse.getEntity().getContent();
                return this.objectMapper.readValue(inputStream, TokenKey.class);
            }
            throw new OAuth2AuthorizerClientRuntimeException("");
        } catch (URISyntaxException e) {
            throw new OAuth2AuthorizerClientRuntimeException(URI_SYNTAX_EXCEPTION, e);
        } catch (IOException e) {
            throw new OAuth2AuthorizerClientRuntimeException(IO_EXCEPTIONS_MESSAGE, e);
        }
    }

    public Optional<JwtTokenClaims> decodeAndVerify(final String token) {
        try {
            Jwt jwt = JwtHelper.decodeAndVerify(token, this.getRsaVerifier());
            return Optional.of(this.objectMapper.readValue(jwt.getClaims(), JwtTokenClaims.class));
        } catch (IllegalArgumentException e) {
            LOGGER.warn(String.format("The token: '%s' is invalid JWT token.", token), e);
            return Optional.empty();
        } catch (RuntimeException e) {
            LOGGER.warn(String.format("Error to try validate token: '%s'. Sign in JWT token is invalid.", token), e);
            return Optional.empty();
        } catch (IOException e) {
            throw new OAuth2AuthorizerClientRuntimeException("Error while reading the jwt claims.", e);
        }
    }

    private Optional<AccessToken> loadAccessToken(final String clientId, final String clientSecret, final String scope) {
        try {
            URI getFromToUri = new URIBuilder(this.hostUri).setPath("oauth/token").build();
            HttpPost httpPost = new HttpPost(getFromToUri);

            List<NameValuePair> form = new ArrayList<>(4);
            form.add(new BasicNameValuePair("grant_type", "client_credentials"));
            form.add(new BasicNameValuePair("client_id", clientId));
            form.add(new BasicNameValuePair("client_secret", clientSecret));
            form.add(new BasicNameValuePair("scope", scope));

            httpPost.setEntity(new UrlEncodedFormEntity(form));
            return this.executeHttpUriRequest(httpPost, AccessToken.class);
        } catch (URISyntaxException | UnsupportedEncodingException e) {
            throw new OAuth2AuthorizerClientRuntimeException(URI_SYNTAX_EXCEPTION, e);
        }
    }

    public Optional<UserApiEntity> getUserByLogin(final String userLogin) {
        try {
            URI getFromToUri = new URIBuilder(this.hostUri).setPath(String.format(USER_WITH_ID_RESOURCE_PATH, userLogin)).build();
            HttpGet httpGet = new HttpGet(getFromToUri);
            httpGet.addHeader("Authorization", "Bearer " + this.getAccessToken().getValue());
            return executeHttpUriRequest(httpGet, UserApiEntity.class);
        } catch (URISyntaxException e) {
            throw new OAuth2AuthorizerClientRuntimeException(URI_SYNTAX_EXCEPTION, e);
        }
    }

    public Optional<UserApiEntity> postUser(final UserApiEntity userApiEntity) {
        try {
            URI postFromToUri = new URIBuilder(hostUri).setPath(USER_RESROUCE_PATH).build();
            HttpPost httpPost = new HttpPost(postFromToUri);
            httpPost.addHeader("Authorization", "Bearer " + this.getAccessToken().getValue());
            StringEntity stringEntity = new StringEntity(objectMapper.writeValueAsString(userApiEntity));
            stringEntity.setContentType("application/json");
            httpPost.setEntity(stringEntity);
            return executeHttpUriRequest(httpPost, UserApiEntity.class);
        } catch (URISyntaxException | JsonProcessingException | UnsupportedEncodingException e) {
            throw new OAuth2AuthorizerClientRuntimeException("Error while http request.", e);
        }
    }

    private <T> Optional<T> executeHttpUriRequest(final HttpUriRequest httpUriRequest, final Class<T> valueType) {
        try {
            HttpResponse httpResponse = this.httpClient.execute(httpUriRequest);
            String contentString = new String(httpResponse.getEntity().getContent().readAllBytes());
            if ("GET".equals(httpUriRequest.getMethod()) && OK == httpResponse.getStatusLine().getStatusCode()) {
                return Optional.of(readContent(contentString, valueType));
            }
            if ("POST".equals(httpUriRequest.getMethod())) {
                if (OK == httpResponse.getStatusLine().getStatusCode()) {
                    return Optional.of(readContent(contentString, valueType));
                }
                if (CREATED == httpResponse.getStatusLine().getStatusCode()) {
                    return Optional.of(readContent(contentString, valueType));
                }
                throw new OAuth2AuthorizerClientRuntimeException(String.format("Error to try register a User. %s", contentString));
            }
            if ("PUT".equals(httpUriRequest.getMethod())) {
                if (OK == httpResponse.getStatusLine().getStatusCode()) {
                    return Optional.of(readContent(contentString, valueType));
                }
                throw new OAuth2AuthorizerClientRuntimeException(String.format("Error to try update a User. %s", contentString));
            }
            if ("DELETE".equals(httpUriRequest.getMethod())) {
                if (OK == httpResponse.getStatusLine().getStatusCode()) {
                    return Optional.empty();
                }
                throw new OAuth2AuthorizerClientRuntimeException(String.format("Error to try delete User."));
            }
        } catch (ClientProtocolException e) {
            throw new OAuth2AuthorizerClientRuntimeException(CLIENT_PROTOCOL_EXCEPTION_MESSAGE, e);
        } catch (IOException e) {
            throw new OAuth2AuthorizerClientRuntimeException(IO_EXCEPTIONS_MESSAGE, e);
        }
        return Optional.empty();
    }

    private <T> T readContent(final String contentString, final Class<T> valueType) throws IOException {
        return this.objectMapper.readValue(contentString, valueType);
    }
}
