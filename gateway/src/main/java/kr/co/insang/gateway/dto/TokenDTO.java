package kr.co.insang.gateway.dto;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TokenDTO {

    private String refreshToken;
    private String accessToken;
    private String warn;


}
