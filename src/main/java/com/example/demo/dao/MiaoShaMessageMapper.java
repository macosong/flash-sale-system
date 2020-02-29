package com.example.demo.dao;

import com.example.demo.domain.MiaoShaMessageInfo;
import com.example.demo.domain.MiaoShaMessageUser;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * MiaoShaMessageMapper
 *
 * @author: songqiang
 * @date: 2019/12/17
 */
@Mapper
public interface MiaoShaMessageMapper {
    @Select("select * from miaosha_message where messageid =  #{messageid}  ")
    List<MiaoShaMessageInfo> listMiaoShaMessage(@Param("messageId") String messageId);

    @Select("<script>select * from miaosha_message_user where 1=1 <if test=\"messageId !=null \">and messageId = #{messageId} </if></script>")
    List<MiaoShaMessageUser> listMiaoShaMessageUser(@Param("messageId") String messageId);

    @Insert("insert into miaosha_message (id , messageid ,content , create_time ,status,over_time,message_type ,send_type , good_name , price,messageHead)" +
            "value (#{id},#{messageId},#{content},#{createTime},#{status},#{overTime},#{messageType},#{sendType},#{goodName},#{price},#{messageHead}) ")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    void insertMiaoShaMessage(MiaoShaMessageInfo miaoShaMessage);

    @Insert("insert into miaosha_message_user (id , userid ,messageid , goodid ,orderid)" +
            "value (#{id},#{userId},#{messageId},#{goodId},#{orderId}) ")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    void insertMiaoShaMessageUser(MiaoShaMessageUser miaoShaMessageUser);

    @Select(" <script> select * from miaosha_message_user mmu , miaosha_message mm where " +
            " mmu.messageid = mm.messageid and  userid=${userId}  <if test=\"status !=null \">and status = #{status} </if></script> ")
    List<MiaoShaMessageInfo> listMiaoShaMessageByUserId(@Param("userId") long userId, @Param("status") Integer status);
}
