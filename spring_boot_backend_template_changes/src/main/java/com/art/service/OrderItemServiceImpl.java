package com.art.service;

import org.modelmapper.ModelMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.art.dao.ArtworkDao;
import com.art.dao.OrderEntityDao;
import com.art.dao.OrderItemDao;
import com.art.dto.OrderItemDto;
import com.art.exception.ResourceNotFoundException;
import com.art.pojos.Artwork;
import com.art.pojos.OrderEntity;
import com.art.pojos.OrderItem;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class OrderItemServiceImpl implements OrderItemService {

    @Autowired
    private OrderItemDao orderItemRepository;

    @Autowired
    private OrderEntityDao orderRepository; // Inject OrderDao to fetch orders

    @Autowired
    private ArtworkDao artworkRepository;
    
    
    @Autowired
    private ModelMapper mapper;

    @Override
    public List<OrderItemDto> getAllOrderItems() {
    	 return orderItemRepository.findAll().stream()
                 .map(orderItem -> {
                     OrderItemDto orderItemDto = mapper.map(orderItem, OrderItemDto.class);
                     // Set the orderId and artworkId in the DTO
                     orderItemDto.setOrderId(orderItem.getOrder().getOrderId());
                     orderItemDto.setArtworkId(orderItem.getArtwork().getArtId());
                     return orderItemDto;
                 })
                 .collect(Collectors.toList());
    }

    @Override
    public String addOrderItem(OrderItemDto orderItemDTO) {
    	System.out.println(orderItemDTO.getOrderItemId());
    	
    
    	OrderEntity order = orderRepository.findById(orderItemDTO.getOrderId())
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));
        Artwork artwork = artworkRepository.findById(orderItemDTO.getArtworkId())
                .orElseThrow(() -> new ResourceNotFoundException("Artwork not found"));

        // Map the DTO to the entity
        OrderItem orderItem = mapper.map(orderItemDTO, OrderItem.class);
        // Set the fetched order and artwork
        orderItem.setOrder(order);
        orderItem.setArtwork(artwork);

        // Save the order item
        OrderItem savedOrderItem = orderItemRepository.save(orderItem);
        return "New Order Item added with id " + savedOrderItem.getOrderItemId();
    }

    @Override
    public String deleteOrderItem(Long orderItemId) {
        if (orderItemRepository.existsById(orderItemId)) {
            orderItemRepository.deleteById(orderItemId);
            return "Order Item deleted";
        }
        throw new ResourceNotFoundException("Invalid Order Item ID!");
    }

    @Override
    public OrderItemDto getOrderItemDetails(Long orderItemId) {
    	 OrderItem orderItem = orderItemRepository.findById(orderItemId)
                 .orElseThrow(() -> new ResourceNotFoundException("Invalid Order Item ID"));
         OrderItemDto orderItemDto = mapper.map(orderItem, OrderItemDto.class);
         // Set the orderId and artworkId in the DTO
         orderItemDto.setOrderId(orderItem.getOrder().getOrderId());
         orderItemDto.setArtworkId(orderItem.getArtwork().getArtId());
         return orderItemDto;
    }

    @Override
    public String updateOrderItem(Long orderItemId, OrderItemDto orderItemDTO) {
    	 if (orderItemRepository.existsById(orderItemId)) {
             // Fetch the order and artwork from the database
             OrderEntity order = orderRepository.findById(orderItemDTO.getOrderId())
                     .orElseThrow(() -> new ResourceNotFoundException("Order not found"));
             Artwork artwork = artworkRepository.findById(orderItemDTO.getArtworkId())
                     .orElseThrow(() -> new ResourceNotFoundException("Artwork not found"));

             // Map the DTO to the entity
             OrderItem orderItem = mapper.map(orderItemDTO, OrderItem.class);
             orderItem.setOrder(order);
             orderItem.setArtwork(artwork);
             orderItem.setOrderItemId(orderItemId); // Set the ID for the update

             orderItemRepository.save(orderItem);
             return "Update success";
         }
         throw new ResourceNotFoundException("Order Item doesn't exist!");
    }
}