package br.com.aqueteron.oauth2.authorizer.client;

import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;
import static org.junit.Assert.assertFalse;

public class JwtTokenClaimsTest {

    @Test
    public void newJwtTokenClaimsTest() {
        JwtTokenClaims jwtTokenClaims = new JwtTokenClaims();
        assertNull(jwtTokenClaims.getActive());
        assertNull(jwtTokenClaims.getAudience());
        assertNull(jwtTokenClaims.getAuthorities());
        assertNull(jwtTokenClaims.getClientId());
        assertNull(jwtTokenClaims.getExpirationTime());
        assertNull(jwtTokenClaims.getScope());
        assertNull(jwtTokenClaims.getUserName());
        assertEquals("", jwtTokenClaims.getJwtId());
    }

    @Test
    public void newJwtTokenClaimsWithJwtIdTest() {
        JwtTokenClaims jwtTokenClaims = new JwtTokenClaims("jwtId");
        assertNull(jwtTokenClaims.getActive());
        assertNull(jwtTokenClaims.getAudience());
        assertNull(jwtTokenClaims.getAuthorities());
        assertNull(jwtTokenClaims.getClientId());
        assertNull(jwtTokenClaims.getExpirationTime());
        assertNull(jwtTokenClaims.getScope());
        assertNull(jwtTokenClaims.getUserName());
        assertEquals("jwtId", jwtTokenClaims.getJwtId());
    }

    @Test
    public void setActiveTest() {
        JwtTokenClaims jwtTokenClaims = new JwtTokenClaims();
        jwtTokenClaims.setActive(Boolean.TRUE);
        assertTrue(jwtTokenClaims.getActive());
    }

    @Test
    public void setAudienceTest() {
        JwtTokenClaims jwtTokenClaims = new JwtTokenClaims();
        Set<String> audience = new HashSet<>();
        jwtTokenClaims.setAudience(audience);
        assertEquals(audience, jwtTokenClaims.getAudience());
    }

    @Test
    public void setAuthoritiesTest() {
        JwtTokenClaims jwtTokenClaims = new JwtTokenClaims();
        Set<String> authorities = new HashSet<>();
        jwtTokenClaims.setAuthorities(authorities);
        assertEquals(authorities, jwtTokenClaims.getAuthorities());
    }

    @Test
    public void setClientIdTest() {
        JwtTokenClaims jwtTokenClaims = new JwtTokenClaims();
        String clientId = "clientId";
        jwtTokenClaims.setClientId(clientId);
        assertEquals(clientId, jwtTokenClaims.getClientId());
    }

    @Test
    public void setExpirationTimeTest() {
        JwtTokenClaims jwtTokenClaims = new JwtTokenClaims();
        Long expirationTime = 100L;
        jwtTokenClaims.setExpirationTime(expirationTime);
        assertEquals(expirationTime, jwtTokenClaims.getExpirationTime());
    }

    @Test
    public void setScopeTest() {
        JwtTokenClaims jwtTokenClaims = new JwtTokenClaims();
        Set<String> scope = new HashSet<>();
        jwtTokenClaims.setScope(scope);
        assertEquals(scope, jwtTokenClaims.getScope());
    }

    @Test
    public void setUserNameTest() {
        JwtTokenClaims jwtTokenClaims = new JwtTokenClaims();
        String userName = "userName";
        jwtTokenClaims.setUserName(userName);
        assertEquals(userName, jwtTokenClaims.getUserName());
    }

    @Test
    public void equalsTest() {
        JwtTokenClaims jwtTokenClaims = new JwtTokenClaims("jwtId");
        assertTrue(jwtTokenClaims.equals(jwtTokenClaims));
        assertFalse(jwtTokenClaims.equals(new String()));
        assertTrue(jwtTokenClaims.equals(new JwtTokenClaims("jwtId")));
        assertFalse(jwtTokenClaims.equals(new JwtTokenClaims()));
    }

    @Test
    public void hashCodeTest() {
        JwtTokenClaims jwtTokenClaims = new JwtTokenClaims("jwtId");
        assertEquals(jwtTokenClaims.hashCode(), jwtTokenClaims.hashCode());
        assertEquals(jwtTokenClaims.hashCode(), new JwtTokenClaims("jwtId").hashCode());
        assertNotEquals(jwtTokenClaims.hashCode(), new JwtTokenClaims().hashCode());
    }

    @Test
    public void toStringTest() {
        JwtTokenClaims jwtTokenClaims = new JwtTokenClaims();
        assertEquals("JwtTokenClaims: { jwtId:  , audience: null , userName: null , scope: null , active: null , expirationTime: null , authorities: null , clientId: null }", jwtTokenClaims.toString());
        jwtTokenClaims = new JwtTokenClaims("jwtId");
        assertEquals("JwtTokenClaims: { jwtId: jwtId , audience: null , userName: null , scope: null , active: null , expirationTime: null , authorities: null , clientId: null }", jwtTokenClaims.toString());
        jwtTokenClaims.setActive(Boolean.TRUE);
        Set<String> audience = new HashSet<>();
        jwtTokenClaims.setAudience(audience);
        Set<String> authorities = new HashSet<>();
        jwtTokenClaims.setAuthorities(authorities);
        String clientId = "clientId";
        jwtTokenClaims.setClientId(clientId);
        Long expirationTime = 100L;
        jwtTokenClaims.setExpirationTime(expirationTime);
        Set<String> scope = new HashSet<>();
        jwtTokenClaims.setScope(scope);
        String userName = "userName";
        jwtTokenClaims.setUserName(userName);
        assertEquals("JwtTokenClaims: { jwtId: jwtId , audience: [] , userName: userName , scope: [] , active: true , expirationTime: 100 , authorities: [] , clientId: clientId }", jwtTokenClaims.toString());
    }
}
