package com.hjh.mapper;

import com.hjh.pojo.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author 洪锦辉
 * 2022/2/15
 */
@Mapper
public interface UserMapper {
    User getUserByPhone(String phone);
}
