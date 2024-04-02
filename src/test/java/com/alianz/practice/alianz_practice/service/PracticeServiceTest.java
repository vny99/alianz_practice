package com.alianz.practice.alianz_practice.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.alianz.practice.alianz_practice.Entity.Person;
import com.alianz.practice.alianz_practice.builder.PersonBuilder;
import com.alianz.practice.alianz_practice.model.PersonModel;
import com.alianz.practice.alianz_practice.repository.PeopleRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.annotation.Resource;

public class PracticeServiceTest {

    private static final String PEOPLE = "[\r\n" + //
            "    {\r\n" + //
            "        \"id\": 1,\r\n" + //
            "        \"age\": 81,\r\n" + //
            "        \"sex\": \"F\"\r\n" + //
            "    },\r\n" + //
            "    {\r\n" + //
            "        \"id\": 2,\r\n" + //
            "        \"age\": 46,\r\n" + //
            "        \"sex\": \"M\"\r\n" + //
            "    },\r\n" + //
            "    {\r\n" + //
            "        \"id\": 3,\r\n" + //
            "        \"age\": 21,\r\n" + //
            "        \"sex\": \"M\"\r\n" + //
            "    },\r\n" + //
            "    {\r\n" + //
            "        \"id\": 4,\r\n" + //
            "        \"age\": 39,\r\n" + //
            "        \"sex\": \"F\"\r\n" + //
            "    },\r\n" + //
            "    {\r\n" + //
            "        \"id\": 5,\r\n" + //
            "        \"age\": 63,\r\n" + //
            "        \"sex\": \"M\"\r\n" + //
            "    },\r\n" + //
            "    {\r\n" + //
            "        \"id\": 6,\r\n" + //
            "        \"age\": 0,\r\n" + //
            "        \"sex\": \"F\"\r\n" + //
            "    },\r\n" + //
            "    {\r\n" + //
            "        \"id\": 7,\r\n" + //
            "        \"age\": 86,\r\n" + //
            "        \"sex\": \"M\"\r\n" + //
            "    },\r\n" + //
            "    {\r\n" + //
            "        \"id\": 8,\r\n" + //
            "        \"age\": 67,\r\n" + //
            "        \"sex\": \"M\"\r\n" + //
            "    },\r\n" + //
            "    {\r\n" + //
            "        \"id\": 9,\r\n" + //
            "        \"age\": 51,\r\n" + //
            "        \"sex\": \"M\"\r\n" + //
            "    },\r\n" + //
            "    {\r\n" + //
            "        \"id\": 10,\r\n" + //
            "        \"age\": 65,\r\n" + //
            "        \"sex\": \"M\"\r\n" + //
            "    },\r\n" + //
            "    {\r\n" + //
            "        \"id\": 11,\r\n" + //
            "        \"age\": 25,\r\n" + //
            "        \"sex\": \"F\"\r\n" + //
            "    },\r\n" + //
            "    {\r\n" + //
            "        \"id\": 12,\r\n" + //
            "        \"age\": 55,\r\n" + //
            "        \"sex\": \"M\"\r\n" + //
            "    },\r\n" + //
            "    {\r\n" + //
            "        \"id\": 13,\r\n" + //
            "        \"age\": 92,\r\n" + //
            "        \"sex\": \"F\"\r\n" + //
            "    },\r\n" + //
            "    {\r\n" + //
            "        \"id\": 14,\r\n" + //
            "        \"age\": 33,\r\n" + //
            "        \"sex\": \"F\"\r\n" + //
            "    },\r\n" + //
            "    {\r\n" + //
            "        \"id\": 15,\r\n" + //
            "        \"age\": 62,\r\n" + //
            "        \"sex\": \"F\"\r\n" + //
            "    },\r\n" + //
            "    {\r\n" + //
            "        \"id\": 16,\r\n" + //
            "        \"age\": 73,\r\n" + //
            "        \"sex\": \"F\"\r\n" + //
            "    },\r\n" + //
            "    {\r\n" + //
            "        \"id\": 17,\r\n" + //
            "        \"age\": 58,\r\n" + //
            "        \"sex\": \"M\"\r\n" + //
            "    },\r\n" + //
            "    {\r\n" + //
            "        \"id\": 18,\r\n" + //
            "        \"age\": 28,\r\n" + //
            "        \"sex\": \"F\"\r\n" + //
            "    },\r\n" + //
            "    {\r\n" + //
            "        \"id\": 19,\r\n" + //
            "        \"age\": 42,\r\n" + //
            "        \"sex\": \"M\"\r\n" + //
            "    },\r\n" + //
            "    {\r\n" + //
            "        \"id\": 20,\r\n" + //
            "        \"age\": 17,\r\n" + //
            "        \"sex\": \"F\"\r\n" + //
            "    },\r\n" + //
            "    {\r\n" + //
            "        \"id\": 21,\r\n" + //
            "        \"age\": 28,\r\n" + //
            "        \"sex\": \"M\"\r\n" + //
            "    },\r\n" + //
            "    {\r\n" + //
            "        \"id\": 22,\r\n" + //
            "        \"age\": 27,\r\n" + //
            "        \"sex\": \"M\"\r\n" + //
            "    },\r\n" + //
            "    {\r\n" + //
            "        \"id\": 23,\r\n" + //
            "        \"age\": 57,\r\n" + //
            "        \"sex\": \"F\"\r\n" + //
            "    },\r\n" + //
            "    {\r\n" + //
            "        \"id\": 24,\r\n" + //
            "        \"age\": 64,\r\n" + //
            "        \"sex\": \"M\"\r\n" + //
            "    },\r\n" + //
            "    {\r\n" + //
            "        \"id\": 25,\r\n" + //
            "        \"age\": 69,\r\n" + //
            "        \"sex\": \"M\"\r\n" + //
            "    },\r\n" + //
            "    {\r\n" + //
            "        \"id\": 26,\r\n" + //
            "        \"age\": 96,\r\n" + //
            "        \"sex\": \"F\"\r\n" + //
            "    },\r\n" + //
            "    {\r\n" + //
            "        \"id\": 27,\r\n" + //
            "        \"age\": 96,\r\n" + //
            "        \"sex\": \"F\"\r\n" + //
            "    },\r\n" + //
            "    {\r\n" + //
            "        \"id\": 28,\r\n" + //
            "        \"age\": 28,\r\n" + //
            "        \"sex\": \"M\"\r\n" + //
            "    },\r\n" + //
            "    {\r\n" + //
            "        \"id\": 29,\r\n" + //
            "        \"age\": 66,\r\n" + //
            "        \"sex\": \"F\"\r\n" + //
            "    },\r\n" + //
            "    {\r\n" + //
            "        \"id\": 30,\r\n" + //
            "        \"age\": 53,\r\n" + //
            "        \"sex\": \"M\"\r\n" + //
            "    },\r\n" + //
            "    {\r\n" + //
            "        \"id\": 31,\r\n" + //
            "        \"age\": 59,\r\n" + //
            "        \"sex\": \"F\"\r\n" + //
            "    },\r\n" + //
            "    {\r\n" + //
            "        \"id\": 32,\r\n" + //
            "        \"age\": 65,\r\n" + //
            "        \"sex\": \"F\"\r\n" + //
            "    },\r\n" + //
            "    {\r\n" + //
            "        \"id\": 33,\r\n" + //
            "        \"age\": 82,\r\n" + //
            "        \"sex\": \"M\"\r\n" + //
            "    },\r\n" + //
            "    {\r\n" + //
            "        \"id\": 34,\r\n" + //
            "        \"age\": 45,\r\n" + //
            "        \"sex\": \"F\"\r\n" + //
            "    },\r\n" + //
            "    {\r\n" + //
            "        \"id\": 35,\r\n" + //
            "        \"age\": 27,\r\n" + //
            "        \"sex\": \"M\"\r\n" + //
            "    },\r\n" + //
            "    {\r\n" + //
            "        \"id\": 36,\r\n" + //
            "        \"age\": 26,\r\n" + //
            "        \"sex\": \"F\"\r\n" + //
            "    },\r\n" + //
            "    {\r\n" + //
            "        \"id\": 37,\r\n" + //
            "        \"age\": 96,\r\n" + //
            "        \"sex\": \"M\"\r\n" + //
            "    },\r\n" + //
            "    {\r\n" + //
            "        \"id\": 38,\r\n" + //
            "        \"age\": 69,\r\n" + //
            "        \"sex\": \"M\"\r\n" + //
            "    },\r\n" + //
            "    {\r\n" + //
            "        \"id\": 39,\r\n" + //
            "        \"age\": 65,\r\n" + //
            "        \"sex\": \"F\"\r\n" + //
            "    },\r\n" + //
            "    {\r\n" + //
            "        \"id\": 40,\r\n" + //
            "        \"age\": 77,\r\n" + //
            "        \"sex\": \"M\"\r\n" + //
            "    },\r\n" + //
            "    {\r\n" + //
            "        \"id\": 41,\r\n" + //
            "        \"age\": 40,\r\n" + //
            "        \"sex\": \"F\"\r\n" + //
            "    },\r\n" + //
            "    {\r\n" + //
            "        \"id\": 42,\r\n" + //
            "        \"age\": 67,\r\n" + //
            "        \"sex\": \"F\"\r\n" + //
            "    },\r\n" + //
            "    {\r\n" + //
            "        \"id\": 43,\r\n" + //
            "        \"age\": 36,\r\n" + //
            "        \"sex\": \"F\"\r\n" + //
            "    },\r\n" + //
            "    {\r\n" + //
            "        \"id\": 44,\r\n" + //
            "        \"age\": 82,\r\n" + //
            "        \"sex\": \"M\"\r\n" + //
            "    },\r\n" + //
            "    {\r\n" + //
            "        \"id\": 45,\r\n" + //
            "        \"age\": 51,\r\n" + //
            "        \"sex\": \"M\"\r\n" + //
            "    },\r\n" + //
            "    {\r\n" + //
            "        \"id\": 46,\r\n" + //
            "        \"age\": 31,\r\n" + //
            "        \"sex\": \"F\"\r\n" + //
            "    },\r\n" + //
            "    {\r\n" + //
            "        \"id\": 47,\r\n" + //
            "        \"age\": 83,\r\n" + //
            "        \"sex\": \"M\"\r\n" + //
            "    },\r\n" + //
            "    {\r\n" + //
            "        \"id\": 48,\r\n" + //
            "        \"age\": 48,\r\n" + //
            "        \"sex\": \"M\"\r\n" + //
            "    },\r\n" + //
            "    {\r\n" + //
            "        \"id\": 49,\r\n" + //
            "        \"age\": 57,\r\n" + //
            "        \"sex\": \"M\"\r\n" + //
            "    },\r\n" + //
            "    {\r\n" + //
            "        \"id\": 50,\r\n" + //
            "        \"age\": 28,\r\n" + //
            "        \"sex\": \"F\"\r\n" + //
            "    }\r\n" + //
            "]";

    @InjectMocks
    @Resource
    private PracticeService service;

    @Mock
    private PeopleRepository repo;

    private ObjectMapper mapper;

    private List<Person> people;

    @Before
    public void setUP() throws Exception {
        MockitoAnnotations.initMocks(this);
        ObjectMapper mapper = new ObjectMapper();
        List<Person> people = Arrays.asList(mapper.readValue(PEOPLE, PersonModel[].class))
                .stream()
                .map(PersonBuilder::from)
                .map(PersonBuilder::build)
                .collect(Collectors.toList());
        when(repo.findAll()).thenReturn(people);
    }

    @Test
    public void testFizzBuzz() {

        List<String> list = new ArrayList<>(Arrays.asList("1",
                "2",
                "FIZZ",
                "4",
                "BUZZ"));
        List<String> result = service.getFizzBuzz(5);

        assertEquals(list.size(), result.size());
        assertEquals(list.get(0), result.get(0));
    }

    /**
     * Series of test for the generateSeries method
     */
    @Test
    public void testEvenWith_2() {
        assertEquals("2 0 ", service.generateSeries(2));
    }

    @Test
    public void testEvenWith_4() {
        assertEquals("4 2 0 ", service.generateSeries(4));
    }

    @Test
    public void testEvenWith_6() {
        assertEquals("6 4 2 0 ", service.generateSeries(6));
    }

    @Test
    public void testEvenWith_8() {
        assertEquals("8 6 4 2 0 ", service.generateSeries(8));
    }

    @Test
    public void testOddWith_3() {
        assertEquals("3 1 ", service.generateSeries(3));
    }

    @Test
    public void testOddWith_5() {
        assertEquals("5 3 1 ", service.generateSeries(5));
    }

    @Test
    public void testOddWith_7() {
        assertEquals("7 5 3 1 ", service.generateSeries(7));
    }

    @Test
    public void testOddWith_9() {
        assertEquals("9 7 5 3 1 ", service.generateSeries(9));
    }

    /**
     * Series of test for the People method
     */

    @Test
    public void testAdults() {
        assertEquals(48, service.getNumOfAdults());
    }

    @Test
    public void testMinors() {
        assertEquals(2, service.getNumOfMinors());
    }

    @Test
    public void testMaleAdults() {
        assertEquals(26, service.getNumOfMaleAdults());
    }

    @Test
    public void testLegalPercentage() {
        assertEquals(96d, service.getLegalPercentage(), 0.5d);
    }

    @Test
    public void testFemalePercentage() {
        assertEquals(48.0, service.getFemalePercentage());
    }

    /**
     * series of tests for the working hours implementation
     */

    @Test
    public void testWorkingHours_10() {
        // 10 * 20 = 200
        assertEquals(200, service.getWage(10, 20), 0.5);
    }

    @Test
    public void testWorkingHours_40() {
        // 40 * 20 = 800
        assertEquals(800, service.getWage(40, 20), 0.5);
    }

    @Test
    public void testWithExtraHours_2() {
        // 40 * 20 + 2 * 30 = 860
        assertEquals(860, service.getWage(42, 20), 0.5);
    }

    @Test
    public void testWithExtraHours_10() {
        // 40 * 20 + 10 * 30 = 1100
        assertEquals(1100, service.getWage(50, 20), 0.5);
    }
}
