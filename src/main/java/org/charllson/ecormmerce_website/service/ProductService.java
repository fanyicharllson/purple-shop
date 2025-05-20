package org.charllson.ecormmerce_website.service;

import org.charllson.ecormmerce_website.model.Product;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductService {
    private static ProductService instance;
    private final Map<Integer, Product> products;

    private ProductService() {
        products = new HashMap<>();
        initializeProducts();
    }

    public static ProductService getInstance() {
        if (instance == null) {
            instance = new ProductService();
        }
        return instance;
    }

    private void initializeProducts() {
        // Product 1: Rolls-Royce Phantom
        Product rollsRoyce = new Product(
                1,
                "Rolls-Royce Phantom",
                "Experience unparalleled luxury with the Rolls-Royce Phantom. This flagship model features a powerful 6.75L V12 engine producing 563 horsepower, handcrafted interior with the finest materials, starlight headliner, and state-of-the-art technology. The Phantom represents the pinnacle of automotive excellence, offering a driving experience unlike any other luxury vehicle on the market.",
                455000,
                499000,
                "Rolls-Royce",
                "Phantom VIII",
                "Luxury",
                5.0,
                42,
                3
        );
        rollsRoyce.addImagePath("/org/charllson/ecormmerce_website/images/carImages/rolls royce2.jpg");
        rollsRoyce.addImagePath("/org/charllson/ecormmerce_website/images/carImages/rolls royce3.jpg");
        rollsRoyce.addImagePath("/org/charllson/ecormmerce_website/images/carImages/rolls royce4.jpg");
        rollsRoyce.addImagePath("/org/charllson/ecormmerce_website/images/carImages/rolls royce5.jpg");
        rollsRoyce.addColor("Black");
        rollsRoyce.addColor("White");
        rollsRoyce.addColor("Purple");
        rollsRoyce.addColor("Blue");
        rollsRoyce.setSpecifications(new String[]{
                "6.75L V12 Twin-Turbo",
                "563 hp",
                "8-speed Automatic",
                "5.1 seconds",
                "155 mph",
                "14 mpg combined",
                "4 Years / Unlimited Miles"
        });
        products.put(rollsRoyce.getId(), rollsRoyce);

        // Product 2: Lamborghini Aventador
        Product lamborghini = new Product(
                2,
                "Lamborghini Aventador",
                "The Lamborghini Aventador is the definition of a supercar with its aggressive styling and incredible performance. Powered by a naturally aspirated V12 engine that produces 730 horsepower, the Aventador accelerates from 0-60 mph in just 2.9 seconds. The carbon fiber monocoque chassis and advanced aerodynamics provide exceptional handling and stability at high speeds.",
                393695,
                393695,
                "Lamborghini",
                "Aventador SVJ",
                "Sports",
                4.8,
                38,
                2
        );
        lamborghini.addImagePath("/org/charllson/ecormmerce_website/images/carImages/lamborghini1.webp");
        lamborghini.addImagePath("/org/charllson/ecormmerce_website/images/carImages/lamborghini2.jpg");
        lamborghini.addImagePath("/org/charllson/ecormmerce_website/images/carImages/lamborghini3.jpg");
        lamborghini.addImagePath("/org/charllson/ecormmerce_website/images/carImages/lamborghini4.jpg");
        lamborghini.addColor("Yellow");
        lamborghini.addColor("Black");
        lamborghini.addColor("White");
        lamborghini.addColor("Green");
        lamborghini.setSpecifications(new String[]{
                "6.5L V12",
                "730 hp",
                "7-speed ISR Automated Manual",
                "2.9 seconds",
                "217 mph",
                "11 mpg combined",
                "3 Years / 36,000 Miles"
        });
        products.put(lamborghini.getId(), lamborghini);

        // Product 3: Bugatti Chiron
        Product bugatti = new Product(
                3,
                "Bugatti Chiron",
                "The Bugatti Chiron combines luxury and performance in a grand touring package. The handcrafted interior features the finest leather, wood, and metal accents. Powered by a W12 engine producing 626 horsepower, the Continental GT delivers effortless acceleration and a smooth, comfortable ride. Advanced technology includes a rotating dashboard display and adaptive chassis.",
                202500,
                219000,
                "Bugatti",
                "Chiron Super Sport 2025",
                "Luxury",
                5.0,
                29,
                5
        );
        bugatti.addImagePath("/org/charllson/ecormmerce_website/images/carImages/buggati.jpg");
        bugatti.addImagePath("/org/charllson/ecormmerce_website/images/carImages/buggati3.jpg");
        bugatti.addColor("Blue");
        bugatti.addColor("Black");
        bugatti.addColor("White");
        bugatti.addColor("Red");
        bugatti.setSpecifications(new String[]{
                "6.0L W12 Twin-Turbo",
                "626 hp",
                "8-speed Dual-Clutch",
                "3.6 seconds",
                "207 mph",
                "15 mpg combined",
                "3 Years / Unlimited Miles"
        });
        products.put(bugatti.getId(), bugatti);

        // Product 4: Mercedes-Benz S-Class
        Product mercedes = new Product(
                4,
                "Mercedes-Benz S-Class",
                "The Mercedes-Benz S-Class sets the standard for luxury sedans with its combination of cutting-edge technology and refined comfort. The cabin features premium materials, ambient lighting, and massaging seats. The latest driver assistance systems provide semi-autonomous driving capabilities. The smooth and powerful engine options deliver effortless performance with exceptional refinement.",
                110000,
                125000,
                "Mercedes-Benz",
                "S580",
                "Sedan",
                4.9,
                45,
                8
        );
        mercedes.addImagePath("/org/charllson/ecormmerce_website/images/carImages/mecedes.jpg");
        mercedes.addImagePath("/org/charllson/ecormmerce_website/images/carImages/mecedes2.jpg");
        mercedes.addImagePath("/org/charllson/ecormmerce_website/images/carImages/mecedes3.jpg");
        mercedes.addImagePath("/org/charllson/ecormmerce_website/images/carImages/mecedes4.jpg");
        mercedes.addColor("Silver");
        mercedes.addColor("Black");
        mercedes.addColor("White");
        mercedes.addColor("Blue");
        mercedes.setSpecifications(new String[]{
                "4.0L V8 Biturbo",
                "496 hp",
                "9-speed Automatic",
                "4.4 seconds",
                "155 mph",
                "20 mpg combined",
                "4 Years / 50,000 Miles"
        });
        products.put(mercedes.getId(), mercedes);

        // Product 5: Ferrari Roma
        Product ferrari = new Product(
                5,
                "Ferrari Roma",
                "The Ferrari Roma is an elegant grand touring sports car that combines timeless design with modern performance. The sleek, minimalist styling houses a 3.9L twin-turbocharged V8 engine producing 612 horsepower. The sophisticated interior features a dual-cockpit design with the latest digital displays and controls. The Roma delivers the perfect balance of everyday usability and thrilling performance.",
                222630,
                222630,
                "Ferrari",
                "Roma",
                "Sports",
                5.0,
                31,
                3
        );
        ferrari.addImagePath("/org/charllson/ecormmerce_website/images/carImages/ferrari2.jpg");
        ferrari.addImagePath("/org/charllson/ecormmerce_website/images/carImages/ferrari3.jpg");
        ferrari.addImagePath("/org/charllson/ecormmerce_website/images/carImages/ferrarri4.jpg");
        ferrari.addColor("Red");
        ferrari.addColor("Silver");
        ferrari.addColor("Blue");
        ferrari.addColor("Black");
        ferrari.setSpecifications(new String[]{
                "3.9L V8 Twin-Turbo",
                "612 hp",
                "8-speed Dual-Clutch",
                "3.4 seconds",
                "199 mph",
                "18 mpg combined",
                "3 Years / Unlimited Miles"
        });
        products.put(ferrari.getId(), ferrari);

        // Product 6: Aston Martin DB11
        Product astonMartin = new Product(
                6,
                "Lamborghini Aventador",
                "The Lamborghini Aventador combines breathtaking design with exhilarating performance. The distinctive aerodynamic elements include the innovative AeroBlade™ that enhances stability without disrupting the car's clean lines. Inside, the handcrafted interior features Bridge of Weir leather and customizable trim options. The powerful V8 or V12 engine options deliver the performance expected from this iconic British brand.",
                205000,
                219000,
                "Lamborghini Aventador",
                "LA25",
                "Sports",
                4.8,
                27,
                4
        );
        astonMartin.addImagePath("/org/charllson/ecormmerce_website/images/carImages/lamborghini1.webp");
        astonMartin.addImagePath("/org/charllson/ecormmerce_website/images/carImages/lamborghini2.jpg");
        astonMartin.addImagePath("/org/charllson/ecormmerce_website/images/carImages/lamborghini3.jpg");
        astonMartin.addImagePath("/org/charllson/ecormmerce_website/images/carImages/lamborghini4.jpg");
        astonMartin.addColor("Green");
        astonMartin.addColor("Silver");
        astonMartin.addColor("Black");
        astonMartin.addColor("Blue");
        astonMartin.setSpecifications(new String[]{
                "4.0L V8 Twin-Turbo",
                "503 hp",
                "8-speed Automatic",
                "4.0 seconds",
                "187 mph",
                "18 mpg combined",
                "3 Years / Unlimited Miles"
        });
        products.put(astonMartin.getId(), astonMartin);
    }

    public Product getProductById(int id) {
        return products.get(id);
    }

    public List<Product> getAllProducts() {
        return new ArrayList<>(products.values());
    }

    public List<Product> getProductsByCategory(String category) {
        List<Product> result = new ArrayList<>();
        for (Product product : products.values()) {
            if (product.getCategory().equalsIgnoreCase(category)) {
                result.add(product);
            }
        }
        return result;
    }

    public List<Product> getRelatedProducts(int currentProductId) {
        Product currentProduct = getProductById(currentProductId);
        List<Product> related = new ArrayList<>();

        if (currentProduct == null) {
            return related;
        }

        // Get products in the same category
        for (Product product : products.values()) {
            if (product.getId() != currentProductId &&
                    product.getCategory().equals(currentProduct.getCategory())) {
                related.add(product);
                if (related.size() >= 4) { // Limit to 4 related products
                    break;
                }
            }
        }

        // If we don't have enough related products, add some from other categories
        if (related.size() < 4) {
            for (Product product : products.values()) {
                if (product.getId() != currentProductId &&
                        !related.contains(product)) {
                    related.add(product);
                    if (related.size() >= 4) {
                        break;
                    }
                }
            }
        }

        return related;
    }
}