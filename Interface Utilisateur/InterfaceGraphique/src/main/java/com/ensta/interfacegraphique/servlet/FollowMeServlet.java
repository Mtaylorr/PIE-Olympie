package com.ensta.interfacegraphique.servlet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/followme")
public class FollowMeServlet extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException,IOException {
        try{
            //run the ROS code
            RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/View/followme.jsp");
            dispatcher.forward(req,resp);
            //wait until the robot arrives to its destination to redirect 
        }catch (Exception e){
            throw new ServletException();
        }
    }
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException,IOException {
        doGet(req,resp);
    }
}
