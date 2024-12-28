package com.ujjwalmaletha.arbnbbackend.infrastructure.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableJpaRepositories({"com.ujjwalmaletha.arbnbbackend.user.repository","com.ujjwalmaletha.arbnbbackend.listing.repository", "com.ujjwalmaletha.arbnbbackend.booking.repository"})
@EnableTransactionManagement
@EnableJpaAuditing
public class DatabaseConfiguration {

}
