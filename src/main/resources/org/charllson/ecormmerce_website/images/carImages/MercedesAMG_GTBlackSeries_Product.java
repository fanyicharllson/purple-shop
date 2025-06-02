// Product 4: Mercedes
Product mercedes = new Product(
        4,
        "Mercedes-AMG GT Black Series",
        "The Mercedes-AMG GT Black Series represents the pinnacle of Mercedes-AMG engineering. This track-focused supercar features the most powerful AMG V8 production engine ever made, advanced aerodynamics, and race-inspired technology.",
        325000,
        325000,
        "Mercedes-AMG",
        "GT Black Series",
        "Cars",
        4.9,
        32,
        3
);
mercedes.addImagePath("/org/charllson/ecormmerce_website/images/carImages/mercedes1.jpg");
mercedes.addImagePath("/org/charllson/ecormmerce_website/images/carImages/mercedes2.jpg");
mercedes.addImagePath("/org/charllson/ecormmerce_website/images/carImages/mercedes3.jpg");
mercedes.setSpecifications(new String[] {
        "4.0L V8 Biturbo", "720 hp", "7-speed DCT", "3.1 seconds", "202 mph", "15 mpg combined", "4 Years / 50,000 Miles"
});
products.put(mercedes.getId(), mercedes);