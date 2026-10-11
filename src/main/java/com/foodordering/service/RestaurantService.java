package com.foodordering.service;

import com.foodordering.model.Restaurant;
import com.foodordering.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;

    public List<Restaurant> getAllRestaurants() {
        return restaurantRepository.findByActiveTrue();
    }

    public List<Restaurant> getAllRestaurantsForAdmin() {
        return restaurantRepository.findAll();
    }

    public Restaurant getRestaurantById(Long id) {
        return restaurantRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Restaurant not found with id: " + id));
    }

    public List<Restaurant> searchRestaurants(String query) {
        if (query == null || query.trim().isEmpty()) {
            return getAllRestaurants();
        }
        return restaurantRepository.findByNameContainingIgnoreCaseOrCuisineContainingIgnoreCase(query, query);
    }

    public Restaurant createRestaurant(Restaurant restaurant) {
        return restaurantRepository.save(restaurant);
    }

    public Restaurant updateRestaurant(Long id, Restaurant updatedData) {
        Restaurant restaurant = getRestaurantById(id);
        restaurant.setName(updatedData.getName());
        restaurant.setDescription(updatedData.getDescription());
        restaurant.setCuisine(updatedData.getCuisine());
        restaurant.setAddress(updatedData.getAddress());
        restaurant.setPhone(updatedData.getPhone());
        restaurant.setImageUrl(updatedData.getImageUrl());
        if (updatedData.getRating() != null) {
            restaurant.setRating(updatedData.getRating());
        }
        if (updatedData.getActive() != null) {
            restaurant.setActive(updatedData.getActive());
        }
        return restaurantRepository.save(restaurant);
    }

    public void deleteRestaurant(Long id) {
        restaurantRepository.deleteById(id);
    }
}
