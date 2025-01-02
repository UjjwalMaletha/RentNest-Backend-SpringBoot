package com.ujjwalmaletha.arbnbbackend.listing.application.dto;

import com.ujjwalmaletha.arbnbbackend.listing.application.dto.vo.PriceVO;

import java.util.UUID;

public record ListingCreateBookingDTO(
        UUID listingPublicId, PriceVO price) {
}
