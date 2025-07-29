package gift.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Member {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, unique = true)
  private String email;

  @Column(nullable = true)
  private String password;

  private String kakaoAccessToken;

  public Member(Long id, String email, String password, String accessToken) {
    this.id = id;
    this.email = email;
    this.password = password;
    this.kakaoAccessToken = accessToken;
  }

  public Member(String email, String password, String accessToken) {
    this(null, email, password, accessToken);
  }

  public Member(String email, String password) {
    this(null, email, password, null);

    this.password = password;
  }

  public Member() {
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getEmail() {
    return email;
  }

  public String getPassword() {
    return password;
  }

  public String getKakaoAccessToken() {
    return kakaoAccessToken;
  }

  public void setKakaoAccessToken(String kakaoAccessToken) {
    this.kakaoAccessToken = kakaoAccessToken;
  }

  public boolean isPasswordMatch(String password) {
    return this.password.equals(password);
  }

  public boolean isPasswordNotMatch(String password) {
    return !this.password.equals(password);
  }

  public void update(String email, String password) {
    this.email = email;
    this.password = password;
  }
}
