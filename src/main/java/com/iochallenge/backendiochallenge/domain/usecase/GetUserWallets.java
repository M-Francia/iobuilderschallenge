package com.iochallenge.backendiochallenge.domain.usecase;

import java.util.List;

import com.iochallenge.backendiochallenge.domain.model.Wallet;

public interface GetUserWallets {
    List<Wallet> getUserWallets(Long user_id);
}
