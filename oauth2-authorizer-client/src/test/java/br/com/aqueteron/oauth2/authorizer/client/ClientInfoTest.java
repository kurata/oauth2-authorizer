package br.com.aqueteron.oauth2.authorizer.client;

import org.junit.Test;

import static org.junit.Assert.*;

public class ClientInfoTest {

    @Test
    public void newClientInfoTest(){
        ClientInfo clientInfo = new ClientInfo();
        assertEquals("",clientInfo.getId());
        assertEquals("",clientInfo.getSecret());
        assertEquals("",clientInfo.getScope());
    }

    @Test
    public void newClientInfoWithIdAndSecretandScopeTest(){
        ClientInfo clientInfo = new ClientInfo("id","secret","scope");
        assertEquals("id",clientInfo.getId());
        assertEquals("secret",clientInfo.getSecret());
        assertEquals("scope",clientInfo.getScope());
    }

    @Test
    public void equalsTest() {
        ClientInfo clientInfo = new ClientInfo("id","secret","scope");
        assertTrue(clientInfo.equals(clientInfo));
        assertFalse(clientInfo.equals(new String()));
        assertTrue(clientInfo.equals(new ClientInfo("id","secret","scope")));
        assertFalse(clientInfo.equals(new ClientInfo()));
    }

    @Test
    public void hashCodeTest() {
        ClientInfo clientInfo = new ClientInfo("id","secret","scope");
        assertEquals(clientInfo.hashCode(), clientInfo.hashCode());
        assertEquals(clientInfo.hashCode(), new ClientInfo("id","secret","scope").hashCode());
        assertNotEquals(clientInfo.hashCode(), new ClientInfo().hashCode());
    }

    @Test
    public void toStringTest() {
        ClientInfo clientInfo = new ClientInfo();
        assertEquals("ClientInfo: { id:  , secret:  , scope:  }", clientInfo.toString());
        clientInfo = new ClientInfo("id","secret","scope");
        assertEquals("ClientInfo: { id: id , secret: ****** , scope: scope }", clientInfo.toString());
    }
}
