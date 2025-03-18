package com.saker.dorak.Entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Entity
public class detail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Setter
    private String detail;
    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "package_sr_id", nullable = false)
    private package_sr package_sr;
}
