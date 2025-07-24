package gift.controller;

import gift.service.KakaoLoginService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class KakaoLoginController {

  private final KakaoLoginService service;

  public KakaoLoginController(KakaoLoginService service) {
    this.service = service;
  }

  @GetMapping("/kakao/login")
  public String redirect() {
    return "redirect:" + service.getRedirectUrl();
  }

  @GetMapping
  public ResponseEntity<Void> getAuthorizationCode(
      @RequestParam(name = "code") String authorizationCode) {
    String token = service.getToken(authorizationCode);
    System.out.println(token);
    return new ResponseEntity<>(HttpStatus.OK);
  }
}
