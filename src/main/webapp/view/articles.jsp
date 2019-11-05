<%@ include file="/WEB-INF/valid.jsp" %>
<body>
    <%@ include file="navbar.jsp" %>
    <div class="container">
	    <h2><fmt:message bundle="${messages}" key="articles"/></h2>
	    <c:forEach items = "${requestScope.article_list}" var = "article_item">
			<h3 style = "text-align: left"><c:out value = "${article_item.name}"/></h3>
			<h5 style="text-align: left; font-style: italic"><c:out value = "${requestScope.periodical.name}"/></h5>
			<div>			
				<form action = "/api/show_article" method = "POST">
					<input type = "hidden" value = "${article_item.id}" name = "article_id">
					<button type="submit" class="btn btn-info pull-right"><fmt:message key="read.article" bundle = "${messages}"/></button>
				</form>
			</div>
			<br/>
			<hr/>
		</c:forEach>
		<div class = "text-center">
			<ul class = "pagination">
				<c:forEach items = "${requestScope.pages}" var = "page">
					<c:choose>
						<c:when test = "${page == requestScope.current_page}">
							<li class="active"><a href="/api/articles?articles_page=${page}&id_periodical=${requestScope.periodical.id}">${page}</a></li>
						</c:when>
						<c:otherwise>
							<li><a href="/api/articles?articles_page=${page}&id_periodical=${requestScope.periodical.id}">${page}</a></li>
						</c:otherwise>
					</c:choose>
				</c:forEach>
			</ul>
		</div>
	</div>
</body>
</html>