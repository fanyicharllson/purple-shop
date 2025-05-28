package org.charllson.ecormmerce_website.service;

import org.charllson.ecormmerce_website.model.Product;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductService {
    private static ProductService instance;
    private Map<Integer, Product> products;

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
        // Cars Category
        addCarsProducts();

        // Sports Category
        addSportsProducts();

        // Shoes Category
        addShoesProducts();

        // Underwears Category
        addUnderwearsProducts();

        // Cloths Category
        addClothsProducts();

        // Vintage Category
        addVintageProducts();
    }

    private void addCarsProducts() {
        // Product 1: Rolls-Royce Phantom
        Product rollsRoyce = new Product(
                1,
                "Rolls-Royce Phantom",
                "Experience unparalleled luxury with the Rolls-Royce Phantom. This flagship model features a powerful 6.75L V12 engine producing 563 horsepower, handcrafted interior with the finest materials, starlight headliner, and state-of-the-art technology.",
                455000,
                499000,
                "Rolls-Royce",
                "Phantom VIII",
                "Cars",
                5.0,
                42,
                3
        );
        rollsRoyce.addImagePath("/org/charllson/ecormmerce_website/images/carImages/rolls royce2.jpg");
        rollsRoyce.addImagePath("/org/charllson/ecormmerce_website/images/carImages/rolls royce3.jpg");
        rollsRoyce.addImagePath("/org/charllson/ecormmerce_website/images/carImages/rolls royce4.jpg");
        rollsRoyce.addImagePath("/org/charllson/ecormmerce_website/images/carImages/rolls royce5.jpg");
        rollsRoyce.setSpecifications(new String[] {
                "6.75L V12 Twin-Turbo", "563 hp", "8-speed Automatic", "5.1 seconds", "155 mph", "14 mpg combined", "4 Years / Unlimited Miles"
        });
        products.put(rollsRoyce.getId(), rollsRoyce);

        // Product 2: Lamborghini Aventador
        Product lamborghini = new Product(
                2,
                "Lamborghini Aventador",
                "The Lamborghini Aventador is the definition of a supercar with its aggressive styling and incredible performance. Powered by a naturally aspirated V12 engine that produces 730 horsepower.",
                393695,
                393695,
                "Lamborghini",
                "Aventador SVJ",
                "Cars",
                4.8,
                38,
                2
        );
        lamborghini.addImagePath("/org/charllson/ecormmerce_website/images/carImages/lamborghini.jpg");
        lamborghini.addImagePath("/org/charllson/ecormmerce_website/images/carImages/lamborghini2.jpg");
        lamborghini.setSpecifications(new String[] {
                "6.5L V12", "730 hp", "7-speed ISR Automated Manual", "2.9 seconds", "217 mph", "11 mpg combined", "3 Years / 36,000 Miles"
        });
        products.put(lamborghini.getId(), lamborghini);
    }

    private void addSportsProducts() {
        // Product 10: Basketball
        Product basketball = new Product(
                10,
                "Professional Basketball",
                "Official size and weight basketball perfect for professional games and training. Made with premium leather for superior grip and durability.",
                89.99,
                119.99,
                "SportsPro",
                "Pro-B100",
                "Sports",
                4.7,
                156,
                25
        );
        basketball.addImagePath("/org/charllson/ecormmerce_website/images/sportsImages/basketball.png");
        basketball.setSpecifications(new String[] {
                "Official Size 7", "Premium Leather", "Indoor/Outdoor", "29.5 inch circumference", "22 oz weight", "FIBA Approved", "1 Year Warranty"
        });
        products.put(basketball.getId(), basketball);

        // Product 11: Tennis Racket
        Product tennisRacket = new Product(
                11,
                "Carbon Fiber Tennis Racket",
                "Professional-grade tennis racket made with carbon fiber for maximum power and control. Perfect for intermediate to advanced players.",
                249.99,
                299.99,
                "TennisAce",
                "Carbon Pro",
                "Sports",
                4.9,
                89,
                15
        );
        tennisRacket.addImagePath("/org/charllson/ecormmerce_website/images/sportsImages/tennis_racket.png");
        tennisRacket.setSpecifications(new String[] {
                "Carbon Fiber Frame", "100 sq in head size", "11.2 oz weight", "27 inch length", "16x19 string pattern", "4 1/4 grip size", "2 Year Warranty"
        });
        products.put(tennisRacket.getId(), tennisRacket);
    }

    private void addShoesProducts() {
        // Product 20: Running Shoes
        Product runningShoes = new Product(
                20,
                "Ultra Boost Running Shoes",
                "Premium running shoes with responsive cushioning and energy return. Features breathable mesh upper and continental rubber outsole for superior traction.",
                179.99,
                199.99,
                "SportsFeet",
                "UltraBoost 22",
                "Shoes",
                4.6,
                234,
                50
        );
        runningShoes.addImagePath("/org/charllson/ecormmerce_website/images/shoesImages/running_shoes.png");
        runningShoes.setSpecifications(new String[] {
                "Boost Midsole", "Primeknit Upper", "Continental Rubber", "Sizes 6-13", "Multiple Colors", "Machine Washable", "1 Year Warranty"
        });
        products.put(runningShoes.getId(), runningShoes);

        // Product 21: Dress Shoes
        Product dressShoes = new Product(
                21,
                "Leather Oxford Dress Shoes",
                "Handcrafted genuine leather oxford shoes perfect for formal occasions. Features classic design with modern comfort technology.",
                299.99,
                349.99,
                "ClassicStyle",
                "Oxford Elite",
                "Shoes",
                4.8,
                167,
                30
        );
        dressShoes.addImagePath("/org/charllson/ecormmerce_website/images/shoesImages/dress_shoes.png");
        dressShoes.setSpecifications(new String[] {
                "Genuine Leather", "Goodyear Welt", "Leather Sole", "Sizes 7-12", "Black/Brown", "Handcrafted", "2 Year Warranty"
        });
        products.put(dressShoes.getId(), dressShoes);
    }

    private void addUnderwearsProducts() {
        // Product 30: Cotton Boxers
        Product cottonBoxers = new Product(
                30,
                "Premium Cotton Boxer Shorts",
                "Ultra-soft 100% organic cotton boxer shorts with comfortable waistband. Perfect for everyday wear with superior breathability.",
                24.99,
                34.99,
                "ComfortWear",
                "Cotton Classic",
                "Underwears",
                4.5,
                312,
                100
        );
        cottonBoxers.addImagePath("/org/charllson/ecormmerce_website/images/underwearsImages/cotton_boxers.png");
        cottonBoxers.setSpecifications(new String[] {
                "100% Organic Cotton", "Elastic Waistband", "Machine Washable", "Sizes S-XXL", "Multiple Colors", "Tagless Design", "6 Month Warranty"
        });
        products.put(cottonBoxers.getId(), cottonBoxers);

        // Product 31: Sports Briefs
        Product sportsBriefs = new Product(
                31,
                "Athletic Performance Briefs",
                "Moisture-wicking performance briefs designed for active lifestyles. Features anti-odor technology and seamless construction.",
                34.99,
                44.99,
                "ActiveFit",
                "Performance Pro",
                "Underwears",
                4.7,
                198,
                75
        );
        sportsBriefs.addImagePath("/org/charllson/ecormmerce_website/images/underwearsImages/sports_briefs.png");
        sportsBriefs.setSpecifications(new String[] {
                "Moisture-Wicking Fabric", "Anti-Odor Technology", "Seamless Construction", "Sizes S-XL", "Quick Dry", "Stretch Fit", "1 Year Warranty"
        });
        products.put(sportsBriefs.getId(), sportsBriefs);
    }

    private void addClothsProducts() {
        // Product 40: Casual T-Shirt
        Product casualTShirt = new Product(
                40,
                "Premium Cotton T-Shirt",
                "Soft and comfortable 100% cotton t-shirt with modern fit. Perfect for casual wear with durable construction and fade-resistant colors.",
                29.99,
                39.99,
                "CasualWear",
                "Essential Tee",
                "Cloths",
                4.4,
                445,
                200
        );
        casualTShirt.addImagePath("/org/charllson/ecormmerce_website/images/clothsImages/casual_tshirt.png");
        casualTShirt.setSpecifications(new String[] {
                "100% Cotton", "Modern Fit", "Pre-Shrunk", "Sizes XS-XXL", "Multiple Colors", "Machine Washable", "6 Month Warranty"
        });
        products.put(casualTShirt.getId(), casualTShirt);

        // Product 41: Formal Shirt
        Product formalShirt = new Product(
                41,
                "Business Dress Shirt",
                "Professional dress shirt made from premium cotton blend. Features wrinkle-resistant fabric and classic collar design perfect for business settings.",
                79.99,
                99.99,
                "BusinessPro",
                "Executive",
                "Cloths",
                4.6,
                278,
                80
        );
        formalShirt.addImagePath("/org/charllson/ecormmerce_website/images/clothsImages/formal_shirt.png");
        formalShirt.setSpecifications(new String[] {
                "Cotton Blend", "Wrinkle Resistant", "Classic Collar", "Sizes 14-18", "White/Blue/Gray", "Easy Care", "1 Year Warranty"
        });
        products.put(formalShirt.getId(), formalShirt);
    }

    private void addVintageProducts() {
        // Product 50: Vintage Watch
        Product vintageWatch = new Product(
                50,
                "Classic Vintage Watch",
                "Authentic vintage-style mechanical watch with leather strap. Features classic design with modern reliability and water resistance.",
                399.99,
                499.99,
                "TimelessStyle",
                "Heritage Classic",
                "Vintage",
                4.8,
                89,
                12
        );
        vintageWatch.addImagePath("/org/charllson/ecormmerce_website/images/vintageImages/vintage_watch.png");
        vintageWatch.setSpecifications(new String[] {
                "Mechanical Movement", "Leather Strap", "Water Resistant", "Sapphire Crystal", "42mm Case", "Vintage Design", "3 Year Warranty"
        });
        products.put(vintageWatch.getId(), vintageWatch);

        // Product 51: Vintage Camera
        Product vintageCamera = new Product(
                51,
                "Retro Film Camera",
                "Classic 35mm film camera with manual controls. Perfect for photography enthusiasts who appreciate vintage aesthetics and film photography.",
                299.99,
                349.99,
                "RetroPhoto",
                "Classic 35",
                "Vintage",
                4.7,
                67,
                8
        );
        vintageCamera.addImagePath("/org/charllson/ecormmerce_website/images/vintageImages/vintage_camera.png");
        vintageCamera.setSpecifications(new String[] {
                "35mm Film", "Manual Focus", "Built-in Light Meter", "Metal Body", "Leather Grip", "Multiple Lenses", "2 Year Warranty"
        });
        products.put(vintageCamera.getId(), vintageCamera);
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
                if (related.size() >= 4) {
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