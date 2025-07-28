package gift.dto.kakao;

public class KakaoUserProperty {

  private Long id;

  public KakaoUserProperty(Long id) {
    this.id = id;
  }

  public KakaoUserProperty() {
  }

  public Long getId() {
    return id;
  }

  public String makeKakaoEmail() {
    return "kakaouser" + this.id + "@kakao.com";
  }
}
