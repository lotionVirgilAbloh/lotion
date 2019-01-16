<html>
<head>
    <link href="https://cdn.bootcss.com/bootstrap/4.1.1/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
	<div class="container">
		<h2>Please Confirm</h2>
        <p>
			<#if authorizationRequest ??>

					Do you authorize "${authorizationRequest.clientId}" at "${authorizationRequest.redirectUri}" to access your protected resources
					with scope ${authorizationRequest.scope?join(", ")}.
				<form id="confirmationForm" name="confirmationForm"
                      action="../oauth/authorize" method="post">
                    <input name="user_oauth_approval" value="true" type="hidden" />
				<#--<input type="hidden" id="csrf_token" name="${_csrf.parameterName}" value="${_csrf.token}"/>-->
                    <button class="btn btn-primary" type="submit">Approve</button>
                </form>
				<form id="denyForm" name="confirmationForm"
					  action="../oauth/authorize" method="post">
					<input name="user_oauth_approval" value="false" type="hidden" />
				<#--<input type="hidden" id="csrf_token" name="${_csrf.parameterName}" value="${_csrf.token}"/>-->
					<button class="btn btn-primary" type="submit">Deny</button>
				</form>
			<#else>
				<script  type="text/javascript">
					window.location.href="login";
			</script>
			</#if>
        </p>
	</div>
</body>
<script src="https://cdn.bootcss.com/bootstrap/4.1.1/js/bootstrap.min.js"></script>
</html>