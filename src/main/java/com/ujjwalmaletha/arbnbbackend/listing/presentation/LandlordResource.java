package com.ujjwalmaletha.arbnbbackend.listing.presentation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ujjwalmaletha.arbnbbackend.infrastructure.config.SecurityUtils;
import com.ujjwalmaletha.arbnbbackend.listing.application.LandLordService;
import com.ujjwalmaletha.arbnbbackend.listing.application.dto.CreatedListingDTO;
import com.ujjwalmaletha.arbnbbackend.listing.application.dto.SaveListingDTO;
import com.ujjwalmaletha.arbnbbackend.listing.application.dto.sub.PictureDTO;
import com.ujjwalmaletha.arbnbbackend.user.application.UserException;
import com.ujjwalmaletha.arbnbbackend.user.application.UserService;
import com.ujjwalmaletha.arbnbbackend.user.application.dto.ReadUserDTO;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/landlord-listing")
@RequiredArgsConstructor
public class LandlordResource {

    private final LandLordService landLordService;
    private final Validator validator;
    private final UserService userService;
    private final ObjectMapper objectMapper = new ObjectMapper();


    @PostMapping(value = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<CreatedListingDTO> create(
            MultipartHttpServletRequest request,
            @RequestPart(name = "dto") String saveListingDTOString
    ) throws IOException {
        List<PictureDTO> pictures = request.getFileMap()
                .values()
                .stream()
                .map(mapMultipartFileToPictureDTO())
                .toList();

        SaveListingDTO saveListingDTO = objectMapper.readValue(saveListingDTOString, SaveListingDTO.class);
        saveListingDTO.setPictures(pictures);

        Set<ConstraintViolation<SaveListingDTO>> violations = validator.validate(saveListingDTO);
        if (!violations.isEmpty()) {
            String violationsJoined = violations.stream()
                    .map(violation -> violation.getPropertyPath() + " " + violation.getMessage())
                    .collect(Collectors.joining());

            ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, violationsJoined);
            return ResponseEntity.of(problemDetail).build();
        } else {
            return ResponseEntity.ok(landLordService.create(saveListingDTO));
        }
    }

    private static Function<MultipartFile, PictureDTO> mapMultipartFileToPictureDTO() {
        return multipartFile -> {
            try {
                return new PictureDTO(multipartFile.getBytes(), multipartFile.getContentType(), false);
            } catch (IOException ioe) {
                throw new UserException(String.format("Cannot parse multipart file: %s", multipartFile.getOriginalFilename()));
            }
        };
    }

//    @GetMapping(value = "/get-all")
//    @PreAuthorize("hasAnyRole('" + SecurityUtils.ROLE_LANDLORD + "')")
//    public ResponseEntity<List<DisplayCardListingDTO>> getAll() {
//        ReadUserDTO connectedUser = userService.getAuthenticatedUserFromSecurityContext();
//        List<DisplayCardListingDTO> allProperties = landLordService.getAllProperties(connectedUser);
//        return ResponseEntity.ok(allProperties);
//    }


}
