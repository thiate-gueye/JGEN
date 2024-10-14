package com.jgen.repositories;

import com.jgen.models.ObjectifsSpecifiques;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface ObjectifsSpecifiquesRepo extends JpaRepository<ObjectifsSpecifiques, Integer> {
    List<ObjectifsSpecifiques> findAllByProjet(String projet);

    ObjectifsSpecifiques findById(int id);

    @Modifying
    @Transactional
    @Query("DELETE FROM ObjectifsSpecifiques os WHERE os.projet LIKE ?1")
    void deleteByProjet(String nom);
}
