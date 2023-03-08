package com.service;

import com.entity.Admin;
import com.entity.User;
import com.entity.valueObject.MessageModel;
import com.mapper.UserMapper;
import com.util.GetSqlSession;
import com.util.StringUtil;
import org.apache.ibatis.session.SqlSession;

/**
 *业务逻辑
 */
public class UserService {
    public MessageModel userLogin(String uname, String upwd) {
        /*
            1. 参数的非空判断
                 如果参数为空
                    将状态码、提示信息、回显数据设置到消息模型对象中，返回消息模型对象
            2. 调用dao层的查询方法，通过用户名查询用户对象
            3. 判断用户对象是否为空
                    如果为空，将状态码、提示信息、回显数据设置到消息模型对象中，返回消息模型对象
            4. 判断数据库中查询到的用户密码与前台传递过来的密码作比较
                    如果不相等，将状态码、提示信息、回显数据设置到消息模型对象中，返回消息模型对象
            5. 登录成功，成功状态、提示信息、用户对象设置消息模型对象，并return
         */

        MessageModel messageModel = new MessageModel();

        //回显
        User u = new User();
        u.setCustomer_id(uname);
        u.setCustomer_pwd(upwd);
        messageModel.setObject(u);

        //参数非空判断
        if(StringUtil.isEmpty(uname) || StringUtil.isEmpty(upwd)){
            //将状态码、提示信息、回显数据设置到消息模型对象中，返回消息模型对象
            messageModel.setCode(0);
            messageModel.setMsg("用户名和密码不能为空");
            //回显
            return messageModel;
        }

        //2. 调用dao层的查询方法，通过用户名查询用户对象
        SqlSession session = GetSqlSession.createSqlSession();
        UserMapper userMapper=session.getMapper(UserMapper.class);
        User user = userMapper.getUserInfo(uname);

        //3.判断用户对象是否为空
        if(user ==null) {
            //将状态码、提示信息、回显数据设置到消息模型对象中，返回消息模型对象
            messageModel.setCode(0);
            messageModel.setMsg("用户不存在");
            //回显(可优化）
            return messageModel;
        }

        //4. 判断数据库中查询到的用户密码与前台传递过来的密码作比较
        //如果不相等，将状态码、提示信息、回显数据设置到消息模型对象中，返回消息模型对象
        if(!upwd.equals(user.getCustomer_pwd())){
            messageModel.setCode(0);
            messageModel.setMsg("用户密码不正确");
            return messageModel;
        }

        //登陆成功，将用户信息设置到消息模型中
        messageModel.setObject(user);
        return  messageModel;
    }

    public MessageModel adminLogin(String uname,String upwd){
        MessageModel messageModel = new MessageModel();

        //回显
        Admin u = new Admin();
        u.setController_id(uname);
        u.setController_pwd(upwd);
        messageModel.setObject(u);

        //参数非空判断
        if(StringUtil.isEmpty(uname) || StringUtil.isEmpty(upwd)){
            //将状态码、提示信息、回显数据设置到消息模型对象中，返回消息模型对象
            messageModel.setCode(0);
            messageModel.setMsg("用户名和密码不能为空");
            //回显
            return messageModel;
        }

        //2. 调用dao层的查询方法，通过用户名查询用户对象
        SqlSession session = GetSqlSession.createSqlSession();
        UserMapper userMapper=session.getMapper(UserMapper.class);
        Admin user = userMapper.getAdminInfo(uname);

        //3.判断用户对象是否为空
        if(user ==null) {
            //将状态码、提示信息、回显数据设置到消息模型对象中，返回消息模型对象
            messageModel.setCode(0);
            messageModel.setMsg("用户不存在");
            //回显(可优化）
            return messageModel;
        }

        //4. 判断数据库中查询到的用户密码与前台传递过来的密码作比较
        //如果不相等，将状态码、提示信息、回显数据设置到消息模型对象中，返回消息模型对象
        if(!upwd.equals(user.getController_pwd())){
            messageModel.setCode(0);
            messageModel.setMsg("用户密码不正确");
            return messageModel;
        }

        //登陆成功，将用户信息设置到消息模型中
        messageModel.setObject(user);
        return  messageModel;
    }

}
