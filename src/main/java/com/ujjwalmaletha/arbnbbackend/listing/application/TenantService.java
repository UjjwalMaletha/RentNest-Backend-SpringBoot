package com.ujjwalmaletha.arbnbbackend.listing.application;


import com.ujjwalmaletha.arbnbbackend.listing.application.dto.DisplayCardListingDTO;
import com.ujjwalmaletha.arbnbbackend.listing.application.dto.DisplayListingDTO;
import com.ujjwalmaletha.arbnbbackend.listing.application.dto.sub.LandlordListingDTO;
import com.ujjwalmaletha.arbnbbackend.listing.domain.BookingCategory;
import com.ujjwalmaletha.arbnbbackend.listing.domain.Listing;
import com.ujjwalmaletha.arbnbbackend.listing.mapper.ListingMapper;
import com.ujjwalmaletha.arbnbbackend.listing.repository.ListingRepository;
import com.ujjwalmaletha.arbnbbackend.sharedkernel.service.State;
import com.ujjwalmaletha.arbnbbackend.user.application.UserService;
import com.ujjwalmaletha.arbnbbackend.user.application.dto.ReadUserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TenantService {
    private final ListingRepository listingRepository;

    private final ListingMapper listingMapper;

    private final UserService userService;
//    private final BookingService bookingService;

    public Page<DisplayCardListingDTO> getAllByCategory(Pageable pageable, BookingCategory category) {
        Page<Listing> allOrBookingCategory;
        if (category == BookingCategory.ALL) {
            allOrBookingCategory = listingRepository.findAllWithCoverOnly(pageable);
        } else {
            allOrBookingCategory = listingRepository.findAllByBookingCategoryWithCoverOnly(pageable, category);
        }

        return allOrBookingCategory.map(listingMapper::listingToDisplayCardListingDTO);
    }

    @Transactional(readOnly = true)
    public State<DisplayListingDTO, String> getOne(UUID publicId) {
        Optional<Listing> listingByPublicIdOpt = listingRepository.findByPublicId(publicId);

        if (listingByPublicIdOpt.isEmpty()) {
            return State.<DisplayListingDTO, String>builder()
                    .forError(String.format("Listing doesn't exist for publicId: %s", publicId));
        }

        DisplayListingDTO displayListingDTO = listingMapper.listingToDisplayListingDTO(listingByPublicIdOpt.get());

        ReadUserDTO readUserDTO = userService.getByPublicId(listingByPublicIdOpt.get().getLandlordPublicId()).orElseThrow();
        LandlordListingDTO landlordListingDTO = new LandlordListingDTO(readUserDTO.firstName(), readUserDTO.imageUrl());
        displayListingDTO.setLandlord(landlordListingDTO);

        return State.<DisplayListingDTO, String>builder().forSuccess(displayListingDTO);
    }

}
