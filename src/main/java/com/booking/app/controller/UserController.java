package com.booking.app.controller;

import com.booking.app.dto.GroceryResponse;
import com.booking.app.dto.OrderDto;
import com.booking.app.entity.GroceryItem;
import com.booking.app.exceptions.ESBExcepcionMessage;
import com.booking.app.service.UserService;
import com.booking.app.utils.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private Util util;

    /**
     * Get 'available grocery items'
     */
    @GetMapping("/groceries")
    public ResponseEntity<GroceryResponse> viewAvailableGroceries() {
        try {
            List<GroceryItem> groceryItemsList = userService.viewAvailableGroceries();
            GroceryResponse groceryResponse = util.prepareResponse("200", "success", groceryItemsList);
            return ResponseEntity.ok(groceryResponse);
        }catch (ESBExcepcionMessage esb){
            String errorCode = esb.getFaultInfo().getErrorCode();
            GroceryResponse groceryResponse = util.prepareResponse(errorCode, esb.getFaultInfo().getErrorDescription(), null);
            if (errorCode.equalsIgnoreCase("400")){
                return ResponseEntity.badRequest().body(groceryResponse);
            }else {
                return ResponseEntity.internalServerError().body(groceryResponse);
            }
        }
    }

    /**
     * Book 'Multiple groceries item'
     */
    @PostMapping("/order")
    public ResponseEntity<GroceryResponse> placeOrder(@RequestBody OrderDto orderDto) {
        try {
            userService.placeOrder(orderDto);
            GroceryResponse groceryResponse = util.prepareResponse("200", "success", "Order placed successfully!");
            return ResponseEntity.ok(groceryResponse);
        }catch (ESBExcepcionMessage esb){
            String errorCode = esb.getFaultInfo().getErrorCode();
            GroceryResponse groceryResponse = util.prepareResponse(errorCode, esb.getFaultInfo().getErrorDescription(), null);
            if (errorCode.equalsIgnoreCase("400")){
                return ResponseEntity.badRequest().body(groceryResponse);
            } else if (errorCode.equalsIgnoreCase("200")) {
                return ResponseEntity.ok(groceryResponse);
            }else {
                return ResponseEntity.internalServerError().body(groceryResponse);
            }
        }
    }

}
