package gift.service.order;

import gift.dto.order.OrderRequestDto;
import gift.dto.order.OrderResponseDto;
import gift.entity.Member;
import java.util.List;

public interface OrderService {

  public OrderResponseDto order(Member member, OrderRequestDto requestDto);

  public List<OrderResponseDto> findByOptionId(Long optionId);

  public OrderResponseDto findById(Long orderId);
}
