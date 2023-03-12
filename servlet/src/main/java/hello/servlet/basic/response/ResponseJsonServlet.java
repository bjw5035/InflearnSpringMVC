package hello.servlet.basic.response;

import com.fasterxml.jackson.databind.ObjectMapper;
import hello.servlet.basic.HelloData;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "responseHeaderServlet", urlPatterns = "/response-json")
public class ResponseJsonServlet extends HttpServlet {

	private ObjectMapper  ObjectMapper = new ObjectMapper();
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		// Content-Type: application/json
		resp.setContentType("application/json");
		resp.setCharacterEncoding("utf-8");

		HelloData helloData = new HelloData();
		helloData.setUsername("kim");
		helloData.setAge(20);

		// {"username":"kim","age":20}
		String result = ObjectMapper.writeValueAsString(helloData);

		resp.getWriter().write(result);
	}
}
