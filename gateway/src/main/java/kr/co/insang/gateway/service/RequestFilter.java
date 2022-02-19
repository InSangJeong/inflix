package kr.co.insang.gateway.service;


import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Service;



@Service
public class RequestFilter {

    // 요청중 토큰 검증이 필요한 요청인지 아닌지 검증 후 리턴.
    // 방화벽에서 쓰이는거처럼 기본적으로 모든 요청이 토큰이 필요하다고 판단하고
    // 토큰이 필요없는 경우만 등록시켜서 필터링.
    //true 토큰 필요, false 토큰 불필요
    //is~~ 으로 메서드명 변경해야함.
    public boolean filterRequest(ServerHttpRequest request){
        // 22.02.18 기준 토큰 필요없는경우는
        //1.  Get  /user   /user?userid=value
        //2.  Post /user   /user
        //3.  Post /login  /login

        if(request.getMethod().matches("GET") ){
            if(request.getPath().toString().equals("/authen/user")){
                if (!request.getQueryParams().isEmpty())
                    return false;//1번.
            }
        }
        if(request.getMethod().matches("POST")){
            if(request.getPath().toString().equals("/authen/user") ||request.getPath().toString().equals("/authen/login")){
                return false;//2, 3번.
            }
        }
        return true;//그 외 모든 요청은 토큰 필요.
    }
}
