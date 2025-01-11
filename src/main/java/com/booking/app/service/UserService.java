package com.booking.app.service;

import com.booking.app.dto.OrderDto;
import com.booking.app.entity.GroceryItem;
import com.booking.app.entity.Order;
import com.booking.app.entity.OrderItem;
import com.booking.app.exceptions.ESBExcepcionMessage;
import com.booking.app.exceptions.ESBExcepcionType;
import com.booking.app.repository.GroceryItemRepository;
import com.booking.app.repository.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private GroceryItemRepository groceryItemRepository;

    @Autowired
    private OrderRepository orderRepository;

    public List<GroceryItem> viewAvailableGroceries() throws ESBExcepcionMessage {
        try {
            Optional<List<GroceryItem>> optionalGroceryItems = groceryItemRepository.getAvailableGroceryList();
            return optionalGroceryItems.orElse(new ArrayList<>());
        }catch (Exception e){
            logger.error("",e);
            throw new ESBExcepcionMessage(new ESBExcepcionType("500", "Something went wrong while retrieving the 'Grocery Details'!"));
        }
    }

    public void placeOrder(OrderDto orderDto) throws ESBExcepcionMessage {
       try {
           List<GroceryItem> groceryItemList = orderDto.getItems().stream()
                   .filter(orderItem -> orderItem.getQuantity() > 0)
                   .map(orderItem -> groceryItemRepository.findById(orderItem.getGroceryItemId())
                           .orElse(null))
                   .filter(Objects::nonNull)
                   .toList();

           if (groceryItemList.isEmpty()){
               throw new ESBExcepcionMessage(new ESBExcepcionType("200", "The basket is empty or something went wrong!"));
           }

           double totalPrice = groceryItemList.stream()
                   .mapToDouble(GroceryItem::getPrice)
                   .sum();

           List<OrderItem> orderItems = groceryItemList.stream().map(groceryItem -> {
               OrderItem orderItem = new OrderItem();
               orderItem.setGroceryItem(groceryItem);
               orderItem.setQuantity(1);
               orderItem.setPrice(groceryItem.getPrice());

               return orderItem;
           }).toList();

           Order order = new Order();
           order.setOrderItems(orderItems);
           order.setUserName(orderDto.getUserName());
           order.setTotalPrice(totalPrice);

           orderRepository.save(order);
       } catch (ESBExcepcionMessage esb){
           throw esb;
       }catch (Exception e){
           logger.error("",e);
           throw new ESBExcepcionMessage(new ESBExcepcionType("500", "Something went wrong while ordering the 'Grocery'!"));
       }
    }
}
