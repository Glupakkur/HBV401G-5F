package com.example.verkefni.modules;

import java.time.LocalDate;

public class Customer {
    private final LocalDate dateOfBirth;
    private final String sex;
    private final String nationality;
    private final String firstName;
    private final String lastName;
    private final String phone;
    private final String email;

    // Constructor
    public Customer(LocalDate dateOfBirth, String sex, String nationality, String firstName, String lastName, String phone, String email) {
        this.dateOfBirth = dateOfBirth;
        this.sex = sex;
        this.nationality = nationality;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.email = email;
    }
}  