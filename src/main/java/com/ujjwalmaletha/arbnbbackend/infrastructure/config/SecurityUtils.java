package com.ujjwalmaletha.arbnbbackend.infrastructure.config;

import com.ujjwalmaletha.arbnbbackend.user.domain.Authority;
import com.ujjwalmaletha.arbnbbackend.user.domain.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SecurityUtils {

    public static final String ROLE_TENANT = "ROLE_TENANT";
    public static final String ROLE_LANDLORD = "ROLE_LANDLORD";
    public static final String CLAIMS_NAMESPACE = "https://www.ujjwalmaletha.um/roles";

    public static User map0auth2AttributesToUser(Map<String, Objects> attributes){
        User user = new User();
        String sub = String.valueOf(attributes.get("sub"));

        String username = null;

        if (attributes.get("preferred_username") != null) {
            username = (attributes.get("preferred_username")).toString().toLowerCase();
        }

        if (attributes.get("given_name") != null) {
            user.setFirstName((attributes.get("given_name")).toString());
        } else if ((attributes.get("nickname") != null)) {
            user.setFirstName((attributes.get("nickname").toString()));
        }

        if (attributes.get("family_name") != null) {
            user.setLastName((attributes.get("family_name")).toString());
        }

        if (attributes.get("email") != null) {
            user.setEmail((attributes.get("email")).toString());
        } else if (sub.contains("|") && (username != null && username.contains("@"))) {
            user.setEmail(username);
        } else {
            user.setEmail(sub);
        }

        if (attributes.get("picture") != null) {
            user.setImageUrl((attributes.get("picture")).toString());
        }

        Object rawAuthorities = attributes.get(CLAIMS_NAMESPACE);
        if (rawAuthorities instanceof List) {
            List<?> authoritiesRaw = (List<?>) rawAuthorities;
            List<String> stringAuthorities = authoritiesRaw.stream()
                    .filter(item -> item instanceof String)
                    .map(item -> (String) item)
                    .collect(Collectors.toList());

            Set<Authority> authorities = stringAuthorities.stream()
                    .map(authority -> {
                        Authority auth = new Authority();
                        auth.setName(authority);
                        return auth;
                    })
                    .collect(Collectors.toSet());
            user.setAuthorities(authorities);
        }

        return user;
    }

    public static List<SimpleGrantedAuthority> extractAuthorityFromClaims(Map<String, Object> claims) {
        return mapRolesToGrantedAuthorities(getRolesFromClaims(claims));
    }

    private static Collection<String> getRolesFromClaims(Map<String, Object> claims) {
        return (List<String>) claims.get(CLAIMS_NAMESPACE);
    }

    private static List<SimpleGrantedAuthority> mapRolesToGrantedAuthorities(Collection<String> roles) {
        return roles.stream().filter(role -> role.startsWith("ROLE_")).map(SimpleGrantedAuthority::new).toList();
    }

    public static boolean hasCurrentUserAnyOfAuthorities(String ...authorities) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (authentication != null && getAuthorities(authentication)
                .anyMatch(authority -> Arrays.asList(authorities).contains(authority)));
    }

    private static Stream<String> getAuthorities(Authentication authentication) {
        Collection<? extends GrantedAuthority> authorities = authentication
                instanceof JwtAuthenticationToken jwtAuthenticationToken ?
                extractAuthorityFromClaims(jwtAuthenticationToken.getToken().getClaims()) : authentication.getAuthorities();
        return authorities.stream().map(GrantedAuthority::getAuthority);
    }
}
