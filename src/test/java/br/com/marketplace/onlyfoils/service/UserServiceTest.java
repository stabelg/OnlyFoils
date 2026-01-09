package br.com.marketplace.onlyfoils.service;

import br.com.marketplace.onlyfoils.dto.CreateAddressRequest;
import br.com.marketplace.onlyfoils.dto.CreateUserRequest;
import br.com.marketplace.onlyfoils.dto.UserResponse;
import br.com.marketplace.onlyfoils.model.User;
import br.com.marketplace.onlyfoils.repository.UserRepository;
import br.com.marketplace.onlyfoils.service.impl.UserServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    private CreateAddressRequest mainAddress;

    @BeforeEach
    void setup() {
        mainAddress = new CreateAddressRequest(
                "01001000",
                "Praça da Sé",
                "100",
                "Apto 12",
                "Sé",
                "São Paulo",
                "SP",
                true
        );
    }

    @Test
    void shouldCreateUserWithMainAddress() {
        CreateUserRequest request = new CreateUserRequest(
                "alice",
                "alice@test.com",
                "12345678901",
                false,
                List.of(mainAddress)
        );

        when(userRepository.save(any(User.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        UserResponse response = userService.create(request);

        assertThat(response).isNotNull();
        assertThat(response.getUsername()).isEqualTo("alice");
        assertThat(response.getEmail()).isEqualTo("alice@test.com");
        assertThat(response.isStore()).isFalse();
        assertThat(response.getReputation()).isEqualByComparingTo(BigDecimal.ZERO);

        verify(userRepository).save(any(User.class));
    }

    @Test
    void shouldThrowExceptionWhenNoMainAddress() {
        CreateAddressRequest address = new CreateAddressRequest(
                "01001000",
                "Praça da Sé",
                "100",
                null,
                "Sé",
                "São Paulo",
                "SP",
                false
        );

        CreateUserRequest request = new CreateUserRequest(
                "alice",
                "alice@test.com",
                "12345678901",
                false,
                List.of(address)
        );

        assertThatThrownBy(() -> userService.create(request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("É obrigatório informar um endereço principal");

        verify(userRepository, never()).save(any());
    }

    @Test
    void shouldThrowExceptionWhenMoreThanOneMainAddress() {
        CreateAddressRequest addr1 = mainAddress;

        CreateAddressRequest addr2 = new CreateAddressRequest(
                "20040002",
                "Rua da Quitanda",
                "50",
                null,
                "Centro",
                "Rio de Janeiro",
                "RJ",
                true
        );

        CreateUserRequest request = new CreateUserRequest(
                "alice",
                "alice@test.com",
                "12345678901",
                false,
                List.of(addr1, addr2)
        );

        assertThatThrownBy(() -> userService.create(request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Apenas um endereço principal é permitido");

        verify(userRepository, never()).save(any());
    }

    @Test
    void shouldReturnUserEntityWhenExists() {
        User user = new User();
        user.setId(1L);
        user.setUsername("alice");

        when(userRepository.findById(1L))
                .thenReturn(Optional.of(user));

        User result = userService.getEntityById(1L);

        assertThat(result).isNotNull();
        assertThat(result.getUsername()).isEqualTo("alice");
    }

    @Test
    void shouldThrowExceptionWhenUserNotFound() {
        when(userRepository.findById(99L))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> userService.getEntityById(99L))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("User not found: 99");
    }
}

