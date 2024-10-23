package br.com.fiap.tech.sub_tech_challenge_seller_api_fase4.adapter.entrypoint.persistance;

import br.com.fiap.tech.sub_tech_challenge_seller_api_fase4.application.purchase.entities.PurchaseEntity;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VehiclePurchaseRepository extends MongoRepository<PurchaseEntity, String> {

    @Query("{'isPaid':  true}")
    List<PurchaseEntity> findAllByIsPaidIsTrueOrderByVehiclePriceDesc();




}
