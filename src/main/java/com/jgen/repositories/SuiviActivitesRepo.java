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
public interface SuiviActivitesRepo extends JpaRepository<SuiviActivites, Integer> {

    SuiviActivites findById(int id);

    @Query("SELECT sa FROM SuiviActivites sa WHERE sa.planification.projet like ?1")
    List<SuiviActivites> findAllSuiviByProjet(String projet);

    @Query("SELECT sa FROM SuiviActivites sa WHERE sa.planification.id = ?1")
    SuiviActivites findSuiviByidPlanif(int id);

    @Modifying
    @Transactional
    @Query("DELETE FROM SuiviActivites sa WHERE sa.planification.id IN (SELECT pa.id FROM PlanificationActivites pa WHERE pa.projet LIKE ?1)")
    void deleteSuiviByProjet(String projet);

    @Query("SELECT sa.planification.activite, sa.planification.cible, sa.atteinte, sa.planification.budget, sa.decaissement FROM SuiviActivites sa WHERE sa.planification.projet like ?1")
    List<Object[]> findActivites(String projet);

}

