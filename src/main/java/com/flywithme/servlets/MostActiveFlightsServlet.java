package com.flywithme.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.flywithme.app.AppConfig;
import com.flywithme.models.MostActiveModel;

public class MostActiveFlightsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String sql = "select a.airline_name,f.flight_id,f.working_day from flight f "
				+ "join airline a on a.airline_id=f.airline_id;";

		Statement st = AppConfig.getStatement();
		String airlineName = null, flightId = null, workingDays = null;
		List<MostActiveModel> activeFlights = new ArrayList<MostActiveModel>();

		try {
			ResultSet rs = st.executeQuery(sql);
			while (rs.next()) {
				airlineName = rs.getString("airline_name");
				flightId = rs.getString("flight_id");
				workingDays = rs.getString("working_day");

				char[] work = workingDays.toCharArray();
				int count = 0;

				for (int i = 0; i < work.length; i++) {
					if (work[i] == '1')
						count++;
				}

				activeFlights.add(new MostActiveModel(airlineName, flightId, count));

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		/*
		 * Collections.sort(activeFlights, (MostActiveModel o1, MostActiveModel o2) -> {
		 * return Integer.compare(o1.getWorkingDays(), o2.getWorkingDays()); });
		 */

		// Collections.reverse(activeFlights);
		
		activeFlights.stream().sorted(Comparator.comparing(MostActiveModel::getWorkingDays).reversed());// Same as above

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
		out.println("<h5>The Most active flights</h5><br>");
		out.println(
				"<table class=\"striped\"><thead><tr><td>Airline</td><td>Flight ID</td><td>Working Days</td></tr></thead><tbody>");

		MostActiveModel prev = null;
		for (MostActiveModel model : activeFlights) {
			if (prev == null) {
				out.println("<tr><td>" + model.getAirlineName() + "</td><td>" + model.getFlightId() + "</td><td>"
						+ model.getWorkingDays() + "</td></tr>");
				prev = model;
			} else if (model.getWorkingDays() == prev.getWorkingDays()) {
				out.println("<tr><td>" + model.getAirlineName() + "</td><td>" + model.getFlightId() + "</td><td>"
						+ model.getWorkingDays() + "</td></tr>");
				prev = model;
			} else {
				break;
			}
		}

		out.println("</tbody></table><br><br>");
		out.println("</body>");
		out.println("</html>");
	}

}
