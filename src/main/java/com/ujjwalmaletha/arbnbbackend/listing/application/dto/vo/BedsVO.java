package com.ujjwalmaletha.arbnbbackend.listing.application.dto.vo;

import jakarta.validation.constraints.NotNull;

public record BedsVO(@NotNull(message = "Beds value must br present") int value) {
}
