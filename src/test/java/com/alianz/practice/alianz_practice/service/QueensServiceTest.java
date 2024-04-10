package com.alianz.practice.alianz_practice.service;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import jakarta.annotation.Resource;

public class QueensServiceTest {
    @InjectMocks
    @Resource
    private QueensService service;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
     public void testQueensBasic_FOUR() {
        List<String> outcomes = Arrays.asList(". Q . . . . . Q Q . . . . . Q .",
                ". . Q . Q . . . . . . Q . Q . .");
        List<String> result = service.arrangeQueens(4);
        System.out.println(outcomes.get(0));
        System.out.println(result.get(0));
        assert (outcomes.equals(result));
    }

    @Test
    public void testQueensEdge_ONE() {
        // Only one cell, so the queen occupies that cell
        List<String> outcomes = Collections.singletonList("Q");
        List<String> result = service.arrangeQueens(1);
        assert (outcomes.equals(result));
    }

    @Test
    public void testQueensEdge_THREE() {
        // No solution exists for N=3
        List<String> outcomes = Collections.emptyList();
        List<String> result = service.arrangeQueens(3);
        assert (outcomes.equals(result));
    }

    @Test
    public void testQueensComplex_EIGHT() {
        List<String> outcomes = Arrays.asList(
                "Q . . . . . . . . . . . Q . . . . . . . . . . Q . . . . . Q . . . . Q . . . . . . . . . . . Q . . Q . . . . . . . . . Q . . . .");
                List<String> result = service.arrangeQueens(8);
                boolean isMatched = false;
                for (String expectedSolution : outcomes) {
                    for (String actualSolution : result) {
                        if (expectedSolution.equals(actualSolution)) {
                            isMatched = true;
                            break;
                        }
                    }
                    if (isMatched) {
                        break;
                    }
                }
                assertTrue(isMatched);
    }
}
