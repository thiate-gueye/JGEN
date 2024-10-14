package com.jgen.repositories;

import com.jgen.models.SuiviActivites;
import com.jgen.models.SuiviFormations;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface SuiviFormationsRepo extends JpaRepository<SuiviFormations, Integer> {

    @Query("SELECT sf FROM SuiviFormations sf WHERE sf.planification.projet like ?1")
    List<SuiviFormations> findAllSuiviByProjet(String projet);

    @Query("SELECT sf FROM SuiviFormations sf WHERE sf.planification.id = ?1")
    SuiviFormations findSuiviByidPlanif(int id);

    @Modifying
    @Transactional
    @Query("DELETE FROM SuiviFormations sf WHERE sf.planification.id IN (SELECT pf.id FROM PlanificationFormations pf WHERE pf.projet LIKE ?1)")
    void deleteSuiviByProjet(String projet);

    @Query("SELECT sf.planification.thematique, sf.planification.budget, sf.decaissement FROM SuiviFormations sf WHERE sf.planification.projet like ?1")
    List<Object[]> findFormations(String projet);
}
