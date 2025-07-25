package gift.auth;


import gift.dto.KakaoAccessTokenResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class KakaoOAuthClient {

    private final String baseUrl = "https://kauth.kakao.com";

    private final RestClient restclient;
    private final String restApiKey;
    private final String redirectUri;

    public KakaoOAuthClient(RestClient.Builder builder,
                            @Value("${kakao.rest-api-key}") String restApiKey,
                            @Value(("${kakao.redirect-uri}")) String redirectUri) {
        this.restclient = RestClient.builder()
                .baseUrl(baseUrl)
                .build();
        this.restApiKey = restApiKey;
        this.redirectUri = redirectUri;
    }

    public String getRedirectUrl() {
        String url = "/oauth/authorize";
        return UriComponentsBuilder
                .fromUriString("https://kauth.kakao.com/oauth/authorize")
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
        KakaoAccessTokenResponse response = restclient.post()
                .uri(url)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .body(body)
                .retrieve()
                .body(KakaoAccessTokenResponse.class);
        return response.getAccessToken();
    }
}
