package com.bitprogress.startermvcfull.mapstruct;

import com.bitprogress.startermvcfull.entity.UserAuthMsg;
import com.bitprogress.usercontext.entity.UserInfo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapStruct {

    UserMapStruct INSTANCE = Mappers.getMapper(UserMapStruct.class);

    UserInfo toUserInfo(UserAuthMsg authMsg);

}
