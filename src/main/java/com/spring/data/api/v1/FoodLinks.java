package com.spring.data.api.v1;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.hateoas.TemplateVariable.VariableType;
import com.spring.data.api.v1.controller.*;
import org.springframework.hateoas.*;
import org.springframework.stereotype.Component;

@Component
public class FoodLinks {

    public static final TemplateVariables PAGINATION_VARIABLES = new TemplateVariables(
            new TemplateVariable("page", VariableType.REQUEST_PARAM),
            new TemplateVariable("size", VariableType.REQUEST_PARAM),
            new TemplateVariable("sort", VariableType.REQUEST_PARAM)
    );

    public static final TemplateVariables PROJECTION_VARIABLES = new TemplateVariables(
            new TemplateVariable("projection", VariableType.REQUEST_PARAM)
    );

    public Link linkToOrders(String rel) {
        TemplateVariables filterVariables = new TemplateVariables(
                new TemplateVariable("clientId", VariableType.REQUEST_PARAM),
                new TemplateVariable("restaurantId", VariableType.REQUEST_PARAM),
                new TemplateVariable("creationDateStart", VariableType.REQUEST_PARAM),
                new TemplateVariable("creationDateStart", VariableType.REQUEST_PARAM));

        String ordersUrl = linkTo(OrderController.class).toUri().toString();

        return Link.of(UriTemplate.of(ordersUrl,
                PAGINATION_VARIABLES.concat(filterVariables)), rel);
    }

    public Link linkToConfirmationOrder(String codeOrder, String rel) {
        return linkTo(methodOn(FlowOrderController.class)
                .confirm(codeOrder)).withRel(rel);
    }

    public Link linkToDeliverOrder(String codeOrder, String rel) {
        return linkTo(methodOn(FlowOrderController.class)
                .deliver(codeOrder)).withRel(rel);
    }

    public Link linkToCanceledOrder(String codeOrder, String rel) {
        return linkTo(methodOn(FlowOrderController.class)
                .cancel(codeOrder)).withRel(rel);
    }

    public Link linkToRestaurant(Long restaurantId, String rel) {
        return linkTo(methodOn(RestaurantController.class)
                .search(restaurantId)).withRel(rel);
    }

    public Link linkToRestaurant(Long restaurantId) {
        return linkToRestaurant(restaurantId, IanaLinkRelations.SELF.value());
    }

    public Link linkToRestaurants(String rel) {
        String restaurantsUrl = linkTo(RestaurantController.class).toUri().toString();

        return Link.of(UriTemplate.of(restaurantsUrl, PROJECTION_VARIABLES), LinkRelation.of(rel));
    }

    public Link linkToRestaurants() {
        return linkToRestaurants(IanaLinkRelations.SELF.value());
    }

    public Link linkToRestaurantPayment(Long restaurantId, String rel) {
        return linkTo(methodOn(RestaurantPaymentController.class)
                .list(restaurantId)).withRel(rel);
    }

    public Link linkToRestaurantPayment(Long restaurantId) {
        return linkToRestaurantPayment(restaurantId, IanaLinkRelations.SELF.value());
    }

    public Link linkToRestaurantPaymentDissociation(
            Long restaurantId, Long paymentId, String rel) {

        return linkTo(methodOn(RestaurantPaymentController.class)
                .dissociate(restaurantId, paymentId)).withRel(rel);
    }

    public Link linkToRestaurantPaymentAssociation(Long restaurantId, String rel) {
        return linkTo(methodOn(RestaurantPaymentController.class)
                .associate(restaurantId, null)).withRel(rel);
    }

    public Link linkToRestaurantOpening(Long restaurantId, String rel) {
        return linkTo(methodOn(RestaurantController.class)
                .open(restaurantId)).withRel(rel);
    }

    public Link linkToRestaurantClosing(Long restaurantId, String rel) {
        return linkTo(methodOn(RestaurantController.class)
                .close(restaurantId)).withRel(rel);
    }

    public Link linkToRestaurantInactivate(Long restaurantId, String rel) {
        return linkTo(methodOn(RestaurantController.class)
                .inactivate(restaurantId)).withRel(rel);
    }

    public Link linkToRestaurantActivate(Long restaurantId, String rel) {
        return linkTo(methodOn(RestaurantController.class)
                .activate(restaurantId)).withRel(rel);
    }

    public Link linkToUser(Long userId, String rel) {
        return linkTo(methodOn(UserController.class)
                .search(userId)).withRel(rel);
    }

    public Link linkToUser(Long userId) {
        return linkToUser(userId, IanaLinkRelations.SELF.value());
    }

    public Link linkToUsers(String rel) {
        return linkTo(UserController.class).withRel(rel);
    }

    public Link linkToUsers() {
        return linkToUsers(IanaLinkRelations.SELF.value());
    }

    public Link linkToUserPartyAssociation(Long userId, String rel) {
        return linkTo(methodOn(UserPartyController.class)
                .associate(userId, null)).withRel(rel);
    }

    public Link linkToUserPartyDissociation(Long userId, Long partyId, String rel) {
        return linkTo(methodOn(UserPartyController.class)
                .dissociate(userId, partyId)).withRel(rel);
    }

    public Link linkToPartiesUser(Long userId, String rel) {
        return linkTo(methodOn(UserPartyController.class)
                .list(userId)).withRel(rel);
    }

    public Link linkToPartiesUser(Long userId) {
        return linkToPartiesUser(userId, IanaLinkRelations.SELF.value());
    }

    public Link linkToParties(String rel) {
        return linkTo(PartyController.class).withRel(rel);
    }

    public Link linkToParties() {
        return linkToParties(IanaLinkRelations.SELF.value());
    }

    public Link linkToPermissions(String rel) {
        return linkTo(PermissionController.class).withRel(rel);
    }

    public Link linkToPermissions() {
        return linkToPermissions(IanaLinkRelations.SELF.value());
    }

    public Link linkToPartyPermissions(Long partyId, String rel) {
        return linkTo(methodOn(PartyPermissionController.class)
                .list(partyId)).withRel(rel);
    }

    public Link linkToPartyPermissions(Long partyId) {
        return linkToPartyPermissions(partyId, IanaLinkRelations.SELF.value());
    }

    public Link linkToPartyPermissionAssociation(Long partyId, String rel) {
        return linkTo(methodOn(PartyPermissionController.class)
                .associate(partyId, null)).withRel(rel);
    }

    public Link linkToPartyPermissionDissociation(Long partyId, Long permissaoId, String rel) {
        return linkTo(methodOn(PartyPermissionController.class)
                .dissociate(partyId, permissaoId)).withRel(rel);
    }

    public Link linkToRestaurantResponsible(Long restaurantId, String rel) {
        return linkTo(methodOn(RestaurantUserResponsibleController.class)
                .list(restaurantId)).withRel(rel);
    }

    public Link linkToRestaurantResponsible(Long restaurantId) {
        return linkToRestaurantResponsible(restaurantId, IanaLinkRelations.SELF.value());
    }

    public Link linkToRestaurantResponsibleDissociation(
            Long restaurantId, Long userId, String rel) {

        return linkTo(methodOn(RestaurantUserResponsibleController.class)
                .dissociate(restaurantId, userId)).withRel(rel);
    }

    public Link linkToRestaurantResponsibleAssociation(Long restaurantId, String rel) {
        return linkTo(methodOn(RestaurantUserResponsibleController.class)
                .associate(restaurantId, null)).withRel(rel);
    }

    public Link linkToPayment(Long paymentId, String rel) {
        return linkTo(methodOn(PaymentController.class)
                .search(paymentId, null)).withRel(rel);
    }

    public Link linkToPayment(Long paymentId) {
        return linkToPayment(paymentId, IanaLinkRelations.SELF.value());
    }

    public Link linkToPayment(String rel) {
        return linkTo(PaymentController.class).withRel(rel);
    }

    public Link linkToPayment() {
        return linkToPayment(IanaLinkRelations.SELF.value());
    }

    public Link linkToCity(Long cityId, String rel) {
        return linkTo(methodOn(CityController.class)
                .search(cityId)).withRel(rel);
    }

    public Link linkToCity(Long cityId) {
        return linkToCity(cityId, IanaLinkRelations.SELF.value());
    }

    public Link linkToCities(String rel) {
        return linkTo(CityController.class).withRel(rel);
    }

    public Link linkToCities() {
        return linkToCities(IanaLinkRelations.SELF.value());
    }

    public Link linkToState(Long stateId, String rel) {
        return linkTo(methodOn(StateController.class)
                .search(stateId)).withRel(rel);
    }

    public Link linkToState(Long stateId) {
        return linkToState(stateId, IanaLinkRelations.SELF.value());
    }

    public Link linkToStates(String rel) {
        return linkTo(StateController.class).withRel(rel);
    }

    public Link linkToStates() {
        return linkToStates(IanaLinkRelations.SELF.value());
    }

    public Link linkToProduct(Long restaurantId, Long productId, String rel) {
        return linkTo(methodOn(RestaurantProductController.class)
                .search(restaurantId, productId))
                .withRel(rel);
    }

    public Link linkToProduct(Long restaurantId, Long productId) {
        return linkToProduct(restaurantId, productId, IanaLinkRelations.SELF.value());
    }

    public Link linkToProducts(Long restaurantId, String rel) {
        return linkTo(methodOn(RestaurantProductController.class)
                .list(restaurantId, null)).withRel(rel);
    }

    public Link linkToProducts(Long restaurantId) {
        return linkToProducts(restaurantId, IanaLinkRelations.SELF.value());
    }

    public Link linkToKitchens(String rel) {
        return linkTo(KitchenController.class).withRel(rel);
    }

    public Link linkToKitchens() {
        return linkToKitchens(IanaLinkRelations.SELF.value());
    }

    public Link linkToKitchen(Long kitchenId, String rel) {
        return linkTo(methodOn(KitchenController.class)
                .search(kitchenId)).withRel(rel);
    }

    public Link linkToKitchen(Long kitchenId) {
        return linkToKitchen(kitchenId, IanaLinkRelations.SELF.value());
    }

    public Link linkToStatistics(String rel) {
        return linkTo(StatisticsController.class).withRel(rel);
    }

    public Link linkToStatisticsDailySales(String rel) {
        TemplateVariables filterVariables = new TemplateVariables(
                new TemplateVariable("restaurantId", VariableType.REQUEST_PARAM),
                new TemplateVariable("creationDateStart", VariableType.REQUEST_PARAM),
                new TemplateVariable("creationDateEnd", VariableType.REQUEST_PARAM),
                new TemplateVariable("timeOffset", VariableType.REQUEST_PARAM));

        String ordersUrl = linkTo(methodOn(StatisticsController.class)
                .queryDailySales(null, null)).toUri().toString();

        return Link.of(UriTemplate.of(ordersUrl, filterVariables), rel);
    }
}
