package com.spring.data.core.security;

import com.spring.data.domain.repository.OrderRepository;
import com.spring.data.domain.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

@Component
public class FoodSecurity {

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private OrderRepository orderRepository;

    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public boolean isAuthenticated() {
        return getAuthentication().isAuthenticated();
    }

    public Long getUserId() {
        Jwt jwt = (Jwt) getAuthentication().getPrincipal();

        return jwt.getClaim("user_id");
    }

    public boolean manageRestaurant(Long restaurantId) {
        if (restaurantId == null) {
            return false;
        }

        return restaurantRepository.existsResponsible(restaurantId, getUserId());
    }

    public boolean manageRestaurantOrder(String codeOrder) {
        return orderRepository.isOrderManagedBy(codeOrder, getUserId());
    }

    public boolean userAuthenticatedIgual(Long userId) {
        return getUserId() != null && userId != null
                && getUserId().equals(userId);
    }

    public boolean hasAuthority(String authorityName) {
        return getAuthentication().getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals(authorityName));
    }

    public boolean hasScopeWrite() {
        return hasAuthority("SCOPE_WRITE");
    }

    public boolean hasScopeRead() {
        return hasAuthority("SCOPE_READ");
    }

    public boolean canManageOrders(String codeOrder) {
        return hasScopeWrite() && (hasAuthority("MANAGE_ORDERS")
                || manageRestaurantOrder(codeOrder));
    }

    public boolean canQueryRestaurants() {
        return hasScopeRead() && isAuthenticated();
    }

    public boolean canManageRegisterRestaurants() {
        return hasScopeWrite() && hasAuthority("EDIT_RESTAURANTS");
    }

    public boolean canManageOperationRestaurants(Long restaurantId) {
        return hasScopeWrite() && (hasAuthority("EDIT_RESTAURANTS")
                || manageRestaurant(restaurantId));
    }

    public boolean canQueryUsersPartyPermissions() {
        return hasScopeRead() && hasAuthority("QUERY_USERS_PARTIES_PERMISSIONS");
    }

    public boolean canEditUsersPartyPermissions() {
        return hasScopeWrite() && hasAuthority("EDIT_USERS_PARTIES_PERMISSIONS");
    }

    public boolean canSearchOrders(Long clientId, Long restaurantId) {
        return hasScopeRead() && (hasAuthority("QUERY_ORDERS")
                || userAuthenticatedIgual(clientId) || manageRestaurant(restaurantId));
    }

    public boolean canSearchOrders() {
        return isAuthenticated() && hasScopeRead();
    }

    public boolean canQueryPayment() {
        return isAuthenticated() && hasScopeRead();
    }

    public boolean canQueryCities() {
        return isAuthenticated() && hasScopeRead();
    }

    public boolean canQueryStates() {
        return isAuthenticated() && hasScopeRead();
    }

    public boolean canQueryKitchens() {
        return isAuthenticated() && hasScopeRead();
    }

    public boolean canQueryStatistics() {
        return hasScopeRead() && hasAuthority("GENERATE_REPORTS");
    }

}
