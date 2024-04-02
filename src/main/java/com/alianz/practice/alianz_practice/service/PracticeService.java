package com.alianz.practice.alianz_practice.service;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alianz.practice.alianz_practice.Entity.Person;
import com.alianz.practice.alianz_practice.repository.PeopleRepository;

/**
 * This class represents a service for practicing various operations.
 * It provides methods for generating series, generating people, and performing calculations on the generated data.
 */
@Service
public class PracticeService {

    private static final Logger LOG = LoggerFactory.getLogger(PracticeService.class);
    private static final String FEMALE = "F";
    private static final String MALE = "M";

    @Autowired
    private PeopleRepository repo;

    /**
     * Generates a list of FizzBuzz values up to the specified limit.
     *
     * @param limit the maximum number to generate FizzBuzz values for
     * @return a list of FizzBuzz values
     */
    public List<String> getFizzBuzz(Integer limit) {
        try {
            var result = IntStream.rangeClosed(1, limit).mapToObj(number -> {
                if (number % 3 == 0 && number % 5 == 0) {
                    return "FIZZBUZZ";
                } else if (number % 3 == 0) {
                    return "FIZZ";
                } else if (number % 5 == 0) {
                    return "BUZZ";
                }
                return String.valueOf(number);
            }).collect(Collectors.toList());
            LOG.info("Fizz buzz generated successfullly");
            return result;
        } catch (Exception e) {
            LOG.error("Error in printing FizzBuzz", e);
            return new ArrayList<>();
        }

    }

    /**
     * Generates a series of numbers in descending order, starting from the given limit and decrementing by 2.
     *
     * @param limit the starting limit for the series
     * @return a string representation of the generated series
     */
    public String generateSeries(int limit) {
            return IntStream.iterate(limit, num -> num -2).limit((limit/2) + 1).mapToObj(num -> num+" ").reduce("",String::concat);
    }

    /**
     * Generates a list of people with random attributes.
     *
     * @param count the number of people to generate
     * @return a list of randomly generated people
     */
    public List<Person> generatePeople(int count){
        Random random = new Random();
        String [] sex = {MALE, FEMALE};

        List<Person> people = IntStream.rangeClosed(1, 50).mapToObj(id -> {
         Person person = new Person(id, random.nextInt(100),sex[ random.nextInt(sex.length)]);
         return person;       
        }).collect(Collectors.toList());

        repo.saveAll(people);

        return people;

    }

    /**
     * Retrieves a list of all people.
     *
     * @return a list of Person objects representing all the people.
     */
    public List<Person> getPeoples(){
        return repo.findAll();
    }

    /**
     * Returns the number of adults in the list of people.
     *
     * @return The number of adults.
     */
    public int getNumOfAdults() {
      return (int) getPeoples().stream().map(Person::getAge).filter(age -> age >= 18).count();
    }

    /**
     * Returns the number of minors in the list of people.
     *
     * @return The number of minors.
     */
    public int getNumOfMinors() {
         return (int) getPeoples().stream().map(Person::getAge).filter(age -> age < 18).count();
    }

    /**
     * Returns the number of male adults in the list of people.
     *
     * @return The number of male adults.
     */
    public int getNumOfMaleAdults() {
        return (int) getPeoples().stream().filter(person -> person.getAge() >= 18 && person.getSex().equals(MALE)).count();
    }

    /**
     * Returns the number of female minors.
     *
     * @return the number of female minors
     */
    public int getNumOfFemaleMinors() {
        return (int) getPeoples().stream().filter(person -> person.getAge() < 18 && person.getSex().equals(FEMALE)).count();
    }

    /**
     * Calculates the legal percentage based on the number of adults and the total number of people.
     *
     * @return The legal percentage as a {@code Double} value.
     */
    public Double getLegalPercentage() {
        return  (Double.valueOf(getNumOfAdults()) / Double.valueOf(getPeoples().size())) * 100;
    }

    /**
     * Returns the percentage of females in the list of people.
     *
     * @return the percentage of females as a double value
     */
    public Object getFemalePercentage() {
        int femaleCount = (int) getPeoples().stream().map(Person::getSex).filter(s -> s.equals(FEMALE)).count();
        return (Double.valueOf(femaleCount) / Double.valueOf(getPeoples().size())) * 100;
    }

    /**
     * Calculates the wage based on the number of hours worked and the hourly rate.
     * If the number of hours worked is less than or equal to 40, the wage is calculated
     * by multiplying the hours worked by the hourly rate. If the number of hours worked
     * is greater than 40, the wage is calculated by multiplying the first 40 hours by
     * the hourly rate, and then adding the product of the remaining hours and 1.5 times
     * the hourly rate.
     *
     * @param hours the number of hours worked
     * @param rate the hourly rate
     * @return the calculated wage
     */
    public double getWage(int hours, int rate) {
        if (hours <= 40){
            return hours * rate;
        }
        return 40 * rate + (hours - 40) * (1.5) * rate;
    }
}
