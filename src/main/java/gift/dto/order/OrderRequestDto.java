package gift.dto.order;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class OrderRequestDto {

  @NotNull(message = "옵션 id는 필수입니다.")
  private Long optionId;

  @NotNull(message = "옵션 개수는 필수입니다")
  @Min(1)
  private int quantity;

  @NotBlank(message = "메시지는 필수입니다.")
  private String message;

  public OrderRequestDto() {
  }

  public OrderRequestDto(Long optionId, int quantity, String message) {
    this.optionId = optionId;
    this.quantity = quantity;
    this.message = message;
  }

  public Long getOptionId() {
    return optionId;
  }

  public int getQuantity() {
    return quantity;
  }

  public String getMessage() {
    return message;
  }
}
