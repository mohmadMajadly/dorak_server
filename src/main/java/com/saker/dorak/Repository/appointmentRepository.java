package com.saker.dorak.Repository;

import com.saker.dorak.Entity.appointment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface appointmentRepository extends JpaRepository<appointment, Long> {
}
