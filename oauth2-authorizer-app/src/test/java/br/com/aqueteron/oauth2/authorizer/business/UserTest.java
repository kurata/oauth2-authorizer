package br.com.aqueteron.oauth2.authorizer.business;

import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class UserTest {

    @Test
    public void newUserTest() {
        User user = new User();
        assertNull(user.getLogin());
        assertNull(user.getPassword());
    }

    @Test
    public void newUserWithLoginTest() {
        User user = new User("login");
        assertEquals("login", user.getLogin());
    }

    @Test
    public void newUserWithOtherUser() {
        User userOriginal = new User("login");
        userOriginal.setPassword("password");
        userOriginal.setEnabled(Boolean.TRUE);
        userOriginal.setRoles(new HashSet<>());

        User newUser = new User(userOriginal);
        assertEquals(userOriginal.getLogin(), newUser.getLogin());
        assertEquals(userOriginal.getPassword(), newUser.getPassword());
        assertEquals(userOriginal.getEnabled(), newUser.getEnabled());
        assertEquals(userOriginal.getRoles(), newUser.getRoles());
    }

    @Test
    public void setPasswordTest() {
        User user = new User();
        user.setPassword("newPassword");
        assertEquals("newPassword", user.getPassword());
    }

    @Test
    public void setEnabledTest() {
        User user = new User();
        user.setEnabled(Boolean.TRUE);
        assertTrue(user.getEnabled());
    }

    @Test
    public void setRoleSetTest() {
        Set<String> roleSet = new HashSet<>();
        roleSet.add("roleName");
        User user = new User();
        user.setRoles(roleSet);
        assertEquals(roleSet, user.getRoles());
    }

    @Test
    public void addRoleTest() {
        Set<String> roleSet = new HashSet<>();
        roleSet.add("roleName");

        User user = new User();
        user.addRole("roleName");
        assertEquals(roleSet, user.getRoles());

    }

    @Test
    public void equalsTest() {
        User user = defaultUser();
        assertTrue(user.equals(user));
        assertFalse(user.equals(new String()));
        assertFalse(user.equals(new User("otherLogin")));
        assertTrue(user.equals(defaultUser()));
        User otherUser = defaultUser();
        otherUser.setEnabled(Boolean.FALSE);
        assertFalse(user.equals(otherUser));
        otherUser = defaultUser();
        otherUser.setPassword("newPassword");
        assertFalse(user.equals(otherUser));
        otherUser = defaultUser();
        otherUser.setRoles(null);
        assertFalse(user.equals(otherUser));
    }
    
    public User defaultUser() {
        User user = new User("login");
        user.setEnabled(Boolean.TRUE);
        user.setPassword("password");
    	return user;
    }

    @Test
    public void hashCodeTest() {
        User user = new User("login");
        assertEquals(user.hashCode(), user.hashCode());
        assertEquals(user.hashCode(), new User("login").hashCode());
        assertFalse(user.hashCode() == new User("otherLogin").hashCode());
    }

    @Test
    public void compareToTest() {
        User user = new User("login");
        assertEquals(0, user.compareTo(user));
        assertEquals(0, user.compareTo(new User("login")));
        assertEquals(-3, user.compareTo(new User("otherLogin")));
    }

    @Test
    public void toStringTest() {
        User user = new User("login");
        assertEquals("User: { login: login , password: null , isEnabled: null , roles: [] }", user.toString());
        user.setPassword("password");
        assertEquals("User: { login: login , password: ******** , isEnabled: null , roles: [] }", user.toString());
    }
}
