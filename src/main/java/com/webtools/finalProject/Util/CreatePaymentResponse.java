package com.webtools.finalProject.Util;

public class CreatePaymentResponse {
    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    private String clientSecret;

    public CreatePaymentResponse(String clientSecret) {
        this.clientSecret = clientSecret;
    }
}

