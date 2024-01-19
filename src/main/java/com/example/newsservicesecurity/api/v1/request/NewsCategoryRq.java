package com.example.newsservicesecurity.api.v1.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class NewsCategoryRq {

    @NotNull
    @Min(0)
    private long id;

    @NotEmpty
    private String newsCategory;

}
