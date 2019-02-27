package br.com.aqueteron.oauth2.authorizer.business;

import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, String> {
}
