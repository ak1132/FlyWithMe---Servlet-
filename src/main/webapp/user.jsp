<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page session="true"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width = device-width, initial-scale = 1">
<link rel="stylesheet"
	href="https://fonts.googleapis.com/icon?family=Material+Icons">
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.97.3/css/materialize.min.css">
<script type="text/javascript"
	src="https://code.jquery.com/jquery-2.1.1.min.js">
	
</script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.97.3/js/materialize.min.js">
	
</script>
<script type="text/javascript">
	$(document).ready(function() {

		$('#accountDetails').click(function() {
			$.ajax({
				type : 'GET',
				url : 'accountInfo',
				success : function(result) {
					$('#m5').html(result);

				}
			});
		});

		$('#allBookings').click(function() {
			$.ajax({
				type : 'GET',
				url : 'allReservations',
				success : function(result) {
					$('#m3').html(result);
				}
			});
		});

		$('#bestSeller').click(function() {
			$.ajax({
				type : 'GET',
				url : 'bestSellerFlight',
				success : function(result) {
					$('#m6').html(result);
				}
			});
		});

		$('.datepicker').pickadate({
			selectMonths : true,
			selectYears : 15,
			today : 'Today',
			clear : 'Clear',
			close : 'Ok',
			closeOnSelect : false,
			format : 'yyyy-mm-dd'
		});

		$('#currentBookings').click(function() {
			$.ajax({
				type : 'GET',
				url : 'currentReservations',
				success : function(result) {
					$('#m2').html(result);
				}
			});
		});

	});

	function myFunction() {
		document.getElementById("returnDate").disabled = false;
	}
	function myFunction2() {
		document.getElementById("returnDate").disabled = true;
	}
</script>
</head>
<body>
	<nav>
	<div class="nav-wrapper">
		<a href="index.jsp" class="brand-logo center">FlyWithMe</a>
		<ul id="nav-mobile" class="right hide-on-med-and-down">
			<li><a href="logout" accesskey="1">Logout</a></li>

		</ul>
	</div>
	</nav>
	<center>
		<h5 class="indigo-text">
			<%
				out.println("Welcome " + request.getSession().getAttribute("firstName") + " "
						+ request.getSession().getAttribute("lastName"));
			%>
		</h5>
	</center>

	<div class="container">
		<!-- <h3>Tabs Demo</h3> -->
		<div class="row">
			<div class="col s12">
				<ul class="tabs">
					<li class="tab col s2"><a href="#m1">Search</a></li>
					<li class="tab col s2" id="currentBookings"><a href="#m2">
							Current Bookings</a></li>
					<li class="tab col s2 " id="allBookings"><a href="#m3">
							All Bookings</a></li>
					<li class="tab col s2 " id="accountDetails"><a href="#m5">
							Account </a></li>
					<li class="tab col s2 " id="bestSeller"><a href="#m6">
							Best Seller</a></li>

				</ul>
			</div>
			<div id="m1" class="col s12">
				<div class="container">
					<div class="row"></div>
					<div class="row">
						<form class="col s12" action="searchFlights" method="post">
							<div class="row">

								<label>Origin</label> <select id="origin" name="origin"
									class="browser-default">

									<option value="2997">Chhatrapati Shivaji International
										Airport Mumbai India</option>
									<option value="2998">Bilaspur Airport Bilaspur India</option>
									<option value="3043">Netaji Subhash Chandra Bose
										International Airport Kolkata India</option>
									<option value="522">Durham Tees Valley Airport
										Teesside United Kingdom</option>
									<option value="523">East Midlands Airport East
										Midlands United Kingdom</option>
									<option value="524">Llanbedr Airport Llanbedr United
										Kingdom</option>
									<option value="525">RAF Ternhill Ternhill United
										Kingdom</option>
									<option value="526">RAF Shawbury Shawbury United
										Kingdom</option>
									<option value="527">RAF Woodvale Woodvale United
										Kingdom</option>
									<option value="528">Kirkwall Airport Kirkwall United
										Kingdom</option>
									<option value="529">Sumburgh Airport Sumburgh United
										Kingdom</option>
									<option value="530">Wick Airport Wick United Kingdom</option>
									<option value="5489">Fort Severn Airport Fort Severn
										Canada</option>
									<option value="5490">Fort Albany Airport Fort Albany
										Canada</option>
									<option value="5491">Fort Hope Airport Fort Hope
										Canada</option>
									<option value="5492">Makkovik Airport Makkovik Canada</option>
									<option value="5493">Texada Gillies Bay Airport Texada
										Canada</option>
									<option value="5494">Gods Lake Narrows Airport Gods
										Lake Narrows Canada</option>
									<option value="5495">Igloolik Airport Igloolik Canada</option>
									<option value="5496">Kuujjuarapik Airport Kuujjuarapik
										Canada</option>
									<option value="5497">Gillam Airport Gillam Canada</option>
									<option value="5498">Grise Fiord Airport Grise Fiord
										Canada</option>
									<option value="5499">Quaqtaq Airport Quaqtaq Canada</option>
									<option value="5500">Vancouver Harbour Water Aerodrome
										Vancouver Canada</option>
									<option value="6262">Doomadgee Airport Doomadgee
										Australia</option>
									<option value="6263">Darnley Island Airport Darnley
										Island Australia</option>
									<option value="6264">Devonport Airport Devonport
										Australia</option>
									<option value="6265">Elcho Island Airport Elcho Island
										Australia</option>
									<option value="6266">Esperance Airport Esperance
										Australia</option>
									<option value="6267">Flinders Island Airport Flinders
										Island Australia</option>
									<option value="6268">Geraldton Airport Geraldton
										Australia</option>
									<option value="6269">Gladstone Airport Gladstone
										Australia</option>
									<option value="6270">Groote Eylandt Airport Groote
										Eylandt Australia</option>
									<option value="6271">Griffith Airport Griffith
										Australia</option>
									<option value="6272">Horn Island Airport Horn Island
										Australia</option>
									<option value="6273">Hooker Creek Airport Hooker Creek
										Australia</option>
									<option value="6274">Mount Hotham Airport Mount Hotham
										Australia</option>
									<option value="7569">Derby Airport Derby Australia</option>
									<option value="7570">Walgett Airport Walgett Australia</option>
									<option value="7571">Bathurst Island Airport Bathurst
										Island Australia</option>
									<option value="7572">Dunk Island Airport Dunk Island
										Australia</option>
									<option value="7573">Lizard Island Airport Lizard
										Island Australia</option>
									<option value="7574">Hamilton Airport Hamilton
										Australia</option>
									<option value="7575">Halls Creek Airport Halls Creek
										Australia</option>
									<option value="7576">Fitzroy Crossing Airport Fitzroy
										Crossing Australia</option>
									<option value="7577">Ravensthorpe Airport Ravensthorpe
										Australia</option>
									<option value="7578">Wilkins Runway Budd Coast
										Antarctica</option>
									<option value="7579">Provo Municipal Airport Provo
										United States</option>
									<option value="7580">Steamboat Springs Bob Adams Field
										Steamboat Springs United States</option>
									<option value="7581">Delta Municipal Airport Delta
										United States</option>
									<option value="7582">Richfield Municipal Airport
										Richfield United States</option>
									<option value="7583">Carbon County Regional/Buck Davis
										Field Price United States</option>
									<option value="7584">Los Alamos Airport Los Alamos
										United States</option>
									<option value="7586">Lake Havasu City Airport Lake
										Havasu City United States</option>
									<option value="7587">Winslow Lindbergh Regional
										Airport Winslow United States</option>
									<option value="7588">Douglas Municipal Airport Douglas
										United States</option>
									<option value="BET">Berlin Tegel Berlin Germany</option>
									<option value="CHI">Chicago O'Hare International
										Chicago Illinois</option>
									<option value="HJI">Hartsfield-Jackson Atlanta Int
										Atlanta United States of America</option>
									<option value="IVI">Ivato International Antananarivo
										Madagascar</option>
									<option value="JFK">John F. Kennedy International New
										York United States of America</option>
									<option value="LAI">Los Angeles International Los
										Angeles United States of America</option>
									<option value="LGA">LaGuardia New York United States
										of America</option>
									<option value="LHA">London Heathrow London United
										Kingdom</option>
									<option value="LIA">Logan International Boston United
										States of America</option>
									<option value="SFI">San Francisco International San
										Francisco United States of America</option>
									<option value="TIA">Tokyo International Tokyo Japan</option>


								</select> <label>Destination</label> <select id="dest" name="dest"
									class="browser-default">
									<option value="JFK">John F. Kennedy International New
										York United States of America</option>
									<option value="2997">Chhatrapati Shivaji International
										Airport Mumbai India</option>
									<option value="2998">Bilaspur Airport Bilaspur India</option>
									<option value="3043">Netaji Subhash Chandra Bose
										International Airport Kolkata India</option>
									<option value="522">Durham Tees Valley Airport
										Teesside United Kingdom</option>
									<option value="523">East Midlands Airport East
										Midlands United Kingdom</option>
									<option value="524">Llanbedr Airport Llanbedr United
										Kingdom</option>
									<option value="525">RAF Ternhill Ternhill United
										Kingdom</option>
									<option value="526">RAF Shawbury Shawbury United
										Kingdom</option>
									<option value="527">RAF Woodvale Woodvale United
										Kingdom</option>
									<option value="528">Kirkwall Airport Kirkwall United
										Kingdom</option>
									<option value="529">Sumburgh Airport Sumburgh United
										Kingdom</option>
									<option value="530">Wick Airport Wick United Kingdom</option>
									<option value="5489">Fort Severn Airport Fort Severn
										Canada</option>
									<option value="5490">Fort Albany Airport Fort Albany
										Canada</option>
									<option value="5491">Fort Hope Airport Fort Hope
										Canada</option>
									<option value="5492">Makkovik Airport Makkovik Canada</option>
									<option value="5493">Texada Gillies Bay Airport Texada
										Canada</option>
									<option value="5494">Gods Lake Narrows Airport Gods
										Lake Narrows Canada</option>
									<option value="5495">Igloolik Airport Igloolik Canada</option>
									<option value="5496">Kuujjuarapik Airport Kuujjuarapik
										Canada</option>
									<option value="5497">Gillam Airport Gillam Canada</option>
									<option value="5498">Grise Fiord Airport Grise Fiord
										Canada</option>
									<option value="5499">Quaqtaq Airport Quaqtaq Canada</option>
									<option value="5500">Vancouver Harbour Water Aerodrome
										Vancouver Canada</option>
									<option value="6262">Doomadgee Airport Doomadgee
										Australia</option>
									<option value="6263">Darnley Island Airport Darnley
										Island Australia</option>
									<option value="6264">Devonport Airport Devonport
										Australia</option>
									<option value="6265">Elcho Island Airport Elcho Island
										Australia</option>
									<option value="6266">Esperance Airport Esperance
										Australia</option>
									<option value="6267">Flinders Island Airport Flinders
										Island Australia</option>
									<option value="6268">Geraldton Airport Geraldton
										Australia</option>
									<option value="6269">Gladstone Airport Gladstone
										Australia</option>
									<option value="6270">Groote Eylandt Airport Groote
										Eylandt Australia</option>
									<option value="6271">Griffith Airport Griffith
										Australia</option>
									<option value="6272">Horn Island Airport Horn Island
										Australia</option>
									<option value="6273">Hooker Creek Airport Hooker Creek
										Australia</option>
									<option value="6274">Mount Hotham Airport Mount Hotham
										Australia</option>
									<option value="7569">Derby Airport Derby Australia</option>
									<option value="7570">Walgett Airport Walgett Australia</option>
									<option value="7571">Bathurst Island Airport Bathurst
										Island Australia</option>
									<option value="7572">Dunk Island Airport Dunk Island
										Australia</option>
									<option value="7573">Lizard Island Airport Lizard
										Island Australia</option>
									<option value="7574">Hamilton Airport Hamilton
										Australia</option>
									<option value="7575">Halls Creek Airport Halls Creek
										Australia</option>
									<option value="7576">Fitzroy Crossing Airport Fitzroy
										Crossing Australia</option>
									<option value="7577">Ravensthorpe Airport Ravensthorpe
										Australia</option>
									<option value="7578">Wilkins Runway Budd Coast
										Antarctica</option>
									<option value="7579">Provo Municipal Airport Provo
										United States</option>
									<option value="7580">Steamboat Springs Bob Adams Field
										Steamboat Springs United States</option>
									<option value="7581">Delta Municipal Airport Delta
										United States</option>
									<option value="7582">Richfield Municipal Airport
										Richfield United States</option>
									<option value="7583">Carbon County Regional/Buck Davis
										Field Price United States</option>
									<option value="7584">Los Alamos Airport Los Alamos
										United States</option>
									<option value="7586">Lake Havasu City Airport Lake
										Havasu City United States</option>
									<option value="7587">Winslow Lindbergh Regional
										Airport Winslow United States</option>
									<option value="7588">Douglas Municipal Airport Douglas
										United States</option>
									<option value="BET">Berlin Tegel Berlin Germany</option>
									<option value="CHI">Chicago O'Hare International
										Chicago Illinois</option>
									<option value="HJI">Hartsfield-Jackson Atlanta Int
										Atlanta United States of America</option>
									<option value="IVI">Ivato International Antananarivo
										Madagascar</option>
									<option value="LAI">Los Angeles International Los
										Angeles United States of America</option>
									<option value="LGA">LaGuardia New York United States
										of America</option>
									<option value="LHA">London Heathrow London United
										Kingdom</option>
									<option value="LIA">Logan International Boston United
										States of America</option>
									<option value="SFI">San Francisco International San
										Francisco United States of America</option>
									<option value="TIA">Tokyo International Tokyo Japan</option>

								</select>

							</div>
							<div class="row">
								<div class="input-field col s6">
									<p>
										<input id="oneway" type="radio" name="rad" value="oneway"
											class="with-gap" checked onchange="myFunction2()" /> <label
											for="oneway">One way</label>
									</p>
								</div>
								<div class="input-field col s6">
									<p>
										<input id="roundway" type="radio" name="rad" class="with-gap"
											value="roundway" onchange="myFunction()" /> <label
											for="roundway">Round way</label>
									</p>
								</div>
							</div>

							<div class="row">
								<label>Departing</label> <input type="date" class="datepicker"
									id="depDate" name="depDate" />
							</div>

							<div class="row">
								<label>Returning</label> <input type='date' class='datepicker'
									id="returnDate" name="returnDate" disabled />
							</div>
							<div class="row">
								<label>Number of seats</label> <select class="browser-default"
									id="nos" name="nos">
									<option value="1">1</option>
									<option value="2">2</option>
									<option value="3">3</option>
								</select>

							</div>

							<button class="btn waves-effect waves-teal" type="submit">Search</button>

						</form>
					</div>
				</div>

			</div>
			<div id="m5" class="col s12">
				<center></center>
			</div>
			<div id="m2" class="col s12">
				<center></center>
			</div>
			<div id="m3" class="col s12">
				<center></center>
			</div>
			<div id="m6" class="col s12">
				<center></center>
			</div>

		</div>
	</div>
</body>
</html>