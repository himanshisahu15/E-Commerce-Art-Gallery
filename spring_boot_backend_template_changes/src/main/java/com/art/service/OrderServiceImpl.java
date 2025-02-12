package com.art.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.art.dao.OrderEntityDao;
import com.art.dao.PaymentDao;
import com.art.dao.ShippingDao;
import com.art.dao.UserDao;
import com.art.dto.OrderDto;
import com.art.exception.ResourceNotFoundException;
import com.art.pojos.OrderEntity;
import com.art.pojos.Payment;
import com.art.pojos.Shipping;
import com.art.pojos.User;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderEntityDao orderRepository;
    
    @Autowired
    private UserDao userRepository; 
    
    @Autowired
    private ShippingDao shippingRepository; // Inject ShippingDao to fetch shipping details

    @Autowired
    private PaymentDao paymentRepository; 
    
    @Autowired
    private ModelMapper mapper;

    @Override
    public List<OrderDto> getAllOrders() {
    	return orderRepository.findAll().stream()
                .map(order -> {
                    OrderDto orderDto = mapper.map(order, OrderDto.class);
                    // Set the customer ID in the DTO
                    orderDto.setCustId(order.getCustomer().getUserId());
                    // Set the shipping ID in the DTO
                    orderDto.setShippingId(order.getShipping().getId());
                    // Set the payment ID in the DTO
                    orderDto.setPaymentId(order.getPayment().getId());
                    return orderDto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public String addOrder(OrderDto orderDTO) {
    	 User user = userRepository.findById(orderDTO.getCustId())
    	            .orElseThrow(() -> new ResourceNotFoundException("User  not found"));

    	    // Fetch the shipping and payment from the database
    	    Shipping shipping = shippingRepository.findById(orderDTO.getShippingId())
    	            .orElseThrow(() -> new ResourceNotFoundException("Shipping not found"));
    	    Payment payment = paymentRepository.findById(orderDTO.getPaymentId())
    	            .orElseThrow(() -> new ResourceNotFoundException("Payment not found"));

    	    // Map the DTO to the entity
    	    OrderEntity order = mapper.map(orderDTO, OrderEntity.class);
    	    
    	    // Set the fetched user (customer), shipping, and payment
    	    order.setCustomer(user);
    	    order.setShipping(shipping);
    	    order.setPayment(payment);

    	    // Save the order
    	    OrderEntity savedOrder = orderRepository.save(order);
    	    return "New Order added with id " + savedOrder.getOrderId();
     }
    

    @Override
    public String deleteOrder(Long orderId) {
        if (orderRepository.existsById(orderId)) {
            orderRepository.deleteById(orderId);
            return "Order deleted";
        }
        throw new ResourceNotFoundException("Invalid Order ID!");
    }

    @Override
    public OrderDto getOrderDetails(Long orderId) {
    	OrderEntity order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Invalid Order ID"));
        OrderDto orderDto = mapper.map(order, OrderDto.class);
        // Set the customer ID in the DTO
        orderDto.setCustId(order.getCustomer().getUserId());
        // Set the shipping ID in the DTO
        orderDto.setShippingId(order.getShipping().getId());
        // Set the payment ID in the DTO
        orderDto.setPaymentId(order.getPayment().getId());
        return orderDto;
    }

    @Override
    public String updateOrder(Long orderId, OrderDto orderDTO) {
    	 if (orderRepository.existsById(orderId)) {
             // Fetch the user (customer) from the database
             User user = userRepository.findById(orderDTO.getCustId())
                     .orElseThrow(() -> new ResourceNotFoundException("User  not found"));

             // Fetch the shipping and payment from the database
             Shipping shipping = shippingRepository.findById(orderDTO.getShippingId())
                     .orElseThrow(() -> new ResourceNotFoundException("Shipping not found"));
             Payment payment = paymentRepository.findById(orderDTO.getPaymentId())
                     .orElseThrow(() -> new ResourceNotFoundException("Payment not found"));

             // Map the DTO to the entity
             OrderEntity order = mapper.map(orderDTO, OrderEntity.class);
             order.setOrderId(orderId); // Set the ID for the update
             order.setCustomer(user); // Set the fetched user (customer)
             order.setShipping(shipping); // Set the fetched shipping
             order.setPayment(payment); // Set the fetched payment

             orderRepository.save(order);
             return "Update success";
         }
         throw new ResourceNotFoundException("Order doesn't exist!");
    }
}