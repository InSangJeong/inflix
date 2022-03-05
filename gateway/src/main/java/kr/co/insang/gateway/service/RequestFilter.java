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
        // 22.02.20 기준 토큰 필요없는경우는... 많아지니까 복잡해지네 리팩토링 할수있을듯한데..
        //1.  Get       /authen/user?userid=value       //중복계정 확인용
        //2.  Post      /authen/user                    //회원가입 결과
        //3.  Post      /authen/login                   //로그인할때 ID,PW로 회원정보 가져오기
        //4.  Get       /authen/user/{userid}           //Refresh 토큰으로 Access 토큰 만들때 정보가져오기
        //5.  Post      /autho/tokens                   //로그인으로 R, A 토큰가져오기
        //6.  Get       /autho/tokenstate               //토큰 상태확인.
        //7.  Delete    /autho/tokens                   //토큰 삭제
        //8. All        /cms/**                         //테스트중으로 일단 모든 CMS접근을 허용한다.
        //9. All        /comment

        boolean result = true;

        //8번 임시코드
        if(request.getPath().toString().startsWith("/cms/") || request.getPath().toString().startsWith("/comment")) {
            return false;
        }

        if(request.getMethod().matches("GET") ){
            if(request.getPath().toString().equals("/authen/user")){
                if (!request.getQueryParams().isEmpty())
                    result = false;//1번.
            }
            else if(request.getPath().toString().startsWith("/authen/user/")){
                result = false; //4번, !!! 이건 인가서비스에서만 호출할 수 있어야함. 추후 체크하는 코드 추가.
            }
            else if(request.getPath().toString().equals("/autho/tokens")){
                result = false;//6번
            }
        }else if(request.getMethod().matches("POST")){
            if(request.getPath().toString().equals("/authen/user") ||request.getPath().toString().equals("/authen/login")){
                result = false;//2, 3번.
            }
            else if(request.getPath().toString().equals("/autho/tokens")){
                result = false;//5번
            }
        }else if(request.getMethod().matches("DELETE")){
            if(request.getPath().toString().equals("/autho/tokens")){
                result = false;//7번
            }
        }

        return result;//그 외 모든 요청은 토큰 필요.
    }
}
