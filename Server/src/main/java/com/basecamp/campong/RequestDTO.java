package com.basecamp.campong;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class RequestDTO {

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    static public class UserBody {
        String email;
        String password;
        String usernick;
        String phone;
        String username;
    }


}
