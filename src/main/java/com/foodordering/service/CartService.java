package com.foodordering.service;

import com.foodordering.dto.CartItemRequest;
import com.foodordering.model.Cart;
import com.foodordering.model.CartItem;
import com.foodordering.model.FoodItem;
import com.foodordering.model.User;
import com.foodordering.repository.CartItemRepository;
import com.foodordering.repository.CartRepository;
import com.foodordering.repository.FoodItemRepository;
import com.foodordering.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final FoodItemRepository foodItemRepository;
    private final UserRepository userRepository;

    public Cart getOrCreateCart(Long userId) {
        return cartRepository.findByUserId(userId).orElseGet(() -> {
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));
            Cart newCart = Cart.builder().user(user).build();
            return cartRepository.save(newCart);
        });
    }

    @Transactional
    public Cart addItemToCart(Long userId, CartItemRequest request) {
        Cart cart = getOrCreateCart(userId);
        FoodItem foodItem = foodItemRepository.findById(request.getFoodItemId())
                .orElseThrow(() -> new RuntimeException("Food item not found with id: " + request.getFoodItemId()));

        Optional<CartItem> existingItem = cartItemRepository.findByCartIdAndFoodItemId(cart.getId(), foodItem.getId());

        if (existingItem.isPresent()) {
            CartItem item = existingItem.get();
            item.setQuantity(item.getQuantity() + request.getQuantity());
            if (item.getQuantity() <= 0) {
                cart.getItems().remove(item);
                cartItemRepository.delete(item);
            } else {
                cartItemRepository.save(item);
            }
        } else {
            if (request.getQuantity() > 0) {
                CartItem newItem = CartItem.builder()
                        .cart(cart)
                        .foodItem(foodItem)
                        .quantity(request.getQuantity())
                        .build();
                cart.getItems().add(newItem);
                cartItemRepository.save(newItem);
            }
        }

        return cartRepository.save(cart);
    }

    @Transactional
    public Cart updateCartItemQuantity(Long userId, Long cartItemId, Integer quantity) {
        Cart cart = getOrCreateCart(userId);
        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new RuntimeException("Cart item not found with id: " + cartItemId));

        if (!cartItem.getCart().getId().equals(cart.getId())) {
            throw new RuntimeException("Unauthorized cart item modification");
        }

        if (quantity <= 0) {
            cart.getItems().remove(cartItem);
            cartItemRepository.delete(cartItem);
        } else {
            cartItem.setQuantity(quantity);
            cartItemRepository.save(cartItem);
        }

        return cartRepository.save(cart);
    }

    @Transactional
    public Cart removeCartItem(Long userId, Long cartItemId) {
        return updateCartItemQuantity(userId, cartItemId, 0);
    }

    @Transactional
    public void clearCart(Long userId) {
        Cart cart = getOrCreateCart(userId);
        cart.getItems().clear();
        cartRepository.save(cart);
    }
}
