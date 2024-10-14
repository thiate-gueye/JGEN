package com.jgen.repositories;

import com.jgen.models.PlanificationActivites;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface PlanificationActivitesRepo extends JpaRepository<PlanificationActivites, Integer> {
    PlanificationActivites findById(int id);

    List<PlanificationActivites> findAllByProjet(String projet);

    @Modifying
    @Transactional
    @Query("DELETE FROM PlanificationActivites pa WHERE pa.projet LIKE ?1")
    void deleteByProjet(String projet);
}
