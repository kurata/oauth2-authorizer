package br.com.aqueteron.oauth2.authorizer.client;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class OAuth2ClientRuntimeExceptionTest {

    @Test
    public void constructorWithMessage() {
        OAuth2AuthorizerClientRuntimeException exception = new OAuth2AuthorizerClientRuntimeException("message");
        assertEquals("message", exception.getMessage());
        assertEquals(null, exception.getCause());
        assertEquals("message", exception.getLocalizedMessage());
    }

    @Test
    public void constructorWithMessageAndThrowable() {
        Throwable throwable = new Throwable();
        OAuth2AuthorizerClientRuntimeException exception = new OAuth2AuthorizerClientRuntimeException("message", throwable);
        assertEquals("message", exception.getMessage());
        assertEquals(throwable, exception.getCause());
        assertEquals("message", exception.getLocalizedMessage());
    }

    @Test
    public void constructorWithThrowable() {
        Throwable throwable = new Throwable();
        OAuth2AuthorizerClientRuntimeException exception = new OAuth2AuthorizerClientRuntimeException(throwable);
        assertEquals("java.lang.Throwable", exception.getMessage());
        assertEquals(throwable, exception.getCause());
        assertEquals("java.lang.Throwable", exception.getLocalizedMessage());
    }
}
