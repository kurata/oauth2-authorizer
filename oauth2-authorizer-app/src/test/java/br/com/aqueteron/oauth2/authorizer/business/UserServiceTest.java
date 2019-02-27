package br.com.aqueteron.oauth2.authorizer.business;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepositoryMock;

    @Mock
    private PasswordEncoder passwordEncoderMock;

    @InjectMocks
    private UserService userService;

    @Test
    public void createUser() {
        User user = new User("login");
        user.setPassword("password");

        when(this.passwordEncoderMock.encode(eq(user.getPassword()))).thenReturn("{bcrypt}encryptedPassword");

        User expectedUser = new User("login");
        expectedUser.setPassword("{bcrypt}encryptedPassword");
        expectedUser.setEnabled(Boolean.TRUE);
        expectedUser.addRole("ROLE_USER");

        when(this.userRepositoryMock.save(eq(expectedUser))).thenReturn(expectedUser);

        User resultUser = this.userService.createUser(user);
        assertNotNull(resultUser);
        assertEquals(expectedUser, resultUser);
    }

    @Test
    public void findAll() {
        Set<User> userSet = new HashSet<>();
        userSet.add(new User("login"));

        when(this.userRepositoryMock.findAll()).thenReturn(userSet);
        Set<User> result = this.userService.findAll();
        assertEquals(userSet, result);
    }

    @Test
    public void findById() {
        String login = "login";
        User user = new User(login);

        when(userRepositoryMock.findById(eq(login))).thenReturn(Optional.of(user));

        Optional<User> result = this.userService.findById(login);
        assertTrue(result.isPresent());
        assertEquals(user, result.get());
    }
}
