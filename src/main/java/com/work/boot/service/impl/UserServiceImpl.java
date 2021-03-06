package com.work.boot.service.impl;

import com.work.boot.dao.AdminDao;
import com.work.boot.dao.PermissionDao;
import com.work.boot.dao.RoleDao;
import com.work.boot.dao.UserDao;
import com.work.boot.pojo.dto.*;
import com.work.boot.pojo.entity.Admin;
import com.work.boot.pojo.entity.Role;
import com.work.boot.pojo.entity.User;
import com.work.boot.pojo.query.UserAddQurey;
import com.work.boot.pojo.query.UserQuery;
import com.work.boot.pojo.query.UserQueryS;
import com.work.boot.pojo.query.UserQuerySS;
import com.work.boot.pojo.vo.PermissionVO;
import com.work.boot.pojo.vo.RoleVO;
import com.work.boot.pojo.vo.UserVO;
import com.work.boot.service.BaseService;
import com.work.boot.service.UserService;
import com.work.boot.util.GetLimit;
import com.work.boot.util.GetUUID32;
import com.work.boot.util.Md5Util;
import com.work.boot.util.UpUtils;
import com.work.boot.util.password.PasswordUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService, BaseService {

    @Resource
    private UserDao userDao;

    @Resource
    private AdminDao adminDao;

    @Resource
    private RoleDao roleDao;

    @Resource
    private PermissionDao permissionDao;

    @Override
    public Result getAll() {

        List<User> list = userDao.selectAll();

        Result result = new Result();

        result.setStatus(0);
        result.setItem(list);
        result.setTotal(list.size());
        return result;

    }

    @Override
    public Result getusers(Integer page, Integer limit) {
        Integer sta = (page - 1) * limit;
        //需要去数据库获取数据,返回对应的信息
        //用户的id需要转换成用户的名字
        //判断当前用户是管理员还是普通用户--->在讲解登录模块的时候来分析权限
        //查所有的视频(包括了上线的视频和下线的视频)
        List<User> list = userDao.getusers(sta, limit);
        //通过遍历list 获取用户的id,使用用户的id获取对应用户的名字,将名字赋值给当前的video
//
//        list.forEach( v -> {
//            String aname = adminMapper.selectanameById(v.getAid());
//            v.setAid(aname);
//        });

        list = aidtoaname(list);

        Result result = new Result();

        result.setStatus(0);
        result.setItem(list);
        result.setTotal(userDao.getCount());
        return result;

    }

    @Override
    public Result seeuser(String uid) {

        User user = userDao.selectByPrimaryKey(uid);
        Result result = new Result();
        List<User> list = new ArrayList();
        list.add(user);
        result.setStatus(0);
        result.setItem(list);
        result.setTotal(1);
        return result;

    }


    //密码校验
    @Override
    public User checkUser(String uname, String upassword) {


        User user = userDao.selectcheckUser(uname, upassword);

        return user;

    }

    @Override

    public Result adduimg(MultipartFile uimg, HttpServletRequest request) {

        Result result = new Result();
        //此处需要将这个文件上传项目中
        try {
            UpUtils.upfile(uimg, request);
            result.setMessage(uimg.getOriginalFilename());
            result.setStatus(200);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            result.setStatus(500);
            result.setMessage("网路异常!");
            return result;

        }

    }

    @Override
    @Transactional
    public Result addUser(User user, HttpServletRequest request) {
        Result result = new Result();

        if (user.getUimg() == null || "".equals(user.getUimg())) {
            result.setMessage("填写错误!");
            result.setStatus(500);
            return result;
        }
        if (user.getUphoneid() == null || "".equals(user.getUphoneid())) {
            result.setMessage("填写错误!");
            result.setStatus(500);
            return result;
        }
        if (user.getUpassword() == null || "".equals(user.getUpassword())) {
            result.setMessage("填写错误!");
            result.setStatus(500);
            return result;
        }
        if (user.getUbroom() == null || "".equals(user.getUbroom())) {
            result.setMessage("填写错误!");
            result.setStatus(500);
            return result;
        }
        if (user.getUpassword() == null || "".equals(user.getUpassword())) {
            result.setMessage("填写错误!");
            result.setStatus(500);
            return result;
        }

        HttpSession session = request.getSession();
        User u = (User) request.getSession().getAttribute("user");
        // 视频的地址
        // 视频的描述
        // 视频的封面
        String id = GetUUID32.getuuid32();

        Date date = new Date();
        user.setUid(id);


//        user.setAid(u.getUid());

//
//        MultimediaInfo info = VideoUtil.getInfo(video.getVideoPath(), request);
//        Float f =  Float.valueOf(info.getDuration()/1000);

        //"xx.mp4"
        user.setUimg("img/" + user.getUimg());
        //因为时添加操作  需要事务  设置手动回滚
        user.setUpassword(Md5Util.encryption(user.getUpassword(), user.getUname()));
        try {

            int i = userDao.insert(user);

            if (i > 0) {
                result.setMessage("成功!");
                result.setStatus(200);
            } else {
                result.setStatus(500);
                result.setMessage("插入失败!");
            }
            return result;

        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            result.setMessage("填写错误!");
            result.setStatus(500);
            return result;
        }


    }

    @Override
    public User selectById(String uid) {
        User user = userDao.selectById(uid);
        return user;
    }

    @Override
    public Result toedituser(User user, HttpServletRequest request) {

        Result result = new Result();

        if (user.getUimg() == null || "".equals(user.getUimg())) {
            result.setMessage("填写错误!");
            result.setStatus(200);
            return result;
        }
        if (user.getUphoneid() == null || "".equals(user.getUphoneid())) {
            result.setMessage("填写错误!");
            result.setStatus(500);
            return result;
        }
        if (user.getUpassword() == null || "".equals(user.getUpassword())) {
            result.setMessage("填写错误!");
            result.setStatus(500);
            return result;
        }
        if (user.getUbroom() == null || "".equals(user.getUbroom())) {
            result.setMessage("填写错误!");
            result.setStatus(500);
            return result;
        }
        if (user.getUpassword() == null || "".equals(user.getUpassword())) {
            result.setMessage("填写错误!");
            result.setStatus(500);
            return result;
        }



        // 视频的地址
        // 视频的描述
        // 视频的封面
        String id = user.getUid();


        user.setUid(id);




        user.setUimg("img/" + user.getUimg().substring(4));
        user.setUupdatedate(new Date());
        User userp = userDao.selectByPrimaryKey(user.getUid());


        if (!userp.getUpassword().equals(user.getUpassword())) {

            user.setUpassword(Md5Util.encryption(user.getUpassword(), user.getUname()));

        }
        try {
            int i = userDao.updateByPrimaryKeySelective(user);

//            userDao.deleteByPrimaryKey(user.getUid());
//            int i = userDao.insert(user);
            if (i > 0) {
                result.setMessage("成功!");
                result.setStatus(200);
            } else {
                result.setStatus(500);
                result.setMessage("插入失败!");
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            result.setMessage("填写错误!");
            result.setStatus(500);
            return result;
        }


    }

    @Override
    public ResultData datatest() {
        ResultData resultData = new ResultData();
        List<RData> list = new ArrayList<>();




        Integer count = userDao.getCount();

        List<User> users = userDao.selectAll();

        int sum = 0;

        for (User user : users) {
            RData rData = new RData();
            rData.setName(user.getUid());
            rData.setValue(user.getCstate());
            list.add(rData);
        }

        users.forEach( user -> {
            RData rData = new RData();
            rData.setName(user.getUid());
            rData.setValue(user.getCstate());
            list.add(rData);
        });


        resultData.setList(list);

        return  resultData;

    }

    @Override
    @Transactional
    public ResponseDTO addusermoney(String uid, BigDecimal money) {
        if (money.compareTo(BigDecimal.ZERO) == 1) {
            User user = userDao.selectByPrimaryKey(uid);
            BigDecimal mymoney = user.getMymoney();
            BigDecimal add = mymoney.add(money);
            userDao.updatemoneyByid(uid,add);
            return ResponseDTO.ok("成功", 200);
        }
        return ResponseDTO.ok("失败", 500);
    }

    @Override
    public User selectDbUserByPhone(UserQueryS query) {

        return userDao.selectDbUserByPhone(query);
    }

    @Override
    public List<RoleVO> selectHisRolesByPhone(String phone) {

        UserVO u = userDao.selectUserByPhone(phone);

        if (!StringUtils.isEmpty(u.getRole())) {

            List<RoleVO> roles = roleDao.selectHisRolesByRoles(u.getRole());
            // 在查询完成roles之后，我们应该 roles的permissionVOS赋值
            if (!CollectionUtils.isEmpty(roles)) {
                List<PermissionVO> permissionVOS = this.selectHisPermissionByRoles(roles);// 查出所有的权限
                return getRoleVOList(roles, permissionVOS);
            }


        }
        return null;
    }

    @Override
    public List<PermissionVO> selectHisPermissionByRoles(List<RoleVO> roles) {
        List<PermissionVO> list = new ArrayList<>();
//        // 第一种 ：
//        for (RoleVO role : roles) {
//            List<PermissionVO> permissionVOS = permissionDao.selectPermissionsByIds(role.getPermissions());
//            list.addAll(permissionVOS);
////            Collections.addAll(list, permissionVOS);// 这里有个把两个集合加入到一个集合里边去
//        }
        // 断言工具
//        Assert.notNull(roles, "传递的集合为null");
        if (!CollectionUtils.isEmpty(roles)) {
            Set<String> paramSet = new TreeSet<>();// 查询参数集合
            for (RoleVO role : roles) {// 在内存之中进行的。效率基本最高的
                String psIds = role.getPermissions();// 1,2,34
                if (!StringUtils.isEmpty(psIds)) {
                    Collections.addAll(paramSet, psIds.split(","));
                }
            }
            if (!CollectionUtils.isEmpty(paramSet)) {
                list = permissionDao.selectPermissionsBySet(paramSet);
            }

        }
        // 第三种
        return list;
    }

    @Override
    public boolean checkPhoneExist(String phone) {

        return  userDao.checkPhoneExist(phone) == null;
    }

    @Override
    public User selectUserByPhone(UserQueryS query) {
        return userDao.selectUserByPhone(query.getPhone());
    }

    @Override
    public ResponseDTO homeuser(UserQuery userQuery) {

        User user = userDao.selectByPrimaryKey(userQuery.getUid());

        return ResponseDTO.ok("", user);
    }

    @Override
    public ResponseDTO add(User user) {
        UserVO userVO = userDao.selectUserByPhone(user.getUphoneid());

        if (userVO != null){

            return ResponseDTO.fail("用户已经注册");
        }
        user.setTypeid(0);
        user.setMymoney(new BigDecimal(0));
        user.setUusername(user.getUname());
        user.setUcreatedate(new Date());
        user.setUupdatedate(new Date());
        user.setUid(GetUUID32.getuuid32());
        user.setCarnumber(0);
        user.setUpassword(PasswordUtil.encodePassword(user.getUpassword()));
        user.setRole("7");
        user.setCstate(0);


        return ResponseDTO.get(userDao.insertSelective(user) == 1 );
    }

    @Override
    public ResponseDTO edit(User user) {
        UserVO userVO = userDao.selectUserByPhone(user.getUphoneid());
        if (userVO != null) {

            user.setUid(userVO.getUid());
            user.setXid("1");
            user.setUupdatedate(new Date());
            user.setCstate(1);
            user.setRole("7,8,9");
            StringBuffer pay = new StringBuffer();
            if (user.getRoomarea() != 0) {
                pay.append("1,");
            }
            if (user.getCarnumber() != 0) {
                pay.append("2,");
            }
            user.setPaymentid("1");
        }

        return ResponseDTO.get(userDao.updateByPrimaryKeySelective(user) == 1 );
    }

    @Override
    public ResponseDTO useraddusermoney(UserAddQurey user) {
        UserVO userVO = userDao.selectUserByPhone(user.getPhone());
        User newuser = new User();
        newuser.setUid(userVO.getUid());
        newuser.setUupdatedate(new Date());
        newuser.setMymoney(userVO.getMymoney().add(new BigDecimal(user.getMoney())));



        return ResponseDTO.get(userDao.updateByPrimaryKeySelective(newuser) == 1 );
    }


    @Override
    public ResponseDTO dispatchUserPermission(String uid, List<Role> roles) {
        TreeSet treeSet = new TreeSet();// 就是装最后分配好的角色id
        // 1、先把权限字符串取出来进行排序操作
        // 当作查询参数,服务器也要对权限字符串进行排序
        Set<String> collect = roles.stream().map(elem -> {
            String permissions = elem.getPermissions();
            if (StringUtils.isEmpty(permissions)) {
                return "";// 这里返回空字符串才行
            } else {
                // StringUtils 和js中的join方法很强大
                String[] split = permissions.split(",");
                // 数组排序
//                Arrays.sort(split);// 从小到大排序(这里要注意的是，如果是字符串，大小排序不是按照数字来的)
                Arrays.sort(split, Comparator.comparingInt(Integer::valueOf));// 特别关键
                // 就是这个方法，就是把数组直接变成字符串
                return StringUtils.arrayToDelimitedString(split, ",");
            }
        }).collect(Collectors.toSet());
        List<Role> roles1 = roleDao.selectByPermissions(collect);
        Map<String, List<Role>> collect1 = roles1.stream().collect(Collectors.groupingBy(Role::getPermissions));
        for (Role r : roles) {
            List<Role> roles2 = collect1.get(r.getPermissions());// 从查询结果里边取角色
            if (CollectionUtils.isEmpty(roles2)) {// 结果集里边没有这个角色，就需要新增角色
                r.setSystem(0);// 不是系统级
                r.setNote("==> 新增角色：");
                // 有没有RoleId有关系吗？
                roleDao.insertSelective(r);
                treeSet.add(r.getRoleId());
            } else {
                treeSet.add(roles2.get(0).getRoleId());
            }
        }
        User u = new User();
        u.setUid(uid);
        u.setRole(StringUtils.collectionToDelimitedString(treeSet, ","));
        return ResponseDTO.get(userDao.updateByPrimaryKeySelective(u) == 1);
    }

    @Override
    public PageDTO ajaxadminlist(UserQuerySS userQueryS) {

        List<UserVO> list = userDao.selectAdminAll(userQueryS);

        Integer count = userDao.selectByAdminCount(userQueryS);

        list.forEach(v -> {

            if (v.getRole().length()> 10 ){
                v.setNote("最高权限");
            } else if (v.getRole().length()  > 5 &&  v.getRole().length()  <= 10){
                v.setNote("普通权限");
            }else if (v.getRole().length() <=5 ){
                v.setNote("员工权限");
            }

        });


        return PageDTO.setPageData(count,list);
    }

    @Override
    public ResponseDTO deleteadmin(String uid) {



        return ResponseDTO.get( userDao.deleteByPrimaryKey(uid) == 1  );
    }

    @Override
    public ResponseDTO editadmin(User user) {




        return ResponseDTO.get(userDao.updateByPrimaryKeySelective(user) == 1 );
    }

    @Override
    public ResponseDTO addadmin(User user) {

        UserVO userVO = userDao.selectUserByPhone(user.getUphoneid());

        if (userVO != null){

            return ResponseDTO.fail("用户已经注册");
        }
        user.setTypeid(1);
        user.setMymoney(new BigDecimal(0));
        user.setUusername(user.getUname());
        user.setUcreatedate(new Date());
        user.setUupdatedate(new Date());
        user.setUid(GetUUID32.getuuid32());
        user.setUpassword(PasswordUtil.encodePassword(user.getUpassword()));
        user.setRole("7");
        user.setCstate(0);


        return ResponseDTO.get(userDao.insertSelective(user) == 1 );


    }

    @Override
    public ResponseDTO edituserpro(User user) {



        return ResponseDTO.get(userDao.updateByPrimaryKeySelective(user) ==1 );
    }

    @Override
    public User login(UserQueryS query) {
        return userDao.login(query);
    }


    @Override
    @Transactional
    public Result delUserById(String... ids) {
        //权限判断
        Result result = new Result();
        result.setStatus(500);
        result.setMessage("操作失败");
        try {
            for (String id : ids) {
                int i = userDao.deleteByPrimaryKey(id);
                if (i == 0) {
                    return result;
                }
            }
            result.setStatus(200);
            result.setMessage("操作成功");
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();

        }

        return result;
    }

    @Override
    @Transactional
    public Result updataUser(String uid, String field, String value) {
        //权限判断
        Result result = new Result();

        result.setStatus(500);
        result.setMessage("操作失败");
        try {
            userDao.updateByuid(uid, field, value);
            result.setStatus(200);
            result.setMessage("操作成功");
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();

        }

        return result;
    }

    // 模糊多条件查询
    @Override
    public Result getLikeUsername(String username, String useruid, String userphone, Integer page, Integer limit) {
        Result result = new Result();
        List<User> list = userDao.getLikeUsername(username, useruid, userphone, GetLimit.getPage(page, limit), limit);

        list = aidtoaname(list);
        result.setStatus(0);
        result.setItem(list);
        result.setTotal(list.size());
        return result;
    }


    // aid 转换为 aname 方法
    private List<User> aidtoaname(List<User> list) {

        list.forEach(v -> {
            String aname = adminDao.selectanameById(v.getAid());
            v.setAid(aname);
        });
        return list;

    }


}
