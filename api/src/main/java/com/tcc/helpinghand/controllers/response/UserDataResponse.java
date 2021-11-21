package com.tcc.helpinghand.controllers.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDataResponse {

    private String name;
    private String email;
    private boolean isDeaf;
}
