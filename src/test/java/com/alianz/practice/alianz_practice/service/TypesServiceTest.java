package com.alianz.practice.alianz_practice.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.alianz.practice.alianz_practice.Entity.ProductCatergory;
import com.alianz.practice.alianz_practice.Entity.Stock;
import com.alianz.practice.alianz_practice.Entity.TypeStock;
import com.alianz.practice.alianz_practice.helper.ProductHelper;
import com.alianz.practice.alianz_practice.repository.StockTypeRepository;

import jakarta.annotation.Resource;

public class TypesServiceTest {
    private static final int INT_ONE = 1;

    private static final String HANDBALL = "HANDBALL";
    private static final String TYPE_NON_FOUND = "Type not found : ";
    private static final int INT_ZERO = 0;

    @InjectMocks
    @Resource
    private TypeService typesService;

    @Mock
    private StockTypeRepository typeRepository;

    @Mock
    private ProductHelper productHelper;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testDeleteByType() {
        TypeStock typeStock = createTypeStock();
        doNothing().when(typeRepository).delete(any());
        doNothing().when(productHelper).deleteProductByType(any());
        when(typeRepository.findByCatergory(any())).thenReturn(Optional.of(typeStock));
        typesService.deleteByType(ProductCatergory.SPORTS_EQUIPMENT.toString());
        verify(typeRepository, times(1)).delete(typeStock);
    }

    @Test
    public void testDeleteByType_NotFound() {
        when(typeRepository.findByCatergory(any())).thenReturn(Optional.empty());
        Exception e = assertThrows(Exception.class, () -> typesService.deleteByType(ProductCatergory.SPORTS_EQUIPMENT.toString()));
        assertEquals(TYPE_NON_FOUND+ProductCatergory.SPORTS_EQUIPMENT, e.getMessage());
    }

    @Test
    public void testGetAllTypes() {
        TypeStock typeStock = createTypeStock();
        when(typeRepository.findAll()).thenReturn(List.of(typeStock));
        List<ProductCatergory> list = typesService.getAllTypes();
        assertEquals(ProductCatergory.SPORTS_EQUIPMENT, list.get(INT_ZERO));
        verify(typeRepository, times(1)).findAll();
    }

    @Test
    public void testGetAllTypes_Empty() {
        when(typeRepository.findAll()).thenReturn(List.of());
        List<ProductCatergory> list = typesService.getAllTypes();
        assertEquals(INT_ZERO, list.size());
        verify(typeRepository, times(1)).findAll();
    }

    @Test
    public void testGetStockByType() {
        TypeStock typeStock = createTypeStock();
        when(typeRepository.findByCatergory(any())).thenReturn(Optional.of(typeStock));
        List<Stock> list = typesService.getStockByType(ProductCatergory.SPORTS_EQUIPMENT.toString());
        assertEquals(HANDBALL, list.get(INT_ZERO).getStockName());
        verify(typeRepository, times(1)).findByCatergory(any());
    }

    @Test
    public void testGetStockByType_NotFound() {
        when(typeRepository.findByCatergory(any())).thenReturn(Optional.empty());
        Exception e = assertThrows(Exception.class, () -> typesService.getStockByType(ProductCatergory.SPORTS_EQUIPMENT.toString()));
        assertEquals(TYPE_NON_FOUND+ProductCatergory.SPORTS_EQUIPMENT, e.getMessage());
    }

    private TypeStock createTypeStock() {
        TypeStock typeStock = new TypeStock();
        typeStock.setCatergory(ProductCatergory.SPORTS_EQUIPMENT);
        Stock stock = new Stock();
        stock.setRemainingStock(INT_ONE);
        stock.setStockName(HANDBALL);
        List<Stock> stockList = List.of(stock);
        typeStock.setStock(stockList);
        return typeStock;
    }
}
