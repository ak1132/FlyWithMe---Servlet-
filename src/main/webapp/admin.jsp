<%@page import="java.util.Enumeration"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page session="true"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
<meta name="viewport" content="width = device-width, initial-scale = 1">
<link rel="stylesheet"
	href="https://fonts.googleapis.com/icon?family=Material+Icons">
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.97.3/css/materialize.min.css">
<script type="text/javascript"
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script type="text/javascript"
	src=" https://cdnjs.cloudflare.com/ajax/libs/materialize/0.97.3/js/materialize.min.js"></script>

<script type="text/javascript">
	

	function clearAddCustFields() {
		$('#firstName').val("");
		$('#lastName').val("");
		$('#address').val("");
		$('#zipcode').val("");
		$('#contactNo').val("");
		$('#creditCardNo').val("");
		$('#email').val("");
		$('#password').val("");
	}
	
	function clearAddCust1Fields() {
		$('#firstName1').val("");
		$('#lastName1').val("");
		$('#address1').val("");
		$('#zipcode1').val("");
		$('#contactNo1').val("");
		$('#creditCardNo1').val("");
	}
	
	function setEditForm(fn, ln, add, zip, cno, cred) {
		$('#firstName1').val(fn);
		$('#lastName1').val(ln);
		$('#address1').val(add);
		$('#zipcode1').val(zip);
		$('#contactNo1').val(cno);
		$('#creditCardNo1').val(cred);
	}

	$(document).ready(function() {
		
		$('.datepicker').pickadate({
			selectMonths : true, // Creates a dropdown to control month
			selectYears : 15, // Creates a dropdown of 15 years to control year,
			today : 'Today',
			clear : 'Clear',
			close : 'Ok',
			closeOnSelect : false,
			format: 'yyyy-mm-dd'
		});
		
		$('#addUser').click(function() {
			var firstName = $('#firstName').val();
			var lastName = $('#lastName').val();
			var address = $('#address').val();
			var zipcode = $('#zipcode').val();
			var contactNo = $('#contactNo').val();
			var creditCardNo = $('#creditCardNo').val();
			var email = $('#email').val();
			var password = $('#password').val();
			$.ajax({
				type : 'POST',
				data : {
					firstName : firstName,
					lastName : lastName,
					address : address,
					zipcode : zipcode,
					contactNo : contactNo,
					creditCardNo : creditCardNo,
					email : email,
					password : password
				},
				url : 'addCustomer',
				success : function(result) {
					$('#result').html(result);
					clearAddCustFields();
				}
			});
		});	
	});
	
	$(document).ready(function(){
		$('#editButton').click(function() {
			var editemailid = $('#editemailid').val();
			$.ajax({
				type : 'POST',
				data : {
					editemailid : editemailid
				},
				url : 'editCustomer',
				success : function(result) {
					var fname="<%=session.getAttribute("fName")%>";
					console.log("<%=session.getAttribute("fName")%>")
					console.log("<%=session.getAttribute("lName")%>");
					var lname = "<%=session.getAttribute("lName")%>";
					var add = "<%=session.getAttribute("address")%>";
					var zip = Number("<%=session.getAttribute("zipcode")%>");
					var cno = Number("<%=session.getAttribute("contactNo")%>");
					var cred = Number("<%=session.getAttribute("creditCardNo")%>");
					setEditForm(fname,lname, add,zip, cno,cred);
				}
				});
			});
	});
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

.page-footer {
	position: absolute;
	bottom: 0;
	width: 100%;
}

#m1 {
	display: none;
}

#m2 {
	display: none;
}

#m3 {
	display: none;
}

#m4 {
	display: none;
}

#m21 {
	display: none;
}

#m22 {
	display: none;
}

#m23 {
	display: none;
}

#m24 {
	display: none;
}

#m25 {
	display: none;
}
</style>

</head>

<body>
	<%
		request.setAttribute("fromPage", "admin.jsp");
	%>
	<nav>
	<div class="nav-wrapper">
		<a href="index.jsp" class="brand-logo center">FlyWithMe-System
			Manager</a>
		<ul id="nav-mobile" class="right hide-on-med-and-down">
			<li><a href="logout" accesskey="1">Logout</a></li>
		</ul>
	</div>
	</nav>

	<div class="container">

		<div class="row">
			<div class="col s12">
				<ul class="tabs">
					<li class="tab col s4"><a href="#m1">Customer Data</a></li>
					<li class="tab col s4"><a class="active" href="#m2">
							Flight Details</a></li>
					<li class="tab col s4 "><a href="#m3"> Sales Summary</a></li>
				</ul>
			</div>

			<div id="m1" class="col s12">

				<center>
					<h5>Manage Customer Data</h5>
					<ul class="collapsible" data-collapsible="accordion">
						<li>
							<div class="collapsible-header">ADD</div>
							<div class="collapsible-body">
								<div class="container">
									<div class="row">
										<div class="row">
											<div class="input-field col s6">
												<input id="firstName" type="text" class="validate" /> <label
													for="firstName">First Name</label>
											</div>
											<div class="input-field col s6">
												<input id="lastName" type="text" class="validate" /> <label
													for="lastName">Last Name</label>
											</div>
										</div>
										<div class="row">
											<div class="input-field col s12">
												<textarea id="address" class="materialize-textarea"></textarea>
												<label for="address">Address</label>
											</div>
										</div>
										<div class="row">
											<div class="input-field col s12">
												<input type="number" name="zipcode" id="zipcode" /> <label
													for="zipcode"><b> Zipcode </b></label>

											</div>
										</div>
										<div class="row">
											<div class="input-field col s12">
												<input type="number" name="contactNo" id="contactNo" /> <label
													for="contactNo"><b> Contact Number </b></label>
											</div>
										</div>
										<div class="row">
											<div class="input-field col s12">
												<input type="number" name="creditCardNo" id="creditCardNo" />
												<label for="creditCardNo"><b> CreditCard Number
												</b><label>
											</div>
										</div>

										<div class="row">
											<div class="input-field col s12">
												<input type="text" name="email" id="email" /> <label
													for="email"><b>Email</b></label>
											</div>
										</div>

										<div class="row">
											<div class="input-field col s12">
												<input type="password" name="password" id="password" /> <label
													for="password"><b>Password</b></label>
											</div>
										</div>

										<button class="btn waves-effect waves-teal" id="addUser">Add</button>
										<div id="result"></div>

									</div>
								</div>
							</div>
						</li>
						<li>
							<div class="collapsible-header">EDIT</div>
							<div class="collapsible-body">
								<div class="container">
									<div class="row">

										<div class="input-field col s12">
											<input type="text" name="editemailid" id="editemailid" /> <label
												for="editemailid"><b> Email</b></label>
										</div>
										<button class="btn waves-effect waves-teal" id="editButton">SUBMIT</button>

										<br> <br>
										<div class="container">
											<form class="col s12" action="saveEdit" method="post"
												id="editForm">
												<div class="row">

													<div class="input-field col s6">
														<input id="firstName1" name="firstName1" type="text"
															class="validate" /> <label for="firstName1">First
															Name</label>
													</div>
													<div class="input-field col s6">
														<input id="lastName1" name="lastName1" type="text"
															class="validate" /> <label for="lastName1">Last
															Name</label>
													</div>
												</div>
												<div class="row">
													<div class="input-field col s12">
														<textarea id="address1" name="address1"
															class="materialize-textarea"></textarea>
														<label for="address1">Address</label>
													</div>
												</div>
												<div class="row">
													<div class="input-field col s12">
														<input type="number" name="zipcode1" id="zipcode1" /> <label
															for="zipcode1"><b> Zipcode </b></label>

													</div>
												</div>
												<div class="row">
													<div class="input-field col s12">
														<input type="number" name="contactNo1" id="contactNo1" />
														<label for="contactNo1"><b> Contact Number </b></label>
													</div>
												</div>
												<div class="row">
													<div class="input-field col s12">
														<input type="number" name="creditCardNo1"
															id="creditCardNo1" /> <label for="creditCardNo1"><b>
																CreditCard Number </b></label>
													</div>
												</div>

												<button class="btn waves-effect waves-teal" type="submit">Edit
													& Save</button>
											</form>
											<div id="editResult"></div>
										</div>
									</div>

								</div>
							</div>
						</li>
						<li>
							<div class="collapsible-header">DELETE</div>
							<div class="collapsible-body">
								<div class="container">
									<div class="row">
										<form id="deleteForm" action="deleteCustomer" method="post">
											<div class="input-field col s12">
												<input type="text" name="deleteEmailid" id="deleteEmailid" />
												<label for="deleteEmailid"><b> Email</b></label>
											</div>
											<button class="btn waves-effect waves-teal" id="deleteButton">SUBMIT</button>
										</form>
									</div>

								</div>
							</div>
						</li>

						<li>
							<div class="collapsible-header">CHECK RESERVATION FOR
								CUSTOMER</div>
							<div class="collapsible-body">
								<div class="container">
									<div class="row">
										<form action="reservationByUID" method="post">
											<div class="input-field col s12">
												<input type="text" name="reserveEmail" id="reserveEmail" />
												<label for="reserveEmail"><b> Email</b></label>
											</div>
											<button class="btn waves-effect waves-teal" type="submit">SUBMIT</button>
										</form>
									</div>

								</div>
							</div>
						</li>

						<li>
							<div class="collapsible-header">BEST CUSTOMER</div>
							<div class="collapsible-body">
								<form id="bestCustomerForm" action="getBestCustomer"
									method="post">
									<button class="btn waves-effect waves-teal" id="bestCustomer"
										type="submit">SUBMIT</button>
								</form>
							</div>
						</li>
					</ul>
			</div>
			<div id="m2" class="col s12">
				<center>
					<h5>Get Flight Details</h5>

					<ul class="collapsible" data-collapsible="accordion">
						<li>
							<div class="collapsible-header">Most Active FLights</div>
							<div class="collapsible-body">
								<form action="mostActiveFlights" method="post">
									<button class="btn waves-effect waves-teal">SUBMIT</button>
								</form>
							</div>
						</li>

						<li>
							<div class="collapsible-header">List of all customers who
								have seats reserved on a given flight</div>
							<div class="collapsible-body">
								<div class="container">

									<form action="reservedSeatsByCustomer" method="post">
										<div class="row">
											<div class="input-field col s12">
												<input type="text" placeholder="ENTER flight id"
													name="custflightid" id="custflightid" /> <label
													for="custflightid"><b> flight id</b></label>
											</div>
										</div>
										<div class="row">
											<div class="input-field col s12">
												<input type="text" placeholder="ENTER airline id"
													name="custairlineid" id="custairlineid" /> <label
													for="custairlineid"><b> Airline id</b></label>
											</div>
										</div>
										<div class="row">
											<label>Flight Date</label>
											<div class="input-field col s12">
												<input type="text" class="datepicker" name="custflightDate"
													id="custflightDate" />
											</div>
										</div>
										<button class="btn waves-effect waves-teal" type="submit">SUBMIT</button>
									</form>
								</div>

							</div>
						</li>


						<li>
							<div class="collapsible-header">Airport Flight Details</div>
							<div class="collapsible-body">
								<div class="container">
									<div class="row">
										<form action="listFlightByAirport" method="post">
											<div class="input-field col s12">
												<input type="text" placeholder="ENTER airport id"
													name="flightAirportId" id="flightAirportId" /> <label
													for="flightAirportId"><b> airport id</b></label>
											</div>
											<button class="btn waves-effect waves-teal" type="submit">SUBMIT</button>
										</form>
									</div>
								</div>
							</div>
						</li>

						<li>
							<div class="collapsible-header">On Time/Delay</div>
							<div class="collapsible-body">
								<form method="post" action="flightStatus">
									<button class="btn waves-effect waves-teal" type="submit">SUBMIT</button>
								</form>
							</div>
						</li>

						<li>
							<div class="collapsible-header">list of flights</div>
							<div class="collapsible-body">
								<form action="listFlights" method="post">
									<button class="btn waves-effect waves-teal" type="submit">SUBMIT</button>
								</form>
							</div>
						</li>

						<li>
							<div class="collapsible-header">Reservation List by flight
								number</div>
							<div class="collapsible-body">
								<div class="container">
									<div class="row">
										<form action="reservationByFlight" method="post">
											<div class="input-field col s12">
												<input type="text" placeholder="ENTER flight id"
													name="reservelistFlight" id="reservelistFlight" /> <label
													for="reservelistFlight"><b> Flight id</b></label>
											</div>
											<div class="input-field col s12">
												<input type="text" placeholder="ENTER airline id"
													name="reservelistAirline" id="reservelistAirline" /> <label
													for="reservelistAirline"><b> Airline id</b></label>
											</div>
											<div class="row">
												<label>From Date</label>
												<div class="input-field col s12">
													<input type="text" class="datepicker" name="fromReserveDate"
														id="fromReserveDate" />
												</div>
											</div>
											<div class="row">
												<label>To Date</label>
												<div class="input-field col s12">
													<input type="text" class="datepicker" name="toReserveDate"
														id="toReserveDate" />
												</div>
											</div>
											<button class="btn waves-effect waves-teal" type="submit">SUBMIT</button>
										</form>
									</div>

								</div>
							</div>
						</li>


					</ul>
			</div>
			<div id="m3" class="col s12">
				<center>
					<h5>Get Sales Details</h5>

					<ul class="collapsible" data-collapsible="accordion">
						<li>
							<div class="collapsible-header">Sales report for particular
								month</div>
							<div class="collapsible-body">
								<div class="container">
									<div class="row">
										<form method="post" action="salesReportByMonth">
											<div class="input-field col s12">
												<input type="text"
													placeholder="ENTER FIRST 3 CHAR MONTH(JAN,FEB,ETC)"
													name="month" id="month" /> <label for="month"><b>
														Month</b></label>
											</div>

											<div class="input-field col s12">
												<input type="number" placeholder="ENTER year" name="year"
													id="year" /> <label for="year"><b> Year</b></label>
											</div>

											<button class="btn waves-effect waves-teal" type="submit">SUBMIT</button>
										</form>
									</div>

								</div>

							</div>
						</li>

						<li>
							<div class="collapsible-header">Revenue by flightid</div>
							<div class="collapsible-body">
								<div class="container">
									<div class="row">
										<form method="post" action="revenueByFlight">
											<div class="input-field col s12">
												<input type="text" placeholder="ENTER flight id"
													name="revenueByFlightId" id="revenueByFlightId" /> <label
													for="revenueByFlightId"><b> Flight id</b></label>
											</div>
											<div class="input-field col s12">
												<input type="text" placeholder="ENTER airline id"
													name="revenueByAirlineId" id="revenueByAirlineId" /> <label
													for="revenueByAirlineId"><b> Airline id</b></label>
											</div>
											<button class="btn waves-effect waves-teal" type="submit">SUBMIT</button>
										</form>
									</div>

								</div>

							</div>
						</li>

						<li>
							<div class="collapsible-header">Revenue by destination city</div>
							<div class="collapsible-body">
								<div class="container">
									<div class="row">
										<form method="post" action="revenueByDestination">
											<div class="input-field col s12">
												<input type="text" placeholder="ENTER destination city"
													name="city" id="city" /> <label for="city"><b>
														Destination City</b></label>
											</div>
											<button class="btn waves-effect waves-teal">SUBMIT</button>
										</form>
									</div>

								</div>

							</div>
						</li>

						<li>
							<div class="collapsible-header">Revenue by customer</div>
							<div class="collapsible-body">
								<div class="container">
									<div class="row">
										<form method="post" action="revenueByCustomer">
											<div class="input-field col s12">
												<input type="text" placeholder="ENTER EMAIL"
													name="revenueCustId" id="revenueCustId" /> <label
													for="revenueCustId"><b> Email</b></label>
											</div>
											<button class="btn waves-effect waves-teal" type="submit">SUBMIT</button>
										</form>
									</div>
								</div>
							</div>
						</li>
					</ul>
			</div>
		</div>
	</div>
</body>

</html>