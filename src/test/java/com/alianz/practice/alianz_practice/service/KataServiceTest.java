package com.alianz.practice.alianz_practice.service;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import jakarta.annotation.Resource;

public class KataServiceTest {
    @Resource
    @InjectMocks
    private KataService service;

    /**
     * for initial set up 
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception {
     MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testBasicBooks(){
        assertEquals(0d, service.getTotalBookPrice(Arrays.asList()), 0.5d);
        assertEquals(8d, service.getTotalBookPrice(Arrays.asList(1)), 0.5d);
        assertEquals(8d, service.getTotalBookPrice(Arrays.asList(2)), 0.5d);
        assertEquals(8d, service.getTotalBookPrice(Arrays.asList(3)), 0.5d);
        assertEquals(8d, service.getTotalBookPrice(Arrays.asList(4)), 0.5d);
        assertEquals(24d, service.getTotalBookPrice(Arrays.asList(1, 1, 1)), 0.5d);
    }

    @Test
    public void testSimpleBookDiscounts(){
        assertEquals(15.2d, service.getTotalBookPrice(Arrays.asList(0, 1)), 0.5d);
        assertEquals(21.6d, service.getTotalBookPrice(Arrays.asList(0, 2, 4)), 0.5d);
        assertEquals(8d * 4d * 0.8d, service.getTotalBookPrice(Arrays.asList(0, 1, 2, 4)), 0.5d);
        assertEquals(8 * 5 * 0.75, service.getTotalBookPrice(Arrays.asList(0, 1, 2, 3, 4)), 0.5d);
    }

    @Test
    public void testSeveralBookDiscounts(){
        assertEquals(8 + (8 * 2 * 0.95), service.getTotalBookPrice(Arrays.asList(0, 0, 1)), 0.5d); 
        assertEquals(2 * (8 * 2 * 0.95), service.getTotalBookPrice(Arrays.asList(0, 0, 1, 1)), 0.5d); 
        assertEquals((8 * 4 * 0.8) + (8 * 2 * 0.95), service.getTotalBookPrice(Arrays.asList(0, 0, 1, 2, 2, 3)), 0.5d); 
        assertEquals(8 + (8 * 5 * 0.75), service.getTotalBookPrice(Arrays.asList(0, 1, 1, 2, 3, 4)), 0.5d); 
    }

    @Test
    public void testEdgeCases(){
        assertEquals(2 * (8 * 4 * 0.8), service.getTotalBookPrice(Arrays.asList(0, 0, 1, 1, 2, 2, 3, 4)), 0.5d);  
        assertEquals(3 * (8 * 5 * 0.75) + 2 * (8 * 4 * 0.8), service.getTotalBookPrice(Arrays.asList(0, 0, 0, 0, 0, 
        1, 1, 1, 1, 1, 
        2, 2, 2, 2, 
        3, 3, 3, 3, 3, 
        4, 4, 4, 4)), 0.5d);  
    }

}
