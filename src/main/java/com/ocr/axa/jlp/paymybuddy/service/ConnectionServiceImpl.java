package com.ocr.axa.jlp.paymybuddy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ocr.axa.jlp.paymybuddy.model.User;
import com.ocr.axa.jlp.paymybuddy.repository.UserRepository;

@Service
public class ConnectionServiceImpl implements ConnectionService {
    @Autowired
    UserRepository userRepository;
    
    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public boolean connectUser(User user) {
        User userToConnect = userRepository.findByEmail(user.getEmail());

        if (!(userToConnect == null)) {
            if(passwordEncoder.matches(user.getPassword(),userToConnect.getPassword())) {
                return true;
            }
        }
        return false;
    }

}
