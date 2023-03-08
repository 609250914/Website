package com.controller;

import com.entity.valueObject.MessageModel;
import com.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *
 */

@WebServlet("/login")
public class UserServlet extends HttpServlet {
    //实例化UserService对象
    private UserService userService = new UserService();

    /*
        1. 接收客户端的请求（接收参数：姓名、密码）
        2. 调用service层的方法，返回消息模型对象
        3. 判断消息模型的状态码
            如果状态码是失败
                将消息模型对象设置到request作用域中，请求转发跳转到login.jsp
            如果状态码是成功
                将消息模型中的用户信息设置到session作用域中，重定向跳转到index.jsp

     */
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        String uname = request.getParameter("customer_id");
        String upwd = request.getParameter("customer_pwd");
        String type = request.getParameter("customer_id");


        //判断账号为管理员或是普通用户
        if (type.equals("admin")) {
            MessageModel messageModel = userService.adminLogin(uname, upwd);
            if (messageModel.getCode() == 1) {
                request.getSession().setAttribute("user", messageModel.getObject());
                request.getSession().setAttribute("uname", uname);
                response.sendRedirect("ctrl.html");
            } else {
                request.setAttribute("messageModel", messageModel);
                request.getRequestDispatcher("login.jsp").forward(request, response);
            }
        } else
        {//判断登录类型
//                checked=user.check_pwd(uname,upwd);
            MessageModel messageModel = userService.userLogin(uname, upwd);
            if (messageModel.getCode() == 1) {
                request.getSession().setAttribute("user", messageModel.getObject());
                request.getSession().setAttribute("uname", uname);
//                    System.out.println(uname);
                response.sendRedirect("index.jsp");
            } else {
                request.setAttribute("messageModel", messageModel);
                request.getRequestDispatcher("login.jsp").forward(request, response);
            }
        }

//            //将消息模型中的用户信息设置到session作用域中，重定向跳转到index.jsp
//            request.getSession().setAttribute("user", messageModel.getObject());
//            response.sendRedirect("customer_view.jsp");
//        }
//        } else {
//                request.setAttribute("messageModel", messageModel);
//                request.getRequestDispatcher("login.jsp").forward(request, response);
//            }
    }
}
