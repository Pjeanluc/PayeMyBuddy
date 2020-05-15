package com.ocr.axa.jlp.paymybuddy.web.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ocr.axa.jlp.paymybuddy.model.User;
import com.ocr.axa.jlp.paymybuddy.service.UserService;
import com.ocr.axa.jlp.paymybuddy.web.exceptions.ControllerException;


@RestController
public class generalController {
    private static final Logger logger = LogManager.getLogger("generalController");
    
    @Autowired
    UserService userService;
    
    @RequestMapping("/userInfo")
    @PostMapping
    public ResponseEntity<String> inscriptionUser(@RequestBody User user) {
      

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
        
        logger.info("Add user OK" + user.toString());
        User userAdded = userService.save(user); 
        return new ResponseEntity("Add user : OK",HttpStatus.OK);
    }
    
    @GetMapping(value = "/userConnection")
    public  ResponseEntity<String> connectUser(@RequestParam(value = "email", required = false) String email,
                                     @RequestParam(value = "password", required = false) String password) {
        if (email.isEmpty()) {
            logger.error("connexion KO");
            throw new ControllerException("email is required");
        }
        if (password.isEmpty()) {
            logger.error("connexion KO");
            throw new ControllerException("password is required");
        }
        logger.info("connexion OK" + email.toString());
        //userService.connectUser(email,password);      
        return new ResponseEntity("Connexion OK",HttpStatus.OK);
    }

}
