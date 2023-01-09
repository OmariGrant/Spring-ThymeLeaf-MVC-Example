package com.example.thymeleafexample.repositories;

import com.example.thymeleafexample.entities.Actor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface ActorRepository extends JpaRepository<Actor, Integer> {
    @Transactional
    @Modifying
    @Query("update Actor a set a.firstName = ?1, a.lastName = ?2")
    int updateFirstNameAndLastNameBy(String firstName, String lastName);
}