package com.alianz.practice.alianz_practice.controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import com.alianz.practice.alianz_practice.service.PracticeService;

import jakarta.annotation.Resource;

public class PracticeControllerTest {
    @Mock
    private PracticeService service;

    @Resource
    @InjectMocks
    private PracticeController controller;

    /**
     * intial set ups
     * throws exeption on error
     */
    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testFizzBuzz() {
        List<String> list = new ArrayList<>(Arrays.asList("1",
                "2",
                "FIZZ",
                "4",
                "BUZZ",
                "FIZZ",
                "7",
                "8",
                "FIZZ",
                "BUZZ",
                "11",
                "FIZZ",
                "13",
                "14",
                "FIZZBUZZ"));

        when(service.getFizzBuzz(anyInt())).thenReturn(list);

        ResponseEntity<List<String>> result = controller.getFizzBuzz(15);

        assertEquals(list.size(), result.getBody().size());
        assertEquals(list.get(list.size()-1), result.getBody().get(result.getBody().size()-1));
    }
}
