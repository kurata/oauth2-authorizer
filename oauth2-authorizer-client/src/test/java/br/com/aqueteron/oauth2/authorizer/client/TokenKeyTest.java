package br.com.aqueteron.oauth2.authorizer.client;

import org.junit.Test;

import static org.junit.Assert.*;

public class TokenKeyTest {

    @Test
    public void newTokenKey() {
        TokenKey tokenKey = new TokenKey("algorithm", "publicKey");
        assertEquals("algorithm", tokenKey.getAlgorithm());
        assertEquals("publicKey", tokenKey.getPublicKey());
    }

    @Test
    public void equalsTest() {
        TokenKey tokenKey = new TokenKey("algorithm", "publicKey");
        assertTrue(tokenKey.equals(tokenKey));
        assertFalse(tokenKey.equals(new String()));
        assertTrue(tokenKey.equals(new TokenKey("algorithm", "publicKey")));
        assertFalse(tokenKey.equals(new TokenKey("otherAlgorithm", "publicKey")));
        assertFalse(tokenKey.equals(new TokenKey("algorithm", "otherPublicKey")));
        assertFalse(tokenKey.equals(new TokenKey("otherAlgorithm", "otherPublicKey")));
    }

    @Test
    public void hashCodeTest() {
        TokenKey tokenKey = new TokenKey("algorithm", "publicKey");
        assertEquals(tokenKey.hashCode(), tokenKey.hashCode());
        assertEquals(tokenKey.hashCode(), new TokenKey("algorithm", "publicKey").hashCode());
        assertNotEquals(tokenKey.hashCode(), new TokenKey("otherAlgorithm", "publicKey").hashCode());
        assertNotEquals(tokenKey.hashCode(), new TokenKey("algorithm", "otherPublicKey").hashCode());
        assertNotEquals(tokenKey.hashCode(), new TokenKey("otherAlgorithm", "otherPublicKey").hashCode());
    }

    @Test
    public void toStringTest() {
        TokenKey tokenKey = new TokenKey();
        assertEquals("TokenKey: { algorithm:  , publicKey:  }", tokenKey.toString());
        tokenKey = new TokenKey("algorithm", "publicKey");
        assertEquals("TokenKey: { algorithm: algorithm , publicKey: publicKey }", tokenKey.toString());
    }
}
