package com.iochallenge.backendiochallenge.domain.model;

import java.util.ArrayList;
import java.util.List;

public class User {

    private String username;

    private String password;

    private List<Wallet> wallets;

    private Long id;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.wallets = new ArrayList<Wallet>();
    }

    public User() {
        this.wallets = new ArrayList<Wallet>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public List<Wallet> getWallets() {
        return wallets;
    }

    public void setWallets(List<Wallet> wallets) {
        this.wallets = wallets;
    }
    public void addWallet(Wallet wallet) {
        this.wallets.add(wallet);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
