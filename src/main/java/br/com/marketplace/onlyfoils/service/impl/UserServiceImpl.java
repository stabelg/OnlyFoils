package br.com.marketplace.onlyfoils.service.impl;

import br.com.marketplace.onlyfoils.dto.CreateAddressRequest;
import br.com.marketplace.onlyfoils.dto.CreateUserRequest;
import br.com.marketplace.onlyfoils.dto.UserResponse;
import br.com.marketplace.onlyfoils.model.Address;
import br.com.marketplace.onlyfoils.model.User;
import br.com.marketplace.onlyfoils.repository.UserRepository;
import br.com.marketplace.onlyfoils.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Transactional
    public UserResponse create(CreateUserRequest request) {

        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setCpf(request.getCpf());
        user.setStore(request.isStore());
        user.setReputation(BigDecimal.ZERO);

        boolean hasMain = false;

        for (CreateAddressRequest addr : request.getAddresses()) {
            Address address = new Address();
            address.setUser(user);
            address.setCep(addr.getCep());
            address.setStreet(addr.getStreet());
            address.setNumber(addr.getNumber());
            address.setComplement(addr.getComplement());
            address.setDistrict(addr.getDistrict());
            address.setCity(addr.getCity());
            address.setState(addr.getState());

            if (addr.isMainAddress()) {
                if (hasMain) {
                    throw new IllegalArgumentException("Apenas um endereço principal é permitido");
                }
                hasMain = true;
            }

            address.setMainAddress(addr.isMainAddress());
            user.getAddresses().add(address);
        }

        if (!hasMain) {
            throw new IllegalArgumentException("É obrigatório informar um endereço principal");
        }

        User saved = userRepository.save(user);
        return UserResponse.from(saved);
    }

    @Override
    @Transactional
    public User getEntityById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("User not found: " + id)
                );
    }
}