package com.boost.box.mybatis.dao;

import com.boost.box.mybatis.model.po.UserEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * 用户类
 * @author qdj
 */
@Mapper
@Repository
public interface UserMapper {

    /**
     * 插入用户信息
     * @param user 用户
     */
    void insertUser(UserEntity user);
}
