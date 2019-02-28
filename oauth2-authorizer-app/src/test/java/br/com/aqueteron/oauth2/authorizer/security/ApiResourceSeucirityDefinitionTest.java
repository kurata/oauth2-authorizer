package br.com.aqueteron.oauth2.authorizer.security;

import org.junit.Test;

import static org.junit.Assert.*;

public class ApiResourceSeucirityDefinitionTest {

    @Test
    public void getPathRegex(){
        ApiResourceSecurityDefinition resourceSecurityDefinition = new ApiResourceSecurityDefinition("GET","user/{id}/info");
        System.out.println(resourceSecurityDefinition.getPathRegex());
        assertEquals("user/.*/info" , resourceSecurityDefinition.getPathRegex());
        assertTrue("user/test52/info".matches(resourceSecurityDefinition.getPathRegex()));
    }

    @Test
    public void equalsTest(){
        ApiResourceSecurityDefinition apiResourceSecurityDefinition = new ApiResourceSecurityDefinition("method", "path", "scope");
        assertTrue(apiResourceSecurityDefinition.equals(apiResourceSecurityDefinition));
        assertFalse(apiResourceSecurityDefinition.equals(new String()));
        assertFalse(apiResourceSecurityDefinition.equals(new ApiResourceSecurityDefinition("otherMethod", "otherPath", "scope")));
        assertTrue(apiResourceSecurityDefinition.equals(new ApiResourceSecurityDefinition("method", "path", "scope")));
        assertFalse(apiResourceSecurityDefinition.equals(new ApiResourceSecurityDefinition("method", "path", "otherScope")));
    }

    @Test
    public void hashCodeTest(){
        ApiResourceSecurityDefinition apiResourceSecurityDefinition = new ApiResourceSecurityDefinition("method", "path", "scope");
        assertEquals(apiResourceSecurityDefinition.hashCode(), apiResourceSecurityDefinition.hashCode());
        assertEquals(apiResourceSecurityDefinition.hashCode(), apiResourceSecurityDefinition.hashCode());
        assertEquals(new ApiResourceSecurityDefinition("method", "path", "scope").hashCode(), apiResourceSecurityDefinition.hashCode());
    }
}
