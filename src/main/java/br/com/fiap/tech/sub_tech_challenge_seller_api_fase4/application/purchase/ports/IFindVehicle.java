package br.com.fiap.tech.sub_tech_challenge_seller_api_fase4.application.purchase.ports;

import br.com.fiap.tech.sub_tech_challenge_seller_api_fase4.adapter.entrypoint.api.model.VehicleDTO;

public interface IFindVehicle {

    VehicleDTO findVehicle(Long vehicleId);

}
