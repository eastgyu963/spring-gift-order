package gift.controller;

import gift.auth.KakaoOAuthClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class KakaoLoginController {

    private static final Logger log = LoggerFactory.getLogger(KakaoLoginController.class);
    private final KakaoOAuthClient oAuthClient;

    public KakaoLoginController(KakaoOAuthClient oAuthClient) {
        this.oAuthClient = oAuthClient;
    }

    @GetMapping("/kakao/login")
    public String redirect() {
        return "redirect:" + oAuthClient.getRedirectUrl();
    }

    @GetMapping
    public ResponseEntity<Void> getAuthorizationCode(
            @RequestParam(name = "code") String authorizationCode) {
        String token = oAuthClient.getToken(authorizationCode);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
