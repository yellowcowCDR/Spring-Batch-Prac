package com.example.batchprac.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter @Setter
public class Person {
    private String lastName;
    private String firstName;
    @Override
    public String toString(){
        return "firstName: " + firstName + ", lastName: " + lastName;
    }
}
