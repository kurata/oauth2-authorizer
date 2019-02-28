package br.com.aqueteron.oauth2.authorizer.security;

import org.junit.Test;

import static org.junit.Assert.*;

public class ApiResourceDefinitionTest {

    @Test
    public void baseTest() {
        ApiResourceDefinition apiResourceDefinition = new ApiResourceDefinition("method", "path");
        assertEquals("method", apiResourceDefinition.getMethod());
        assertEquals("path", apiResourceDefinition.getPath());
    }

    @Test
    public void equalsTest(){
        ApiResourceDefinition apiResourceDefinition = new ApiResourceDefinition("method", "path");
        assertTrue(apiResourceDefinition.equals(apiResourceDefinition));
        assertTrue(apiResourceDefinition.equals(new ApiResourceDefinition("method", "path")));
        assertFalse(apiResourceDefinition.equals(new String()));
        assertFalse(apiResourceDefinition.equals(new ApiResourceDefinition("otherMehod", "otherPath")));
        assertFalse(apiResourceDefinition.equals(new ApiResourceDefinition("method", "otherPath")));
        assertFalse(apiResourceDefinition.equals(new ApiResourceDefinition("otherMehod", "path")));
    }

    @Test
    public void hashCodeTest(){
        ApiResourceDefinition apiResourceDefinition = new ApiResourceDefinition("method", "path");
        assertEquals(apiResourceDefinition.hashCode(), apiResourceDefinition.hashCode());
        assertEquals(new ApiResourceDefinition("method", "path").hashCode(), apiResourceDefinition.hashCode());
    }
}
