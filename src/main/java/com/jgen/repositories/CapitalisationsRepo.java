package com.jgen.repositories;

import com.jgen.models.Capitalisations;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface CapitalisationsRepo extends JpaRepository<Capitalisations, Integer> {

    List<Capitalisations> findAllByProjet(String projet);

    Capitalisations findById(int id);

    @Modifying
    @Transactional
    @Query("DELETE FROM Capitalisations ca WHERE ca.projet LIKE ?1")
    void deleteByProjet(String projet);

}
