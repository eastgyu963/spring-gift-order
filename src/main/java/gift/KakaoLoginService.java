package gift;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestClient;

@Service
public class KakaoLoginService {

  private final RestClient restclient;
  @Value("${kakao.rest-api-key}")
  private String restApiKey;
  @Value(("${kakao.redirect-uri}"))
  private String redirectUri;

  public KakaoLoginService(RestClient.Builder builder) {
    this.restclient = RestClient.builder()
        .baseUrl("https://kauth.kakao.com")
        .build();
  }

  public String getRedirectUrl() {
    String url = "/oauth/authorize";
    return "https://kauth.kakao.com" + url +
        "?scope=talk_message" +
        "&response_type=code" +
        "&client_id=" + restApiKey +
        "&redirect_uri=" + redirectUri;
  }

  public String getToken(String authorizationCode) {
    String url = "/oauth/token";

    LinkedMultiValueMap<String, String> body = new LinkedMultiValueMap<>();
    body.add("grant_type", "authorization_code");
    body.add("client_id", restApiKey);
    body.add("redirect_uri", redirectUri);
    body.add("code", authorizationCode);
    KakaoAccessTokenResponse response = restclient.post()
        .uri(url)
        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
        .body(body)
        .retrieve()
        .body(KakaoAccessTokenResponse.class);
    return response.getAccessToken();
  }
}
