package com.ocr.axa.jlp.paymybuddy.DAO;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ocr.axa.jlp.paymybuddy.model.Account;

@Repository
public interface AccountDAO extends JpaRepository<Account, Long> {
    
    Account findByUserId(Long id);

}
