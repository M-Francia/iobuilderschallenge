package com.iochallenge.backendiochallenge.domain.usecase;

import com.iochallenge.backendiochallenge.domain.model.User;

public interface GetUser {
    User getUser(String username, String password);
}
