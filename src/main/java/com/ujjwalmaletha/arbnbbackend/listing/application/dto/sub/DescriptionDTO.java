package com.ujjwalmaletha.arbnbbackend.listing.application.dto.sub;

import com.ujjwalmaletha.arbnbbackend.listing.application.dto.vo.DescriptionVO;
import com.ujjwalmaletha.arbnbbackend.listing.application.dto.vo.TitleVO;
import jakarta.validation.constraints.NotNull;


public record DescriptionDTO(
        @NotNull TitleVO title,
        @NotNull DescriptionVO description
) {
}
