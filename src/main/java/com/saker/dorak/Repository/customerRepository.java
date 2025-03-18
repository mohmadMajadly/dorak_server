package com.saker.dorak.Repository;

import com.saker.dorak.Entity.customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface customerRepository extends JpaRepository<customer, Long> {
}
