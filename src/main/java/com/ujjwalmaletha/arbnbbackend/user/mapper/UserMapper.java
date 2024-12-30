package com.ujjwalmaletha.arbnbbackend.user.mapper;

import com.ujjwalmaletha.arbnbbackend.user.application.dto.ReadUserDTO;
import com.ujjwalmaletha.arbnbbackend.user.domain.Authority;
import com.ujjwalmaletha.arbnbbackend.user.domain.User;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface UserMapper {

    ReadUserDTO readUserDTOToUser(User user);

    default String mapAuthoritiesToString(Authority authority) {
        return authority.getName();
    }

}
