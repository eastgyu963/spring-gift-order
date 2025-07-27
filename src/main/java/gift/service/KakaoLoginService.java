package gift.service;

import gift.auth.KakaoOAuthClient;
import gift.config.JwtProvider;
import gift.dto.KakaoUserProperty;
import gift.entity.Member;
import gift.entity.Token;
import gift.repository.member.MemberJpaRepository;
import jakarta.transaction.Transactional;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class KakaoLoginService {

  private final KakaoOAuthClient kakaoOAuthClient;
  private final MemberJpaRepository repository;
  private final JwtProvider jwtProvider;

  public KakaoLoginService(KakaoOAuthClient kakaoOAuthClient, MemberJpaRepository repository,
      JwtProvider jwtProvider) {
    this.kakaoOAuthClient = kakaoOAuthClient;
    this.repository = repository;
    this.jwtProvider = jwtProvider;
  }

  public String getRedirectUrl() {
    return kakaoOAuthClient.getRedirectUrl();
  }

  @Transactional
  public Token loginWithAuthorizationCode(String authorizationCode) {
    KakaoUserProperty userProperty = kakaoOAuthClient.getKakaoUserProperty(
        kakaoOAuthClient.getToken(authorizationCode));
    String email = "kakaouser" + userProperty.getId() + "@kakao.com";
    Optional<Member> memberOptional = repository.findByEmail(email);
    if (memberOptional.isEmpty()) {
      //회원가입
      Member member = repository.save(new Member(email, null));
      //소셜로그인 시 비밀번호는 null로 설정
      return jwtProvider.generateToken(member);
    } else {
      //로그인
      return jwtProvider.generateToken(memberOptional.get());
    }
  }
}
