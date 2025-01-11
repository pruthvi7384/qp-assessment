package com.booking.app.utils;

import com.booking.app.dto.GroceryResponse;
import org.springframework.stereotype.Component;

@Component
public class Util {
    public GroceryResponse prepareResponse(String errorCode, String errorDescription, Object response){
        GroceryResponse groceryResponse = new GroceryResponse();
        groceryResponse.setResponse(response);
        groceryResponse.setErrorCode(errorCode);
        groceryResponse.setErrorDescription(errorDescription);

        return groceryResponse;
    }
}
