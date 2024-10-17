package com.jgen.repositories;

import com.jgen.models.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface UsersRepository extends JpaRepository<Users, Integer> {
    Users findByUsername(String username);

    @Modifying
    @Transactional
    @Query("UPDATE Users u SET u.role = ?1 WHERE u.username LIKE ?2")
    void changeRole(String role, String username);

    @Query("SELECT u FROM Users u WHERE u.username <> ?1")
    List<Users> getAllUsers(String username);

    @Query("SELECT u.role FROM Users u WHERE u.username LIKE ?1")
    String getRoleUser(String username);

    @Query("SELECT u.id FROM Users u WHERE u.username LIKE ?1")
    String getIdUser(String username);

    @Query("SELECT count(u.username) FROM Users u")
    int countUsers();

    @Query("SELECT u.actif FROM Users u WHERE u.username LIKE ?1")
    boolean isActif(String username);

    @Modifying
    @Transactional
    @Query("UPDATE Users u SET u.actif = ?1 WHERE u.username LIKE ?2")
    void changeStatus(boolean status, String username);
}

