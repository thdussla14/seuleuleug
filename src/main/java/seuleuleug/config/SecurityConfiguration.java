package seuleuleug.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration // 스프링 빈에 등록 [ MVC 컴포넌트 ]
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // super.configure(http);
        http
                .csrf() // 사이트 간 요청 위조 [ post,put http 사용 불가능 ]
                    .ignoringAntMatchers("/member/login")// 특정 매핑URL csrf 무시
                    .ignoringAntMatchers("/member/singup")
                    .ignoringAntMatchers("/hmember/hlogin")
                    .ignoringAntMatchers("/hmember/hsignup")
                    .ignoringAntMatchers("/hmember/files");

    }



}
