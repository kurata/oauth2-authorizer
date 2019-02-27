package br.com.aqueteron.oauth2.authorizer.client.integration;

//import br.com.aqueteron.oauth2.authorizer.client.JwtTokenClaims;
//import br.com.aqueteron.oauth2.authorizer.client.OAuth2AuthorizerClient;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.web.server.LocalServerPort;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import java.net.URISyntaxException;
//import java.util.HashSet;
//import java.util.Optional;
//import java.util.Set;
//
//import static junit.framework.TestCase.assertNull;
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertFalse;
//import static org.junit.Assert.assertTrue;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest(classes = IntegrationTestApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = {"spring.profiles.active=local"})
public class OAuth2AuthorizerClientIT {

//    @LocalServerPort
//    private int port;
//
//    private OAuth2AuthorizerClient oAuth2AuthorizerClient;
//
//    @Before
//    public void setUp() throws URISyntaxException {
//        this.oAuth2AuthorizerClient = new OAuth2AuthorizerClient("http","localhost",port);
//    }
//
//    @Test
//    public void decodeAndValidate() throws URISyntaxException {
//        Optional<JwtTokenClaims> result =  this.oAuth2AuthorizerClient.decodeAndVerify("eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOlsiZnJvbXRvLXJlc291cmNlIl0sInNjb3BlIjpbInJlYWQiLCJydW4iLCJ3cml0ZSJdLCJleHAiOjE1NDIxNDA0NzAsImF1dGhvcml0aWVzIjpbIlJPTEVfQVBQTElDQVRJT04iXSwianRpIjoiMTFmZGIyZDMtZmZmMS00YTAyLTk4ODQtYWZlODkwZjk1ZjI1IiwiY2xpZW50X2lkIjoiZnJvbVRvLmFwaS5hcXVldGVyb24uY29tLmJyIn0.DUSMxrYzecKnYylAzinMB3wHTlJPuJWg0ctcxaTPxA8yMkDVrbKxPEEkZJQ2ZaduTOQNTDd3uD0XomTIyiIh1oSAjC1V7A91VVuxSzGSuPRNGyMZ56KjjyMCxT-NgGOPRkFDRPH7FsOvfQc4qOfiYoc46oBBNgq4tRDMM2qxh3XiC69YxiYQXJZYiTi3EeFpy1x5Kp4D5pByLm2wHg0iXWi-lmaJF1M9WJxPSgE682q3M37bSWMIOFiiUkOkJ3v7Ns-xRQOCT9-kBa_tm-dc1s5zVMyeHZNaIb6_-EeKxmnJ6gBCoEFO-sAUpotavo4LbfUOKtOnYN6snZxMgL6cuw");
//
//        JwtTokenClaims claims = result.get();
//
//        Set<String> audienceSet = new HashSet<>();
//        audienceSet.add("fromto-resource");
//
//        Set<String> scopeSet = new HashSet<>();
//        scopeSet.add("read");
//        scopeSet.add("run");
//        scopeSet.add("write");
//
//        Set<String> authorities = new HashSet<>();
//        authorities.add("ROLE_APPLICATION");
//
//        assertTrue(result.isPresent());
//        assertEquals("11fdb2d3-fff1-4a02-9884-afe890f95f25",claims.getJwtId());
//        assertEquals(audienceSet,claims.getAudience());
//        assertNull(claims.getUserName());
//        assertEquals(scopeSet,claims.getScope());
//        assertEquals(new Long(1542140470),claims.getExpirationTime());
//        assertNull(claims.getActive());
//        assertEquals(authorities,claims.getAuthorities());
//        assertEquals("fromTo.api.aqueteron.com.br",claims.getClientId());
//    }
//
//    @Test
//    public void decodeAndValidateNotSign() throws URISyntaxException {
//        Optional<JwtTokenClaims> result =  this.oAuth2AuthorizerClient.decodeAndVerify("eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOlsiZnJvbXRvLXJlc291cmNlIl0sInNjb3BlIjpbInJlYWQiLCJydW4iLCJ3cml0ZSJdLCJleHAiOjE1NDIxNDA0NzAsImF1dGhvcml0aWVzIjpbIlJPTEVfQVBQTElDQVRJT04iXSwianRpIjoiMTFmZGIyZDMtZmZmMS00YTAyLTk4ODQtYWZlODkwZjk1ZjI1IiwiY2xpZW50X2lkIjoiZnJvbVRvLmFwaS5hcXVldGVyb24uY29tLmJyIn0");
//        assertFalse(result.isPresent());
//    }
//
//    @Test
//    public void decodeAndValidateInvalid() throws URISyntaxException {
//        Optional<JwtTokenClaims> result =  this.oAuth2AuthorizerClient.decodeAndVerify("eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOlsiZnJvbXRvLXJlc291cmNlIl0sInNjb3BlIjpbInJlYWQiLCJydW4iLCJ3cml0ZSJdLCJleHAiOjE1NDIxNDA0NzAsImF1dGhvcml0aWVzIjpbIlJPTEVfQVBQTElDQVRJT04iXSwianRpIjoiMTFmZGIyZDMtZmZmMS00YTAyLTk4ODQtYWZlODkwZjk1ZjI1IiwiY2xpZW50X2lkIjoiZnJvbVRvLmFwaS5hcXVldGVyb24uY29tLmJyIn0.invalid_sign");
//        assertFalse(result.isPresent());
//    }
}
