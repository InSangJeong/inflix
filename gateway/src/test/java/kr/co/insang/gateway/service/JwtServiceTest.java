package kr.co.insang.gateway.service;

import kr.co.insang.gateway.constant.JwtType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class JwtServiceTest {

    @Autowired
    private JWTService testService;

    @Test
    public void verifyToken() throws Exception {
        //given
        String jwtToken = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJpbnNhbmciLCJpYXQiOjE2NDY4NzgzNzksImF1ZCI6ImhvbGR3aW5uIiwiZXhwIjoxNjUyMDYyMzc5LCJncmFkZSI6IlVTRVIiLCJ0eXBlIjoiQUNDRVNTIiwibmlja25hbWUiOiJob2xkIn0.YnneuaX955jgmYgzLfV9lBaopzJdfUFP2161vQgVjPE";

        //when
        boolean resultOk = testService.verifyToken(jwtToken, JwtType.ACCESS);
        boolean resultFail = testService.verifyToken(jwtToken, JwtType.REFRESH);
        boolean resultFail2 = testService.verifyToken("WrongData", JwtType.REFRESH);
        //then
        assertTrue(resultOk, "not True");//토큰 시간 지나면 Fail이 나올 수 있음.
        assertFalse(resultFail, "It's not RefreshToken");
        assertFalse(resultFail2, "Wrong Data");

    }
}
