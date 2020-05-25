package com.ocr.axa.jlp.paymybuddy.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.ocr.axa.jlp.paymybuddy.model.User;

@Transactional
public interface UserRepository extends JpaRepository<User, Long>{
    
    User findByEmail(String email);
    
    User findByLastname (String lastName);
    
    Boolean existsByEmail(String email);
    
    //Boolean existByFirstName(String firstname);
    
}
