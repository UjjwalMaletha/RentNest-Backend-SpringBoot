package com.ujjwalmaletha.arbnbbackend.listing.application.dto.vo;

import jakarta.validation.constraints.NotNull;

public record DescriptionVO(@NotNull(message = "Descriptions value must br present") int value) {
}
