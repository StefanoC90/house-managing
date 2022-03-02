package com.oneandone.housemanaging.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class House {

    private int id;

    @Positive(message = "The size should have a positive value")
    private Double size;

    @NotBlank(message="Address is required")
    private String address;

    @NotBlank(message="Owner Name is required")
    private String ownerName;

}
