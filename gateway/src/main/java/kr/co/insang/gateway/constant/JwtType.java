package kr.co.insang.gateway.constant;

import lombok.Getter;

@Getter
public enum JwtType {
    ACCESS(86400,"Access"),        //a day
    REFRESH(604800,"REFRESH");   //a week

    private final int time;
    private final String type;
    JwtType(int time, String type){
        this.time = time;
        this.type = type;
    }


}
