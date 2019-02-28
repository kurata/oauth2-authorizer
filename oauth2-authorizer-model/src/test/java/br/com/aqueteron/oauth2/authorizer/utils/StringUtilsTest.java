package br.com.aqueteron.oauth2.authorizer.utils;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class StringUtilsTest {

    @Test
    public void mask() {
        assertEquals("********", StringUtils.mask("password"));
        assertEquals("null", StringUtils.mask(null));
    }
}
