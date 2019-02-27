package br.com.aqueteron.oauth2.authorizer.client;

import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

public class AccessTokenTest {

    @Test
    public void newAccessTokenTest() {
        AccessToken accessToken = new AccessToken();
        assertEquals("", accessToken.getJti());
        assertEquals("", accessToken.getValue());
        assertNull(accessToken.getExpiresIn());
        assertNull(accessToken.getScope());
        assertNull(accessToken.getType());
    }

    @Test
    public void newAccessTokenWithJtiAndValueTest() {
        AccessToken accessToken = new AccessToken("jti", "value");
        assertEquals("jti", accessToken.getJti());
        assertEquals("value", accessToken.getValue());
        assertNull(accessToken.getExpiresIn());
        assertNull(accessToken.getScope());
        assertNull(accessToken.getType());
    }

    @Test
    public void setExpiresInTest() {
        AccessToken accessToken = new AccessToken();
        accessToken.setExpiresIn(100L);
        assertEquals(new Long(100), accessToken.getExpiresIn());
    }

    @Test
    public void setScopeTest() {
        AccessToken accessToken = new AccessToken();
        String scope = "scope";
        accessToken.setScope(scope);
        assertEquals(scope, accessToken.getScope());
    }

    @Test
    public void setTypeTest() {
        AccessToken accessToken = new AccessToken();
        String userName = "type";
        accessToken.setType(userName);
        assertEquals(userName, accessToken.getType());
    }

    @Test
    public void equalsTest() {
        AccessToken accessToken = new AccessToken("jti", "value");
        assertTrue(accessToken.equals(accessToken));
        assertFalse(accessToken.equals(new String()));
        assertTrue(accessToken.equals(new AccessToken("jti", "value")));
        assertFalse(accessToken.equals(new AccessToken()));
    }

    @Test
    public void hashCodeTest() {
        AccessToken accessToken = new AccessToken("jti", "value");
        assertEquals(accessToken.hashCode(), accessToken.hashCode());
        assertEquals(accessToken.hashCode(), new AccessToken("jti", "value").hashCode());
        assertNotEquals(accessToken.hashCode(), new AccessToken().hashCode());
    }

    @Test
    public void toStringTest() {
        AccessToken accessToken = new AccessToken();
        assertEquals("AccessToken: { jti:  , value:  , type: null , expiresIn: null , scope: null }", accessToken.toString());
        accessToken = new AccessToken("jti", "value");
        assertEquals("AccessToken: { jti: jti , value: value , type: null , expiresIn: null , scope: null }", accessToken.toString());
    }
}
