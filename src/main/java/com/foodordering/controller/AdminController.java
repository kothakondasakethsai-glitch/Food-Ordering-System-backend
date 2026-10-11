package com.foodordering.controller;

import com.foodordering.dto.UpdateOrderStatusRequest;
import com.foodordering.model.FoodItem;
import com.foodordering.model.Order;
import com.foodordering.model.Restaurant;
import com.foodordering.model.User;
import com.foodordering.service.FoodItemService;
import com.foodordering.service.OrderService;
import com.foodordering.service.RestaurantService;
import com.foodordering.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@PreAuthorize("hasAuthority('ROLE_ADMIN')")
public class AdminController {

    private final RestaurantService restaurantService;
    private final FoodItemService foodItemService;
    private final OrderService orderService;
    private final UserService userService;

    // --- Restaurant Management ---
    @GetMapping("/restaurants")
    public ResponseEntity<List<Restaurant>> getAllRestaurants() {
        return ResponseEntity.ok(restaurantService.getAllRestaurantsForAdmin());
    }

    @PostMapping("/restaurants")
    public ResponseEntity<Restaurant> createRestaurant(@RequestBody Restaurant restaurant) {
        return ResponseEntity.ok(restaurantService.createRestaurant(restaurant));
    }

    @PutMapping("/restaurants/{id}")
    public ResponseEntity<Restaurant> updateRestaurant(@PathVariable Long id, @RequestBody Restaurant restaurant) {
        return ResponseEntity.ok(restaurantService.updateRestaurant(id, restaurant));
    }

    @DeleteMapping("/restaurants/{id}")
    public ResponseEntity<Void> deleteRestaurant(@PathVariable Long id) {
        restaurantService.deleteRestaurant(id);
        return ResponseEntity.ok().build();
    }

    // --- Food Item Management ---
    @PostMapping("/restaurants/{restaurantId}/food-items")
    public ResponseEntity<FoodItem> createFoodItem(@PathVariable Long restaurantId, @RequestBody FoodItem foodItem) {
        return ResponseEntity.ok(foodItemService.createFoodItem(restaurantId, foodItem));
    }

    @PutMapping("/food-items/{id}")
    public ResponseEntity<FoodItem> updateFoodItem(@PathVariable Long id, @RequestBody FoodItem foodItem) {
        return ResponseEntity.ok(foodItemService.updateFoodItem(id, foodItem));
    }

    @DeleteMapping("/food-items/{id}")
    public ResponseEntity<Void> deleteFoodItem(@PathVariable Long id) {
        foodItemService.deleteFoodItem(id);
        return ResponseEntity.ok().build();
    }

    // --- Order Management ---
    @GetMapping("/orders")
    public ResponseEntity<List<Order>> getAllOrders() {
        return ResponseEntity.ok(orderService.getAllOrdersForAdmin());
    }

    @PutMapping("/orders/{orderId}/status")
    public ResponseEntity<Order> updateOrderStatus(
            @PathVariable Long orderId,
            @Valid @RequestBody UpdateOrderStatusRequest request) {
        return ResponseEntity.ok(orderService.updateOrderStatus(orderId, request.getStatus()));
    }

    // --- User Management ---
    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok().build();
    }
}
