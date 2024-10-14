package com.jgen.repositories;

import com.jgen.models.SuiviActivites;
import com.jgen.models.SuiviIndicateurs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface SuiviIndicateursRepo extends JpaRepository<SuiviIndicateurs, Integer> {

    SuiviIndicateurs findById(int id);

    @Query("SELECT si FROM SuiviIndicateurs si WHERE si.planification.projet like ?1")
    List<SuiviIndicateurs> findAllSuiviByProjet(String projet);

    @Query("SELECT si FROM SuiviIndicateurs si WHERE si.planification.id = ?1")
    SuiviIndicateurs findSuiviByidPlanif(int id);

    @Modifying
    @Transactional
    @Query("DELETE FROM SuiviIndicateurs si WHERE si.planification.id IN (SELECT pi.id FROM PlanificationIndicateurs pi WHERE pi.projet LIKE ?1)")
    void deleteSuiviByProjet(String projet);

    @Query("SELECT si.planification.indicateur, si.planification.reference, si.planification.cible, si.atteinte FROM SuiviIndicateurs si WHERE si.planification.projet like ?1")
    List<Object[]> findIndicateurs(String projet);
}
