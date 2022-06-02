package com.iochallenge.backendiochallenge.infrastructure.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.iochallenge.backendiochallenge.domain.model.User;
import com.iochallenge.backendiochallenge.domain.model.Wallet;
import com.iochallenge.backendiochallenge.domain.usecase.FindUserUseCase;
import com.iochallenge.backendiochallenge.domain.usecase.GetUserWalletsUseCase;
import com.iochallenge.backendiochallenge.domain.usecase.SignUpUserUseCase;
import com.iochallenge.backendiochallenge.utils.JWTUtil;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/user")
public class UserController {

    private final SignUpUserUseCase signUpUserUseCase;
    private final FindUserUseCase findUserUseCase;
    private final GetUserWalletsUseCase getUserWalletsUseCase;
    private final JWTUtil jwtUtil;


    public UserController(SignUpUserUseCase signUpUserUseCase, FindUserUseCase findUserUseCase,
            GetUserWalletsUseCase getUserWalletsUseCase, JWTUtil jwtUtil) {
        this.signUpUserUseCase = signUpUserUseCase;
        this.findUserUseCase = findUserUseCase;
        this.getUserWalletsUseCase = getUserWalletsUseCase;
        this.jwtUtil = jwtUtil;
    }

    @RequestMapping(value = "/{username}", method = RequestMethod.GET)
    public ResponseEntity getUser(@PathVariable(value = "username") String username, @RequestHeader("token") String token) {

        if(jwtUtil.logged(token, username)){
            User user = findUserUseCase.findUser(username);
            if (user != null) {
                return new ResponseEntity(mapUserBase(user), HttpStatus.OK);
            }
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(HttpStatus.FORBIDDEN);

    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity signUpUser(@RequestParam String username, @RequestParam String password) {

        User newUser = signUpUserUseCase.signUpUser(username, password);
        if (newUser != null) {
            return new ResponseEntity(mapUserBase(newUser), HttpStatus.CREATED);
        }
        return new ResponseEntity(HttpStatus.NOT_FOUND);

    }

    @RequestMapping(value = "/{user_id}/wallets", method = RequestMethod.GET)
    public ResponseEntity getWallets(@PathVariable(value = "user_id") Long user_id, @RequestHeader("token") String token) {

        if(jwtUtil.logged(token, user_id.toString())) {
            List<Wallet> wallets = getUserWalletsUseCase.getUserWallets(user_id);
            if (wallets != null) {
                return new ResponseEntity(wallets.stream().map(wallet -> mapWalletBase(wallet)).collect(Collectors.toList()), HttpStatus.OK);
            }
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(HttpStatus.FORBIDDEN);

    }

    //REFACTOR
    private Map<String, Object> mapUserBase (User user){
        Map<String, Object> map = new HashMap<>();
        map.put("username", user.getUsername());
        map.put("id", user.getId());
        return map;
    }

    private Map<String, Object> mapWalletBase(Wallet wallet){
        Map<String, Object> map = new HashMap<>();
        map.put("name", wallet.getName());
        map.put("balance", wallet.getBalance());
        map.put("id", wallet.getId());
        return map;
    }

}
