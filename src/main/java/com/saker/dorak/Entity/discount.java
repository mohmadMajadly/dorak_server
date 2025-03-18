package com.saker.dorak.Entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Entity
public class discount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Setter
    private String discount_name;
    @Setter
    private double discount_value;
    @Setter
    private String discount_code;
    @Setter
    private boolean is_percent;
    @Setter
    private boolean for_all;
    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "service_id", nullable = false)
    private service service;
}
