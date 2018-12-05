package com.xujing.miaosha.dao;

import com.xujing.miaosha.entity.MiaoshaUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface MiaoshaUserDao {

    @Select("select * from miaosha_user")
    public List<MiaoshaUser> findAll();


    @Select("select * from miaosha_user where id = #{id}")
    public MiaoshaUser getById(@Param("id")long id);

    @Update("update miaosha_user set PASSWORD = #{password} where id = #{id}")
    public void update(MiaoshaUser miaoshaUser);


}
