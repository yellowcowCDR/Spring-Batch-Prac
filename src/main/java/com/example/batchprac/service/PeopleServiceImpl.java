package com.example.batchprac.service;

import com.example.batchprac.dto.Person;
import com.example.batchprac.entity.People;
import com.example.batchprac.repository.PeopleRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Transactional
@Service
public class PeopleServiceImpl implements PeopleService{
    private final PeopleRepository peopleRepository;

    @Override
    public List<People> getPeople() {
        return peopleRepository.findAll();
    }

    @Override
    public void addOrUpdatePerson(People people) {
        peopleRepository.save(people);
    }
}
