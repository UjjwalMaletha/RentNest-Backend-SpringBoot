package com.ujjwalmaletha.arbnbbackend.booking.application.dto;

import jakarta.validation.constraints.NotNull;

import java.time.OffsetDateTime;

public record BookedDateDTO(
        @NotNull OffsetDateTime startDate,
        @NotNull OffsetDateTime endDate
) {
}