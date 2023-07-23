package com.spring.data.api.v1.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import java.math.BigDecimal;

@Getter
@Setter
public class ItemOrderModel extends RepresentationModel<ItemOrderModel> {

    @Schema(example = "0")
    private Long productId;

    @Schema(example = "Meat")
    private String productName;

    @Schema(example = "0")
    private Integer quantity;

    @Schema(example = "0.00")
    private BigDecimal unitPrice;

    @Schema(example = "0.00")
    private BigDecimal totalPrice;

    @Schema(example = "No pickles")
    private String observation;

}
