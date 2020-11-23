package com.camisola10.camisolabackend.persistence.user;

import com.camisola10.camisolabackend.domain.EmailAddress;
import com.camisola10.camisolabackend.domain.user.User;
import com.camisola10.camisolabackend.domain.user.UserId;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
interface UserMapper {

    @Mapping(target = "id", source = "userId")
    @Mapping(target = "emailAddress", source = "email")
    User map(UserDb userDb);

    @Mapping(target = "userId", source = "id")
    @Mapping(target = "email", source = "emailAddress")
    UserDb map(User user);

    default EmailAddress mapEmail(String email){
        return EmailAddress.from(email);
    }

    default String mapEmail(EmailAddress emailAddress){
        return emailAddress.asString();
    }

    default UserId mapUserId(String userId){
        return UserId.from(userId);
    }

    default String mapUserId(UserId userId) {
        return userId.asString();
    }
}
