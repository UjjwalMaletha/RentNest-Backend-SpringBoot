package com.ujjwalmaletha.arbnbbackend.listing.application.dto;

import com.ujjwalmaletha.arbnbbackend.listing.application.dto.sub.PictureDTO;
import com.ujjwalmaletha.arbnbbackend.listing.application.dto.vo.PriceVO;
import com.ujjwalmaletha.arbnbbackend.listing.domain.BookingCategory;

import java.util.UUID;

public record DisplayCardListingDTO(PriceVO price,
                                    String location,
                                    PictureDTO cover,
                                    BookingCategory bookingCategory,
                                    UUID publicId) {
}
