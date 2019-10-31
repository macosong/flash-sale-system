package com.example.demo.dao;

import com.example.demo.domain.MiaoshaUser;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

/**
 * MiaoShaUserMapper
 *
 * @author maco
 * @data 2019/10/24
 */
@Mapper
public interface MiaoShaUserMapper {
//    @Select("select * from miaosha_user where nickname = #{nickname}")
    /**
     * 通过昵称获取用户对象
     *
     * @param nickname
     * @return
     */
    MiaoshaUser getByNickname(@Param("nickname") String nickname ) ;

//    @Select("select * from miaosha_user where id = #{id}")
    /**
     * 通过id获取用户对象
     *
     * @param id
     * @return
     */
    MiaoshaUser getById(@Param("id") long id ) ;


//    @Update("update miaosha_user set password = #{password} where id = #{id}")
    /**
     * 更新用户的密码
     *
     * @param toBeUpdate
     */
    void updateUser(MiaoshaUser toBeUpdate);


//    @Insert("insert into miaosha_user (id , nickname ,password , salt ,head,register_date,last_login_date)values (#{id},#{nickname},#{password},#{salt},#{head},#{registerDate},#{lastLoginDate}) ")
//    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")

    /**
     * 新增用户
     *
     * @param miaoshaUser
     */
    void insertMiaoShaUser(MiaoshaUser miaoshaUser);
}
