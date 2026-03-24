package com.projetofmds.fraudchecker.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.projetofmds.fraudchecker.model.Account;
import com.projetofmds.fraudchecker.repository.AccountRepository;
import lombok.RequiredArgsConstructor;

/**
 * Service responsável pela gestão das contas dos clientes.
 * Concentra as regras de negócio relacionadas ao ciclo de vida de uma Account.
 */
@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;

    /**
     * Registra uma nova conta no sistema.
     * @param account Objeto contendo os dados da conta a ser persistida.
     * @return A conta salva com seu respectivo ID gerado.
     */
    @Transactional
    public Account createAccount(Account account) {
        // Camada preparada para futuras validações de negócio 
        // (Ex: verificar se o número da conta já existe ou validar CPF/CNPJ)
        return accountRepository.save(account);
    }

    /**
     * Recupera a listagem completa de contas cadastradas.
     * @return Lista contendo todos os registros de contas do banco de dados.
     */
    @Transactional(readOnly = true)
    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }
}