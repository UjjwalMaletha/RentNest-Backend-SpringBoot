package com.ujjwalmaletha.arbnbbackend.listing.application.dto;

import com.ujjwalmaletha.arbnbbackend.listing.application.dto.sub.DescriptionDTO;
import com.ujjwalmaletha.arbnbbackend.listing.application.dto.sub.ListingInfoDTO;
import com.ujjwalmaletha.arbnbbackend.listing.application.dto.sub.PictureDTO;
import com.ujjwalmaletha.arbnbbackend.listing.application.dto.vo.PriceVO;
import com.ujjwalmaletha.arbnbbackend.listing.domain.BookingCategory;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class SaveListingDTO {

    @NotNull
    BookingCategory bookingCategory;

    @NotNull String location;

    @NotNull @Valid
    ListingInfoDTO info;

    @NotNull @Valid
    DescriptionDTO description;

    @NotNull @Valid
    PriceVO price;

    @NotNull
    List<PictureDTO> pictures;



}
