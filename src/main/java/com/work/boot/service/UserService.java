package com.work.boot.service;

import com.work.boot.pojo.dto.ResponseDTO;
import com.work.boot.pojo.dto.Result;
import com.work.boot.pojo.dto.ResultData;
import com.work.boot.pojo.entity.User;
import com.work.boot.pojo.query.UserQuery;
import com.work.boot.pojo.query.UserQueryS;
import com.work.boot.pojo.vo.PermissionVO;
import com.work.boot.pojo.vo.RoleVO;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.List;

public interface UserService {

    Result getAll();

    Result delUserById(String... ids);

    Result updataUser(String uid, String field, String value);

    Result getLikeUsername(String username, String useruid, String userphone, Integer page, Integer limit);

    Result getusers(Integer page, Integer limit);

    Result seeuser(String uid);


    User checkUser(String username, String password);

    Result adduimg(MultipartFile uimg, HttpServletRequest request);

    Result addUser(User user, HttpServletRequest request);

    User selectById(String uid);

    Result toedituser(User user, HttpServletRequest request);

    ResultData datatest();

    ResponseDTO addusermoney(String uid, BigDecimal money);

    User selectDbUserByPhone(UserQueryS query);

    List<RoleVO> selectHisRolesByPhone(String uphoneid);

    User login(UserQueryS query);

    // 根据角色直接就能查询这些角色的权限
    List<PermissionVO> selectHisPermissionByRoles(List<RoleVO> roles);

    boolean checkPhoneExist(String phone);

    User selectUserByPhone(UserQueryS query);

    ResponseDTO homeuser(UserQuery userQuery);
}
