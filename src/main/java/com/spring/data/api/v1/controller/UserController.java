package com.spring.data.api.v1.controller;

import com.spring.data.api.v1.assembler.UserInputDisassembler;
import com.spring.data.api.v1.assembler.UserModelAssembler;
import com.spring.data.api.v1.model.UserModel;
import com.spring.data.api.v1.model.input.PasswordInput;
import com.spring.data.api.v1.model.input.UserInput;
import com.spring.data.api.v1.model.input.UserWithPasswordInput;
import com.spring.data.api.v1.openapi.controller.UserControllerOpenApi;
import com.spring.data.core.security.CheckSecurity;
import com.spring.data.domain.model.User;
import com.spring.data.domain.repository.UserRepository;
import com.spring.data.domain.service.UserRegisterService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/v1/users", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController implements UserControllerOpenApi {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRegisterService userRegister;

    @Autowired
    private UserModelAssembler userModelAssembler;

    @Autowired
    private UserInputDisassembler userInputDisassembler;

    @Override
    @CheckSecurity.UsersPartyPermissions.CanQuery
    @GetMapping
    public CollectionModel<UserModel> list() {
        List<User> allUsers = userRepository.findAll();

        return userModelAssembler.toCollectionModel(allUsers);
    }

    @Override
    @CheckSecurity.UsersPartyPermissions.CanQuery
    @GetMapping("/{userId}")
    public UserModel search(@PathVariable Long userId) {
        User user = userRegister.searchOrFail(userId);

        return userModelAssembler.toModel(user);
    }

    @Override
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserModel add(@RequestBody @Valid UserWithPasswordInput userWithPasswordInput) {
        User user = userInputDisassembler.toDomainObject(userWithPasswordInput);
        user = userRegister.save(user);

        return userModelAssembler.toModel(user);
    }

    @Override
    @CheckSecurity.UsersPartyPermissions.CanAlterUser
    @PutMapping("/{userId}")
    public UserModel update(@PathVariable Long userId,
                            @RequestBody @Valid UserInput userInput) {
        User currentUser = userRegister.searchOrFail(userId);
        userInputDisassembler.copyToDomainObject(userInput, currentUser);
        currentUser = userRegister.save(currentUser);

        return userModelAssembler.toModel(currentUser);
    }

    @Override
    @CheckSecurity.UsersPartyPermissions.CanAlterOwnPassword
    @PutMapping("/{userId}/password")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> alterPassword(@PathVariable Long userId, @RequestBody @Valid PasswordInput password) {
        userRegister.alterPassword(userId, password.getCurrentPassword(), password.getNewPassword());

        return ResponseEntity.noContent().build();
    }
}
