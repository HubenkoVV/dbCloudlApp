package ua.training.controller.filter;

import ua.training.model.entities.UserRole;
import ua.training.util.constant.Attributes;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthorizationFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        UserRole userRole = (UserRole)(request.getSession().getAttribute(Attributes.USER_ROLE));
        if (userRole == null) {
            userRole = UserRole.GUEST;
            request.getSession().setAttribute(Attributes.USER_ROLE, userRole);
        }
        filterChain.doFilter(request,response);
    }

    @Override
    public void destroy() {

    }
}
