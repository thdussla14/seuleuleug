package seuleuleug.socket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Configuration // 컴포넌트 등록
@EnableWebSocket // ws 프로토콜의 매핑
public class WebSocketConfiguration implements WebSocketConfigurer {
    @Autowired // 컴포넌트에 등록한 클래스이므로 Autowired 가능
    private ChattingHandler chattingHandler;
    @Autowired
    private LoginHandler loginHandler;

    @Override // 서버 소켓으로 사용되고 있는 클래스를 등록
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(chattingHandler,"/chat/*").setAllowedOrigins("*").addInterceptors(auctionInterceptor());;
        registry.addHandler(loginHandler,"/intoHomePage/*").setAllowedOrigins("*").addInterceptors(auctionInterceptor());;

    }

    @Bean
    public HandshakeInterceptor auctionInterceptor() {
        return new HandshakeInterceptor() {
            @Override
            public boolean beforeHandshake(org.springframework.http.server.ServerHttpRequest request, org.springframework.http.server.ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {

                // 핸드셰이크 중 auction ID에 해당하는 URI 세그먼트 가져오기
                String path = request.getURI().getPath();
                String auctionId = path.substring(path.lastIndexOf('/') + 1);

                // websocket 세션에 pathes 라는 이름으로 추가
                attributes.put("pathes", auctionId);
                return true;
            }

            @Override
            public void afterHandshake(org.springframework.http.server.ServerHttpRequest request, org.springframework.http.server.ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {
                // 핸드쉐이크 후 할 일 없음
            }
        };
    }

}