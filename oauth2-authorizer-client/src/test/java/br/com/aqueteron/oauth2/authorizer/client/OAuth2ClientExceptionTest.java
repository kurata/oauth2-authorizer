package br.com.aqueteron.oauth2.authorizer.client;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class OAuth2ClientExceptionTest {

    @Test
    public void constructor() {
        OAuth2AuthorizerClientException exception = new OAuth2AuthorizerClientException();
        assertEquals(null, exception.getMessage());
        assertEquals(null, exception.getCause());
        assertEquals(null, exception.getLocalizedMessage());
    }

    @Test
    public void constructorWithMessage() {
        OAuth2AuthorizerClientException exception = new OAuth2AuthorizerClientException("message");
        assertEquals("message", exception.getMessage());
        assertEquals(null, exception.getCause());
        assertEquals("message", exception.getLocalizedMessage());
    }

    @Test
    public void constructorWithMessageAndThrowable() {
        Throwable throwable = new Throwable();
        OAuth2AuthorizerClientException exception = new OAuth2AuthorizerClientException("message", throwable);
        assertEquals("message", exception.getMessage());
        assertEquals(throwable, exception.getCause());
        assertEquals("message", exception.getLocalizedMessage());
    }

    @Test
    public void constructorWithThrowable() {
        Throwable throwable = new Throwable();
        OAuth2AuthorizerClientException exception = new OAuth2AuthorizerClientException(throwable);
        assertEquals("java.lang.Throwable", exception.getMessage());
        assertEquals(throwable, exception.getCause());
        assertEquals("java.lang.Throwable", exception.getLocalizedMessage());
    }
}
