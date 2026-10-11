package com.foodordering.service;

import com.foodordering.model.FoodItem;
import com.foodordering.model.Restaurant;
import com.foodordering.repository.FoodItemRepository;
import com.foodordering.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FoodItemService {

    private final FoodItemRepository foodItemRepository;
    private final RestaurantRepository restaurantRepository;

    public List<FoodItem> getFoodItemsByRestaurant(Long restaurantId, boolean onlyAvailable) {
        if (onlyAvailable) {
            return foodItemRepository.findByRestaurantIdAndIsAvailableTrue(restaurantId);
        }
        return foodItemRepository.findByRestaurantId(restaurantId);
    }

    public FoodItem getFoodItemById(Long id) {
        return foodItemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Food item not found with id: " + id));
    }

    public FoodItem createFoodItem(Long restaurantId, FoodItem foodItem) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new RuntimeException("Restaurant not found with id: " + restaurantId));
        foodItem.setRestaurant(restaurant);
        return foodItemRepository.save(foodItem);
    }

    public FoodItem updateFoodItem(Long id, FoodItem updatedData) {
        FoodItem item = getFoodItemById(id);
        item.setName(updatedData.getName());
        item.setDescription(updatedData.getDescription());
        item.setPrice(updatedData.getPrice());
        item.setCategory(updatedData.getCategory());
        item.setImageUrl(updatedData.getImageUrl());
        if (updatedData.getIsVegetarian() != null) {
            item.setIsVegetarian(updatedData.getIsVegetarian());
        }
        if (updatedData.getIsAvailable() != null) {
            item.setIsAvailable(updatedData.getIsAvailable());
        }
        return foodItemRepository.save(item);
    }

    public void deleteFoodItem(Long id) {
        foodItemRepository.deleteById(id);
    }
}
