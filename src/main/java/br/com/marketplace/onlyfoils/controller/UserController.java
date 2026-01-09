package br.com.marketplace.onlyfoils.controller;

import br.com.marketplace.onlyfoils.dto.CreateUserRequest;
import br.com.marketplace.onlyfoils.dto.UserResponse;
import br.com.marketplace.onlyfoils.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse create(@RequestBody @Valid CreateUserRequest request) {
        return userService.create(request);
    }
}
