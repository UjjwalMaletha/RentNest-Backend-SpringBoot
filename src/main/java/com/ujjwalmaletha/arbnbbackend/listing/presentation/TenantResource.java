package com.ujjwalmaletha.arbnbbackend.listing.presentation;

import com.ujjwalmaletha.arbnbbackend.listing.application.TenantService;
import com.ujjwalmaletha.arbnbbackend.listing.application.dto.DisplayCardListingDTO;
import com.ujjwalmaletha.arbnbbackend.listing.application.dto.DisplayListingDTO;
import com.ujjwalmaletha.arbnbbackend.listing.application.dto.SearchDTO;
import com.ujjwalmaletha.arbnbbackend.listing.domain.BookingCategory;
import com.ujjwalmaletha.arbnbbackend.sharedkernel.service.State;
import com.ujjwalmaletha.arbnbbackend.sharedkernel.service.StatusNotification;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/tenant-listing")
@RequiredArgsConstructor
public class TenantResource {

    private final TenantService tenantService;

    @GetMapping("/get-all-by-category")
    public ResponseEntity<Page<DisplayCardListingDTO>> findAllByBookingCategory(Pageable pageable,
                                                                                @RequestParam BookingCategory category) {
        return ResponseEntity.ok(tenantService.getAllByCategory(pageable, category));
    }

    @GetMapping("/get-one")
    public ResponseEntity<DisplayListingDTO> getOne(@RequestParam UUID publicId) {
        State<DisplayListingDTO, String> displayListingState = tenantService.getOne(publicId);
        if (displayListingState.getStatus().equals(StatusNotification.OK)) {
            return ResponseEntity.ok(displayListingState.getValue());
        } else {
            ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, displayListingState.getError());
            return ResponseEntity.of(problemDetail).build();
        }
    }

    @PostMapping("/search")
    public ResponseEntity<Page<DisplayCardListingDTO>> search(Pageable pageable,
                                                              @Valid @RequestBody SearchDTO searchDTO) {
        return ResponseEntity.ok(tenantService.search(pageable, searchDTO));
    }


}
