package com.ujjwalmaletha.arbnbbackend.infrastructure.config;

import com.ujjwalmaletha.arbnbbackend.user.domain.User;

import java.util.Map;
import java.util.Objects;

public class SecurityUtils {

    public static final String ROLE_TENANT = "ROLE_TENANT";
    public static final String ROLE_LANDLORD = "ROLE_LANDLORD";
    public static final String CLAIMS_NAMESPACE = "https://www.ujjwalmaletha.um/roles";

    public static User map0auth2AttributesToUser(Map<String, Objects> attributes){
        User user = new User();
    }
}
