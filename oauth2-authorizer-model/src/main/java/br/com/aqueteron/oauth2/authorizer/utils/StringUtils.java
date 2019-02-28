package br.com.aqueteron.oauth2.authorizer.utils;

import java.util.stream.Collectors;

public class StringUtils {

    private StringUtils(){
    }

    public static String mask(final String string){
        if( string != null){
            return string.chars().mapToObj(i -> "*").collect(Collectors.joining());
        }
        return "null";
    }
}
