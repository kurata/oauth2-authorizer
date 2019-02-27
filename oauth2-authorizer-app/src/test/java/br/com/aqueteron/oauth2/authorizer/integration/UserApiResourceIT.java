package br.com.aqueteron.oauth2.authorizer.integration;

import br.com.aqueteron.oauth2.authorizer.OAuth2AuthorizerApplication;
import br.com.aqueteron.oauth2.authorizer.model.UserApiEntity;
import org.apache.http.impl.client.HttpClients;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = OAuth2AuthorizerApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = {"spring.profiles.active=local"})
public class UserApiResourceIT {

    @LocalServerPort
    private int port;

    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }

    private RestTemplate restTemplate;

    @Before
    public void setUp() {
        ClientHttpRequestFactory requestFactory = new
                HttpComponentsClientHttpRequestFactory(HttpClients.createDefault());

        this.restTemplate = new RestTemplate(requestFactory);
    }

    @Test
    public void options() {
        HttpEntity<String> entity = new HttpEntity<String>(null, new HttpHeaders());

        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/api/user"),
                HttpMethod.OPTIONS, entity, String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    public OAuth2Token authenticate() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/x-www-form-urlencoded");

        HttpEntity<String> entity = new HttpEntity("grant_type=client_credentials&scope=read write run&client_id=fromTo.api.aqueteron.com.br&client_secret=secret", headers);

        ResponseEntity<OAuth2Token> response = restTemplate.exchange(createURLWithPort("oauth/token"), HttpMethod.POST, entity, OAuth2Token.class);
        return response.getBody();
    }

    public HttpHeaders authenticatedHeaders(final OAuth2Token oAuth2Token) {
        HttpHeaders httpHeaders = new HttpHeaders();

        System.out.println("AccessToken: Bearer " + oAuth2Token.getAccessToken());
        httpHeaders.add("Authorization", "Bearer " + oAuth2Token.getAccessToken());

        return httpHeaders;
    }

    @Test
    public void getUserSet() {
        OAuth2Token oAuth2Token = authenticate();
        HttpEntity<String> httpEntity = new HttpEntity<>(null, authenticatedHeaders(oAuth2Token));

        ResponseEntity<UserApiEntity[]> response = this.restTemplate.exchange(
                createURLWithPort("/api/user"),
                HttpMethod.GET, httpEntity, UserApiEntity[].class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        List<UserApiEntity> userList = Arrays.asList(response.getBody());
        assertFalse(userList.isEmpty());
        UserApiEntity userExpected = new UserApiEntity("user","{bcrypt}$2a$10$HK0Xa/MM6HGp6JC8SajkJ.78.3Z4L1EpAoG6TlqZVjB.LaKpj/nxe");
        assertEquals(userExpected,userList.get(0));
    }

    @Test
    public void postUser() {
        UserApiEntity userApiEntity = new UserApiEntity("newUser" , "password");
        HttpEntity<UserApiEntity> httpEntity = new HttpEntity<>(userApiEntity,new HttpHeaders());

        ResponseEntity<UserApiEntity> response = this.restTemplate.exchange(
                createURLWithPort("/api/user"),
                HttpMethod.POST, httpEntity, UserApiEntity.class);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        List<UserApiEntity> userList = Arrays.asList(response.getBody());
        assertFalse(userList.isEmpty());
        UserApiEntity userApiResponse = response.getBody();
        assertNotNull(userApiResponse);
        assertEquals(userApiEntity.getLogin(),userApiResponse.getLogin());
    }
//
//    @Test
//    public void getUserSetNotResult() {
//        SecurityContext securityContextMock = mock(SecurityContext.class);
//
//        Set<User> userSet = new HashSet();
//
//        when(this.userServiceMock.findAll()).thenReturn(userSet);
//
//        Response response = this.userApiResource.getUserSet(securityContextMock);
//        assertEquals(200, response.getStatus());
//        assertNotNull(response.getEntity());
//    }
//
//    @Test
//    public void getUser() {
//        SecurityContext securityContextMock = mock(SecurityContext.class);
//
//        String login = "login";
//        User user = new User(login);
//
//        when(this.userServiceMock.findById(eq(login))).thenReturn(Optional.of(user));
//
//        Response response = this.userApiResource.getUser(login, securityContextMock);
//        assertEquals(200, response.getStatus());
//        assertNotNull(response.getEntity());
//        assertEquals(new UserApiEntity("login", null), response.getEntity());
//    }
//
//    @Test
//    public void getUserNotFound() {
//        SecurityContext securityContextMock = mock(SecurityContext.class);
//
//        String login = "login";
//
//        when(this.userServiceMock.findById(eq(login))).thenReturn(Optional.empty());
//
//        Response response = this.userApiResource.getUser(login, securityContextMock);
//        assertEquals(404, response.getStatus());
//        assertNull(response.getEntity());
//    }
}
