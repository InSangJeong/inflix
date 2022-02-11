package kr.co.insang.gateway.constant;

import lombok.Getter;

@Getter
public enum JwtType {
    ACCESS(30,"Access"),
    REFRESH(1440,"REFRESH"); //an hour

    private final int time;
    private final String type;
    JwtType(int time, String type){
        this.time = time;
        this.type = type;
    }



}
