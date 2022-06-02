package com.iochallenge.backendiochallenge.domain.usecase;

import com.iochallenge.backendiochallenge.domain.model.User;
import com.iochallenge.backendiochallenge.domain.model.Wallet;

public interface GetWallet {
    Wallet getWallet(User user, String name);
}
