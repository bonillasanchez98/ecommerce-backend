package com.alumnione.ecommerce.service;

import com.alumnione.ecommerce.dto.OrderCreationDto;
import com.alumnione.ecommerce.entity.Order;
import com.alumnione.ecommerce.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderServiceImpl implements OrderService{

    private final OrderRepository orderRepository;

    @Override
    public ResponseEntity<OrderCreationDto> createOrder(Order order) {
        if(order != null){
            OrderCreationDto orderCreationDto = convertEntityToDto(order);
            orderRepository.save(order);
            return new ResponseEntity<>(orderCreationDto, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }

    @Override
    public ResponseEntity<List<Order>> getAllOrder() {

        if(orderRepository.findAll().isEmpty()){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }else{
            return new ResponseEntity<>(orderRepository.findAll(), HttpStatus.OK);
        }
    }

    @Override
    public ResponseEntity<OrderCreationDto> findOrderById(Long id) {
        if(orderRepository.existsById(id)){
            Order order = orderRepository.getReferenceById(id);
            return new ResponseEntity<>(new OrderCreationDto(
                    order.getId(),
                    order.getOrderStatus(),
                    order.getOrderCreatedAt(),
                    order.getInvoice()
            ), HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @Override
    public ResponseEntity<String> updateOrder(Long id, OrderCreationDto orderCreationDto) {
        return null;
    }

    @Override
    public ResponseEntity<?> deleteOrder(Long id) {
        return null;
    }

    public static OrderCreationDto convertEntityToDto(Order order){
        return new OrderCreationDto(
                order.getId(),
                order.getOrderStatus(),
                order.getOrderCreatedAt(),
                order.getInvoice()
        );
    }

    public static Order convertDtoToEntity(OrderCreationDto orderCreationDto){
            Order order = new Order();
            order.setOrderStatus(orderCreationDto.getOrderStatus());
            order.setOrderCreatedAt(orderCreationDto.getOrderCreatedAt());
            order.setInvoice(orderCreationDto.getInvoice());
            return order;
    }

}
