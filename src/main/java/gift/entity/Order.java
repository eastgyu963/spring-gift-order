package gift.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
public class Order {

  @Id
  @GeneratedValue
  private Long id;

  @Column(nullable = false)
  private int quantity;

  @Column(nullable = false)
  private LocalDateTime orderDateTime;

  @Column(nullable = false)
  private String message;

  @ManyToOne
  @JoinColumn(name = "member_id")
  private Member member;


  @ManyToOne
  @JoinColumn(name = "option_id")
  private Option option;

  public Order() {
  }

  public Order(Long id, int quantity, String message, Member member,
      Option option) {
    this.id = id;
    this.quantity = quantity;
    this.message = message;
    this.member = member;
    this.option = option;
    this.orderDateTime = LocalDateTime.now();
  }

  public Order(int quantity, String message, Member member,
      Option option) {
    this(null, quantity, message, member, option);
  }

  public Long getId() {
    return id;
  }

  public int getQuantity() {
    return quantity;
  }

  public LocalDateTime getOrderDateTime() {
    return orderDateTime;
  }

  public String getMessage() {
    return message;
  }

  public Member getMember() {
    return member;
  }

  public Option getOption() {
    return option;
  }
}
