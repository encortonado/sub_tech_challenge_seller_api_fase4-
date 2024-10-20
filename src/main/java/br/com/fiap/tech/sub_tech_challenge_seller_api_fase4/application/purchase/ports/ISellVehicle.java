package br.com.fiap.tech.sub_tech_challenge_seller_api_fase4.application.purchase.ports;

import br.com.fiap.tech.sub_tech_challenge_seller_api_fase4.adapter.entrypoint.api.model.PurchaseDTO;
import br.com.fiap.tech.sub_tech_challenge_seller_api_fase4.application.purchase.entities.PurchaseEntity;

public interface ISellVehicle {

    PurchaseEntity sellVehicle(PurchaseDTO purchaseDTO);
}
