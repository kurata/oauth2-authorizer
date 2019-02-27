package br.com.aqueteron.oauth2.authorizer.client;

import org.junit.Test;

import static org.junit.Assert.*;

public class ServerInfoTest {

    @Test
    public void newServerInfoTest() {
        ServerInfo serverInfo = new ServerInfo();
        assertEquals("http",serverInfo.getScheme());
        assertEquals("localhost",serverInfo.getHost());
        assertEquals(new Integer(8080),serverInfo.getPort());
    }

    @Test
    public void newServerInfoWithSchemeHostAndPortTest() {
        ServerInfo serverInfo = new ServerInfo("https","host",8443);
        assertEquals("https",serverInfo.getScheme());
        assertEquals("host",serverInfo.getHost());
        assertEquals(new Integer(8443),serverInfo.getPort());
    }

    @Test
    public void equalsTest() {
        ServerInfo serverInfo = new ServerInfo("https","host",8443);
        assertTrue(serverInfo.equals(serverInfo));
        assertFalse(serverInfo.equals(new String()));
        assertTrue(serverInfo.equals(new ServerInfo("https","host",8443)));
        assertFalse(serverInfo.equals(new ServerInfo()));
    }

    @Test
    public void hashCodeTest() {
        ServerInfo serverInfo = new ServerInfo("https","host",8443);
        assertEquals(serverInfo.hashCode(), serverInfo.hashCode());
        assertEquals(serverInfo.hashCode(), new ServerInfo("https","host",8443).hashCode());
        assertNotEquals(serverInfo.hashCode(), new ServerInfo().hashCode());
    }

    @Test
    public void toStringTest() {
        ServerInfo serverInfo = new ServerInfo();
        assertEquals("ServerInfo: { scheme: http , host: localhost , port: 8080 }", serverInfo.toString());
    }
}
