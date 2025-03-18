package com.saker.dorak.Entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Getter
@Entity
public class service {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Setter
    private String name;
    @Setter
    private String description;
    @Setter
    private String price;
    @Setter
    private String photo_url;
    @Setter
    private String category;
    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "business_id", nullable = false)
    private business business;

    @Setter
    @OneToMany(mappedBy = "service", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true)
    private List<discount> discounts;

    @Setter
    @ManyToMany(mappedBy = "services",cascade = CascadeType.ALL)
    private Set<appointment> appointments;

    @Setter
    @ManyToMany(mappedBy = "services",cascade = CascadeType.ALL)
    private Set<package_sr> packageSrSet;

    public service() {
        appointments = new HashSet<>();
    }
}
