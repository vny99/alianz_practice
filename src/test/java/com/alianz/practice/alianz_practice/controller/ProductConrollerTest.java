package com.alianz.practice.alianz_practice.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;

import com.alianz.practice.alianz_practice.Entity.Product;
import com.alianz.practice.alianz_practice.Entity.ProductCatergory;
import com.alianz.practice.alianz_practice.exceptions.ProductCouldNotBeAdderException;
import com.alianz.practice.alianz_practice.repository.ProductRespository;
import com.alianz.practice.alianz_practice.requests.CreateProductRequest;
import com.alianz.practice.alianz_practice.service.ProductService;

import jakarta.annotation.Resource;

public class ProductConrollerTest {
    @InjectMocks
    @Resource
    private ProductController productConroller; 

    @Mock
    private ProductService productService;

    @Mock
    private BindingResult bindingResult;

    private static final String STATE = "STATE";
    private static final String STATUS = "STATUS";
    private static final String PAYLOAD = "PAYLOAD";
    private static final String SUCCESS = "SUCCESS";
    private static final String ERROR = "ERROR";
    private static final String PRODUCT_ID = "1";
    private static final String PRODUCT_NAME = "Product 1";
    private static final String PRODUCT_DESCRIPTION = "Product 1 description";
    private static final Double PRODUCT_PRICE = 100.0;  
    private static final String PRODUCT_NOT_FOUND = "Product with not found id : ";
    private static final String PRODUCT_DELETED_SUCCESSFULLY = "Product deleted successfully id : ";
    private static final String PRODUCT_UPDATED_SUCCESSFULLY = "Product updated successfully id : ";
    private static final String PRODUCT_ADDED_SUCCESSFULLY = "Product added successfully id : ";
    private static final String PRODUCTS_LOADED_SUCCESSFULLY = "Products loaded sucessfully";
    private static final String PRODUCTS_DELETED_SUCCESSFULLY = "Products deleted sucessfully";
    private static final String PRODUCT_ADDED_FAILURE = "Product could not be added";
    

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetProductById_Found() {
        Product product = getProduct();

        when(productService.getProductById(PRODUCT_ID)).thenReturn(product);

        Map<String, Object> response = productConroller.getProductById(PRODUCT_ID).getBody();

        assertEquals(HttpStatus.OK, response.get(STATUS));
        assertEquals(product, response.get(PAYLOAD));
        assertEquals(SUCCESS, response.get(STATE));
    }

    @Test
    public void testGetProductById_NotFound() {
        when(productService.getProductById(PRODUCT_ID)).thenThrow(new NoSuchElementException(PRODUCT_NOT_FOUND + PRODUCT_ID));
       Exception exception =  assertThrows(NoSuchElementException.class, () -> {
        productConroller.getProductById(PRODUCT_ID);
       });
       assertEquals(PRODUCT_NOT_FOUND + PRODUCT_ID, exception.getMessage());
    }

    @Test
    public void testDeleteProductById_Found() {
       doNothing().when(productService).deleteProductById(PRODUCT_ID);

        Map<String, Object> response = productConroller.deleteProductById(PRODUCT_ID).getBody();

        assertEquals(HttpStatus.OK, response.get(STATUS));
        assertEquals(PRODUCT_DELETED_SUCCESSFULLY + PRODUCT_ID, response.get(PAYLOAD));
        assertEquals(SUCCESS, response.get(STATE));
    }

    @Test
    public void testDeleteProductById_NOTFound() {
        doThrow(new NoSuchElementException(PRODUCT_NOT_FOUND + PRODUCT_ID)).when(productService).deleteProductById(PRODUCT_ID);
       Exception exception =  assertThrows(NoSuchElementException.class, () -> {
        productConroller.deleteProductById(PRODUCT_ID);
       });
       assertEquals(PRODUCT_NOT_FOUND + PRODUCT_ID, exception.getMessage());
    }

    @Test
    public void testUpdateProduct_Found() {
        CreateProductRequest product = getCreateProductRequest();

        doNothing().when(productService).updateProduct(product, PRODUCT_ID);

        Map<String, Object> response = productConroller.updateProduct(PRODUCT_ID, product, bindingResult).getBody();

        assertEquals(HttpStatus.OK, response.get(STATUS));
        assertEquals(PRODUCT_UPDATED_SUCCESSFULLY, response.get(PAYLOAD));
        assertEquals(SUCCESS, response.get(STATE));
    }

    @Test
    public void testUpdateProduct_NOTFound() {
        CreateProductRequest product = getCreateProductRequest();

        doThrow(new NoSuchElementException(PRODUCT_NOT_FOUND + PRODUCT_ID)).when(productService).updateProduct(product, PRODUCT_ID);
       Exception exception =  assertThrows(NoSuchElementException.class, () -> {
        productConroller.updateProduct(PRODUCT_ID, product, bindingResult);
       });
       assertEquals(PRODUCT_NOT_FOUND + PRODUCT_ID, exception.getMessage());
    }

    @Test
    public void testUpdateProduct_InvalidRequest() {
        CreateProductRequest product = getCreateProductRequest();

        when(bindingResult.hasErrors()).thenReturn(true);

        Map<String, Object> response = productConroller.updateProduct(PRODUCT_ID, product, bindingResult).getBody();

        assertEquals(HttpStatus.BAD_REQUEST, response.get(STATUS));
        assertEquals(ERROR, response.get(STATE));
    }

    @Test
    public void testAddProduct() {
        CreateProductRequest product = getCreateProductRequest();

        when(productService.addProduct(product)).thenReturn(PRODUCT_ID);

        Map<String, Object> response = productConroller.addProduct(product, bindingResult).getBody();

        assertEquals(HttpStatus.OK, response.get(STATUS));
        assertEquals(PRODUCT_ADDED_SUCCESSFULLY + PRODUCT_ID, response.get(PAYLOAD));
        assertEquals(SUCCESS, response.get(STATE));
    }

    @Test
    public void testAddProduct_InternelError() {
        CreateProductRequest product = getCreateProductRequest();

        when(productService.addProduct(product)).thenThrow(new ProductCouldNotBeAdderException(PRODUCT_ADDED_FAILURE));

       Exception e = assertThrows(ProductCouldNotBeAdderException.class, () -> {
        productConroller.addProduct(product, bindingResult);
       });
         assertEquals(PRODUCT_ADDED_FAILURE, e.getMessage());
    }

    @Test
    public void testAddProduct_InvalidRequest() {
        CreateProductRequest product = getCreateProductRequest();

        when(bindingResult.hasErrors()).thenReturn(true);

        Map<String, Object> response = productConroller.addProduct(product, bindingResult).getBody();

        assertEquals(HttpStatus.BAD_REQUEST, response.get(STATUS));
        assertEquals(ERROR, response.get(STATE));
    }

    @Test
    public void testLoadProducts() {
        doNothing().when(productService).loadProducts();

        Map<String, Object> response = productConroller.loadProducts().getBody();

        assertEquals(HttpStatus.OK, response.get(STATUS));
        assertEquals(PRODUCTS_LOADED_SUCCESSFULLY, response.get(PAYLOAD));
        assertEquals(SUCCESS, response.get(STATE));
    }



    @Test
    public void testDeleteProducts() {
        doNothing().when(productService).deleteAllProducts();

        Map<String, Object> response = productConroller.unloadProducts().getBody();

        assertEquals(HttpStatus.OK, response.get(STATUS));
        assertEquals(PRODUCTS_DELETED_SUCCESSFULLY, response.get(PAYLOAD));
        assertEquals(SUCCESS, response.get(STATE));
    }


    private Product getProduct() {
        Product product = new Product();
        product.setProductId(PRODUCT_ID);
        product.setName(PRODUCT_NAME);
        product.setDescription(PRODUCT_DESCRIPTION);
        product.setPrice(PRODUCT_PRICE);
        product.setType(ProductCatergory.ELECTRONICS);
        return product;
    }

    private CreateProductRequest getCreateProductRequest() {
        CreateProductRequest request = new CreateProductRequest();
        request.setProductName(PRODUCT_NAME);
        request.setProductDescription(PRODUCT_DESCRIPTION);
        request.setProductPrice(PRODUCT_PRICE);
        request.setProductType(ProductCatergory.ELECTRONICS.toString());
        return request;
    }

   
}
