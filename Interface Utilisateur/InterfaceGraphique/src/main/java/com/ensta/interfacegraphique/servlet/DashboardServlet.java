package com.ensta.interfacegraphique.servlet;

import com.ensta.interfacegraphique.model.Personnel;
import com.ensta.interfacegraphique.model.Salle;
import com.ensta.interfacegraphique.service.PersonnelService;
import com.ensta.interfacegraphique.service.SalleService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/dashboard")
public class DashboardServlet extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException,IOException {
        PersonnelService personnelService = PersonnelService.getInstance();
        SalleService salleService = SalleService.getInstance();
        try{
            String searchVal = req.getParameter("searchVal");
            List<Personnel> personnels = personnelService.getByString(searchVal);
            List<Salle> salles = salleService.getByNameorId(searchVal);
            req.setAttribute("personnels",personnels);
            req.setAttribute("personnelExists",(personnels!=null && personnels.size()!=0));
            req.setAttribute("salleExists",(salles!=null && salles.size()!=0));
            req.setAttribute("salles",salles);
            RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/View/dashboard.jsp");
            dispatcher.forward(req,resp);
        }catch (Exception e){
            throw new ServletException();
        }
    }
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException,IOException {
        doGet(req,resp);
    }
}
