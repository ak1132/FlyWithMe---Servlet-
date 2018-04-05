package com.flywithme.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.flywithme.app.AppConfig;

public class ShowSchedule extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String res = (String) request.getParameter("reservationButton");
		Integer reservation_id = Integer.parseInt(res);
		ResultSet rs;
		System.out.println(reservation_id);
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<html>\n" + "<head>\n"
				+ "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n"
				+ "<meta name=\"viewport\" content=\"width = device-width, initial-scale = 1\">\n"
				+ "<link rel=\"stylesheet\"\n" + "	href=\"https://fonts.googleapis.com/icon?family=Material+Icons\">\n"
				+ "<link rel=\"stylesheet\"\n"
				+ "	href=\"https://cdnjs.cloudflare.com/ajax/libs/materialize/0.97.3/css/materialize.min.css\">\n"
				+ "<script type=\"text/javascript\"\n" + "	src=\"https://code.jquery.com/jquery-2.1.1.min.js\">\n"
				+ "	\n" + "</script>\n" + "<script\n"
				+ "	src=\"https://cdnjs.cloudflare.com/ajax/libs/materialize/0.97.3/js/materialize.min.js\">\n" + "	\n"
				+ "</script></head><body><nav>\n" + "	<div class=\"nav-wrapper\">\n"
				+ "		<a href=\"index.jsp\" class=\"brand-logo center\">FlyWithMe</a>\n"
				+ "		<ul id=\"nav-mobile\" class=\"right hide-on-med-and-down\">\n"
				+ "			<li><a href=\"logout\" accesskey=\"1\">Logout</a></li>\n" + "			\n" + "		</ul>\n"
				+ "	</div>\n" + "	</nav>");

		out.println("<table><thead><tr><td>Airline</td><td>Flight</td><td>FlightLeg</td>"
				+ "<td>Origin</td><td>Dep. time</td><td>Destination</td><td>Arr.. time</td></tr></thead><tbody>");

		try {
			rs = AppConfig.getStatement()
					.executeQuery("select r.airline_id,r.flight_id,r.flightleg_id, a1.airport_name as origin,"
							+ "f.scheduled_departure," + "a2.airport_name as dest,f.scheduled_arrival "
							+ "from reservations r "
							+ "join flightleg f on f.airline_id=r.airline_id and f.flight_id=r.flight_id and f.flightleg_id=r.flightleg_id "
							+ "join airport a1 on a1.airport_id=f.origin_airport_id "
							+ "join airport a2 on a2.airport_id=f.destination_airport_id" + " where  r.reservation_id='"
							+ reservation_id + "'");

			while (rs.next()) {
				String airline = (rs.getString("airline_id"));
				String flight = (rs.getString("flight_id"));
				String fl = (rs.getString("flightleg_id"));
				String a1 = (rs.getString("origin"));
				String dt = (rs.getString("scheduled_departure"));
				String a2 = (rs.getString("dest"));
				String at = (rs.getString("scheduled_arrival"));

				out.println("<tr><td>" + airline + "</td><td>" + flight + "</td><td>" + fl + "</td><td>" + a1
						+ "</td><td>" + dt + "</td><td>" + a2 + "</td>" + "<td>" + at + "</td></tr>");

			}
			out.println("</tbody></table></body>");

		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		out.println("</body>");
		out.println("</html>");
	}

}
