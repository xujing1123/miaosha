package com.xujing.miaosha.dao;

import com.xujing.miaosha.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserDao {

    @Select("select * from user where id = #{id}")
    public User getById(@Param("id") Integer id);


    @Insert("INSERT INTO user(id,NAME)VALUES (#{id},#{name})")
    public int insert(User user);

    @Select("select * from user")
    public List<User> findAll();
}
