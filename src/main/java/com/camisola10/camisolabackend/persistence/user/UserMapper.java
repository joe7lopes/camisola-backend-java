package com.camisola10.camisolabackend.persistence.user;

import com.camisola10.camisolabackend.domain.Email;
import com.camisola10.camisolabackend.domain.user.User;
import com.camisola10.camisolabackend.domain.user.UserId;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
interface UserMapper {

    @Mapping(target = "id", source = "userId")
    User map(UserDb userDb);

    @Mapping(target = "userId", source = "id")
    UserDb map(User user);

    default Email mapEmail(String email){
        return Email.from(email);
    }

    default String mapEmail(Email email){
        return email.asString();
    }

    default UserId mapUserId(String userId){
        return UserId.from(userId);
    }

    default String mapUserId(UserId userId) {
        return userId.asString();
    }
}
