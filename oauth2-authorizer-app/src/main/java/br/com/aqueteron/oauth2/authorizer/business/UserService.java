package br.com.aqueteron.oauth2.authorizer.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class UserService {

    private static final String DEFAULT_ROLE_NAME = "ROLE_USER";

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(final UserRepository userRepository, final PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User createUser(final User newUser) {
        User user = new User(newUser);
        user.setPassword(this.passwordEncoder.encode(newUser.getPassword()));
        user.setEnabled(Boolean.TRUE);
        user.addRole(DEFAULT_ROLE_NAME);
        return this.userRepository.save(user);
    }

    public Set<User> findAll() {
        Iterable<User> result = this.userRepository.findAll();
        return StreamSupport.stream(result.spliterator(), true).collect(Collectors.toSet());
    }

    public Optional<User> findById(final String login) {
        return this.userRepository.findById(login);
    }
}
