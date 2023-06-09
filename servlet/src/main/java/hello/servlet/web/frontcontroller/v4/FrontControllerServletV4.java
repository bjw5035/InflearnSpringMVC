package hello.servlet.web.frontcontroller.v4;

import hello.servlet.web.frontcontroller.ModelView;
import hello.servlet.web.frontcontroller.MyView;
import hello.servlet.web.frontcontroller.v4.controller.MemberFromControllerV4;
import hello.servlet.web.frontcontroller.v4.controller.MemberListControllerV4;
import hello.servlet.web.frontcontroller.v4.controller.MemberSaveControllerV4;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "frontControllerServletV4", urlPatterns = "/front-controller/v4/*")
public class FrontControllerServletV4 extends HttpServlet {
	private Map<String, ControllerV4> controllerMap = new HashMap<>();

	public FrontControllerServletV4() {
		controllerMap.put("/front-controller/v3/members/new-form", new MemberFromControllerV4());
		controllerMap.put("/front-controller/v3/members/save", new MemberSaveControllerV4());
		controllerMap.put("/front-controller/v3/members", new MemberListControllerV4());
	}

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String requestURI = request.getRequestURI();
		ControllerV4 controller = controllerMap.get(requestURI);
		if (controller == null) {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return;
		}

		Map<String, String> paramMap = createParamMap(request);

		// 모델 객체 전달(프론트 컨트롤러에서 생성해서 넘겨줌. 컨트롤러에서 모델객체에 값을 담으면 여기에 그대로 담겨있음)
		Map<String, Object> model = new HashMap<>();

		/*컨트롤러가 직접 뷰의 논리 이름을 반환, 이 값을 사용해서 실제 물리 뷰를 찾을 수 있다.*/
		String viewName = controller.process(paramMap, model);
		MyView view = viewResolver(viewName);

		view.render(model, request, response);
	}

	private Map<String, String> createParamMap(HttpServletRequest request) {
		Map<String, String> paramMap = new HashMap<>();
		request.getParameterNames().asIterator()
				.forEachRemaining(paramName -> paramMap.put(paramName, request.getParameter(paramName)));
		return paramMap;
	}

	private MyView viewResolver(String viewName) {
		return new MyView("/WEB-INF/views/" + viewName + ".jsp");
	}
}
