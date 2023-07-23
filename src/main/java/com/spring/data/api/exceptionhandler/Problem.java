package com.spring.data.api.exceptionhandler;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.time.OffsetDateTime;
import java.util.List;

@JsonInclude(Include.NON_NULL)
@Builder
@Getter
@Schema(name = "Problem")
public class Problem {

    @Schema(example = "400")
    private Integer status;

    @Schema(example = "https://food.com/invalid-data")
    private String type;

    @Schema(example = "Invalid Data")
    private String title;

    @Schema(example = "One or more fields are invalid.")
    private String detail;

    @Schema(example = "One or more fields are invalid.")
    private String userMessage;

    @Schema(example = "2023-07-22T14:24:00.000000000Z")
    private OffsetDateTime timestamp;

    @Schema(description = "List of objects or fields that generated the error")
    private List<Object> objects;

    @Builder
    @Getter
    @Schema(name = "ObjectProblem")
    public static class Object {

        @Schema(example = "price")
        private String name;

        @Schema(example = "Invalid Price")
        private String userMessage;

    }

}
