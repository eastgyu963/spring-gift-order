package gift.exception;

public class KakaoLoginTimeoutException extends RuntimeException {
    public KakaoLoginTimeoutException(String message) {
        super(message);
    }
}
