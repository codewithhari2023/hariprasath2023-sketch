package OnlineEBookStore.demo.Dto;

import OnlineEBookStore.demo.Model.Order;
import OnlineEBookStore.demo.Response.OrderResponse;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class OrderDto {


    public List<OrderResponse> mapToOrderResponse(List<Order> orderList) {
        List<OrderResponse> orderResponseList = new ArrayList<>();

        for (Order order : orderList) {
            OrderResponse orderResponse = new OrderResponse();
            orderResponse.setId(order.getId());
            orderResponse.setUserId(order.getCommonUser().getId());
//            orderResponse.se(order.getCommonUser().getEmail());
            orderResponse.setUsername(order.getCommonUser().getUsername());
            orderResponse.setOrderStatus(order.getStatus().getStatus());
            orderResponse.setDeliveryaddress(order.getDeliveryAddress());
            orderResponse.setBookList(order.getOrderedBooks());
            orderResponseList.add(orderResponse);
        }

        return orderResponseList;
    }
}
