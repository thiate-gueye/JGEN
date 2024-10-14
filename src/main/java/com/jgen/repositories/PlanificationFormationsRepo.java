package com.jgen.repositories;

import com.jgen.models.PlanificationFormations;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface PlanificationFormationsRepo  extends JpaRepository<PlanificationFormations, Integer> {

    PlanificationFormations getById(int id);

    List<PlanificationFormations> findAllByProjet(String projet);

    @Modifying
    @Transactional
    @Query("DELETE FROM PlanificationFormations pf WHERE pf.projet LIKE ?1")
    void deleteByProjet(String projet);
}
