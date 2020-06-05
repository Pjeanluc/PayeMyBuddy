package com.ocr.axa.jlp.paymybuddy.DAO;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ocr.axa.jlp.paymybuddy.model.User;

@Repository
public interface UserDAO  extends JpaRepository<User, Long>{
       
    public User findByEmail(String email);

    public boolean existsByEmail(String email);

    public boolean existsByEmailAndBuddies(String email, User buddyTransfer);

}
