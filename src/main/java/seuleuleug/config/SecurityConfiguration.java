package seuleuleug.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import seuleuleug.controller.AuthSuccessFailHandler;
import seuleuleug.service.MemberService;

import java.util.Arrays;

@Configuration // 스프링 빈에 등록 [ MVC 컴포넌트 ]
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private AuthSuccessFailHandler authSuccessFailHandler;
    @Autowired
    private MemberService memberService;
    // 인증[로그인] 관련 보안 담당 메소드
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(memberService).passwordEncoder( new BCryptPasswordEncoder() );
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // super.configure(http);
        http
                .authorizeHttpRequests() // 1.인증[권한]에 따른 http 요청 제한
                    .antMatchers("/board/user/**").hasRole("USER")
                    .antMatchers("/board/doctor/**").hasRole("DOCTOR")
                    .antMatchers("/**").permitAll()
                .and()
                    .formLogin()
                        .loginPage("/member/login")                         // 로그인으로 사용된 페이지의 매핑 URL
                        .loginProcessingUrl("/member/login")                // 로그인을 처리할 매핑 URL
                        .successHandler(authSuccessFailHandler)             // 로그인 성공했을때 이동할 매핑 URL
                        .failureHandler(authSuccessFailHandler)             // 로그인 실패했을때 이동할 매핑 URL
                        .usernameParameter("email") // 로그인시 사용된 계정 아이디 의 필드명
                        .passwordParameter("password") // 로그인시 사용된 계정 패스워드의 필드명
                .and()
                        .logout()
                            .logoutRequestMatcher( new AntPathRequestMatcher("/member/logout")) // 로그아웃 처리 를 요청할 매핑 URL
                            .logoutSuccessUrl("/")                                                          //로그아웃 성공했을때 이동할 매핑 URL
                            .invalidateHttpSession( true )// 세션 초기화
                .and()
                    .oauth2Login() // 소셜 로그인 설정     /oauth2/authorization/클라이언트이름
                    .defaultSuccessUrl("/") // 로그인 성공시 이동할 매핑 URL
                    .userInfoEndpoint()
                    .userService( memberService ); //  oauth2 서비스를 처리할 서비스 구현
        http.cors();
        http.csrf().disable(); // cosf  사용 해제

    }



}
