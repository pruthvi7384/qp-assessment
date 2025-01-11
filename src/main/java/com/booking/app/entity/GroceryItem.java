package com.booking.app.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "grocery_item")
@NamedQueries(
        {
                @NamedQuery(
                        name = "GroceryItem.findByName",
                        query = "SELECT g FROM GroceryItem g WHERE g.name = :name"
                ),
                @NamedQuery(
                        name = "GroceryItem.getGroceryList",
                        query = "SELECT g FROM GroceryItem g WHERE g.quantity IS NOT NULL AND g.quantity != 0"
                )
        }
)

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GroceryItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private double price;
    private int quantity;
}
