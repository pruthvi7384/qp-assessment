package com.booking.app.repository;

import com.booking.app.entity.GroceryItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GroceryItemRepository extends JpaRepository<GroceryItem, Long> {
    @Query(name = "GroceryItem.findByName")
    Optional<GroceryItem> findByName(String name);

    @Query(name = "GroceryItem.getGroceryList")
    Optional<List<GroceryItem>> getAvailableGroceryList();
}
