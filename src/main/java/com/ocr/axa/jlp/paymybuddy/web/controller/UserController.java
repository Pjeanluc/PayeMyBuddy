package com.ocr.axa.jlp.paymybuddy.web.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ocr.axa.jlp.paymybuddy.model.User;
import com.ocr.axa.jlp.paymybuddy.repository.UserRepository;
import com.ocr.axa.jlp.paymybuddy.service.ConnectionService;
import com.ocr.axa.jlp.paymybuddy.service.CreateService;
import com.ocr.axa.jlp.paymybuddy.web.exceptions.ControllerException;


@RestController
@RequestMapping(path = "/user")
public class UserController {
    private static final Logger logger = LogManager.getLogger("generalController");
    
    @Autowired
    UserRepository userRepository;
    
    @Autowired
    CreateService createService;
    
    @Autowired
    ConnectionService connectionService;
    
    @GetMapping(path = "/all")
    @ResponseBody
    public List<User> getAllUsers() {
        List<User> usersFound = createService.findAll();
        logger.info(" get all user : OK");
        return usersFound;
    }
    
    @PostMapping("/userInfo")
    public ResponseEntity<Long> createUser(@RequestBody User user) {
      

        if (user.getEmail().isEmpty()) {
            logger.error("inscriptionPerson : KO");
            throw new ControllerException("email is required");
        }
        if (user.getFirstname().isEmpty()) {
            logger.error("inscriptionPerson : KO");
            throw new ControllerException("firstname is required");
        }
        if (user.getFirstname().isEmpty()) {
            logger.error("inscriptionPerson : KO");
            throw new ControllerException("lastname is required");
        }
        if (user.getPassword().isEmpty()) {
            logger.error("inscriptionPerson : KO");
            throw new ControllerException("passeword is required");
        }
        if (userRepository.existsByEmail(user.getEmail())) {
            logger.error("Email already exist");
            throw new ControllerException("email already exist");
        }
        
        logger.info("Add user OK " + user.toString());
        Long idUserAdded = createService.create(user); 
        return new ResponseEntity(idUserAdded,HttpStatus.OK);
    }
    
    @GetMapping("/connect")
    public ResponseEntity<Boolean> connectUser(@RequestBody User user) {
      

        if (user.getEmail().isEmpty()) {
            logger.error("inscriptionPerson : KO");
            throw new ControllerException("email is required");
        }
        if (user.getPassword().isEmpty()) {
            logger.error("inscriptionPerson : KO");
            throw new ControllerException("password is required");
        }
        
        if (connectionService.connectUser(user)) {
            logger.info("Connect user OK " + user.toString());
            return new ResponseEntity(true,HttpStatus.OK);
        } else {
            logger.error("Connect user KO " + user.toString());
            throw new ControllerException("user/password not exist");
        }
    }

}