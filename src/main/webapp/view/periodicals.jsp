<%@ include file="/WEB-INF/valid.jsp" %>
<body>
    <%@ include file="navbar.jsp" %>
    <div class="container">
	    <h2><fmt:message bundle="${messages}" key="periodicals"/></h2>
	    <c:if test="${sessionScope.user_role.name eq 'ADMIN'}">
	    	<form action = "/api/add_periodical" method = "POST">
	    		<input type = "hidden" value = "<fmt:message key='periodical.name' bundle = '${messages}'/>" name = "periodical_name">
	    		<input type = "hidden" value = "<fmt:message key='description' bundle = '${messages}'/>" name = "periodical_description">				<button type="submit" class="btn btn-info"><fmt:message key="add.periodical" bundle = "${messages}"/></button>
			</form>
	    </c:if>
	    <c:forEach items = "${requestScope.periodicals_list}" var = "periodical_item">
			<h3 style = "text-align: left"><c:out value = "${periodical_item.name}"/></h3>
			<div><c:out value = "${periodical_item.shortDescription}"/></div>
			
				<c:if test = "${not cust:containsPeriodical(requestScope.user_periodicals, periodical_item) and not cust:containsPeriodical(sessionScope.periodicals_in_basket, periodical_item)}">
					<div>			
						<form action = "/api/add_to_basket" method = "POST">
							<input type = "hidden" value = "${periodical_item.id}" name = "id_periodical">
							<button type="submit" class="btn btn-info pull-right"><fmt:message key="subscribe.now" bundle = "${messages}"/><c:out value = "${periodical_item.price}"/></button>
						</form>
					</div>
				</c:if>
				<div>
					<c:if test = "${cust:containsPeriodical(requestScope.user_periodicals, periodical_item)}">			
						<form action = "/api/articles" method = "POST">
							<input type = "hidden" value = "${periodical_item.id}" name = "id_periodical">
							<button type="submit" class="btn btn-info pull-right"><fmt:message key="show.articles" bundle = "${messages}"/></button>
						</form>
			        </c:if>
					<c:if test="${sessionScope.user_role.name eq 'ADMIN'}">		
						<form action = "/api/add_article" method = "POST">
							<input type = "hidden" value = "${periodical_item.id}" name = "id_periodical">
							<input type = "hidden" value = "<fmt:message key='enter.name' bundle = '${messages}'/>" name = "article_name">
							<input type = "hidden" value = "<fmt:message key='enter.text' bundle = '${messages}'/>" name = "article_text">
							<button type="submit" class="btn btn-info pull-right" style="margin-right: 5px"><fmt:message key="add.article" bundle = "${messages}"/></button>
						</form>
					</c:if>
				</div>
			<br/>
			<hr/>
		</c:forEach>
		<div class = "text-center">
			<ul class = "pagination">
				<c:forEach items = "${requestScope.pages}" var = "page">
					<c:choose>
						<c:when test = "${page == requestScope.current_page}">
							<li class="active"><a href="/api/periodicals?periodicals_page=${page}">${page}</a></li>
						</c:when>
						<c:otherwise>
							<li><a href="/api/periodicals?periodicals_page=${page}">${page}</a></li>
						</c:otherwise>
					</c:choose>
				</c:forEach>
			</ul>
		</div>
	</div>
</body>
</html>