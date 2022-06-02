package com.iochallenge.backendiochallenge.domain.model;

import java.math.BigDecimal;
import java.util.Date;

public class Transaction {

    private Date createAt;

    private String description;

    private Long id;

    private BigDecimal amount;

    private Long walletId;


    public Transaction(String description, BigDecimal amount) {
        this.description = description;
        this.amount = amount;
    }

    public Date getCreateAt() {
        return createAt;
    }
    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
    public Long getWalletId() {
        return walletId;
    }
    public void setWalletId(Long walletId) {
        this.walletId = walletId;
    }


}
