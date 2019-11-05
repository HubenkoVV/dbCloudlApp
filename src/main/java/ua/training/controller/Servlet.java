package ua.training.controller;

import ua.training.controller.command.CreatorCommand;
import ua.training.util.constant.Attributes;
import ua.training.util.constant.Pages;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Servlet extends HttpServlet {

    private CreatorCommand creatorCommand = new CreatorCommand();

    public void init(){
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        doCommand(request,response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        doCommand(request,response);
    }

    private void doCommand(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String page = creatorCommand.action(request);
        if(page == null){
            request.getSession().setAttribute(Attributes.PAGE, Pages.HOME);
            page = Pages.HOME;
        }
        RequestDispatcher dispatcher = request.getRequestDispatcher(page);
        dispatcher.forward(request, response);
    }


}
