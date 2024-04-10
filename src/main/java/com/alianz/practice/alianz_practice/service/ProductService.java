package com.alianz.practice.alianz_practice.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alianz.practice.alianz_practice.Entity.Product;
import com.alianz.practice.alianz_practice.Entity.ProductCatergory;
import com.alianz.practice.alianz_practice.builder.ProductBuilder;
import com.alianz.practice.alianz_practice.exceptions.ProductCouldNotBeAdderException;
import com.alianz.practice.alianz_practice.helper.StockHelper;
import com.alianz.practice.alianz_practice.repository.ProductRespository;
import com.alianz.practice.alianz_practice.requests.CreateProductRequest;

import jakarta.annotation.PostConstruct;

@Service
public class ProductService {

    private static final String PRODUCT_NOT_FOUND = "Product not found id : ";
    private static final String PRODUCT_ADD_ERROR = "Product could not be added";

    private static final int INT_ONE = 1;

    @Autowired
    private ProductRespository repo;

    @Autowired
    private StockHelper typeHelper;

    public Product getProductById(String id) {
        return repo.findById(id).orElseThrow(() -> new NoSuchElementException(PRODUCT_NOT_FOUND + id));
    }

    public void deleteProductById(String id) {
        if (id != null && repo.existsById(id)) {
            Product product = getProductById(id);
            repo.deleteById(id);
            typeHelper.deleteStock(product.getName(), product.getType());

        } else {
            throw new NoSuchElementException(PRODUCT_NOT_FOUND + id);
        }
    }

    public void updateProduct(CreateProductRequest request, String id) {
        Product product = ProductBuilder.from(request).build();
        typeHelper.deleteStock(product.getName(), product.getType());
        if (product != null && repo.existsById(id)) {
            product.setProductId(id);
            repo.save(product);
            typeHelper.storeAndGet(product);
        } else {
            throw new NoSuchElementException(PRODUCT_NOT_FOUND + id);
        }
    }

    public String addProduct(CreateProductRequest request) {
        Product product = ProductBuilder.from(request).build();
        try {
            repo.save(product);
            typeHelper.storeAndGet(product);
            return product.getProductId();
        } catch (Exception e) {
            throw new ProductCouldNotBeAdderException(PRODUCT_ADD_ERROR);
        }
    }

    public List<String> getAllProductIds() {
        return repo.findAll().stream().map(Product::getProductId).collect(Collectors.toList());
    }


    @PostConstruct
    public void loadProducts() {
        Random random = new Random();
        String[] productNames = { "Smartphone", "Laptop", "Smartwatch", "Television", "Camera", "Headphones", "T-Shirt",
                "Jeans", "Dress Shirt", "Skirt", "Novel", "Cookbook", "Self-Help Book", "Blender", "Toaster",
                "Microwave", "Vacuum Cleaner", "Treadmill", "Dumbbells", "Yoga Mat", "Shampoo", "Skin Cream",
                "Protein Shake", "Energy Bar", "Coffee", "Wine", "Tea", "Sofa", "Dining Table", "Bed Frame",
                "Bookshelf", "Teddy Bear", "Lego Set", "Puzzle", "Necklace", "Bracelet", "Earrings", "Car",
                "Motorcycle", "Bike", "Pet Food", "Pet Toy", "Pet Bed", "Printer", "Scanner", "Desk Lamp", "Notebook",
                "Pen", "Folder", "DVD", "Blu-Ray", "Vinyl Record", "Digital Download" };
        String[] productDescriptions = {
                "A powerful handheld device with advanced features for communication and computing.",
                "A portable computer with a keyboard and screen for personal or professional use.",
                "A wearable device with various functionalities like fitness tracking and notifications.",
                "An electronic device with a screen for viewing broadcast television programs.",
                "A device used to capture photographs and videos.",
                "Audio equipment worn on the head to listen to audio content privately.",
                "A garment with short sleeves and a round neckline, typically made of cotton.",
                "A type of pants made of denim fabric.",
                "A formal shirt worn with a suit jacket or blazer.",
                "A garment worn by women or girls that hangs from the waist and covers part of the legs.",
                "A long written fictional narrative.",
                "A book containing recipes and instructions for cooking.",
                "A book providing advice or guidance on personal development or problem-solving.",
                "An appliance used to mix or purée food ingredients.",
                "An appliance used to brown bread by exposing it to radiant heat.",
                "An appliance used to cook or heat food by dielectric heating.",
                "A device used to suck up dirt and dust from floors and surfaces.",
                "A device used for running or walking in one place.",
                "Weights used for physical exercise.",
                "A piece of equipment used for yoga practice.",
                "A hair care product used for cleaning the scalp and hair.",
                "A cosmetic applied to the skin to improve its appearance.",
                "A dietary supplement used for muscle building or recovery.",
                "A snack bar containing ingredients intended to provide a quick energy boost.",
                "A brewed beverage made from roasted coffee beans.",
                "An alcoholic beverage made from fermented grapes.",
                "A beverage made by steeping dried leaves in hot water.",
                "A long upholstered piece of furniture for seating multiple people.",
                "A piece of furniture with a flat top and one or more legs, used as a surface for eating.",
                "A piece of furniture used for sleeping and relaxation.",
                "A piece of furniture with shelves for storing books.",
                "A stuffed toy bear.",
                "A set of interlocking plastic bricks for building models.",
                "A game or toy designed to test ingenuity or knowledge.",
                "A piece of jewelry worn around the neck.",
                "A piece of jewelry worn around the wrist.",
                "A piece of jewelry worn on the earlobe.",
                "A wheeled motor vehicle used for transportation.",
                "A two-wheeled vehicle used for transportation.",
                "A two-wheeled pedal-driven vehicle.",
                "Food formulated and intended for consumption by pets.",
                "A plaything for a pet animal to chew on or play with.",
                "A piece of furniture designed for a pet to sleep on.",
                "A machine used for printing text or images onto paper.",
                "A device used to capture images or documents and convert them into digital format.",
                "A lamp designed for use on a desk.",
                "A book with blank pages for writing notes.",
                "A tool used for writing or drawing with ink.",
                "A folded cover or holder for loose papers.",
                "A digital optical disc storage format.",
                "A digital optical disc storage format for high-definition video.",
                "A format for storing audio recordings on vinyl discs.",
                "A digital file or software available for download from the internet."
        };

        for (int i = 0; i < 50; i++) {
            double price = 50.0 + (150.0 - 50.0) * random.nextDouble();
            int randName = random.nextInt(productNames.length);
            String name = productNames[randName];
            String description = productDescriptions[randName];
            ProductCatergory type = ProductCatergory.parse(random.nextInt(ProductCatergory.values().length) + INT_ONE);
            Product product = new Product(String.valueOf(i), name, description, type, price);
            repo.save(product);
            typeHelper.storeAndGet(product);
        }

    }

    public void deleteAllProducts() {
        repo.deleteAll();
        typeHelper.deleteAllStocks();
    }
}
