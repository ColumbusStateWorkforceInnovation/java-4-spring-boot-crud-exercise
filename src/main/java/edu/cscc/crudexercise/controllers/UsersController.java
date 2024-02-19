package edu.cscc.crudexercise.controllers;

import edu.cscc.crudexercise.models.User;
import edu.cscc.crudexercise.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UsersController {

    @Autowired
    private UsersRepository usersRepository;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody User user) {
        User newUser = usersRepository.create(user);
        return new ResponseEntity<>(newUser, HttpStatus.OK);
    }
}
