<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
<link href="https://fonts.googleapis.com/icon?family=Material+Icons"
	rel="stylesheet" />
<link rel="stylesheet" type="text/css"
	href="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.97.5/css/materialize.min.css" />
<script type="text/javascript"
	src="https://cdnjs.cloudflare.com/ajax/libs/jquery/2.2.1/jquery.min.js"></script>
<script type="text/javascript"
	src="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.97.5/js/materialize.min.js"></script>
<script type="text/javascript">
	function redirection(){
		var bookingType = "<%=session.getAttribute("bkType")%>";
		if(bookingType =="twoway"){
			 window.history.back();
		}
	}
</script>
<style>
body {
	display: flex;
	min-height: 100vh;
	flex-direction: column;
}

main {
	flex: 1 0 auto;
}

body {
	background: #fff;
}

.input-field input[type=date]:focus+label, .input-field input[type=text]:focus+label,
	.input-field input[type=email]:focus+label, .input-field input[type=password]:focus+label
	{
	color: #e91e63;
}

.input-field input[type=date]:focus, .input-field input[type=text]:focus,
	.input-field input[type=email]:focus, .input-field input[type=password]:focus
	{
	border-bottom: 2px solid #e91e63;
	box-shadow: none;
}
</style>
</head>

<body>
	<nav>
	<div class="nav-wrapper">
		<a href="index.html" class="brand-logo center">FlyWithMe</a>
	</div>
	</nav>

	<div class="section"></div>

	<main>
	<center>


		<div class="container">

			<form class="col s12" method="post" action="addPassenger">
				<div class="row">
					<div class="col s12"></div>
				</div>

				<div class="row">
					<div class="input-field col s12">
						<input type="text" placeholder="Full Name" name="fullname1"
							id="fullname1" /> <label for="fullname1">Enter passenger
							name</label>
					</div>
				</div>

				<div class="row">
					<div class="input-field col s12">
						<input type="text" placeholder="Full Name" name="fullname2"
							id="fullname2" /> <label for="fullname2">Enter passenger
							name</label>
					</div>
				</div>


				<div class="row">
					<div class="input-field col s12">
						<input type="text" placeholder="Full Name" name="fullname3"
							id="fullname3" /> <label for="fullname3">Enter passenger
							name</label>
					</div>
				</div>



				<center>
					<div class="row">
						<button type="submit" name="btn_login" onclick="redirection()"
							class="col s12 btn btn-large waves-effect indigo" type="submit">Add</button>
					</div>
				</center>
			</form>

		</div>


	</center>


	</main>

</body>

</html>