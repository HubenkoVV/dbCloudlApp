<%@ page language="java" isErrorPage="true"  contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*, java.text.*" %>
<%@ include file="/WEB-INF/valid.jsp" %>

    <body>
        <div class = "container">
            <div class = "jumbotron">
                <h1><i class="fa fa-ban red"></i>Error Page</h1>
                <p class="lead">Error <%= exception %></p>
                <p><a href = "/view/index.jsp" class="btn btn-default btn-lg">Homepage</a></p>
            </div>
        </div>
    </body>
</html>