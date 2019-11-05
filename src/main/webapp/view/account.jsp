<%@ include file="/WEB-INF/valid.jsp" %>
<body>
    <%@ include file="navbar.jsp" %>
    <div class="container">
	    <h2><fmt:message bundle="${messages}" key="account"/></h2>
	    <cust:userInfo user = "${sessionScope.user}"/>
	    <form  action = "/api/update_account" method = "POST">
			<div class = "row">
				<h2><fmt:message key = "update.balance" bundle = "${messages}"/></h2>
				<input type = "number" name = "user_money" id = "user_money">	
				<button type="submit" class="btn btn-default btn-info col-sm-offset-1"><fmt:message key = "update" bundle = "${messages}"/></button>
			</div>
		</form>
	</div>
</body>
</html>