package com.booking.app.service;

import com.booking.app.dto.GroceryItemDto;
import com.booking.app.dto.GroceryResponse;
import com.booking.app.entity.GroceryItem;
import com.booking.app.exceptions.ESBExcepcionMessage;
import com.booking.app.exceptions.ESBExcepcionType;
import com.booking.app.repository.GroceryItemRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdminService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private GroceryItemRepository groceryItemRepository;

    /**
     * Add grocery process
     * @param groceryItemDto
     * @return
     * @throws ESBExcepcionMessage
     */
    public GroceryItem addGroceryItem(GroceryItemDto groceryItemDto) throws ESBExcepcionMessage {
          try {
              Optional<GroceryItem> getGroceryItem = groceryItemRepository.findByName(groceryItemDto.getName());

              if (getGroceryItem.isPresent()){
                  throw new ESBExcepcionMessage(new ESBExcepcionType("400", "Grocery details have already been added!"));
              }

              GroceryItem groceryItem = new GroceryItem();
              groceryItem.setName(groceryItemDto.getName());
              groceryItem.setPrice(groceryItemDto.getPrice());
              groceryItem.setQuantity(groceryItemDto.getQuantity());

              return groceryItemRepository.save(groceryItem);
          } catch (ESBExcepcionMessage esb){
              throw esb;
          }catch (Exception e){
              logger.error("",e);
              throw new ESBExcepcionMessage(new ESBExcepcionType("500", "Something went wrong while adding 'Grocery Details'!"));
          }
    }

    /**
     * Gat all grocery items process
     * @return
     * @throws ESBExcepcionMessage
     */
    public List<GroceryItem> getAllGroceryItems() throws ESBExcepcionMessage {
        try {
            return groceryItemRepository.findAll();
        }catch (Exception e){
            logger.error("",e);
            throw new ESBExcepcionMessage(new ESBExcepcionType("500", "Something went wrong while get 'Grocery Details'!"));
        }
    }

    /**
     * Update grocery details process
     * @param id
     * @param groceryItemDto
     * @return
     * @throws ESBExcepcionMessage
     */
    public GroceryItem updateGroceryItem(Long id, GroceryItemDto groceryItemDto) throws ESBExcepcionMessage {
       try {
           Optional<GroceryItem> groceryItemOptional = groceryItemRepository.findById(id);
           if (groceryItemOptional.isEmpty()){
               throw new ESBExcepcionMessage(new ESBExcepcionType("404", "Requested grocery details not found!"));
           }
           GroceryItem groceryItem = groceryItemOptional.get();
           groceryItem.setName(groceryItemDto.getName());
           groceryItem.setPrice(groceryItemDto.getPrice());
           groceryItem.setQuantity(groceryItemDto.getQuantity());
           return groceryItemRepository.save(groceryItem);
       } catch (ESBExcepcionMessage esb){
           throw esb;
       } catch (Exception e){
            logger.error("",e);
            throw new ESBExcepcionMessage(new ESBExcepcionType("500", "Something went wrong while updating 'Grocery Details'!"));
       }
    }

    /**
     * Remove grocery detail from system process
     * @param id
     * @throws ESBExcepcionMessage
     */
    public void deleteGroceryItem(Long id) throws ESBExcepcionMessage {
        try {
            Optional<GroceryItem> groceryItemOptional = groceryItemRepository.findById(id);
            if (groceryItemOptional.isEmpty()){
                throw new ESBExcepcionMessage(new ESBExcepcionType("404", "Requested grocery details not found!"));
            }
            groceryItemRepository.deleteById(id);
        } catch (ESBExcepcionMessage esb){
            throw esb;
        }catch (Exception e){
            logger.error("",e);
            throw new ESBExcepcionMessage(new ESBExcepcionType("500", "Something went wrong while removing 'Grocery Details'!"));
        }
    }
}
