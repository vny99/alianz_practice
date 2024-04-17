package com.alianz.practice.alianz_practice.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;

import com.alianz.practice.alianz_practice.Entity.ProductCatergory;
import com.alianz.practice.alianz_practice.Entity.Stock;
import com.alianz.practice.alianz_practice.Entity.TypeStock;
import com.alianz.practice.alianz_practice.response.Response;
import com.alianz.practice.alianz_practice.service.TypeService;

import jakarta.annotation.Resource;

public class TypeControllerTest {
    private static final int INT_ONE = 1;

    private static final String HANDBALL = "HANDBALL";
    private static final String TYPE = "SPORTS_EQUIPMENT";

    private static final String STATE = "STATE";
    private static final String STATUS = "STATUS";
    private static final String PAYLOAD = "PAYLOAD";
    private static final String SUCCESS = "SUCCESS";
    private static final String ERROR = "ERROR";

    private static final String PRODUCT_DELETED_SUCCESSFULLY = "Product deleted successfully type : ";

    private static final String TYPE_NOT_FOUND = "Type not found : ";

    @InjectMocks
    @Resource
    private TypeController typeController;

    @Mock
    private TypeService service;

    @Mock
    private Response response;

    Map<String, Object> responseMap;

    /**
     * Initializes the mocks
     * 
     * @throws Exception on error
     */
    @Before
    public void setUp() throws Exception {
        responseMap = new java.util.TreeMap<>();
        MockitoAnnotations.initMocks(this);

    }

    @Test
    public void testGetStockByType_Found() {
        List<Stock> stock = createTypeStock();

        when(service.getStockByType(TYPE)).thenReturn(stock);
        when(response.buildResponse(any(), any())).thenReturn(buildResponse(createTypeStock(), HttpStatus.OK));

        Map<String, Object> response = typeController.getStockByType(TYPE).getBody();

        @SuppressWarnings("unchecked")
        List<Stock> result = (List<Stock>) response.get(PAYLOAD);
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.get(STATUS));
        assertEquals(stock.size(), result.size());
        assertEquals(SUCCESS, response.get(STATE));
    }

    @Test
    public void testGetStockByType_NotFound() {
        when(service.getStockByType(TYPE)).thenThrow(new NoSuchElementException(TYPE_NOT_FOUND));

        Exception exception = assertThrows(NoSuchElementException.class, () -> {
            typeController.getStockByType(TYPE);
        });

        assertEquals(TYPE_NOT_FOUND, exception.getMessage());
    }

    @Test
    public void testDeleteProductByType_Found() {
        when(response.buildResponse(any(), any()))
                .thenReturn(buildResponse(PRODUCT_DELETED_SUCCESSFULLY + TYPE, HttpStatus.OK));

        Map<String, Object> response = typeController.deleteProductByType(TYPE).getBody();

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.get(STATUS));
        assertEquals(SUCCESS, response.get(STATE));
    }

    @Test
    public void testDeleteProductByType_NotFound() {
        doThrow(new NoSuchElementException(TYPE_NOT_FOUND)).when(service).deleteByType(TYPE);

        Exception exception = assertThrows(NoSuchElementException.class, () -> {
            typeController.deleteProductByType(TYPE);
        });

        assertEquals(TYPE_NOT_FOUND, exception.getMessage());
    }

    @Test
    public void testGetAllTypes_Found() {
        List<ProductCatergory> typeList = createTypeList();
        when(service.getAllTypes()).thenReturn(typeList);
        when(response.buildResponse(any(), any())).thenReturn(buildResponse(typeList, HttpStatus.OK));

        Map<String, Object> response = typeController.getAllTypes().getBody();

        List<ProductCatergory> result = (List<ProductCatergory>) response.get(PAYLOAD);
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.get(STATUS));
        assertEquals(SUCCESS, response.get(STATE));
        for (int i = 0; i < typeList.size(); i++) {
            assertEquals(typeList.get(i), result.get(i));
        }
    }

    @Test
    public void testGetAllTypes_NotFound() {
        when(service.getAllTypes()).thenReturn(new ArrayList<>());

        when(response.buildResponse(any(), any())).thenReturn(buildResponse(new ArrayList<>(), HttpStatus.OK));
        Map<String, Object> response = typeController.getAllTypes().getBody();
        assertNotNull(response);
        List<ProductCatergory> result = (List<ProductCatergory>) response.get(PAYLOAD);
        assertEquals(HttpStatus.OK, response.get(STATUS));
        assertEquals(0, result.size());
        assertEquals(SUCCESS, response.get(STATE));

    }

    private List<ProductCatergory> createTypeList() {
        return List.of(ProductCatergory.SPORTS_EQUIPMENT);
    }

    private List<Stock> createTypeStock() {
        TypeStock typeStock = new TypeStock();
        typeStock.setCatergory(ProductCatergory.SPORTS_EQUIPMENT);
        Stock stock = new Stock();
        stock.setRemainingStock(INT_ONE);
        stock.setStockName(HANDBALL);
        List<Stock> stockList = List.of(stock);
        typeStock.setStock(stockList);
        return typeStock.getStock();
    }

    public Map<String, Object> buildResponse(Object payLoad, HttpStatus status) {
        if (HttpStatus.OK.equals(status)) {
            responseMap.put(STATE, SUCCESS);
        } else {
            responseMap.put(STATE, ERROR);
        }
        responseMap.put(STATUS, status);
        responseMap.put(PAYLOAD, payLoad);
        return responseMap;
    }

}
