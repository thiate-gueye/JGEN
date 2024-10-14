package com.jgen.repositories;

import com.jgen.models.PlanificationIndicateurs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface PlanificationIndicateursRepo extends JpaRepository<PlanificationIndicateurs, Integer> {

    PlanificationIndicateurs findById(int id);

    List<PlanificationIndicateurs> findAllByProjet(String projet);

    @Modifying
    @Transactional
    @Query("DELETE FROM PlanificationIndicateurs pi WHERE pi.projet LIKE ?1")
    void deleteByProjet(String projet);

}
