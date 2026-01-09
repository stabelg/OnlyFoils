package br.com.marketplace.onlyfoils.dto;

import br.com.marketplace.onlyfoils.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {

    private Long id;
    private String username;
    private String email;
    private BigDecimal reputation;
    private boolean store;
    private List<AddressResponse> addresses;

    public static UserResponse from(User user) {
        return new UserResponse(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getReputation(),
                user.isStore(),
                user.getAddresses()
                        .stream()
                        .map(AddressResponse::from)
                        .toList()
        );
    }
}

