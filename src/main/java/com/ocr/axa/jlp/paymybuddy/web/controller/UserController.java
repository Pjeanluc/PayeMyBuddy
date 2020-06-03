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

import com.ocr.axa.jlp.paymybuddy.model.Bank;
import com.ocr.axa.jlp.paymybuddy.model.User;
import com.ocr.axa.jlp.paymybuddy.service.ConnectionService;
import com.ocr.axa.jlp.paymybuddy.service.UserService;
import com.ocr.axa.jlp.paymybuddy.web.exceptions.ControllerException;

@RestController
@RequestMapping(path = "/user")
public class UserController {
    private static final Logger logger = LogManager.getLogger("generalController");

    @Autowired
    UserService userService;

    @Autowired
    ConnectionService connectionService;

    /**
     * 
     * @return List of all USER
     */
    
    @GetMapping(path = "/all")
    @ResponseBody
    public List<User> getAllUsers() {
        List<User> usersFound = userService.findAll();
        logger.info(" get all user : OK");
        return usersFound;
    }
    
    /**
     * 
     * @param user (email)
     * @return information for the user
     */
    @GetMapping
    @ResponseBody
    public User getUser(@RequestBody User user) {
        User userFound = userService.findUser(user);
        logger.info(" get user : OK");
        return userFound;
    }

    /**
     * 
     * @param user (firstname, lastname, email, password required)
     * @return user created
     */
    @PostMapping("/userInfo")
    public ResponseEntity<User> createUser(@RequestBody User user) {

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

        User userAdded = userService.create(user);
        
        if (userAdded == null) {
            logger.error("inscriptionPerson : KO");
            throw new ControllerException("email already exist");
        }
        else {
            logger.info("Add user OK " + user.toString());
            return new ResponseEntity(userAdded, HttpStatus.OK);
        }
      
        
    }

   /**
    * 
    * @param user (email and password)
    * @return ok si user/password are matching
    */
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
            return new ResponseEntity(true, HttpStatus.OK);
        } else {
            logger.error("Connect user KO " + user.toString());
            throw new ControllerException("user/password not exist");
        }
    }

    /**
     * 
     * @param bank (code BIC and email of the user)
     * @return bank created
     */
    @PostMapping("/createbank")
    public ResponseEntity<Bank> createBank(@RequestBody Bank bank) {

        if (bank.getUser().getEmail().isEmpty()) {
            logger.error("create bank : KO");
            throw new ControllerException("email is required");
        }
        
        if (bank.getBicCode().isEmpty()) {
            logger.error("create bank : KO");
            throw new ControllerException("Code bank is required");
        }

        Bank bankAdded = userService.createBank(bank);
        logger.info("create bank OK ");
        return new ResponseEntity(bankAdded, HttpStatus.OK);
    }

    /**
     * 
     * @param bank (email of the user)
     * @return list of the bank for this user
     */
    @GetMapping("/bank")
    public ResponseEntity<List<Bank>> getAllBankForUser(@RequestBody Bank bank) {

        if (bank.getUser().getEmail().isEmpty()) {
            logger.error("get bank : KO");
            throw new ControllerException("email is required");
        }

        List<Bank> banks = userService.findAllBankByUser(bank);
        logger.info("get bank OK ");
        return new ResponseEntity(banks, HttpStatus.OK);
    }
    
    /**
     * 
     * @param user (email and buddy)
     * @return user updated
     */
    @PostMapping("/createbuddy")
    public ResponseEntity<User> createBuddy(@RequestBody User user) {

        if (user.getEmail().isEmpty()) {
            logger.error("create buddy : KO");
            throw new ControllerException("email is required");
        }

        User userUpdated = userService.createBuddy(user);
        logger.info("create buddy OK ");
        return new ResponseEntity(userUpdated, HttpStatus.OK);
    }
    
    /**
     * 
     * @param user (email)
     * @return list of all buddy for this user
     */
    @GetMapping("/buddy")
    public ResponseEntity<List<User>> getAllBuddyForUser(@RequestBody User user) {

        if (user.getEmail().isEmpty()) {
            logger.error("get buddy : KO");
            throw new ControllerException("email is required");
        }

        List<User> buddys = userService.findAllBuddyByUser(user);
        logger.info("get buddy OK ");
        
        return new ResponseEntity(buddys, HttpStatus.OK);
    }
}
