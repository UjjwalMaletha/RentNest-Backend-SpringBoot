package com.ujjwalmaletha.arbnbbackend.listing.presentation;

import com.ujjwalmaletha.arbnbbackend.listing.application.TenantService;
import com.ujjwalmaletha.arbnbbackend.listing.application.dto.DisplayCardListingDTO;
import com.ujjwalmaletha.arbnbbackend.listing.domain.BookingCategory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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


}
