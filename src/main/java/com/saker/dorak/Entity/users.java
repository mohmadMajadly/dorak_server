package com.saker.dorak.Entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Data
@Getter
@Entity
public class users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Setter
    @Column(nullable = false)
    private String name;
    @Setter
    @Column(unique = true, nullable = false)
    private String username;
    @Setter
    private String email;
    @Setter
    @Column(nullable = false)
    private String password;
    @Setter
    private String code;
    @Setter
    private int number_of_enter_code;
    @Setter
    private String phone;
    @Setter
    private String age;
    @Setter
    private String birthday;
    @Setter
    private String date_of_joining;
    @Setter
    private String forget_password_token;
    @Setter
    @Column(nullable = false, columnDefinition = "BOOLEAN DEFAULT false")
    private boolean active;
    @Setter
    @Column(nullable = false, columnDefinition = "BOOLEAN DEFAULT false")
    private boolean gender;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true)
    private List<business> business;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true)
    private List<session> sessions;

    public users() {
        this.code = String.valueOf(ThreadLocalRandom.current().nextInt(100000,1000000));
        active = false;
        gender = false;
    }

    public void generateCode() {
        this.code = String.valueOf(ThreadLocalRandom.current().nextInt(100000,1000000));
    }
}
