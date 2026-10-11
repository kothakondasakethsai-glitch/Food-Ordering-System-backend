package com.foodordering.config;

import com.foodordering.model.FoodItem;
import com.foodordering.model.Restaurant;
import com.foodordering.model.Role;
import com.foodordering.model.User;
import com.foodordering.repository.FoodItemRepository;
import com.foodordering.repository.RestaurantRepository;
import com.foodordering.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;
import java.util.Arrays;

@Configuration
@RequiredArgsConstructor
public class DataInitializer {

    private final UserRepository userRepository;
    private final RestaurantRepository restaurantRepository;
    private final FoodItemRepository foodItemRepository;
    private final PasswordEncoder passwordEncoder;

    @Bean
    public CommandLineRunner initDatabase() {
        return args -> {
            // Seed Admin User
            if (!userRepository.existsByEmail("admin@foodorder.com")) {
                User admin = User.builder()
                        .name("System Admin")
                        .email("admin@foodorder.com")
                        .password(passwordEncoder.encode("admin123"))
                        .phone("9876543210")
                        .address("Admin HQ, Central Avenue")
                        .role(Role.ROLE_ADMIN)
                        .build();
                userRepository.save(admin);
            }

            // Seed Customer User
            if (!userRepository.existsByEmail("customer@example.com")) {
                User customer = User.builder()
                        .name("John Doe")
                        .email("customer@example.com")
                        .password(passwordEncoder.encode("customer123"))
                        .phone("9123456780")
                        .address("123 Baker Street, Downtown")
                        .role(Role.ROLE_CUSTOMER)
                        .build();
                userRepository.save(customer);
            }

            // Seed Sample Restaurants and Dishes if empty
            if (restaurantRepository.count() == 0) {
                Restaurant r1 = Restaurant.builder()
                        .name("Spice Symphony")
                        .description("Authentic Indian curries, tandoori specialties and aromatic biryanis.")
                        .cuisine("Indian")
                        .address("104 Market Road, City Center")
                        .phone("040-23456781")
                        .imageUrl("https://images.unsplash.com/photo-1517248135467-4c7edcad34c4?auto=format&fit=crop&w=800&q=80")
                        .rating(4.8)
                        .active(true)
                        .build();

                Restaurant r2 = Restaurant.builder()
                        .name("Bella Italia Bistro")
                        .description("Wood-fired handcrafted pizzas, creamy pasta and authentic gelato.")
                        .cuisine("Italian")
                        .address("45 Hill View Road")
                        .phone("040-23456782")
                        .imageUrl("https://images.unsplash.com/photo-1555396273-367ea4eb4db5?auto=format&fit=crop&w=800&q=80")
                        .rating(4.6)
                        .active(true)
                        .build();

                Restaurant r3 = Restaurant.builder()
                        .name("Tokyo Sushi & Ramen Bar")
                        .description("Fresh sashimi, classic nigiri, rich tonkotsu ramen and gyoza.")
                        .cuisine("Japanese")
                        .address("78 Cyber Towers Road")
                        .phone("040-23456783")
                        .imageUrl("https://images.unsplash.com/photo-1579871494447-9811cf80d66c?auto=format&fit=crop&w=800&q=80")
                        .rating(4.9)
                        .active(true)
                        .build();

                restaurantRepository.saveAll(Arrays.asList(r1, r2, r3));

                // Dishes for Spice Symphony
                FoodItem f1 = FoodItem.builder()
                        .restaurant(r1)
                        .name("Butter Chicken Deluxe")
                        .description("Tender chicken cooked in rich buttery tomato gravy with cream.")
                        .price(new BigDecimal("340.00"))
                        .category("Main Course")
                        .isVegetarian(false)
                        .isAvailable(true)
                        .imageUrl("https://images.unsplash.com/photo-1603894584373-5ac82b2ae398?auto=format&fit=crop&w=600&q=80")
                        .build();

                FoodItem f2 = FoodItem.builder()
                        .restaurant(r1)
                        .name("Paneer Tikka Masala")
                        .description("Grilled cottage cheese cubes in spiced aromatic onion-tomato gravy.")
                        .price(new BigDecimal("290.00"))
                        .category("Main Course")
                        .isVegetarian(true)
                        .isAvailable(true)
                        .imageUrl("https://images.unsplash.com/photo-1631452180519-c014fe946bc7?auto=format&fit=crop&w=600&q=80")
                        .build();

                FoodItem f3 = FoodItem.builder()
                        .restaurant(r1)
                        .name("Hyderabadi Dum Biryani")
                        .description("Fragrant basmati rice layered with spiced marinated meat and saffron.")
                        .price(new BigDecimal("380.00"))
                        .category("Main Course")
                        .isVegetarian(false)
                        .isAvailable(true)
                        .imageUrl("https://images.unsplash.com/photo-1563379091339-03b21ab4a4f8?auto=format&fit=crop&w=600&q=80")
                        .build();

                // Dishes for Bella Italia Bistro
                FoodItem f4 = FoodItem.builder()
                        .restaurant(r2)
                        .name("Margherita Pizza")
                        .description("San Marzano tomato sauce, fresh mozzarella, and aromatic basil.")
                        .price(new BigDecimal("399.00"))
                        .category("Main Course")
                        .isVegetarian(true)
                        .isAvailable(true)
                        .imageUrl("https://images.unsplash.com/photo-1604382355076-af4b0eb60143?auto=format&fit=crop&w=600&q=80")
                        .build();

                FoodItem f5 = FoodItem.builder()
                        .restaurant(r2)
                        .name("Fettuccine Alfredo")
                        .description("Creamy parmesan cheese sauce tossed with fresh fettuccine pasta.")
                        .price(new BigDecimal("350.00"))
                        .category("Main Course")
                        .isVegetarian(true)
                        .isAvailable(true)
                        .imageUrl("https://images.unsplash.com/photo-1645112411341-6c4fd023714a?auto=format&fit=crop&w=600&q=80")
                        .build();

                // Dishes for Tokyo Sushi
                FoodItem f6 = FoodItem.builder()
                        .restaurant(r3)
                        .name("Salmon Nigiri & Roll Platter")
                        .description("Assorted fresh salmon nigiri, avocado maki rolls with wasabi and pickled ginger.")
                        .price(new BigDecimal("550.00"))
                        .category("Main Course")
                        .isVegetarian(false)
                        .isAvailable(true)
                        .imageUrl("https://images.unsplash.com/photo-1611143669185-af224c5e3252?auto=format&fit=crop&w=600&q=80")
                        .build();

                foodItemRepository.saveAll(Arrays.asList(f1, f2, f3, f4, f5, f6));
            }
        };
    }
}
