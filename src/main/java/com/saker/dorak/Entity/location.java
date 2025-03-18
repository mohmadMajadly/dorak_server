package com.saker.dorak.Entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Entity
public class location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Setter
    private String url;
    @Setter
    private double latitude;
    @Setter
    private double longitude;
    @Setter
    @OneToOne(mappedBy = "location")
    private business business;

    public location() {
    }
}
