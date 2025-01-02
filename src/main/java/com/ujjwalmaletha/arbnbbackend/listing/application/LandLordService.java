package com.ujjwalmaletha.arbnbbackend.listing.application;

import com.ujjwalmaletha.arbnbbackend.listing.application.dto.CreatedListingDTO;
import com.ujjwalmaletha.arbnbbackend.listing.application.dto.DisplayCardListingDTO;
import com.ujjwalmaletha.arbnbbackend.listing.application.dto.ListingCreateBookingDTO;
import com.ujjwalmaletha.arbnbbackend.listing.application.dto.SaveListingDTO;
import com.ujjwalmaletha.arbnbbackend.listing.domain.Listing;
import com.ujjwalmaletha.arbnbbackend.listing.mapper.ListingMapper;
import com.ujjwalmaletha.arbnbbackend.listing.repository.ListingRepository;
import com.ujjwalmaletha.arbnbbackend.sharedkernel.service.State;
import com.ujjwalmaletha.arbnbbackend.user.application.Auth0Service;
import com.ujjwalmaletha.arbnbbackend.user.application.UserService;
import com.ujjwalmaletha.arbnbbackend.user.application.dto.ReadUserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

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

    @Transactional(readOnly = true)
    public List<DisplayCardListingDTO> getAllProperties(ReadUserDTO landlord) {
        List<Listing> properties = listingRepository.findAllByLandlordPublicIdFetchCoverPicture(landlord.publicId());
        return listingMapper.listingToDisplayCardListingDTOs(properties);
    }

    @Transactional
    public State<UUID, String> delete(UUID publicId, ReadUserDTO landlord) {
        long deletedSuccessfuly = listingRepository.deleteByPublicIdAndLandlordPublicId(publicId, landlord.publicId());
        if (deletedSuccessfuly > 0) {
            return State.<UUID, String>builder().forSuccess(publicId);
        } else {
            return State.<UUID, String>builder().forUnauthorized("User not authorized to delete this listing");
        }
    }

    public Optional<ListingCreateBookingDTO> getByListingPublicId(UUID publicId) {
        return listingRepository.findByPublicId(publicId).map(listingMapper::mapListingToListingCreateBookingDTO);
    }


    public List<DisplayCardListingDTO> getCardDisplayByListingPublicId(List<UUID> allListingPublicIDs) {
        return listingRepository.findAllByPublicIdIn(allListingPublicIDs)
                .stream()
                .map(listingMapper::listingToDisplayCardListingDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public Optional<DisplayCardListingDTO> getByPublicIdAndLandlordPublicId(UUID listingPublicId, UUID landlordPublicId) {
        return listingRepository.findOneByPublicIdAndLandlordPublicId(listingPublicId, landlordPublicId)
                .map(listingMapper::listingToDisplayCardListingDTO);
    }




}
