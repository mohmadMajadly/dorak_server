package com.saker.dorak.Entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
@Getter
@Entity
public class admin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Setter
    private String name;
    @Setter
    private String email;
    @Setter
    private String phone;
    @Setter
    private String rule;
    @Setter
    private String username;
    @Setter
    private String password;

    @OneToMany(mappedBy = "admin", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true)
    private List<session> sessions;

    public admin() {
    }
}
