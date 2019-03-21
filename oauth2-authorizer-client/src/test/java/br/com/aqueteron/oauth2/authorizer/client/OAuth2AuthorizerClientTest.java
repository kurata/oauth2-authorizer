package br.com.aqueteron.oauth2.authorizer.client;

import br.com.aqueteron.oauth2.authorizer.model.UserApiEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.jwt.crypto.sign.RsaVerifier;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest(JwtHelper.class)
public class OAuth2AuthorizerClientTest {

    private static final String PUBLIC_KEY = "-----BEGIN PUBLIC KEY-----\nMIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAkLjYQqbkPQqIaoivNxxz/tjOb7S2LvVMj/giDt6nmzu+4gaTLcxbNWQ0lQKgNO4eRW7Ep5Bkt6HrA/V1HNWO4V1zrXBo2ypQT514ZsZaa294cnRL9PmarhVS07SmhiJch7kCNYvvKzJtvR4QQslSTUNi3ciT1NNBXkEonRgf2O/j1X/E0VUP8GyLFUYM02LBjIOblAkcFZFJ5LPn5Y1YTdv1GWcYArvyjqLuSOxK9Qn7zhEcH+q7jJbb3ux/niT1EUS1NXz6UJW7xT0KRcEb8eUkPp9iB1yOB1rxVD6NufuuafNcAI9GVTlct+jsP7p5wQWuiFshJllyuLb5jFOBLwIDAQAB\n-----END PUBLIC KEY-----";

    @Mock
    private HttpClient httpClientMock;

    @Mock
    private ObjectMapper objectMapperMock;

    private ObjectMapper objectMapper;

    private OAuth2AuthorizerClient oAuth2AuthorizerClient;

    private OAuth2AuthorizerClient oAuth2AuthorizerClientInjected;

    @Before
    public void setUp() throws URISyntaxException {
        RsaVerifier rsaVerifier = new RsaVerifier(PUBLIC_KEY);
        AccessToken accessToken = new AccessToken();
        this.objectMapper = new ObjectMapper();
        this.oAuth2AuthorizerClient = new OAuth2AuthorizerClient(this.httpClientMock, this.objectMapper, new URI(""), rsaVerifier, accessToken);
        this.oAuth2AuthorizerClientInjected = new OAuth2AuthorizerClient(this.httpClientMock, this.objectMapperMock, new URI(""), rsaVerifier, accessToken);
    }

    @Test
    public void decodeAndValidateTest() throws URISyntaxException {
        JwtTokenClaims jwtTokenClaims = new JwtTokenClaims("11fdb2d3-fff1-4a02-9884-afe890f95f25");
        Set<String> audienceSet = new HashSet<>();
        audienceSet.add("fromto-resource");
        jwtTokenClaims.setAudience(audienceSet);
        Set<String> scopeSet = new HashSet<>();
        scopeSet.add("read");
        scopeSet.add("run");
        scopeSet.add("write");
        jwtTokenClaims.setScope(scopeSet);
        jwtTokenClaims.setExpirationTime(1542140470L);
        Set<String> authorities = new HashSet<>();
        authorities.add("ROLE_APPLICATION");
        jwtTokenClaims.setAuthorities(authorities);
        jwtTokenClaims.setClientId("fromTo.api.aqueteron.com.br");

        Optional<JwtTokenClaims> result = this.oAuth2AuthorizerClient.decodeAndVerify("eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOlsiZnJvbXRvLXJlc291cmNlIl0sInNjb3BlIjpbInJlYWQiLCJydW4iLCJ3cml0ZSJdLCJleHAiOjE1NDIxNDA0NzAsImF1dGhvcml0aWVzIjpbIlJPTEVfQVBQTElDQVRJT04iXSwianRpIjoiMTFmZGIyZDMtZmZmMS00YTAyLTk4ODQtYWZlODkwZjk1ZjI1IiwiY2xpZW50X2lkIjoiZnJvbVRvLmFwaS5hcXVldGVyb24uY29tLmJyIn0.DUSMxrYzecKnYylAzinMB3wHTlJPuJWg0ctcxaTPxA8yMkDVrbKxPEEkZJQ2ZaduTOQNTDd3uD0XomTIyiIh1oSAjC1V7A91VVuxSzGSuPRNGyMZ56KjjyMCxT-NgGOPRkFDRPH7FsOvfQc4qOfiYoc46oBBNgq4tRDMM2qxh3XiC69YxiYQXJZYiTi3EeFpy1x5Kp4D5pByLm2wHg0iXWi-lmaJF1M9WJxPSgE682q3M37bSWMIOFiiUkOkJ3v7Ns-xRQOCT9-kBa_tm-dc1s5zVMyeHZNaIb6_-EeKxmnJ6gBCoEFO-sAUpotavo4LbfUOKtOnYN6snZxMgL6cuw");
        assertTrue(result.isPresent());
        assertEquals(jwtTokenClaims, result.get());
    }

    @Test
    public void decodeAndValidateNotSign() throws URISyntaxException {
        Optional<JwtTokenClaims> result = this.oAuth2AuthorizerClient.decodeAndVerify("eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOlsiZnJvbXRvLXJlc291cmNlIl0sInNjb3BlIjpbInJlYWQiLCJydW4iLCJ3cml0ZSJdLCJleHAiOjE1NDIxNDA0NzAsImF1dGhvcml0aWVzIjpbIlJPTEVfQVBQTElDQVRJT04iXSwianRpIjoiMTFmZGIyZDMtZmZmMS00YTAyLTk4ODQtYWZlODkwZjk1ZjI1IiwiY2xpZW50X2lkIjoiZnJvbVRvLmFwaS5hcXVldGVyb24uY29tLmJyIn0");
        assertFalse(result.isPresent());
    }

    @Test
    public void decodeAndValidateInvalid() throws URISyntaxException {
        Optional<JwtTokenClaims> result = this.oAuth2AuthorizerClient.decodeAndVerify("eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOlsiZnJvbXRvLXJlc291cmNlIl0sInNjb3BlIjpbInJlYWQiLCJydW4iLCJ3cml0ZSJdLCJleHAiOjE1NDIxNDA0NzAsImF1dGhvcml0aWVzIjpbIlJPTEVfQVBQTElDQVRJT04iXSwianRpIjoiMTFmZGIyZDMtZmZmMS00YTAyLTk4ODQtYWZlODkwZjk1ZjI1IiwiY2xpZW50X2lkIjoiZnJvbVRvLmFwaS5hcXVldGVyb24uY29tLmJyIn0.invalid_sign");
        assertFalse(result.isPresent());
    }

    @Test
    public void getTokenKey() throws IOException {
        TokenKey tokenKey = new TokenKey("algorithm", "publicKey");

        HttpResponse getHttpResponseMock = mock(HttpResponse.class);

        StatusLine statusLineMock = mock(StatusLine.class);
        when(statusLineMock.getStatusCode()).thenReturn(200);
        when(getHttpResponseMock.getStatusLine()).thenReturn(statusLineMock);

        HttpEntity entityMock = mock(HttpEntity.class);

        when(entityMock.getContent()).thenReturn(this.convertToInputStream(tokenKey));
        when(getHttpResponseMock.getEntity()).thenReturn(entityMock);

        when(this.httpClientMock.execute(any(HttpGet.class))).thenReturn(getHttpResponseMock);

        TokenKey tokenKeyResponse = this.oAuth2AuthorizerClient.getTokenKey();
        assertNotNull(tokenKeyResponse);
        assertEquals(tokenKey, tokenKeyResponse);
    }

    @Test(expected = OAuth2AuthorizerClientRuntimeException.class)
    public void getTokenKeyNotFountTest() throws IOException {
        HttpResponse getHttpResponseMock = mock(HttpResponse.class);

        StatusLine statusLineMock = mock(StatusLine.class);
        when(statusLineMock.getStatusCode()).thenReturn(404);
        when(getHttpResponseMock.getStatusLine()).thenReturn(statusLineMock);

        when(this.httpClientMock.execute(any(HttpGet.class))).thenReturn(getHttpResponseMock);

        this.oAuth2AuthorizerClient.getTokenKey();
    }

    @Test
    public void decodeAndVerify() throws IOException {
        PowerMockito.mockStatic(JwtHelper.class);

        Jwt jwt = mock(Jwt.class);
        when(jwt.getClaims()).thenReturn("claimsString");

        JwtTokenClaims jwtTokenClaims = new JwtTokenClaims();
        when(this.objectMapperMock.readValue(eq("claimsString"), eq(JwtTokenClaims.class))).thenReturn(jwtTokenClaims);

        when(JwtHelper.decodeAndVerify(eq("token"), any())).thenReturn(jwt);

        Optional<JwtTokenClaims> result = this.oAuth2AuthorizerClientInjected.decodeAndVerify("token");
        assertTrue(result.isPresent());
        assertEquals(jwtTokenClaims, result.get());
    }

    @Test
    public void decodeAndVerifyIllegalArgumentException() throws IOException {
        PowerMockito.mockStatic(JwtHelper.class);

        when(JwtHelper.decodeAndVerify(eq("token"), any())).thenThrow(new IllegalArgumentException());

        Optional<JwtTokenClaims> result = this.oAuth2AuthorizerClientInjected.decodeAndVerify("token");
        assertFalse(result.isPresent());
    }

    @Test
    public void decodeAndVerifyRuntimeException() throws IOException {
        PowerMockito.mockStatic(JwtHelper.class);

        when(JwtHelper.decodeAndVerify(eq("token"), any())).thenThrow(new RuntimeException());

        Optional<JwtTokenClaims> result = this.oAuth2AuthorizerClientInjected.decodeAndVerify("token");
        assertFalse(result.isPresent());
    }

    @Test(expected = OAuth2AuthorizerClientRuntimeException.class)
    public void decodeAndVerifyIOException() throws IOException {
        PowerMockito.mockStatic(JwtHelper.class);

        Jwt jwt = mock(Jwt.class);
        when(jwt.getClaims()).thenReturn("claimsString");

        when(this.objectMapperMock.readValue(eq("claimsString"), eq(JwtTokenClaims.class))).thenThrow(new IOException());

        when(JwtHelper.decodeAndVerify(eq("token"), any())).thenReturn(jwt);

        Optional<JwtTokenClaims> result = this.oAuth2AuthorizerClientInjected.decodeAndVerify("token");
        assertFalse(result.isPresent());
    }

    @Test
    public void getUserByLoginTest() throws IOException {
        UserApiEntity user = new UserApiEntity("login", "password");

        HttpResponse getHttpResponseMock = mock(HttpResponse.class);

        StatusLine statusLineMock = mock(StatusLine.class);
        when(statusLineMock.getStatusCode()).thenReturn(200);
        when(getHttpResponseMock.getStatusLine()).thenReturn(statusLineMock);

        HttpEntity entityMock = mock(HttpEntity.class);

        when(entityMock.getContent()).thenReturn(this.convertToInputStream(user));
        when(getHttpResponseMock.getEntity()).thenReturn(entityMock);

        when(this.httpClientMock.execute(any(HttpGet.class))).thenReturn(getHttpResponseMock);

        Optional<UserApiEntity> response = this.oAuth2AuthorizerClient.getUserByLogin("login");
        assertTrue(response.isPresent());
        assertEquals(user, response.get());
    }

    @Test
    public void getUserByLoginNotFountTest() throws IOException {
        HttpResponse getHttpResponseMock = mock(HttpResponse.class);
        HttpEntity httpEntityMock = mock(HttpEntity.class);
        InputStream inputStreamMock = mock(InputStream.class);

        StatusLine statusLineMock = mock(StatusLine.class);
        when(statusLineMock.getStatusCode()).thenReturn(404);
        when(getHttpResponseMock.getStatusLine()).thenReturn(statusLineMock);

        when(this.httpClientMock.execute(any(HttpGet.class))).thenReturn(getHttpResponseMock);
        when(getHttpResponseMock.getEntity()).thenReturn(httpEntityMock);
        when(httpEntityMock.getContent()).thenReturn(inputStreamMock);
        when(inputStreamMock.readAllBytes()).thenReturn(new byte[0]);

        Optional<UserApiEntity> response = this.oAuth2AuthorizerClient.getUserByLogin("login");
        assertFalse(response.isPresent());
    }

    @Test
    public void postUserTest() throws IOException, OAuth2AuthorizerClientException {
        UserApiEntity user = new UserApiEntity("login", "password");

        HttpResponse getHttpResponseMock = mock(HttpResponse.class);

        StatusLine statusLineMock = mock(StatusLine.class);
        when(statusLineMock.getStatusCode()).thenReturn(201);
        when(getHttpResponseMock.getStatusLine()).thenReturn(statusLineMock);

        HttpEntity entityMock = mock(HttpEntity.class);

        when(entityMock.getContent()).thenReturn(this.convertToInputStream(user));
        when(getHttpResponseMock.getEntity()).thenReturn(entityMock);

        when(this.httpClientMock.execute(any(HttpPost.class))).thenReturn(getHttpResponseMock);
        Optional<UserApiEntity> response = this.oAuth2AuthorizerClient.postUser(user);
        assertTrue(response.isPresent());
        assertEquals(user, response.get());
    }

    @Test(expected = OAuth2AuthorizerClientRuntimeException.class)
    public void postUserErrorTest() throws IOException, OAuth2AuthorizerClientException {
        UserApiEntity user = new UserApiEntity("login", "password");

        HttpResponse getHttpResponseMock = mock(HttpResponse.class);
        HttpEntity httpEntityMock = mock(HttpEntity.class);
        InputStream inputStreamMock = mock(InputStream.class);

        StatusLine statusLineMock = mock(StatusLine.class);
        when(statusLineMock.getStatusCode()).thenReturn(400);
        when(getHttpResponseMock.getStatusLine()).thenReturn(statusLineMock);
        when(getHttpResponseMock.getEntity()).thenReturn(httpEntityMock);
        when(httpEntityMock.getContent()).thenReturn(inputStreamMock);
        when(inputStreamMock.readAllBytes()).thenReturn(new byte[0]);

        when(this.httpClientMock.execute(any(HttpPost.class))).thenReturn(getHttpResponseMock);
        this.oAuth2AuthorizerClient.postUser(user);
    }

    private InputStream convertToInputStream(final Object object) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        this.objectMapper.writeValue(baos, object);
        return new ByteArrayInputStream(baos.toByteArray());
    }
}
