package com.ujjwalmaletha.arbnbbackend.listing.repository;

import com.ujjwalmaletha.arbnbbackend.listing.domain.Listing;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ListingRepository extends JpaRepository<Listing,Long> {
}
