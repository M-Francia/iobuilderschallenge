package com.iochallenge.backendiochallenge.domain.usecase;

import com.iochallenge.backendiochallenge.domain.model.User;

public interface FindUserById {
    User findUserById(Long id);
}
