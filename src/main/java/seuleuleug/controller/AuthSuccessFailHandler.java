package seuleuleug.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import seuleuleug.domain.hospital.HMemberDto;
import seuleuleug.domain.member.MemberDto;
import seuleuleug.domain.member.MemberEntityRepository;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@Component // 빈 등록
@Slf4j // 로그
public class AuthSuccessFailHandler implements AuthenticationSuccessHandler , AuthenticationFailureHandler {

    @Autowired
    MemberEntityRepository memberEntityRepository;
    private ObjectMapper objectMapper = new ObjectMapper();


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String json = null;
        if(authentication.getAuthorities().toString().equals("[ROLE_DOCTOR]")){
            HMemberDto dto = (HMemberDto) authentication.getPrincipal();
            json = objectMapper.writeValueAsString(dto);
        }else {
            MemberDto dto = (MemberDto) authentication.getPrincipal();
            json = objectMapper.writeValueAsString(dto);
        }
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.getWriter().println(json);
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        log.info("exception : " + exception.toString());
        String json  = objectMapper.writeValueAsString(false);

        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json"); // ? @ResponseBody 사용 안했을때는 직접 작용
        response.getWriter().println(json);
    }
}
