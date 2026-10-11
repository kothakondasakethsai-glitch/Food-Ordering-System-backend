package com.foodordering.controller;

import com.foodordering.dto.CartItemRequest;
import com.foodordering.model.Cart;
import com.foodordering.security.UserPrincipal;
import com.foodordering.service.CartService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class CartController {

    private final CartService cartService;

    @GetMapping
    public ResponseEntity<Cart> getCart(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        return ResponseEntity.ok(cartService.getOrCreateCart(userPrincipal.getId()));
    }

    @PostMapping("/items")
    public ResponseEntity<Cart> addItemToCart(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @Valid @RequestBody CartItemRequest request) {
        return ResponseEntity.ok(cartService.addItemToCart(userPrincipal.getId(), request));
    }

    @PutMapping("/items/{itemId}")
    public ResponseEntity<Cart> updateItemQuantity(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @PathVariable Long itemId,
            @RequestParam Integer quantity) {
        return ResponseEntity.ok(cartService.updateCartItemQuantity(userPrincipal.getId(), itemId, quantity));
    }

    @DeleteMapping("/items/{itemId}")
    public ResponseEntity<Cart> removeItem(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @PathVariable Long itemId) {
        return ResponseEntity.ok(cartService.removeCartItem(userPrincipal.getId(), itemId));
    }

    @DeleteMapping("/clear")
    public ResponseEntity<Void> clearCart(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        cartService.clearCart(userPrincipal.getId());
        return ResponseEntity.ok().build();
    }
}
