package com.gymcontrol.repository;

import com.gymcontrol.domain.Rutina;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RutinaRepository extends JpaRepository<Rutina, Long> {
    List<Rutina> findByClienteIdOrderByIdDesc(Long clienteId);
}
