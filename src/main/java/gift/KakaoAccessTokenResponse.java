package gift;

import com.fasterxml.jackson.annotation.JsonProperty;

public class KakaoAccessTokenResponse {

  @JsonProperty("token_type")
  private String tokenType;

  @JsonProperty("access_token")
  private String accessToken;

  @JsonProperty("id_token")
  private String idToken;

  @JsonProperty("expires_in")
  private Integer expiresIn;

  @JsonProperty("refresh_token")
  private String refreshToken;

  @JsonProperty("refresh_token_expires_in")
  private Integer refreshTokenExpiresIn;

  private String scope;

  public KakaoAccessTokenResponse() {
  }

  public String getTokenType() {
    return tokenType;
  }

  public String getAccessToken() {
    return accessToken;
  }

  public String getIdToken() {
    return idToken;
  }

  public Integer getExpiresIn() {
    return expiresIn;
  }

  public String getRefreshToken() {
    return refreshToken;
  }

  public Integer getRefreshTokenExpiresIn() {
    return refreshTokenExpiresIn;
  }

  public String getScope() {
    return scope;
  }
}
