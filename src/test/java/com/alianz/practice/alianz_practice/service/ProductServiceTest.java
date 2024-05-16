package com.alianz.practice.alianz_practice.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.NoSuchElementException;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.alianz.practice.alianz_practice.Entity.Product;
import com.alianz.practice.alianz_practice.Entity.ProductCategory;
import com.alianz.practice.alianz_practice.exceptions.ProductCouldNotBeAdderException;
import com.alianz.practice.alianz_practice.repository.ProductRespository;
import com.alianz.practice.alianz_practice.requests.CreateProductRequest;

import jakarta.annotation.Resource;

public class ProductServiceTest {
    @InjectMocks
    @Resource
    private ProductService productService;

    @Mock
    private ProductRespository productRespository;

    @Mock
  //  private StockHelper stockHelper;

    private static final String PRODUCT_ID = "1";
    private static final String PRODUCT_NAME = "Product 1";
    private static final String PRODUCT_DESCRIPTION = "Product 1 description";
    private static final String SPORTS = "SPORTS";
    private static final Double PRODUCT_PRICE = 100.0;
    private static final String PRODUCT_NOT_FOUND = "Product not found id : ";
    private static final String PRODUCT_ADDED_FAILURE = "Product could not be added";
    private static final String PRODUCT_LOAD_FAILURE = "Product could not be loaded";

    /**
     * Setup method to initialize the mock objects
     * 
     * @throws Exception
     */
    // @Before
    // public void setUp() throws Exception {
    //     MockitoAnnotations.initMocks(this);
    // }

    // @Test
    // public void testGetProductById_Found() {
    //     Product product = getProduct();
    //     when(productRespository.findById(PRODUCT_ID)).thenReturn(Optional.of(product));
    //     Product result = productService.getProductById(PRODUCT_ID);
    //     assertEquals(product, result);
    // }

    // @Test
    // public void testGetProductById_NotFound() {
    //     when(productRespository.findById(PRODUCT_ID)).thenReturn(Optional.empty());
    //     Exception exception = assertThrows(NoSuchElementException.class,
    //             () -> productService.getProductById(PRODUCT_ID));
    //     assertEquals(PRODUCT_NOT_FOUND + PRODUCT_ID, exception.getMessage());
    // }

    // @Test
    // public void testDeleteProductById_Found() {
    //     when(productRespository.existsById(PRODUCT_ID)).thenReturn(true);
    //     when(productRespository.findById(PRODUCT_ID)).thenReturn(Optional.of(getProduct()));
    //     doNothing().when(stockHelper).deleteStock(PRODUCT_NAME, any(ProductCategory.class));
    //     doNothing().when(productRespository).deleteById(PRODUCT_ID);
    //     productService.deleteProductById(PRODUCT_ID);
    //     verify(productRespository, times(1)).deleteById(PRODUCT_ID);
    //     ;
    // }

    // @Test
    // public void testDeleteProductById_NotFound() {
    //     when(productRespository.existsById(PRODUCT_ID)).thenReturn(false);
    //     Exception exception = assertThrows(NoSuchElementException.class,
    //             () -> productService.deleteProductById(PRODUCT_ID));
    //     assertEquals(PRODUCT_NOT_FOUND + PRODUCT_ID, exception.getMessage());
    // }

    // @Test
    // public void testUpdateProduct_Found() {
    //     Product product = getProduct();
    //     CreateProductRequest request = getCreateProductRequest();
    //     when(productRespository.existsById(PRODUCT_ID)).thenReturn(true);
    //     when(productRespository.save(any(Product.class))).thenReturn(product);
    //     productService.updateProduct(request, PRODUCT_ID);
    //     ArgumentCaptor<Product> productCaptor = ArgumentCaptor.forClass(Product.class);
    //     verify(productRespository, times(1)).save(productCaptor.capture());
    //     Product savedProduct = productCaptor.getValue();

    //     assertEquals(PRODUCT_ID, savedProduct.getProductId());
    //     assertEquals(PRODUCT_NAME, savedProduct.getName());

    // }

    // @Test
    // public void testUpdateProduct_NotFound() {
    //     CreateProductRequest request = getCreateProductRequest();
    //     when(productRespository.existsById(PRODUCT_ID)).thenReturn(false);
    //     Exception exception = assertThrows(NoSuchElementException.class,
    //             () -> productService.updateProduct(request, PRODUCT_ID));
    //     assertEquals(PRODUCT_NOT_FOUND + PRODUCT_ID, exception.getMessage());
    // }

    // @Test
    // public void testAddProduct() {
    //     Product product = getProduct();
    //     CreateProductRequest request = getCreateProductRequest();
    //     when(productRespository.save(product)).thenReturn(product);
    //     productService.addProduct(request);
    //     ArgumentCaptor<Product> productCaptor = ArgumentCaptor.forClass(Product.class);
    //     verify(productRespository, times(1)).save(productCaptor.capture());
    //     Product savedProduct = productCaptor.getValue();
    //     assertNotNull(savedProduct);
    //     assertEquals(PRODUCT_NAME, savedProduct.getName());
    // }

    // @Test
    // public void testAddProduct_Exception() {
    //     CreateProductRequest request = getCreateProductRequest();
    //     when(productRespository.save(any(Product.class)))
    //             .thenThrow(new ProductCouldNotBeAdderException(PRODUCT_ADDED_FAILURE));
    //     Exception exception = assertThrows(ProductCouldNotBeAdderException.class,
    //             () -> productService.addProduct(request));
    //     assertEquals(PRODUCT_ADDED_FAILURE, exception.getMessage());
    // }

    // @Test
    // public void testAddProduct_Null() {
    //     CreateProductRequest request = getCreateProductRequest();
    //     when(productRespository.save(null)).thenReturn(null);
    //     String result = productService.addProduct(request);
    //     assertNull(result);
    // }

    // @Test
    // public void testGetAllProductsIds() {
    //     productService.getAllProductIds();
    //     verify(productRespository, times(1)).findAll();
    // }

    // @Test
    // public void testLoadProducts() {
    //     productService.loadProducts();
    //     ArgumentCaptor<Product> productCaptor = ArgumentCaptor.forClass(Product.class);
    //     verify(productRespository, times(50)).save(productCaptor.capture());

    // }

    // @Test
    // public void testLoadProducts_Exception() {
    //     when(productRespository.save(any(Product.class)))
    //             .thenThrow(new ProductsCanNotBeLoadedException(PRODUCT_LOAD_FAILURE));
    //     Exception exception = assertThrows(ProductsCanNotBeLoadedException.class, () -> productService.loadProducts());
    //     assertEquals(PRODUCT_LOAD_FAILURE, exception.getMessage());
    // }

    // @Test
    // public void testDeleteAllProducts() {
    //     productService.deleteAllProducts();
    //     verify(productRespository, times(1)).deleteAll();
    // }

    // private Product getProduct() {
    //     Product product = new Product();
    //     product.setProductId(PRODUCT_ID);
    //     product.setName(PRODUCT_NAME);
    //     product.setDescription(PRODUCT_DESCRIPTION);
    //     product.setPrice(PRODUCT_PRICE);
    //     product.setType(new ProductCategory(1, SPORTS));
    //     return product;
    // }

    // private CreateProductRequest getCreateProductRequest() {
    //     CreateProductRequest request = new CreateProductRequest();
    //     request.setProductName(PRODUCT_NAME);
    //     request.setProductDescription(PRODUCT_DESCRIPTION);
    //     request.setProductPrice(PRODUCT_PRICE);
    //     request.setProductType(SPORTS);
    //     return request;
    // };
}
