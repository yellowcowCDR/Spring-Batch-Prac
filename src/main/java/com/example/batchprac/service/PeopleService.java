package com.example.batchprac.service;

import com.example.batchprac.dto.Person;
import com.example.batchprac.entity.People;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

public interface PeopleService {
    List<People> getPeople();
    void addOrUpdatePerson(People person);
}
