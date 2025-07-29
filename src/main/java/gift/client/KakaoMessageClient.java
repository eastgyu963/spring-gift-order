package gift.client;

import gift.dto.kakao.KakaoMessageResponse;
import gift.exception.KakaoApiResponseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestClient;

@Component
public class KakaoMessageClient {

  private static final Logger LOGGER = LoggerFactory.getLogger(KakaoMessageClient.class);
  private final String baseUrl = "https://kapi.kakao.com";

  private final RestClient restclient;
  private final String redirectUri;

  public KakaoMessageClient(RestClient.Builder builder,
      @Value("${kakao.redirect-uri}") String redirectUri) {
    SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
    factory.setReadTimeout(6000);
    factory.setConnectTimeout(6000);

    this.restclient = RestClient.builder()
        .requestFactory(factory)
        .baseUrl(baseUrl)
        .build();
    this.redirectUri = redirectUri;
  }

  public void sendToMe(String accessToken, String message) {
    LOGGER.info("kakaoaccessToken={}", accessToken);
    LOGGER.info("message={}", message);
    LinkedMultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
    String templateJson = """
        {
            "object_type": "text",
                "text": "%s",
                "link": {
                    "web_url": "https://developers.kakao.com",
                    "mobile_web_url": "https://developers.kakao.com"
                },
                "button_title": "바로 확인"
        }
        """.formatted(message);
    body.add("template_object", templateJson);
    KakaoMessageResponse response = restclient.post()
        .uri("/v2/api/talk/memo/default/send")
        .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
        .body(body)
        .retrieve()
        .body(KakaoMessageResponse.class);
    if (response == null || response.getResult_code() != 0) {
      throw new KakaoApiResponseException("카카오 api 응답 오류가 발생");
    }
  }
}
