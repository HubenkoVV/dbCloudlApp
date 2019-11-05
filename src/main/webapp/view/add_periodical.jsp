<%@ include file="/WEB-INF/valid.jsp" %>
<body>
    <%@ include file="navbar.jsp" %>
    <div class="container">
		<h2><fmt:message key="create.periodical" bundle = "${messages}"/></h2>
		<form style="width: 100%" class = "form-horizontal" action = "/api/create_periodical" method = "POST">
			<table style="width: 100%">
				<tr>
					<td style="width: 15%" style="text-align:left;vertical-align:top">
						<h4 class="text-right"><fmt:message key="periodical.name" bundle = "${messages}"/>&nbsp;</h4>
						&nbsp;
					</td>
					<td>
						<input type="text" class="form-control" id="periodical_name" value = "${requestScope.periodical_name}" required name = "periodical_name"> &nbsp;
					</td>
				</tr>
				<tr>
					<td style="text-align:left;vertical-align:top">
						<h4 class="text-right"><fmt:message key="description" bundle = "${messages}"/>&nbsp;</h4>
					</td>
					<td>
						<textarea class="form-control" id="periodical_description" rows="20" required name = "periodical_description">${requestScope.periodical_description}</textarea> &nbsp;
					</td>
				</tr>
				<tr>
					<td style="text-align:left;vertical-align:top">
						<h4 class="text-right"><fmt:message key="price" bundle = "${messages}"/>&nbsp;</h4>
					</td>
					<td>
						<input type="number" class="form-control" id="periodical_price" required name = "periodical_price">
					</td>
				</tr>
			</table>
			<div class="text-right"><button type="submit" class="btn btn-default btn-info" style="margin-top:5px"><fmt:message key = "create" bundle = "${messages}"/></button></div>	
		</form>
	</div>
</body>
</html>