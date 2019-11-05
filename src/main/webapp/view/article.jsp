<%@ include file="/WEB-INF/valid.jsp" %>
<body>
    <%@ include file="navbar.jsp" %>
    <div class="container">
		<h2 style = "text-align: left"><c:out value = "${requestScope.article.name}"/></h2>
		${requestScope.article.text}
		<h5 style="color: #c3c3c3;font-style: italic"><c:out value = "${requestScope.article.date}"/></h5>
		<h5 style="color: #c3c3c3;font-style: italic"><c:out value = "${requestScope.periodical.name}"/></h5>
	</div>
</body>
</html>