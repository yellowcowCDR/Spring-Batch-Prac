package com.example.batchprac.repository;

import com.example.batchprac.entity.People;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PeopleRepository extends JpaRepository<People, Long> {
    Optional<People> findByFirstNameAndLastName(String firstName, String lastName);
}
