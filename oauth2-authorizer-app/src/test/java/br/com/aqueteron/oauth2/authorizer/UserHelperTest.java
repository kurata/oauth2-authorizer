package br.com.aqueteron.oauth2.authorizer;

import br.com.aqueteron.oauth2.authorizer.business.User;
import br.com.aqueteron.oauth2.authorizer.model.UserApiEntity;
import org.junit.Test;

import static org.junit.Assert.*;

public class UserHelperTest {
	
    @Test
    public void loadUser() {
        UserApiEntity userApiEntity = new UserApiEntity("login", "password");
        User user = UserHelper.loadUser(userApiEntity);
        assertEquals(userApiEntity.getLogin(), user.getLogin());
        assertEquals(userApiEntity.getPassword(), user.getPassword());
        assertNull(user.getEnabled());
        assertNotNull(user.getRoles());
        assertTrue(user.getRoles().isEmpty());
    }

    @Test
    public void loadUserResource() {
        User user = new User("login");
        user.setPassword("password");
        UserApiEntity userApiEntity = UserHelper.loadUserResource(user);
        assertEquals(user.getLogin(), userApiEntity.getLogin());
        assertEquals(user.getPassword(), userApiEntity.getPassword());
    }
}
