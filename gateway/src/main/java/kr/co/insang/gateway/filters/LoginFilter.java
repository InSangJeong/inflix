package kr.co.insang.gateway.filters;

import lombok.Data;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class LoginFilter extends AbstractGatewayFilterFactory<LoginFilter.Config> {
    private static final Logger logger = LogManager.getLogger(LoginFilter.class);
    public LoginFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {
            logger.info("LoginFilter baseMessage>>>>>>" + config.getBaseMessage());
            ServerHttpRequest httpRequest = exchange.getRequest();

            //토큰이 필요한 요청인 경우 httponly(cookie) 토큰이 검증된 경우에만 pass한다.
            HttpMethod method = httpRequest.getMethod();
            if(method.matches(""))
            httpRequest.getPath();
            if(true){
                //토큰 검증 필요

            }else{
                //토큰 검증 X

            }


            if (config.isPreLogger()) {
                logger.info("LoginFilter Start>>>>>>" + exchange.getRequest());
            }
            return chain.filter(exchange).then(Mono.fromRunnable(()->{
                if (config.isPostLogger()) {
                    ServerHttpResponse httpRespone = exchange.getResponse();
                    DataBufferFactory dbf = httpRespone.bufferFactory();


                    //exchange.getResponse().getHeaders().add("Access-Control-Allow-Credentials", "true");




                    logger.info("LoginFilter End>>>>>>" + exchange.getResponse());
                }
            }));
        });
    }

    @Data
    public static class Config {
        private String baseMessage;
        private boolean preLogger;
        private boolean postLogger;
    }
}
