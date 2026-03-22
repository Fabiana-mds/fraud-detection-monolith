package com.projetofmds.fraudchecker.repository;

import com.projetofmds.fraudchecker.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    // Aqui podemos criar métodos customizados, por exemplo:
    Optional<Account> findByAccountNumber(String accountNumber);
}