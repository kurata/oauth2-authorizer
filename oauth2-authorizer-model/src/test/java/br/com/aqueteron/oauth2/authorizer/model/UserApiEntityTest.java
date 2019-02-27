package br.com.aqueteron.oauth2.authorizer.model;

import org.junit.Test;

import static org.junit.Assert.*;

public class UserApiEntityTest {

    @Test
    public void newUserResourceTest() {
        UserApiEntity user = new UserApiEntity();
        assertNull(user.getLogin());
        assertNull(user.getPassword());
    }

    @Test
    public void newUserResourceWithLoginAndPasswordTest() {
        UserApiEntity user = new UserApiEntity("login", "password");
        assertEquals("login", user.getLogin());
        assertEquals("password", user.getPassword());
    }

    @Test
    public void setPasswordTest(){
        UserApiEntity user = new UserApiEntity();
        user.setPassword("newPassword");
        assertEquals("newPassword", user.getPassword());
    }

    @Test
    public void equalsTest() {
        UserApiEntity user = new UserApiEntity("login","password");
        assertTrue(user.equals(user));
        assertFalse(user.equals(new String()));
        assertFalse(user.equals(new UserApiEntity("otherLogin" , "otherPassword")));
        assertFalse(user.equals(new UserApiEntity("login" , "otherPassword")));
        assertFalse(user.equals(new UserApiEntity("otherLogin" , "password")));
        assertTrue(user.equals(new UserApiEntity("login" , "password")));
    }

    @Test
    public void hashCodeTest(){
        UserApiEntity user = new UserApiEntity("login","password");
        assertEquals(user.hashCode(),user.hashCode());
        assertEquals(user.hashCode(),new UserApiEntity("login" , "otherPassword").hashCode());
        assertFalse(user.hashCode() == new UserApiEntity("otherLogin" , "otherPassword").hashCode());
        assertFalse(user.hashCode() == new UserApiEntity("otherLogin" , "password").hashCode());
    }

    @Test
    public void compareToTest() {
        UserApiEntity user = new UserApiEntity("login","password");
        assertEquals(0 , user.compareTo(user));
        assertEquals(0 , user.compareTo(new UserApiEntity("login" , "otherPassword")));
        assertEquals(-3 , user.compareTo(new UserApiEntity("otherLogin" , "otherPassword")));
        assertEquals(-3 , user.compareTo(new UserApiEntity("otherLogin" , "otherPassword")));
    }

    @Test
    public void toStringTest(){
        UserApiEntity user = new UserApiEntity("login","password");
        assertEquals("UserApiEntity: { login: login , password: ******** }" , user.toString());
        user = new UserApiEntity("login",null);
        assertEquals("UserApiEntity: { login: login , password: null }" , user.toString());
    }
}
