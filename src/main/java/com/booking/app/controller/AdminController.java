package com.booking.app.controller;

import com.booking.app.dto.GroceryItemDto;
import com.booking.app.dto.GroceryResponse;
import com.booking.app.entity.GroceryItem;
import com.booking.app.exceptions.ESBExcepcionMessage;
import com.booking.app.service.AdminService;
import com.booking.app.utils.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private Util util;

    /**
     * Add new 'groceryItem'
     */
    @PostMapping("/grocery")
    public ResponseEntity<GroceryResponse> addGroceryItem(@RequestBody GroceryItemDto groceryItemDto) {
       try {
           GroceryItem groceryItem = adminService.addGroceryItem(groceryItemDto);
           GroceryResponse groceryResponse = util.prepareResponse("200", "success", groceryItem);
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
     * View 'existing grocery items'
     */
    @GetMapping("/groceries")
    public ResponseEntity<GroceryResponse> getAllGroceryItems() {
        try {
            List<GroceryItem> groceryItemsList = adminService.getAllGroceryItems();
            GroceryResponse groceryResponse = util.prepareResponse("200", "success", groceryItemsList);

            return ResponseEntity.ok(groceryResponse);
        } catch (ESBExcepcionMessage esb) {
            String errorCode = esb.getFaultInfo().getErrorCode();
            GroceryResponse groceryResponse = util.prepareResponse(errorCode, esb.getFaultInfo().getErrorDescription(), null);
            if (errorCode.equalsIgnoreCase("400")) {
                return ResponseEntity.badRequest().body(groceryResponse);
            } else {
                return ResponseEntity.internalServerError().body(groceryResponse);
            }
        }
    }

    /**
     * Update 'existing grocery items'
     */
    @PutMapping("/grocery/{id}")
    public ResponseEntity<GroceryResponse> updateGroceryItem(@PathVariable Long id, @RequestBody GroceryItemDto groceryItemDto) {
        try {
            GroceryItem groceryItem = adminService.updateGroceryItem(id, groceryItemDto);
            GroceryResponse groceryResponse = util.prepareResponse("200", "success", groceryItem);

            return ResponseEntity.ok(groceryResponse);
        } catch (ESBExcepcionMessage esb) {
            String errorCode = esb.getFaultInfo().getErrorCode();
            GroceryResponse groceryResponse = util.prepareResponse(errorCode, esb.getFaultInfo().getErrorDescription(), null);
            if (errorCode.equalsIgnoreCase("400")) {
                return ResponseEntity.badRequest().body(groceryResponse);
            } else {
                return ResponseEntity.internalServerError().body(groceryResponse);
            }
        }
    }

    /**
     * Remove 'grocery items from database'
     */
    @DeleteMapping("/grocery/{id}")
    public ResponseEntity<GroceryResponse> deleteGroceryItem(@PathVariable Long id) {
        try {
            adminService.deleteGroceryItem(id);
            GroceryResponse groceryResponse = util.prepareResponse("200", "Requested grocery details have been removed!", null);
            return ResponseEntity.ok(groceryResponse);
        } catch (ESBExcepcionMessage esb) {
            String errorCode = esb.getFaultInfo().getErrorCode();
            GroceryResponse groceryResponse = util.prepareResponse(errorCode, esb.getFaultInfo().getErrorDescription(), null);
            if (errorCode.equalsIgnoreCase("400")) {
                return ResponseEntity.badRequest().body(groceryResponse);
            } else {
                return ResponseEntity.internalServerError().body(groceryResponse);
            }
        }
    }

}
