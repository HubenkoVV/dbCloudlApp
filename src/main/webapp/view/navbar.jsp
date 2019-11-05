<%@ include file="/WEB-INF/valid.jsp" %>
<nav class="navbar navbar-inverse">
  <div class="container-fluid">
    <div class="navbar-header">
      <a class="navbar-brand" href="${pageContext.request.contextPath}/api/periodicals">NEWS</a>
    </div>
    <ul class="nav navbar-nav">
      <li><a href="${pageContext.request.contextPath}/api/periodicals"><fmt:message bundle="${messages}" key="periodicals"/></a></li>
      <form class="navbar-form navbar-left" action="/api/periodicals" method = "POST">
        <div class="form-group">
          <input type="text" class="form-control" id="search_name" placeholder="<fmt:message bundle='${messages}' key='search'/>" name="search_name" required>
        </div>
        <button type="submit" class="btn btn-default"><span class="glyphicon glyphicon-search"></span></button>
      </form>
    </ul>
    <ul class="nav navbar-nav navbar-right">
      <li class = "dropdown ${requestScope.dropdown_open}">
      				<a href="#" data-toggle="dropdown" class = "dropdown-toggle">
      				    <c:if test = "${sessionScope.periodicals_in_basket ne null}">
                    <span class="badge progress-bar-danger">${sessionScope.periodicals_in_basket.size()}</span>
                  </c:if>
      					<span class="glyphicon glyphicon-shopping-cart"></span>
      				</a>
      				<ul class = "dropdown-menu" id = "dropdown" style = "width:300px; padding-right:5px; padding-left:5px;">
      					<li class="text-info text-center"><h4><fmt:message key = "basket" bundle = "${messages}"/></h4></li>
      					<li class="divider"></li>
      					<c:if test = "${sessionScope.periodicals_in_basket eq null or sessionScope.periodicals_in_basket.size() == 0}">
      					<li>
      						<fmt:message key = "empty.basket" bundle = "${messages}"/>
      					</li>
      					</c:if>
      					<c:forEach items = "${sessionScope.periodicals_in_basket}" var = "periodical_item">
      						<li>
      							<form action = "/api/delete_from_basket" method = "POST" class="form-horizontal">
      								<span><c:out value = "${periodical_item.name}"/></span>
                      <div class = "pull-right">
        								<input type = "hidden" value = "${periodical_item.id}" name = "id_periodical">        									
        								<c:out value = "${periodical_item.price}$"/>
                        <button style="margin-top: -2px;" type="submit" class="close ">&times;</button>
        									&nbsp;
      								</div>
      							</form>
      						</li>
      					</c:forEach>
      					<li class="divider"></li>
      					<li>
      						<div class = "row" style = "margin-right:0px; margin-lef:0px">
      							<span class = "pull-right bg-info">
      							<fmt:message key = "total.price" bundle = "${messages}"/>
      							<c:set var = "total_price" target = "sessionScope.full_price" value = "${sessionScope.full_price}"/>
      							<c:out value = "${total_price}"/>
      							</span>
      						</div>
      					</li>
      					<li class="divider"></li>
      					<li>
      						<form action = "/api/subscribe" method = "POST" class="form-horizontal" style = "margin-bottom:0;">
      							<button type = "submit" class = "btn btn-info center-block"><fmt:message key = "subscribe" bundle = "${messages}"/></button>
      						</form>
      					</li>
      				</ul>
          </li>
          <li class="dropdown"><a class="dropdown-toggle" data-toggle="dropdown" href="#"><fmt:message key = "language" bundle = "${messages}"/> <span class="caret"></span></a>
            <ul class="dropdown-menu">
              <li><a href="/api/language?locale=en"><fmt:message bundle="${messages}" key="en"/></a></li>
              <li><a href="/api/language?locale=uk"><fmt:message bundle="${messages}" key="ua"/></a></li>
            </ul>
          </li>

      <c:if test="${sessionScope.user_role.name eq 'GUEST'}">
        <li><a href="#" data-toggle="modal" data-target="#login_modal"><span class="glyphicon glyphicon-user"></span> <fmt:message bundle="${messages}" key="signIn"/></a></li>
        <li><a href="#" data-toggle="modal" data-target="#registration_modal"><span class="glyphicon glyphicon-log-in"></span> <fmt:message bundle="${messages}" key="registration"/></a></li>
      </c:if>
      <c:if test="${sessionScope.user_role.name ne 'GUEST'}">
        <p class="navbar-text" style="color: white;">${sessionScope.user.surname} ${sessionScope.user.name}</p>
        <li><a href="${pageContext.request.contextPath}/api/my_account"><span class="glyphicon glyphicon-sunglasses"></span> <fmt:message bundle="${messages}" key="account"/></a></li>
        <li><a href="${pageContext.request.contextPath}/api/sign_out"><span class="glyphicon glyphicon-log-out"></span> <fmt:message bundle="${messages}" key="signOut"/></a></li>
      </c:if>
    </ul>
  </div>
</nav>

<cust:exceptionAlert exception="${requestScope.exception}"/>
<c:if test="${requestScope.message ne null}">
  <div class="alert alert-success alert-dismissible center-block" style="width: 90%;">
    <button type="button" class="close" data-dismiss="alert" aria-label="Close">
      <span aria-hidden="true">&times;</span>
    </button>${requestScope.message}
  </div>
</c:if>

      <div class="modal fade" id="login_modal" role="dialog">
          <div class="modal-dialog modal-sm">
            <div class="modal-content">
              <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">&times;</button>
                <h3><fmt:message bundle="${messages}" key="signIn"/></h3>
              </div>
              <div class="modal-body">
                <cust:exceptionAlert exception="${requestScope.signup_exception}"/>
                <form action="/api/login" method = "POST">
                  <h4><fmt:message bundle="${messages}" key="login"/></h4>
                  <input class="form-control" type="text" name="loginSignIn" id="loginSignIn" placeholder= "<fmt:message bundle="${messages}" key="login"/>" required>
                  <h4><fmt:message bundle="${messages}" key="password"/></h4>
                  <input class="form-control" type="password" name="passwordSignIn" id="passwordSignIn" placeholder= "<fmt:message bundle="${messages}" key="password"/>" required>
                  <div class="text-center"><button class="btn btn-success" style="border-color: black; background-color: #5c5c5c; margin: 10px;" id="signInButton" type="submit"> <fmt:message bundle="${messages}" key="signIn"/></button></div>
                </form>
              </div>
              <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal"><fmt:message bundle="${messages}" key="back"/></button>
              </div>
            </div>
          </div>
      </div>

      <div class="modal fade" id="registration_modal" role="dialog">
          <div class="modal-dialog modal-sm">
            <div class="modal-content">
              <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">&times;</button>
                <h3><fmt:message bundle="${messages}" key="registration"/></h3>
              </div>
              <div class="modal-body">
                <cust:exceptionAlert exception = "${requestScope.reg_exception}"/>
                <form action="/api/registration" method = "POST">
                  <h4><fmt:message bundle="${messages}" key="login"/></h4>
                  <input class="form-control" type="text" name="login" id="login" placeholder= "<fmt:message bundle="${messages}" key="login"/>" required>
                  <h4><fmt:message bundle="${messages}" key="password"/></h4>
                  <input class="form-control" type="password" name="password" id="password" placeholder= "<fmt:message bundle="${messages}" key="password"/>" required>
                  <h4><fmt:message bundle="${messages}" key="passwordAgain"/></h4>
                  <input class="form-control" type="password" name="passwordAgain" id="passwordAgain" placeholder= "<fmt:message bundle="${messages}" key="password"/>" required>
                  <h4><fmt:message bundle="${messages}" key="name"/></h4>
                  <input class="form-control" type="text" name="name" id="name" placeholder= "<fmt:message bundle="${messages}" key="name"/>" required>
                  <h4><fmt:message bundle="${messages}" key="surname"/></h4>
                  <input class="form-control" type="text" name="surname" id="surname" placeholder= "<fmt:message bundle="${messages}" key="surname"/>" required>
                  <h4><fmt:message bundle="${messages}" key="phone"/></h4>
                  <input class="form-control" type="text" name="phone" id="phone" placeholder= "<fmt:message bundle="${messages}" key="phone.format"/>">
                  <div class="text-center"><button class="btn btn-success" style="border-color: black; background-color: #5c5c5c; margin: 10px;" id="registrationButton" type="submit"> <fmt:message bundle="${messages}" key="registrate"/></button></div>
                </form>
              </div>
              <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal"><fmt:message bundle="${messages}" key="back"/></button>
              </div>
            </div>
          </div>
      </div>