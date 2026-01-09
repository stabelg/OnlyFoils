package br.com.marketplace.onlyfoils.dto;

import br.com.marketplace.onlyfoils.model.Address;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressResponse {

    private Long id;
    private String cep;
    private String street;
    private String number;
    private String complement;
    private String district;
    private String city;
    private String state;
    private boolean mainAddress;

    public static AddressResponse from(Address address) {
        return new AddressResponse(
                address.getId(),
                address.getCep(),
                address.getStreet(),
                address.getNumber(),
                address.getComplement(),
                address.getDistrict(),
                address.getCity(),
                address.getState(),
                address.isMainAddress()
        );
    }
}

