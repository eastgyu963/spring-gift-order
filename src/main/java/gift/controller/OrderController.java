package gift.controller;

import gift.config.LoginMember;
import gift.dto.order.OrderRequestDto;
import gift.dto.order.OrderResponseDto;
import gift.entity.Member;
import gift.service.order.OrderServiceImpl;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

  private OrderServiceImpl service;

  public OrderController(OrderServiceImpl service) {
    this.service = service;
  }

  @PostMapping
  public ResponseEntity<OrderResponseDto> order(@LoginMember Member member,
      @Valid @RequestBody OrderRequestDto requestDto) {
    OrderResponseDto responseDto = service.order(member, requestDto);
    return new ResponseEntity<>(responseDto, HttpStatus.OK);
  }
}
