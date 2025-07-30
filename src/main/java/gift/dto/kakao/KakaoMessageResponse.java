package gift.dto.kakao;

public class KakaoMessageResponse {

  private Integer result_code;

  public KakaoMessageResponse(Integer result_code) {
    this.result_code = result_code;
  }

  public KakaoMessageResponse() {
  }

  public Integer getResult_code() {
    return result_code;
  }
}
