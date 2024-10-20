package br.com.fiap.tech.sub_tech_challenge_seller_api_fase4.application.purchase.entities;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collation = "clientVehicles")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class PurchaseEntity {

    @Id
    private String id;

    private String cpf;
    private long vehicleId;
    private double vehiclePrice;
    private Date purchaseDate;
    private boolean isPaid;
}
