package gift.controller;

import gift.entity.Token;
import gift.service.kakao.KakaoLoginService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class KakaoLoginController {

  private static final Logger LOGGER = LoggerFactory.getLogger(KakaoLoginController.class);
  private final String AuthorizationCategory = "Bearer ";
  private final KakaoLoginService service;

  public KakaoLoginController(KakaoLoginService service) {
    this.service = service;
  }

  @GetMapping("/kakao/login")
  public String redirect() {
    return "redirect:" + service.getRedirectUrl();
  }

  @GetMapping("kakao/callback")
  public ResponseEntity<Token> handleKakaoLogin(
      @RequestParam(name = "code") String authorizationCode) {
    Token token = service.loginWithAuthorizationCode(authorizationCode);

    HttpHeaders headers = new HttpHeaders();
    headers.set("Authorization", AuthorizationCategory + token.getToken());
    return new ResponseEntity<>(token, headers, HttpStatus.OK);
  }
}
