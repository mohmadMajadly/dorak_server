package com.saker.dorak.Repository;

import com.saker.dorak.Entity.jwt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface jwtRepository extends JpaRepository<jwt, Long> {
    @Query("SELECT COUNT(t) > 0 FROM jwt t WHERE t.token = :token")
    boolean existsByToken(@Param("token") String token);

    @Query("SELECT COUNT(t) > 0 FROM jwt t WHERE t.refresh_token = :refresh_token")
    boolean existsByRefresh_token(@Param("refresh_token") String refresh_token);

    @Query("SELECT t.user_id FROM jwt t where t.token = :token")
    Long findByToken(@Param("token") String token);
}
