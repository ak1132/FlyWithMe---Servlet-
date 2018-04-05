package com.flywithme.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.joda.time.DateTime;

import com.flywithme.app.AppConfig;

public class SearchFlightsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public double getFare(Date journeyDate, double fare) {

		Calendar cal = Calendar.getInstance();
		cal.setTime(journeyDate);
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH) + 1;
		int day = cal.get(Calendar.DAY_OF_MONTH);

		LocalDate startDate = LocalDate.of(year, month, day);
		LocalDate currentDate = LocalDate.now();
		long diffInDays = Math.abs(ChronoUnit.DAYS.between(startDate, currentDate));

		if (diffInDays <= 3) {
			fare = fare - (0.03 * fare);
		} else if (diffInDays > 3 && diffInDays <= 7) {
			fare = fare - (0.07 * fare);
		} else if (diffInDays > 7 && diffInDays <= 14) {
			fare = fare - (0.14 * fare);
		} else {
			fare = fare - (0.21 * fare);
		}
		return fare;
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String origin = request.getParameter("origin");
		String dest = request.getParameter("dest");
		String type = request.getParameter("rad");
		String departDate = request.getParameter("depDate");
		String returnDate = request.getParameter("returnDate");
		String nos = request.getParameter("nos");

		if (nos == null)
			nos = "1";

		Date flightDate = null, returnFlightDate = null;
		SimpleDateFormat availDate = new SimpleDateFormat("yyyy-MM-dd");

		try {
			flightDate = availDate.parse(departDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		if (returnDate != null) {
			try {
				returnFlightDate = availDate.parse(returnDate);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}

		String nonStop = "select a.airline_id,a.airline_name, f.flight_id,f.flightleg_id,a1.airport_name as origin, "
				+ "f.scheduled_departure, a2.airport_name as dest, f.scheduled_arrival,f.leg_fare "
				+ "from flightleg f join airline a on a.airline_id=f.airline_id "
				+ "join airport a1 on a1.airport_id=f.origin_airport_id "
				+ "join airport a2 on a2.airport_id=f.destination_airport_id "
				+ "join flightstatus fs on fs.airline_id=f.airline_id and fs.flight_id=f.flight_id and fs.flightleg_id=f.flightleg_id "
				+ "where a1.airport_id=? and a2.airport_id=? and fs.journey_date=? and fs.no_of_seats>=?";

		String multiLeg = "select aa1.airline_id as aa1air, aa2.airline_id as aa2air, aa1.airline_name, f1.flight_id as ff1Id,"
				+ "a1.airport_name as origin,"
				+ " f1.scheduled_departure as originstart, f1.flightleg_id as f1Id, f2.flightleg_id as f2Id,"
				+ "f1.leg_fare as fare1,a3.airport_name as stops,aa2.airline_name as leg2Airline, f2.flight_id as leg2Flight,"
				+ " f1.scheduled_arrival as stoparrive, "
				+ "f2.scheduled_departure as stopstart, a2.airport_name as destination,f2.scheduled_arrival as destarr ,\r\n"
				+ "f2.leg_fare as fare2\r\n" + "from flightleg f1\r\n"
				+ "join flightleg f2 on f1.destination_airport_id=f2.origin_airport_id\r\n"
				+ "join airline aa1 on aa1.airline_id=f1.airline_id\r\n"
				+ "join airline aa2 on aa2.airline_id=f2.airline_id\r\n"
				+ "join airport a1 on a1.airport_id=f1.origin_airport_id\r\n"
				+ "join airport a2 on a2.airport_id=f2.destination_airport_id\r\n"
				+ "join airport a3 on a3.airport_id=f1.destination_airport_id\r\n"
				+ "join flightstatus fs1 on fs1.airline_id=f1.airline_id and fs1.flight_id=f1.flight_id and fs1.flightleg_id=f1.flightleg_id\r\n"
				+ "join flightstatus fs2 on fs2.airline_id=f2.airline_id and fs2.flight_id=f2.flight_id and fs2.flightleg_id=f2.flightleg_id\r\n"
				+ "where a1.airport_id=? and a2.airport_id=? " + "and fs1.journey_date=? and  fs2.journey_date=? "
				+ "and fs1.no_of_seats>=? and fs2.no_of_seats>=?";

		String country = "select country from airport where airport_id='";

		PreparedStatement ps = AppConfig.getPreparedStatement(nonStop);
		Statement st = AppConfig.getStatement();
		ResultSet rs = null;
		String airlineName, flightId = null, flightlegId = null, originalAirport = null, scheduledDeparture = null,
				destinationAirport = null, scheduledArrival, fare, originCountry = null, destCountry = null,
				originStart = null, fare1 = null, stops = null, stopArrival = null, stopStart = null,
				destinationArr = null, fare2 = null, leg2Airline = null, leg2Flight = null, stoparrive = null, aId,
				aa1Id, aa2Id, f1Id, f2Id, ff1Id, ff2Id;

		try {
			rs = st.executeQuery(country + origin + "'");
			while (rs.next()) {
				originCountry = rs.getString("country");
			}
			rs = st.executeQuery(country + dest + "'");
			while (rs.next()) {
				destCountry = rs.getString("country");
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<html>");
		out.println("<head><meta name=\"viewport\" content=\"width = device-width, initial-scale = 1\">\r\n"
				+ "<link rel=\"stylesheet\"\r\n"
				+ "	href=\"https://fonts.googleapis.com/icon?family=Material+Icons\">\r\n"
				+ "<link rel=\"stylesheet\"\r\n"
				+ "	href=\"https://cdnjs.cloudflare.com/ajax/libs/materialize/0.97.3/css/materialize.min.css\">\r\n"
				+ "<script type=\"text/javascript\"\r\n"
				+ "	src=\"https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js\"></script>\r\n"
				+ "<script type=\"text/javascript\"\r\n"
				+ "	src=\" https://cdnjs.cloudflare.com/ajax/libs/materialize/0.97.3/js/materialize.min.js\"></script></head>");
		out.println("<body>");
		if (originCountry.equals(destCountry)) {
			out.println("<center><h5>Search Results for Domestic Booking</h5><br></center>");
		} else {
			out.println("<center><h5>Search Results for International Booking</h5><br></center><br><br>");
		}

		if (type != null && (type.equals("oneway") || type.equals("roundway"))) {

			out.println("<center>One way non stop flights</center><br><br>");
			out.println(
					"<table class=\"striped\"><thead><tr><td>Airline</td><td>Flight</td><td>FlightLeg</td><td>Origin</td>"
							+ "<td>Departure Time</td><td>Destination</td><td>Arrival Time</td><td>Fare</td></tr></thead><tbody>");

			try {
				rs.close();
				ps.setString(1, origin);
				ps.setString(2, dest);
				ps.setDate(3, new java.sql.Date(flightDate.getTime()));
				ps.setInt(4, Integer.parseInt(nos));

				rs = ps.executeQuery();

				if (!rs.isBeforeFirst()) {

					ps.setString(1, origin);
					ps.setString(2, dest);
					ps.setDate(3, new java.sql.Date(new DateTime(flightDate).minusDays(1).toDate().getTime()));
					ps.setInt(4, Integer.parseInt(nos));
					rs = ps.executeQuery();

					if (!rs.isBeforeFirst()) {

						ps.setString(1, origin);
						ps.setString(2, dest);
						ps.setDate(3, new java.sql.Date(new DateTime(flightDate).plusDays(1).toDate().getTime()));
						ps.setInt(4, Integer.parseInt(nos));
						rs = ps.executeQuery();

					}
				}

				if (!rs.isBeforeFirst()) {
					out.println("<br><br><center>No Search Results found</center><br><br>");
				}

				/*
				 * "select a.airline_name, f.flight_id,f.flightleg_id,a1.airport_name as origin, "
				 * +
				 * "f.scheduled_departure, a2.airport_name as dest, f.scheduled_arrival,f.leg_fare "
				 */
				while (rs.next()) {
					aId = rs.getString("airline_id");
					airlineName = rs.getString("airline_name");
					flightId = rs.getString("flight_id");
					flightlegId = rs.getString("flightleg_id");
					originalAirport = rs.getString("origin");
					scheduledDeparture = rs.getString("scheduled_departure");
					destinationAirport = rs.getString("dest");
					scheduledArrival = rs.getString("scheduled_arrival");
					fare = rs.getString("leg_fare");

					double actualFare = getFare(flightDate, Double.parseDouble(fare));
					out.println("<tr><td>" + airlineName + "</td><td>" + flightId + "</td><td>" + flightlegId
							+ "</td><td>" + originalAirport + "</td><td>" + scheduledDeparture + "</td><td>"
							+ destinationAirport + "</td><td>" + scheduledArrival + "</td><td>" + actualFare + "</td>"
							+ "<td><form method='POST' action='bookOrder'><button type='submit' name='book' "
							+ "id='book' class=\"waves-effect waves-light btn\" value='" + "1," + aId + "," + flightId
							+ "," + flightlegId + "," + actualFare + "," + departDate + "," + nos
							+ "'>Book</button></form></td></tr>");
				}

			} catch (SQLException e) {
				e.printStackTrace();
			}
			out.println("</tbody></table><br>");
			out.println("<center>One way multileg flights</center><br><br>");
			out.println(
					"<table class=\"striped\"><thead><tr><td>Airline</td><td>FlightID</td><td>Origin</td><td>Start</td><td>Fare1</td>"
							+ "<td>Stops</td><td>Airline2</td><td>FlightLeg2</td><td>Stop Arrive</td><td>Stop Start</td>"
							+ "<td>Destination</td><td>Dest Arrival</td><td>Fare 2</td></tr></thead><tbody>");

			/*
			 * "select aa1.airline_id as aa1air, aa2.airline_id as aa2air, aa1.airline_name, f1.flight_id as ff1Id,"
			 * + "a1.airport_name as origin," +
			 * " f1.scheduled_departure as originstart, f1.flightleg_id as f1Id, f2.flightleg_id as f2Id,"
			 * +
			 * "f1.leg_fare as fare1,a3.airport_name as stops,aa2.airline_name as leg2Airline, f2.flight_id as leg2Flight,"
			 * + " f1.scheduled_arrival as stoparrive, " +
			 * "f2.scheduled_departure as stopstart, a2.airport_name as destination,f2.scheduled_arrival as destarr ,\r\n"
			 * + "f2.leg_fare as fare2\r\n" + "from flightleg f1\r\n"
			 */

			try {
				ps = AppConfig.getPreparedStatement(multiLeg);
				ps.setString(1, origin);
				ps.setString(2, dest);
				ps.setDate(3, new java.sql.Date(flightDate.getTime()));
				ps.setDate(4, new java.sql.Date(flightDate.getTime()));
				ps.setInt(5, Integer.parseInt(nos));
				ps.setInt(6, Integer.parseInt(nos));

				rs = ps.executeQuery();

				if (!rs.isBeforeFirst()) {

					ps = AppConfig.getPreparedStatement(multiLeg);
					ps.setString(1, origin);
					ps.setString(2, dest);
					ps.setDate(3, new java.sql.Date(new DateTime(flightDate).minusDays(1).toDate().getTime()));
					ps.setDate(4, new java.sql.Date(new DateTime(flightDate).minusDays(1).toDate().getTime()));
					ps.setInt(5, Integer.parseInt(nos));
					ps.setInt(6, Integer.parseInt(nos));

					rs = ps.executeQuery();

					if (!rs.isBeforeFirst()) {

						ps = AppConfig.getPreparedStatement(multiLeg);
						ps.setString(1, origin);
						ps.setString(2, dest);
						ps.setDate(3, new java.sql.Date(new DateTime(flightDate).plusDays(1).toDate().getTime()));
						ps.setDate(4, new java.sql.Date(new DateTime(flightDate).plusDays(1).toDate().getTime()));
						ps.setInt(5, Integer.parseInt(nos));
						ps.setInt(6, Integer.parseInt(nos));

						rs = ps.executeQuery();

					}
				}

				if (!rs.isBeforeFirst()) {
					out.println("<br><br><center>No Search Results found</center><br><br>");
				}

				/*
				 * "select aa1.airline_id as aa1air, aa2.airline_id as aa2air, aa1.airline_name, f1.flight_id as ff1Id,"
				 * + "a1.airport_name as origin," +
				 * " f1.scheduled_departure as originstart, f1.flightleg_id as f1Id, f2.flightleg_id as f2Id,"
				 * +
				 * "f1.leg_fare as fare1,a3.airport_name as stops,aa2.airline_name as leg2Airline, f2.flight_id as leg2Flight,"
				 * + " f1.scheduled_arrival as stoparrive, " +
				 * "f2.scheduled_departure as stopstart, a2.airport_name as destination,f2.scheduled_arrival as destarr ,\r\n"
				 * + "f2.leg_fare as fare2\r\n" + "from flightleg f1\r\n"
				 */

				while (rs.next()) {
					aa1Id = rs.getString("aa1air");
					aa2Id = rs.getString("aa2air");
					f1Id = rs.getString("f1Id");
					f2Id = rs.getString("f2Id");
					airlineName = rs.getString("airline_name");
					ff1Id = rs.getString("ff1Id");
					originalAirport = rs.getString("origin");
					originStart = rs.getString("originstart");
					fare1 = rs.getString("fare1");
					stops = rs.getString("stops");
					leg2Airline = rs.getString("leg2Airline");
					leg2Flight = rs.getString("leg2Flight");
					stopArrival = rs.getString("stoparrive");
					stopStart = rs.getString("stopstart");
					destinationAirport = rs.getString("destination");
					destinationArr = rs.getString("destarr");
					fare2 = rs.getString("fare2");

					double f1 = getFare(flightDate, Double.parseDouble(fare1));
					double f2 = getFare(flightDate, Double.parseDouble(fare2));

					out.println("<tr><td>" + airlineName + "</td><td>" + ff1Id + "</td><td>" + originalAirport
							+ "</td><td>" + originStart + "</td><td>" + f1 + "</td><td>" + stops + "</td><td>"
							+ leg2Airline + "</td><td>" + leg2Flight + "</td><td>" + stopArrival + "</td><td>"
							+ stopStart + "</td><td>" + destinationAirport + "</td><td>" + destinationArr + "</td><td>"
							+ f2 + "</td>"
							+ "<td><form method='POST' action='bookOrder'><button type='submit' name='book' "
							+ "id='book' class=\"waves-effect waves-light btn\" value='" + "2," + aa1Id + "," + ff1Id
							+ "," + f1Id + "," + aa2Id + "," + leg2Flight + "," + f2Id + "," + departDate + "," + f1
							+ "," + f2 + "," + nos + "'>Book</button></form></td></tr>");
				}

			} catch (SQLException e) {
				e.printStackTrace();
			}
			out.println("</tbody></table></body>");
		}
		if (type != null && type.equals("roundway")) {

			out.println("<center>Return non stop flights</center><br><br>");
			out.println(
					"<table class=\"striped\"><thead><tr><td>Airline</td><td>Flight</td><td>FlightLeg</td><td>Origin</td>"
							+ "<td>Departure Time</td><td>Destination</td><td>Arrival Time</td><td>Fare</td></tr></thead><tbody>");

			try {
				rs.close();

				ps.setString(1, dest);
				ps.setString(2, origin);
				ps.setDate(3, new java.sql.Date(returnFlightDate.getTime()));
				ps.setInt(4, Integer.parseInt(nos));

				rs = ps.executeQuery();

				if (!rs.isBeforeFirst()) {

					ps.setString(1, dest);
					ps.setString(2, origin);
					ps.setDate(3, new java.sql.Date(new DateTime(returnFlightDate).minusDays(1).toDate().getTime()));
					ps.setInt(4, Integer.parseInt(nos));
					rs = ps.executeQuery();

					if (!rs.isBeforeFirst()) {

						ps.setString(1, dest);
						ps.setString(2, origin);
						ps.setDate(3, new java.sql.Date(new DateTime(returnFlightDate).plusDays(1).toDate().getTime()));
						ps.setInt(4, Integer.parseInt(nos));
						rs = ps.executeQuery();

					}
				}

				if (!rs.isBeforeFirst()) {
					out.println("<br><br><center>No Search Results found</center><br><br>");
				}

				/*
				 * "select a.airline_name, f.flight_id,f.flightleg_id,a1.airport_name as origin, "
				 * +
				 * "f.scheduled_departure, a2.airport_name as dest, f.scheduled_arrival,f.leg_fare "
				 */
				while (rs.next()) {
					aId = rs.getString("airline_id");
					airlineName = rs.getString("airline_name");
					flightId = rs.getString("flight_id");
					flightlegId = rs.getString("flightleg_id");
					originalAirport = rs.getString("origin");
					scheduledDeparture = rs.getString("scheduled_departure");
					destinationAirport = rs.getString("dest");
					scheduledArrival = rs.getString("scheduled_arrival");
					fare = rs.getString("leg_fare");

					double actualFare = getFare(returnFlightDate, Double.parseDouble(fare));
					out.println("<tr><td>" + airlineName + "</td><td>" + flightId + "</td><td>" + flightlegId
							+ "</td><td>" + originalAirport + "</td><td>" + scheduledDeparture + "</td><td>"
							+ destinationAirport + "</td><td>" + scheduledArrival + "</td><td>" + actualFare + "</td>"
							+ "<td><form method='POST' action='bookOrder'><button type='submit' name='book' "
							+ "id='book' class=\"waves-effect waves-light btn\" value='" + "1," + aId + "," + flightId
							+ "," + flightlegId + "," + actualFare + "," + returnFlightDate + "," + nos
							+ "'>Book</button></form></td></tr>");
				}

			} catch (SQLException e) {
				e.printStackTrace();
			}
			out.println("</tbody></table><br>");
			out.println("<center>Return multileg flights</center><br><br>");
			out.println(
					"<table class=\"striped\"><thead><tr><td>Airline</td><td>FlightID</td><td>Origin</td><td>Start</td><td>Fare1</td>"
							+ "<td>Stops</td><td>Airline2</td><td>FlightLeg2</td><td>Stop Arrive</td><td>Stop Start</td>"
							+ "<td>Destination</td><td>Dest Arrival</td><td>Fare 2</td></tr></thead><tbody>");

			/*
			 * "select aa1.airline_id as aa1air, aa2.airline_id as aa2air, aa1.airline_name, f1.flight_id as ff1Id,"
			 * + "a1.airport_name as origin," +
			 * " f1.scheduled_departure as originstart, f1.flightleg_id as f1Id, f2.flightleg_id as f2Id,"
			 * +
			 * "f1.leg_fare as fare1,a3.airport_name as stops,aa2.airline_name as leg2Airline, f2.flight_id as leg2Flight,"
			 * + " f1.scheduled_arrival as stoparrive, " +
			 * "f2.scheduled_departure as stopstart, a2.airport_name as destination,f2.scheduled_arrival as destarr ,\r\n"
			 * + "f2.leg_fare as fare2\r\n" + "from flightleg f1\r\n"
			 */

			try {
				ps = AppConfig.getPreparedStatement(multiLeg);
				ps.setString(1, dest);
				ps.setString(2, origin);
				ps.setDate(3, new java.sql.Date(returnFlightDate.getTime()));
				ps.setDate(4, new java.sql.Date(returnFlightDate.getTime()));
				ps.setInt(5, Integer.parseInt(nos));
				ps.setInt(6, Integer.parseInt(nos));

				rs = ps.executeQuery();

				if (!rs.isBeforeFirst()) {

					ps = AppConfig.getPreparedStatement(multiLeg);
					ps.setString(1, dest);
					ps.setString(2, origin);
					ps.setDate(3, new java.sql.Date(new DateTime(returnFlightDate).minusDays(1).toDate().getTime()));
					ps.setDate(4, new java.sql.Date(new DateTime(returnFlightDate).minusDays(1).toDate().getTime()));
					ps.setInt(5, Integer.parseInt(nos));
					ps.setInt(6, Integer.parseInt(nos));

					rs = ps.executeQuery();

					if (!rs.isBeforeFirst()) {

						ps = AppConfig.getPreparedStatement(multiLeg);
						ps.setString(1, dest);
						ps.setString(2, origin);
						ps.setDate(3, new java.sql.Date(new DateTime(returnFlightDate).plusDays(1).toDate().getTime()));
						ps.setDate(4, new java.sql.Date(new DateTime(returnFlightDate).plusDays(1).toDate().getTime()));
						ps.setInt(5, Integer.parseInt(nos));
						ps.setInt(6, Integer.parseInt(nos));
						rs = ps.executeQuery();

					}
				}

				if (!rs.isBeforeFirst()) {
					out.println("<br><br><center>No Search Results found</center><br><br>");
				}

				/*
				 * "select aa1.airline_id as aa1air, aa2.airline_id as aa2air, aa1.airline_name, f1.flight_id as ff1Id,"
				 * + "a1.airport_name as origin," +
				 * " f1.scheduled_departure as originstart, f1.flightleg_id as f1Id, f2.flightleg_id as f2Id,"
				 * +
				 * "f1.leg_fare as fare1,a3.airport_name as stops,aa2.airline_name as leg2Airline, f2.flight_id as leg2Flight,"
				 * + " f1.scheduled_arrival as stoparrive, " +
				 * "f2.scheduled_departure as stopstart, a2.airport_name as destination,f2.scheduled_arrival as destarr ,\r\n"
				 * + "f2.leg_fare as fare2\r\n" + "from flightleg f1\r\n"
				 */

				while (rs.next()) {
					aa1Id = rs.getString("aa1air");
					aa2Id = rs.getString("aa2air");
					f1Id = rs.getString("f1Id");
					f2Id = rs.getString("f2Id");
					airlineName = rs.getString("airline_name");
					ff1Id = rs.getString("ff1Id");
					originalAirport = rs.getString("origin");
					originStart = rs.getString("originstart");
					fare1 = rs.getString("fare1");
					stops = rs.getString("stops");
					leg2Airline = rs.getString("leg2Airline");
					leg2Flight = rs.getString("leg2Flight");
					stopArrival = rs.getString("stoparrive");
					stopStart = rs.getString("stopstart");
					destinationAirport = rs.getString("destination");
					destinationArr = rs.getString("destarr");
					fare2 = rs.getString("fare2");

					double f1 = getFare(returnFlightDate, Double.parseDouble(fare1));
					double f2 = getFare(returnFlightDate, Double.parseDouble(fare2));

					out.println("<tr><td>" + airlineName + "</td><td>" + ff1Id + "</td><td>" + originalAirport
							+ "</td><td>" + originStart + "</td><td>" + f1 + "</td><td>" + stops + "</td><td>"
							+ leg2Airline + "</td><td>" + leg2Flight + "</td><td>" + stopArrival + "</td><td>"
							+ stopStart + "</td><td>" + destinationAirport + "</td><td>" + destinationArr + "</td><td>"
							+ f2 + "</td>"
							+ "<td><form method='POST' action='bookOrder'><button type='submit' name='book' "
							+ "id='book' class=\"waves-effect waves-light btn\" value='" + "2," + aa1Id + "," + ff1Id
							+ "," + f1Id + "," + aa2Id + "," + leg2Flight + "," + f2Id + "," + returnFlightDate + ","
							+ f1 + "," + f2 + "," + nos + "'>Book</button></form></td></tr>");

				}

			} catch (SQLException e) {
				e.printStackTrace();
			}
			out.println("</tbody></table></body>");
		}

		out.println("</body>");
		out.println("</html>");
	}

}
