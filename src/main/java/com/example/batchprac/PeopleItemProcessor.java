package com.example.batchprac;

import com.example.batchprac.entity.People;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;

@Slf4j
public class PeopleItemProcessor implements ItemProcessor<People, People> {
    @Override
    public People process(final People people) throws Exception {
        final String firstName = people.getFirstName().toUpperCase();
        final String lastName = people.getLastName().toUpperCase();

        final People transformedPeople = new People(people.getId(),firstName, lastName);

        log.info("Converting (" + people + ") into (" + transformedPeople + ")");

        return transformedPeople;
    }
}
