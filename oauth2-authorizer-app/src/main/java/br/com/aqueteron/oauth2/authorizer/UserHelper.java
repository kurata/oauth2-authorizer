package br.com.aqueteron.oauth2.authorizer;

import br.com.aqueteron.oauth2.authorizer.business.User;
import br.com.aqueteron.oauth2.authorizer.model.UserApiEntity;

public class UserHelper {

    private UserHelper() {
    }

    public static User loadUser(final UserApiEntity userApiEntity) {
        User user = new User(userApiEntity.getLogin());
        user.setPassword(userApiEntity.getPassword());
        return user;
    }

    public static UserApiEntity loadUserResource(final User user) {
        return new UserApiEntity(user.getLogin(), user.getPassword());
    }
}
