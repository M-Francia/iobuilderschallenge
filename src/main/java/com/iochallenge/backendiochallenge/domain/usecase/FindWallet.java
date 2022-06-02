package com.iochallenge.backendiochallenge.domain.usecase;

import com.iochallenge.backendiochallenge.domain.model.Wallet;

public interface FindWallet {
    Wallet findWallet(Long id);
}
