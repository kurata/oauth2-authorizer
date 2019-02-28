package br.com.aqueteron.oauth2.authorizer.api;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.SecurityContext;

import br.com.aqueteron.oauth2.authorizer.business.User;
import br.com.aqueteron.oauth2.authorizer.model.UserApiEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import br.com.aqueteron.oauth2.authorizer.business.UserService;

@Controller
public class UserApiResourceImpl implements UserApiResource {

    private UserService userService;

    @Autowired
    public UserApiResourceImpl(final UserService userService) {
        this.userService = userService;
    }

    @Override
    public Response options() {
        return Response.status(Status.OK).build();
    }

    @Override
    public Response getUserSet(final SecurityContext securityContext) {
        Set<User> userSet = this.userService.findAll();
        Set<UserApiEntity> userApiEntitySet = userSet.parallelStream().map(UserHelper::loadUserResource).collect(Collectors.toSet());
        return Response.status(Status.OK).entity(userApiEntitySet).build();
    }

    @Override
    public Response postUser(final UserApiEntity newUserApiEntity, final SecurityContext securityContext) {
        User newUser = UserHelper.loadUser(newUserApiEntity);
        newUser = this.userService.createUser(newUser);
        UserApiEntity userApiEntityResponse = UserHelper.loadUserResource(newUser);
        return Response.status(Status.CREATED).entity(userApiEntityResponse).build();
    }

    @Override
    public Response getUser(final String login, final SecurityContext securityContext) {
        Optional<User> findResult = this.userService.findById(login);
        if (findResult.isPresent()) {
            UserApiEntity userApiEntity = UserHelper.loadUserResource(findResult.get());
            return Response.status(Status.OK).entity(userApiEntity).build();
        }
        return Response.status(Status.NOT_FOUND).build();
    }
}
