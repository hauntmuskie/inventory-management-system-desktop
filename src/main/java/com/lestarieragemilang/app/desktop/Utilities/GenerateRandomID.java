package com.lestarieragemilang.app.desktop.Utilities;

import java.time.LocalDate;
import java.util.Random;

public class GenerateRandomID {
    public int generateRandomId() {
        Random random = new Random();
        return random.nextInt(1000);
        
    }

    public LocalDate generateTransactionId() {
        
        throw new UnsupportedOperationException("Unimplemented method 'generateTransactionId'");
    }
}
