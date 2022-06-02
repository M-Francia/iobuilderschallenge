package com.iochallenge.backendiochallenge.infrastructure.controller;

import com.iochallenge.backendiochallenge.domain.model.User;
import com.iochallenge.backendiochallenge.domain.usecase.GetUserUseCase;
import com.iochallenge.backendiochallenge.utils.JWTUtil;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/login")
public class AuthController {

    private JWTUtil jwtUtil;
    private GetUserUseCase getUserUseCase;


    public AuthController(JWTUtil jwtUtil, GetUserUseCase getUserUseCase) {
        this.jwtUtil = jwtUtil;
        this.getUserUseCase = getUserUseCase;
    }

    @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> login(@RequestParam String username, @RequestParam String password) {

        User user = getUserUseCase.getUser(username, password);
        if(user != null){
            Map<String, Object> map = new HashMap<>();
            String token = jwtUtil.create(user.getId().toString(), user.getUsername());
            map.put("token", token);
            return new ResponseEntity(map, HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.NOT_FOUND);

    }
    
}
