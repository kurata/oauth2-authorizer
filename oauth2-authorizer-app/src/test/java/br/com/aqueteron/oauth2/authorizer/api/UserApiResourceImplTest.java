package br.com.aqueteron.oauth2.authorizer.api;

import br.com.aqueteron.oauth2.authorizer.api.UserApiResourceImpl;
import br.com.aqueteron.oauth2.authorizer.business.User;
import br.com.aqueteron.oauth2.authorizer.business.UserService;
import br.com.aqueteron.oauth2.authorizer.model.UserApiEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserApiResourceImplTest {

    @Mock
    private UserService userServiceMock;

    @InjectMocks
    private UserApiResourceImpl userApiResource;

    @Test
    public void options() {
        Response response = this.userApiResource.options();
        assertEquals(200, response.getStatus());
    }

    @Test
    public void getUserSet() {
        SecurityContext securityContextMock = mock(SecurityContext.class);

        Set<User> userSet = new HashSet();
        User user = new User("login");
        userSet.add(user);

        when(this.userServiceMock.findAll()).thenReturn(userSet);

        Response response = this.userApiResource.getUserSet(securityContextMock);
        assertEquals(200, response.getStatus());
        assertNotNull(response.getEntity());

        Set<UserApiEntity> responseEntity = new HashSet<>();
        responseEntity.add(new UserApiEntity("login", null));
        assertEquals(responseEntity, response.getEntity());
    }

    @Test
    public void getUserSetNotResult() {
        SecurityContext securityContextMock = mock(SecurityContext.class);

        Set<User> userSet = new HashSet();

        when(this.userServiceMock.findAll()).thenReturn(userSet);

        Response response = this.userApiResource.getUserSet(securityContextMock);
        assertEquals(200, response.getStatus());
        assertNotNull(response.getEntity());
    }

    @Test
    public void postUser() {
        UserApiEntity newUserApiEntity = new UserApiEntity("login", "password");
        SecurityContext securityContextMock = mock(SecurityContext.class);

        User user = new User("login");
        user.setPassword("password");

        when(this.userServiceMock.createUser(any(User.class))).thenReturn(user);

        Response response = this.userApiResource.postUser(newUserApiEntity, securityContextMock);
        assertEquals(201, response.getStatus());
        assertNotNull(response.getEntity());
        assertEquals(new UserApiEntity("login", "password"), response.getEntity());
    }

    @Test
    public void getUser() {
        SecurityContext securityContextMock = mock(SecurityContext.class);

        String login = "login";
        User user = new User(login);

        when(this.userServiceMock.findById(eq(login))).thenReturn(Optional.of(user));

        Response response = this.userApiResource.getUser(login, securityContextMock);
        assertEquals(200, response.getStatus());
        assertNotNull(response.getEntity());
        assertEquals(new UserApiEntity("login", null), response.getEntity());
    }

    @Test
    public void getUserNotFound() {
        SecurityContext securityContextMock = mock(SecurityContext.class);

        String login = "login";

        when(this.userServiceMock.findById(eq(login))).thenReturn(Optional.empty());

        Response response = this.userApiResource.getUser(login, securityContextMock);
        assertEquals(404, response.getStatus());
        assertNull(response.getEntity());
    }
}
