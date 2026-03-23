package com.projetofmds.fraudchecker.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.projetofmds.fraudchecker.model.Account;
import com.projetofmds.fraudchecker.repository.AccountRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor

public class AccountService {
    private final AccountRepository accountRepository;

    public Account createAccount(Account account) {

        //POR REGRAS DE NEGOCIOS (Validacoes) AQUI

        return accountRepository.save(account);
    }

    public List<Account> getAllAccounts() {
        
        return accountRepository.findAll();
    }


}
