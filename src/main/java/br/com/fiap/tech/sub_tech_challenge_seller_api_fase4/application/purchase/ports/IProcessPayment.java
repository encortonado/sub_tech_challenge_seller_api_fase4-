package br.com.fiap.tech.sub_tech_challenge_seller_api_fase4.application.purchase.ports;

public interface IProcessPayment {

    void allowPayment(String purchaseId);

}