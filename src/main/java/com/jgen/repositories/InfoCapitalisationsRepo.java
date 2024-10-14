package com.jgen.repositories;

import com.jgen.models.InfoCapitalisations;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InfoCapitalisationsRepo extends JpaRepository<InfoCapitalisations, Integer> {
    InfoCapitalisations findById(int id);
}
