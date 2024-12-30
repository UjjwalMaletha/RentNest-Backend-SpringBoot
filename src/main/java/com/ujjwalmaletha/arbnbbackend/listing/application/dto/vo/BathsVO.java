package com.ujjwalmaletha.arbnbbackend.listing.application.dto.vo;

import jakarta.validation.constraints.NotNull;

public record BathsVO(@NotNull(message = "Bath Value must be present") int value) {

}
