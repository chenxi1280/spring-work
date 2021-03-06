package com.work.boot.dao;

import com.work.boot.pojo.entity.User;
import com.work.boot.pojo.query.PayuserQuery;
import com.work.boot.pojo.query.UserQuery;
import com.work.boot.pojo.query.UserQueryS;
import com.work.boot.pojo.query.UserQuerySS;
import com.work.boot.pojo.vo.EvaluationVO;
import com.work.boot.pojo.vo.PayuserVo;
import com.work.boot.pojo.vo.UserVO;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

public interface UserDao {
    int deleteByPrimaryKey(String uid);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(String uid);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);


    // 自己的 接口

    List<User> selectAll();

    List<User> getusers(@Param("start") Integer page, @Param("limits") Integer limti);

    void updateByuid(@Param("uid") String uid, @Param("field") String field, @Param("value") String value);

    List<User> getLikeUsername(@Param("username") String username, @Param("useruid") String useruid,
                               @Param("userphone") String userphone, @Param("start") Integer page,
                               @Param("limits") Integer limti);

    Integer getCount();

    User selectByUserName(String uname);

    User selectcheckUser(@Param("uname") String uname, @Param("upassword") String upassword);

    User selectById(String uid);

    List<UserQuery> selectLikeUsername(@Param("username")String username, @Param("userphone")String uphone);

    List<User> selectByListPay(@Param("list") List<PayuserVo> list);

    int updatemoneyByid(@Param("uid") String uid,@Param("money") BigDecimal subtract);

    User selectDbUserByPhone(UserQueryS query);

    UserVO selectUserByPhone(String phone);

    User login(UserQueryS query);

    List<UserVO> selectUserBylist(@Param("ids") List<EvaluationVO> list);

    UserVO checkPhoneExist(String phone);

    List<User> selectByPayUser(PayuserQuery payuserQuery);

    List<UserVO> selectAdminAll(UserQuerySS userQueryS );

    Integer selectByAdminCount(UserQuerySS userQueryS );
}