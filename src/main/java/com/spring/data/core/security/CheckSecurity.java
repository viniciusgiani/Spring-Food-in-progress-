package com.spring.data.core.security;

import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

public @interface CheckSecurity {

    public @interface Kitchens {
        @PreAuthorize("hasAuthority('SCOPE_WRITE') and hasAuthority('EDIT_KITCHENS')")
        @Retention(RUNTIME)
        @Target(METHOD)
        public @interface CanEdit { }

        @PreAuthorize("@foodSecurity.canQuery()")
        @Retention(RUNTIME)
        @Target(METHOD)
        public @interface CanQuery { }
    }

    public @interface Restaurants {
        @PreAuthorize("@foodSecurity.canManageRestaurantRegisters()")
        @Retention(RUNTIME)
        @Target(METHOD)
        public @interface CanManageRegister { }

        @PreAuthorize("@foodSecurity.canQueryRestaurants()")
        @Retention(RUNTIME)
        @Target(METHOD)
        public @interface CanQuery { }
    }

    public @interface Orders {
        @PreAuthorize("hasAuthority('SCOPE_READ') and isAuthenticated()")
        @PostAuthorize("hasAuthority('QUERY_ORDERS') or "
                + "@algaSecurity.userAuthenticatedEqual(returnObject.client.id) or "
                + "@algaSecurity.manageRestaurant(returnObject.restaurant.id)")
        @Retention(RUNTIME)
        @Target(METHOD)
        public @interface CanGet { }

        @PreAuthorize("@foodSecurity.canSearchOrders(#filter.clientId, #filter.restaurantId)")
        @Retention(RUNTIME)
        @Target(METHOD)
        public @interface CanSearch { }

        @PreAuthorize("hasAuthority('SCOPE_WRITE') and isAuthenticated()")
        @Retention(RUNTIME)
        @Target(METHOD)
        public @interface CanCreate { }

        @PreAuthorize("@foodSecurity.canManageOrders(#orderCode)")
        @Retention(RUNTIME)
        @Target(METHOD)
        public @interface CanManageOrders { }
    }

    public @interface Payment {
        @PreAuthorize("hasAuthority('SCOPE_WRITE') and hasAuthority('EDIT_PAYMENT_METHODS')")
        @Retention(RUNTIME)
        @Target(METHOD)
        public @interface CanEdit { }

        @PreAuthorize("@foodSecurity.canQueryPayment()")
        @Retention(RUNTIME)
        @Target(METHOD)
        public @interface CanQuery { }
    }

    public @interface Cities {
        @PreAuthorize("hasAuthority('SCOPE_WRITE') and hasAuthority('EDIT_CITIES')")
        @Retention(RUNTIME)
        @Target(METHOD)
        public @interface CanEdit { }

        @PreAuthorize("@foodSecurity.canQueryCities()")
        @Retention(RUNTIME)
        @Target(METHOD)
        public @interface CanQuery { }
    }
    public @interface States {
        @PreAuthorize("hasAuthority('SCOPE_WRITE') and hasAuthority('EDIT_STATES')")
        @Retention(RUNTIME)
        @Target(METHOD)
        public @interface CanEdit { }

        @PreAuthorize("@foodSecurity.canQueryStates()")
        @Retention(RUNTIME)
        @Target(METHOD)
        public @interface CanQuery { }
    }

    public @interface UsersPartyPermissions {
        @PreAuthorize("hasAuthority('SCOPE_WRITE') and"
                + "@foodSecurity.userAuthenticatedEqual(#userId)")
        @Retention(RUNTIME)
        @Target(METHOD)
        public @interface CanAlterOwnPassword { }

        @PreAuthorize("hasAuthority('SCOPE_WRITE') and (hasAuthority('EDIT_USERS_PARTIES_PERMISSIONS') or "
                + "@foodSecurity.userAuthenticatedEqual(#userId))")
        @Retention(RUNTIME)
        @Target(METHOD)
        public @interface CanAlterUser { }

        @PreAuthorize("@foodSecurity.canEditUsersPartiesPermissions()")
        @Retention(RUNTIME)
        @Target(METHOD)
        public @interface CanEdit { }

        @PreAuthorize("@foodSecurity.canQueryUsersPartiesPermissions()")
        @Retention(RUNTIME)
        @Target(METHOD)
        public @interface CanQuery { }
    }

    public @interface Statistics {

        @PreAuthorize("@foodSecurity.canQueryStatistics()")
        @Retention(RUNTIME)
        @Target(METHOD)
        public @interface CanQuery { }
    }

}
