package com.ujjwalmaletha.arbnbbackend.listing.application.dto.vo;

import jakarta.validation.constraints.NotNull;

public record BedroomsVO(@NotNull(message = "Bedrooms value must br present") int value) {
}
