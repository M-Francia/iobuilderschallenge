package com.iochallenge.backendiochallenge.infrastructure.controller;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.iochallenge.backendiochallenge.domain.model.Transaction;
import com.iochallenge.backendiochallenge.domain.model.Wallet;
import com.iochallenge.backendiochallenge.domain.usecase.*;

import com.iochallenge.backendiochallenge.utils.JWTUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/wallet")
public class WalletController {

    private final CreateWalletUseCase createWalletUseCase;
    private final FindWalletUseCase findWalletUseCase;
    private final DepositUseCase depositUseCase;
    private final TransferUseCase transferUseCase;
    private final JWTUtil jwtUtil;
    private final GetWalletTransactionsUseCase getWalletTransactionsUseCase;

    public WalletController(CreateWalletUseCase createWalletUseCase,
                            FindWalletUseCase findWalletUseCase,
                            DepositUseCase depositUseCase,
                            TransferUseCase transferUseCase,
                            JWTUtil jwtUtil, GetWalletTransactionsUseCase getWalletTransactionsUseCase
    ) {
        this.createWalletUseCase = createWalletUseCase;
        this.findWalletUseCase = findWalletUseCase;
        this.depositUseCase = depositUseCase;
        this.transferUseCase = transferUseCase;
        this.jwtUtil = jwtUtil;
        this.getWalletTransactionsUseCase = getWalletTransactionsUseCase;
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity createWallet(@RequestParam(value = "user_id") Long user_id, @RequestParam(value = "name") String name, @RequestHeader("token") String token) {

        if (jwtUtil.logged(token, user_id.toString())){
            Wallet wallet = createWalletUseCase.createWallet(user_id, name);
            if (wallet != null) {
                return new ResponseEntity(mapWalletBase(wallet), HttpStatus.CREATED);
            }
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(HttpStatus.FORBIDDEN);

    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity getWallet(@PathVariable(value = "id") Long id, @RequestHeader("token") String token) {

        Wallet wallet = findWalletUseCase.findWallet(id);
        if ( wallet != null){
            if (jwtUtil.logged(token, wallet.getUserId().toString())){
                return new ResponseEntity(mapWalletBase(wallet), HttpStatus.OK);
            }
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity(HttpStatus.NOT_FOUND);

    }

    @RequestMapping(value = "/{id}/deposit", method = RequestMethod.PUT)
    public ResponseEntity deposit(@PathVariable(value = "id") Long id, @RequestParam(value = "amount") BigDecimal amount) {
            if(depositUseCase.deposit(amount, id)){
                return new ResponseEntity(HttpStatus.OK);
            }
            return new ResponseEntity(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(value = "/{id}/transfer", method = RequestMethod.POST)
    public ResponseEntity createTransfer(
            @PathVariable(value = "id") Long fromId,
            @RequestParam(value = "toWalletId") Long toId,
            @RequestParam(value = "user_id") Long user_id,
            @RequestParam(value = "amount") BigDecimal amount,
            @RequestHeader("token") String token) {

        if (jwtUtil.logged(token, user_id.toString())) {
            if (transferUseCase.transfer(amount, fromId, toId, user_id)) {
                return new ResponseEntity(HttpStatus.OK);
            }
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(HttpStatus.FORBIDDEN);
    }

    @RequestMapping(value = "/{id}/transactions", method = RequestMethod.GET)
    public ResponseEntity getTransactions(@PathVariable(value = "id") Long id, @RequestHeader("token") String token) {

        Wallet wallet = findWalletUseCase.findWallet(id);
        if (wallet != null){
            if (jwtUtil.logged(token, wallet.getUserId().toString())) {
                List<Transaction> transactions = getWalletTransactionsUseCase.getWalletTransactionsUseCase(id);
                if (transactions != null) {
                    return new ResponseEntity(transactions.stream().map(transaction -> mapTransactionBase(transaction)).collect(Collectors.toList()), HttpStatus.OK);
                }
                return new ResponseEntity(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity(HttpStatus.NOT_FOUND);

    }

    //REFACTOR

    private Map<String, Object> mapWalletBase(Wallet wallet){
        Map<String, Object> map = new HashMap<>();
        map.put("name", wallet.getName());
        map.put("balance", wallet.getBalance());
        map.put("id", wallet.getId());
        return map;
    }

    private Map<String, Object> mapTransactionBase(Transaction transaction){
        Map<String, Object> map = new HashMap<>();
        map.put("description", transaction.getDescription());
        map.put("amount", transaction.getAmount());
        map.put("date", transaction.getCreateAt());
        return map;
    }

}
