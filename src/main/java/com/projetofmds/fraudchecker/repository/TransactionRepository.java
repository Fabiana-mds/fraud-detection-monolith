package com.projetofmds.fraudchecker.repository;

import com.projetofmds.fraudchecker.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    // Busca todas as transações de uma conta específica
    List<Transaction> findByAccountId(Long accountId);
}