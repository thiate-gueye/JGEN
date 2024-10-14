package com.jgen.repositories;

import com.jgen.models.Projects;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface ProjetcsRepo extends JpaRepository<Projects, Integer> {

    Projects findByNom(String nom);
}
