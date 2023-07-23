package com.spring.data.api.v1.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Relation(collectionRelation = "cities")
@Setter
@Getter
public class CityResumeModel extends RepresentationModel<CityResumeModel> {

    @Schema(example = "1")
    private Long id;

    @Schema(example = "Porto")
    private String name;

    @Schema(example = "Rio")
    private String state;

}
