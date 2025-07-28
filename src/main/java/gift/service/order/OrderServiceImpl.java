package gift.service.order;

import gift.dto.order.OrderRequestDto;
import gift.dto.order.OrderResponseDto;
import gift.entity.Member;
import gift.entity.Option;
import gift.entity.Order;
import gift.repository.option.OptionJpaRepository;
import gift.repository.order.OrderJpaRepository;
import gift.repository.wish.WishJpaRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {

  private OptionJpaRepository optionRepository;
  private OrderJpaRepository orderRepository;
  private WishJpaRepository wishRepository;

  public OrderServiceImpl(OptionJpaRepository optionRepository,
      OrderJpaRepository orderRepository, WishJpaRepository wishRepository) {
    this.optionRepository = optionRepository;
    this.orderRepository = orderRepository;
    this.wishRepository = wishRepository;
  }

  @Transactional
  @Override
  public OrderResponseDto order(Member member, OrderRequestDto requestDto) {
    Option option = optionRepository.findById(requestDto.getOptionId()).orElseThrow(() ->
        new IllegalStateException("옵션이 존재하지 않습니다"));
    option.subtractQuantity(requestDto.getQuantity());
    wishRepository.findByMemberIdAndProductId(member.getId(), option.getProduct().getId());
    Order order = new Order(requestDto.getQuantity(), requestDto.getMessage(), member, option);
    Order save = orderRepository.save(order);
    return new OrderResponseDto(save);
  }
}
