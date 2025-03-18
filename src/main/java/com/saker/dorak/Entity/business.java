package com.saker.dorak.Entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Getter
@Entity
public class business {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Setter
    private String name;
    @Setter
    private String description;
    @Setter
    private String email;
    @Setter
    private String phone;
    @Setter
    private String profile_url;
    @Setter
    private String banner_url;
    @Setter
    private boolean is_active;
    @Setter
    private LocalDate date_of_joining;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private users user;

    @Setter
    @OneToMany(mappedBy = "business", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true)
    private List<worker> workers;


    @Setter
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "location_id", nullable = false)
    private location location;

    @Setter
    @OneToMany(mappedBy = "business", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true)
    private List<example> examples;

    @Setter
    @OneToMany(mappedBy = "business", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true)
    private List<social_media> social_media;

    @Setter
    @OneToMany(mappedBy = "business", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true)
    private List<ad> ads;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "package_id")
    private packages packages;

    @Setter
    @OneToMany(mappedBy = "business", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true)
    private List<service> services;

    @Setter
    @OneToMany(mappedBy = "business", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true)
    private List<massage> massages;

    @Setter
    @OneToMany(mappedBy = "business", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true)
    private List<package_sr> packageSrList;

    @Setter
    @OneToMany(mappedBy = "business", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true)
    private List<appointment> appointments;

    public business() {
        this.is_active = false;
        this.date_of_joining = LocalDate.now();
    }
}
