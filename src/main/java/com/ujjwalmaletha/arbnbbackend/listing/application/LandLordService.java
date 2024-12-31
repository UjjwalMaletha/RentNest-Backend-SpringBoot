package com.ujjwalmaletha.arbnbbackend.listing.application;

import com.ujjwalmaletha.arbnbbackend.listing.application.dto.CreatedListingDTO;
import com.ujjwalmaletha.arbnbbackend.listing.application.dto.SaveListingDTO;
import com.ujjwalmaletha.arbnbbackend.listing.domain.Listing;
import com.ujjwalmaletha.arbnbbackend.listing.mapper.ListingMapper;
import com.ujjwalmaletha.arbnbbackend.listing.repository.ListingRepository;
import com.ujjwalmaletha.arbnbbackend.user.application.Auth0Service;
import com.ujjwalmaletha.arbnbbackend.user.application.UserService;
import com.ujjwalmaletha.arbnbbackend.user.application.dto.ReadUserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LandLordService {


    private final ListingRepository listingRepository;
    private final UserService userService;
    private final Auth0Service auth0Service;
    private final PictureService pictureService;

    private final ListingMapper listingMapper;


    public CreatedListingDTO create(SaveListingDTO saveListingDTO){

        Listing newListing = listingMapper.saveListingDTOToListing(saveListingDTO);
        ReadUserDTO readUserDTO = userService.getAuthenticatedUserFromSecurityContext();
        newListing.setLandlordPublicId(readUserDTO.publicId());

        Listing savedListing = listingRepository.saveAndFlush(newListing);


        pictureService.saveAll(saveListingDTO.getPictures(),savedListing);

        auth0Service.addLandlordRoleToUser(readUserDTO);

        return listingMapper.listingToCreatedListingDTO(savedListing);

    }
}
