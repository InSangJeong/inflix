package kr.co.insang.gateway.filters;

import kr.co.insang.gateway.service.JWTService;
import kr.co.insang.gateway.service.RequestFilter;
import lombok.Data;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class GlobalFilter extends AbstractGatewayFilterFactory<GlobalFilter.Config> {
    private static final Logger logger = LogManager.getLogger(GlobalFilter.class);
    private RequestFilter requestFilter;
    private JWTService jwtService;

    @Autowired
    public GlobalFilter(RequestFilter requestFilter, JWTService jwtService) {
        super(Config.class);
        this.requestFilter = requestFilter;
        this.jwtService = jwtService;
    }

    @Override
    public GatewayFilter apply(Config config) {

        return ((exchange, chain) -> {
            //logger.info("GlobalFilter baseMessage>>>>>>" + config.getBaseMessage());
            if (config.isPreLogger()) {
            }
            //application.yml에서 predicates를 이용해서 하려했는데 더 복잡할듯해서 여기에다가함.
            //토큰이 필요한 요청인 경우 httponly(cookie) 토큰이 검증된 경우에만 pass한다.
            if(requestFilter.filterRequest(exchange.getRequest())){                 //토큰 검증 필요
                int result = jwtService.isValuedToken(exchange.getRequest());
                if(result == 200){         //유효한 토큰이며 유효한 요청.
                    logger.info("Available Request... : " + exchange.getRequest().getPath().toString());
                    ;
                }
                else if(result == 403){    //유효하지 않은 토큰 403 return
                    logger.info("Get 403 Request... : " + exchange.getRequest().getPath().toString());
                    return handleUnAuthorized(exchange,HttpStatus.FORBIDDEN); // 403 Error
                }
                else{                                                           //유효하지 않은 요청 401 return
                    logger.info("UnAuthorized Request... : " + exchange.getRequest().getPath().toString());
                    return handleUnAuthorized(exchange,HttpStatus.UNAUTHORIZED); // 401 Error
                }
            }



            return chain.filter(exchange).then(Mono.fromRunnable(()->{
                if (config.isPostLogger()) {
                }
            }));
        });
    }
    private Mono<Void> handleUnAuthorized(ServerWebExchange exchange, HttpStatus httpStatus) {
        ServerHttpResponse response = exchange.getResponse();

        response.setStatusCode(httpStatus);
        return response.setComplete();
    }
    @Data
    public static class Config {
        private String baseMessage;
        private boolean preLogger;
        private boolean postLogger;
    }
}
