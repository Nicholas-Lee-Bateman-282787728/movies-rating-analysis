package io.anhkhue.akphim.models.custom;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VendorWrapper {

    private String username;
    private String password;
    private String name;
    private String image;
    private String address1;
    private String address2;
    private String tel;
    private String email;
}
