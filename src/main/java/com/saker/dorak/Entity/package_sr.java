package com.saker.dorak.Entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Data
@Getter
@Entity
public class package_sr {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Setter
    private String package_name;
    @Setter
    private String price;
    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "business_id", nullable = false)
    private business business;

    @Setter
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "package_sr_service",
            joinColumns = @JoinColumn(name = "package_sr_id"),
            inverseJoinColumns = @JoinColumn(name = "service_id")
    )
    private Set<service> services;

    @Setter
    @OneToMany(mappedBy = "package_sr", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true)
    private List<detail> details;
}
