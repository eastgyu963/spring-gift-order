package gift.repository.order;

import gift.entity.Order;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderJpaRepository extends JpaRepository<Order, Long> {

  List<Order> findByOptionId(Long optionId);
}
