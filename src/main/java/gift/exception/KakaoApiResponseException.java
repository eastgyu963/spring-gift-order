package gift.exception;

public class KakaoApiResponseException extends RuntimeException {
    public KakaoApiResponseException(String message) {
        super(message);
    }
}
