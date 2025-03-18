package com.saker.dorak.Entity;

import com.saker.dorak.Service.SubService.tokenService;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

@Data
@Getter
@Entity
public class jwt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Setter
    @Column(unique = true)
    private String token;
    @Setter
    @Column(unique = true)
    private String refresh_token;
    @Setter
    private Long user_id;
    @Setter
    @OneToOne(mappedBy = "jwt")
    private session session;

    public jwt() {

    }
}
