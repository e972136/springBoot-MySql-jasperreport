package com.gaspar.report.repository;

import com.gaspar.report.entity.Personas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonasRepository extends JpaRepository<Personas,Integer> {
}
