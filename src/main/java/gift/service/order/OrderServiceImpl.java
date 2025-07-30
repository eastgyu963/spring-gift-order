package gift.service.order;

import gift.dto.order.OrderRequestDto;
import gift.dto.order.OrderResponseDto;
import gift.entity.Member;
import gift.entity.Option;
import gift.entity.Order;
import gift.entity.Wish;
import gift.exception.notfound.OptionNotFoundException;
import gift.repository.option.OptionJpaRepository;
import gift.repository.order.OrderJpaRepository;
import gift.repository.wish.WishJpaRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
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
        new OptionNotFoundException("옵션이 존재하지 않습니다"));
    option.subtractQuantity(requestDto.getQuantity());
    Optional<Wish> optionalWish = wishRepository.findByMemberIdAndProductId(
        member.getId(), option.getProduct().getId());
    if (optionalWish.isPresent()) {
      wishRepository.deleteByMemberIdAndProductId(member.getId(), option.getProduct().getId());
    }
    Order order = new Order(requestDto.getQuantity(), requestDto.getMessage(), member, option);
    Order save = orderRepository.save(order);
    return new OrderResponseDto(save);
  }

  @Override
  public List<OrderResponseDto> findByOptionId(Long optionId) {
    return orderRepository
        .findByOptionId(optionId)
        .stream()
        .map(OrderResponseDto::new)
        .toList();
  }

  @Override
  public OrderResponseDto findById(Long orderId) {
    return orderRepository
        .findById(orderId)
        .map(OrderResponseDto::new)
        .orElseThrow(() -> new OptionNotFoundException("해당하는 id의 옵션이 존재하지 않습니다."));
  }
}
