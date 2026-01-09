package br.com.marketplace.onlyfoils.service;

import br.com.marketplace.onlyfoils.dto.CreateUserRequest;
import br.com.marketplace.onlyfoils.dto.UserResponse;
import br.com.marketplace.onlyfoils.model.User;

public interface UserService {

    public UserResponse create(CreateUserRequest request);
    User getEntityById(Long id);
}
