package gift.client.auth;


import gift.dto.kakao.KakaoAccessTokenResponse;
import gift.dto.kakao.KakaoUserProperty;
import gift.exception.KakaoApiResponseException;
import gift.exception.KakaoLoginTimeoutException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class KakaoOAuthClient {

  private static final String baseUrl = "https://kauth.kakao.com";
  private static final Logger log = LoggerFactory.getLogger(KakaoOAuthClient.class);

  private final RestClient restclient;
  private final String restApiKey;
  private final String redirectUri;

  public KakaoOAuthClient(RestClient.Builder builder,
      @Value("${kakao.rest-api-key}") String restApiKey,
      @Value(("${kakao.redirect-uri}")) String redirectUri) {
    SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
    factory.setReadTimeout(6000);
    factory.setConnectTimeout(6000);

    this.restclient = RestClient.builder()
        .requestFactory(factory)
        .baseUrl(baseUrl)
        .build();
    this.restApiKey = restApiKey;
    this.redirectUri = redirectUri;
  }

  public String getRedirectUrl() {
    String url = "/oauth/authorize";
    return UriComponentsBuilder
        .fromUriString(baseUrl + url)
        .queryParam("scope", "talk_message")
        .queryParam("response_type", "code")
        .queryParam("client_id", restApiKey)
        .queryParam("redirect_uri", redirectUri)
        .toUriString();
  }

  public String getToken(String authorizationCode) {
    String url = "/oauth/token";
    LinkedMultiValueMap<String, String> body = new LinkedMultiValueMap<>();
    body.add("grant_type", "authorization_code");
    body.add("client_id", restApiKey);
    body.add("redirect_uri", redirectUri);
    body.add("code", authorizationCode);
    try {
      KakaoAccessTokenResponse response = restclient.post()
          .uri(baseUrl + url)
          .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
          .body(body)
          .retrieve()
          .body(KakaoAccessTokenResponse.class);
      if (response == null) {
        throw new KakaoApiResponseException("카카오 api 응답 오류가 발생");
      }
      return response.getAccessToken();
    } catch (ResourceAccessException e) {
      throw new KakaoLoginTimeoutException("카카오 api 타임아웃 발생");
    }
  }

  public KakaoUserProperty getKakaoUserProperty(String accessToken) {
    try {
      KakaoUserProperty response = restclient.post()
          .uri("https://kapi.kakao.com/v2/user/me")
          .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
          .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
          .retrieve()
          .body(KakaoUserProperty.class);
      if (response == null) {
        throw new KakaoApiResponseException("카카오 api 응답 오류가 발생");
      }
      log.info("user id={}", response.getId());
      return response;
    } catch (ResourceAccessException e) {
      throw new KakaoLoginTimeoutException("카카오 api 타임아웃 발생");
    }
  }
}
