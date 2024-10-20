package br.com.fiap.tech.sub_tech_challenge_seller_api_fase4.application.purchase.services;

import br.com.fiap.tech.sub_tech_challenge_seller_api_fase4.adapter.entrypoint.api.model.PurchaseDTO;
import br.com.fiap.tech.sub_tech_challenge_seller_api_fase4.adapter.entrypoint.api.model.VehicleDTO;
import br.com.fiap.tech.sub_tech_challenge_seller_api_fase4.adapter.entrypoint.persistance.VehiclePurchaseRepository;
import br.com.fiap.tech.sub_tech_challenge_seller_api_fase4.application.purchase.entities.PurchaseEntity;
import br.com.fiap.tech.sub_tech_challenge_seller_api_fase4.application.purchase.ports.IVehiclePurshaceService;
import br.com.fiap.tech.sub_tech_challenge_seller_api_fase4.infrastructure.exceptions.CustomErrorTypeException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.List;

@Service
public class VehiclePurchaseService implements IVehiclePurshaceService {

    private VehiclePurchaseRepository vehiclePurchaseRepository;


    public VehiclePurchaseService(VehiclePurchaseRepository vehiclePurchaseRepository) {
        this.vehiclePurchaseRepository = vehiclePurchaseRepository;
    }

    @Value("${vehicle.url}")
    private String vehicleAPI;

    @Override
    public VehicleDTO findVehicle(Long vehicleId) {

        try {
            RestTemplate restTemplate = new RestTemplate();
            String vehicleURL = vehicleAPI + vehicleId;

            URI uri = new URI(vehicleURL);

            return restTemplate.exchange(
                    uri, HttpMethod.GET, HttpEntity.EMPTY, VehicleDTO.class).getBody();

        } catch (HttpClientErrorException ex) {
            throw new CustomErrorTypeException("nao encontrado");
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public List<PurchaseEntity> listSoldVehicles() {
        return vehiclePurchaseRepository.findAllByIsPaidIsTrueOrderByVehiclePriceDesc();
    }

    @Override
    public void allowPayment(String purchaseId) {
        PurchaseEntity purchase = findById(purchaseId);
        purchase.setPaid(true);
        vehiclePurchaseRepository.save(purchase);
    }

    public PurchaseEntity findById(String id) {
        return vehiclePurchaseRepository.findById(id)
                .orElseThrow(() -> new CustomErrorTypeException("compra nao encontrada"));
    }

    @Override
    public PurchaseEntity sellVehicle(PurchaseDTO purchaseDTO) {

        VehicleDTO vehicle = findVehicle(purchaseDTO.getVehicleId());

        PurchaseEntity purchase = PurchaseEntity.builder()
                .isPaid(false)
                .cpf(purchaseDTO.getCpf())
                .purchaseDate(new Date())
                .vehicleId(purchaseDTO.getVehicleId())
                .vehiclePrice(vehicle.getId())
                .build();

        return vehiclePurchaseRepository.save(purchase);
    }
}
