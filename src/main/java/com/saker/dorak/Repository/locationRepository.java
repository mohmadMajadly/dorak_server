package com.saker.dorak.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.saker.dorak.Entity.location;

public interface locationRepository extends JpaRepository<location, Long> {
}
