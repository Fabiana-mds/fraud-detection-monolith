package com.projetofmds.fraudchecker.controller;

import com.projetofmds.fraudchecker.model.Account;
import com.projetofmds.fraudchecker.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/accounts")
@RequiredArgsConstructor 
public class AccountController {

    private final AccountRepository accountRepository;

    // 1. Endpoint para criar uma conta
    @PostMapping
    public ResponseEntity<Account> createAccount(@RequestBody Account account) {
        Account savedAccount = accountRepository.save(account);
        return new ResponseEntity<>(savedAccount, HttpStatus.CREATED);
    }

    // 2. Endpoint para listar todas as contas
    @GetMapping
    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }
}