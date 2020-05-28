package com.ocr.axa.jlp.paymybuddy.DAO;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ocr.axa.jlp.paymybuddy.model.Movement;

@Repository
public interface MovementDAO extends JpaRepository<Movement, Long> {

}
