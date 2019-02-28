package br.com.aqueteron.oauth2.authorizer.api;

import org.junit.Test;

import javax.ws.rs.core.Response;

import static org.junit.Assert.*;

public class ErrorApiEntityTest {

    @Test
    public void newErrorApiEntityTest() {
        ErrorApiEntity errorApiEntity = new ErrorApiEntity(Response.Status.BAD_REQUEST , "message");

        assertEquals(Response.Status.BAD_REQUEST , errorApiEntity.getStatus());
        assertEquals(Integer.valueOf(400),errorApiEntity.getCode());
        assertEquals("message",errorApiEntity.getMessage());
        assertNotNull(errorApiEntity.getTimestamp());
        assertTrue(errorApiEntity.getDevelopMessage().isEmpty());
    }

    @Test
    public void newErrorApiEntityWithThrowableTest() {
        ErrorApiEntity errorApiEntity = new ErrorApiEntity(Response.Status.BAD_REQUEST , "message" , new RuntimeException("RuntimeException message"));

        assertEquals(Response.Status.BAD_REQUEST , errorApiEntity.getStatus());
        assertEquals(Integer.valueOf(400),errorApiEntity.getCode());
        assertEquals("message",errorApiEntity.getMessage());
        assertNotNull(errorApiEntity.getTimestamp());
        assertFalse(errorApiEntity.getDevelopMessage().isEmpty());
    }
}
