<%@ include file="/WEB-INF/valid.jsp" %>
<body>
    <%@ include file="navbar.jsp" %>
    <div class="container">
		<h2><fmt:message key="create.article" bundle = "${messages}"/></h2>
		<form style="width: 100%" class = "form-horizontal" action = "/api/create_article" method = "POST">
			<table style="width: 100%">
				<tr>
					<td style="width: 12%" style="text-align:left;vertical-align:top">
						<h4 class="text-right"><fmt:message key="article.name" bundle = "${messages}"/>&nbsp;</h4>
						&nbsp;
					</td>
					<td>
						<input type="text" class="form-control" id="article_name" value = "${requestScope.article_name}" required name = "article_name">
						&nbsp;
					</td>
				</tr>
				<tr>
					<td style="text-align:left;vertical-align:top">
						<h4 class="text-right" class="align-baseline"><fmt:message key="article.text" bundle = "${messages}"/>&nbsp;</h4>
					</td>
					<td>
						<textarea class="form-control" id="article_text" rows="20" required name = "article_text">${requestScope.article_text}</textarea>
					</td>
				</tr>
			</table>
			<input type = "hidden" value = "${requestScope.periodical.id}" name = "id_periodical">
			<div class="text-right"><button type="submit" class="btn btn-default btn-info" style="margin-top:5px"><fmt:message key = "create" bundle = "${messages}"/></button></div>	
		</form>
	</div>
</body>
</html>