package kr.co.insang.gateway.constant;

import lombok.Getter;

@Getter
public enum JwtType {
    ACCESS(3600,"Access"),        //an hour
    REFRESH(86400,"REFRESH");   //a day

    private final int time;
    private final String type;
    JwtType(int time, String type){
        this.time = time;
        this.type = type;
    }


}
