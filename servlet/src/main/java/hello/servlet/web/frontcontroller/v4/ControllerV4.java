package hello.servlet.web.frontcontroller.v4;

import hello.servlet.domain.member.Member;
import hello.servlet.domain.member.MemberRepository;

import java.util.List;
import java.util.Map;

public interface ControllerV4 {
    /**
     * @param paramMap
     * @param model
     * @return viewName
     */

    String process(Map<String, String> paramMap, Map<String, Object> model);
}


