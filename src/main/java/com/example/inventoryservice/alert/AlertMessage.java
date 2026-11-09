package com.example.inventoryservice.alert;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AlertMessage {
    private String type;
    private String message;
}
