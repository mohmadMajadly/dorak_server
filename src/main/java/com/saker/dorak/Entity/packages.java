package com.saker.dorak.Entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
@Getter
@Entity
public class packages {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Setter
    private String name;
    @Setter
    private String description;
    @Setter
    private String price;
    @OneToMany(mappedBy = "packages", fetch = FetchType.LAZY)
    private List<business> businesses;

    public packages() {
    }
}
