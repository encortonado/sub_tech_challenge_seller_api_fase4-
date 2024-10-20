package br.com.fiap.tech.sub_tech_challenge_seller_api_fase4.application.purchase.services;

import br.com.fiap.tech.sub_tech_challenge_seller_api_fase4.adapter.entrypoint.api.model.PurchaseDTO;
import br.com.fiap.tech.sub_tech_challenge_seller_api_fase4.adapter.entrypoint.api.model.VehicleDTO;
import br.com.fiap.tech.sub_tech_challenge_seller_api_fase4.adapter.entrypoint.persistance.VehiclePurchaseRepository;
import br.com.fiap.tech.sub_tech_challenge_seller_api_fase4.application.purchase.entities.PurchaseEntity;
import br.com.fiap.tech.sub_tech_challenge_seller_api_fase4.infrastructure.exceptions.CustomErrorTypeException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@TestPropertySource(properties = "vehicle.url=http://localhost:8080/api/vehicle/")
class VehiclePurchaseServiceTest {


    private VehiclePurchaseService vehiclePurchaseService;

    @Mock
    private VehiclePurchaseRepository vehiclePurchaseRepository;


    @Mock
    private RestTemplate restTemplate;

    AutoCloseable openMocks;

    @BeforeEach()
    void setup() {
        vehiclePurchaseService = new VehiclePurchaseService(vehiclePurchaseRepository);
    }

    @AfterEach
    void tearDown() throws Exception{
        
    }




    @Test
    void listSoldVehicles() {

        List<PurchaseEntity> list = purchasesList();

        when(vehiclePurchaseRepository.findAllByIsPaidIsTrueOrderByVehiclePriceDesc()).thenReturn(list);

        List<PurchaseEntity> result = vehiclePurchaseService.listSoldVehicles();

        assertThat(result)
                .isNotNull()
                .isNotEmpty()
                .hasSize(3)
                .containsExactly(list.get(0), list.get(1), list.get(2));

        verify(vehiclePurchaseRepository, times(1)).findAllByIsPaidIsTrueOrderByVehiclePriceDesc();
    }

    @Test
    void allowPayment() {
        PurchaseEntity purchase = PurchaseEntity.builder()
                .id("123")
                .vehicleId(123)
                .vehiclePrice(1222.00)
                .purchaseDate(new Date())
                .cpf("123345")
                .isPaid(false)
                .build();

        when(vehiclePurchaseRepository.findById("123")).thenReturn(Optional.of(purchase));

        vehiclePurchaseService.allowPayment("123");

        verify(vehiclePurchaseRepository, times(1)).findById(any());
    }

    @Test
    void findById() {
        PurchaseEntity purchase = PurchaseEntity.builder()
                .id("123")
                .vehicleId(123)
                .vehiclePrice(1222.00)
                .purchaseDate(new Date())
                .cpf("123345")
                .isPaid(false)
                .build();

        when(vehiclePurchaseRepository.findById("123")).thenReturn(Optional.of(purchase));

        vehiclePurchaseService.findById("123");

        assertThat(purchase).isNotNull();
        verify(vehiclePurchaseRepository, times(1)).findById(any());

    }




    List<PurchaseEntity> purchasesList() {
        var purchase1 = PurchaseEntity.builder()
                .cpf("1234567890")
                .purchaseDate(new Date())
                .vehicleId(1)
                .vehiclePrice(50000.0)
                .isPaid(true)
                .build();

        var purchase2 = PurchaseEntity.builder()
                .cpf("0987654321")
                .purchaseDate(new Date())
                .vehicleId(2)
                .vehiclePrice(30000.0)
                .isPaid(false)
                .build();

        var purchase3 = PurchaseEntity.builder()
                .cpf("1112223334")
                .purchaseDate(new Date())
                .vehicleId(3)
                .vehiclePrice(20000.0)
                .isPaid(true)
                .build();

        return Arrays.asList(purchase1, purchase2, purchase3);
    }

}