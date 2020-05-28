package com.ocr.axa.jlp.paymybuddy.DAO;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ocr.axa.jlp.paymybuddy.model.Bank;

@Repository
public interface BankDAO extends JpaRepository<Bank, Long> {

    List<Bank> findByUserId(Long id);
    
    boolean existsById(Long id);

}
