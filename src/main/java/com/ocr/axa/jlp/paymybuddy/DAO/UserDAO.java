package com.ocr.axa.jlp.paymybuddy.DAO;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ocr.axa.jlp.paymybuddy.model.User;

@Transactional
public interface UserDAO  extends JpaRepository<User, Long>{
       
    public User findByEmail(String email);

    public boolean existsByEmail(String email);

    public boolean existsByEmailAndBuddies(String email, User buddyTransfer);

}
