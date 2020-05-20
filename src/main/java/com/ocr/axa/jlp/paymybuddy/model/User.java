package com.ocr.axa.jlp.paymybuddy.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@JsonIgnoreProperties(value = {"createdAt", "updatedAt"}, allowGetters = true)
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    private Long id;

    @Column(length=100)
    @NotNull
    private String firstname;

    @Column(length=100)
    @NotNull
    private String lastname;

    @Column(unique = true,length=100)
    @Email
    @NotNull
    private String email;

    @Column(length=100)
    @NotNull
    private String password;

    @Column(length=100)
    @NotNull
    private String pseudo;
    
    @ManyToMany
    private List<User> buddies;

    @OneToMany(mappedBy = "user",fetch=FetchType.LAZY)
    private List<Bank> bank;
    
    @OneToMany(mappedBy = "user",fetch=FetchType.LAZY)
    private List<Transfer> transfers;
    
    @OneToOne(mappedBy = "user",fetch=FetchType.LAZY)
    private Account account;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

}
