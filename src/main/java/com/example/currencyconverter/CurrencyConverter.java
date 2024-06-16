package com.example.currencyconverter;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class CurrencyConverter {
    private static final String API_URL = "TU API KEY";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Solicitar al usuario la cantidad y la moneda de origen
        System.out.print("Introduce la cantidad a convertir: ");
        double amount = scanner.nextDouble();
        scanner.nextLine();  // Limpiar el buffer del scanner

        System.out.print("Introduce la moneda de origen (por ejemplo, MXN): ");
        String sourceCurrency = scanner.nextLine().toUpperCase();

        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(API_URL + sourceCurrency))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            String jsonResponse = response.body();

            JsonObject jsonObject = JsonParser.parseString(jsonResponse).getAsJsonObject();
            JsonObject conversionRates = jsonObject.getAsJsonObject("conversion_rates");

            System.out.println("Conversión de " + amount + " " + sourceCurrency + ":");
            for (String currency : conversionRates.keySet()) {
                double rate = conversionRates.get(currency).getAsDouble();
                double convertedAmount = amount * rate;
                System.out.println(currency + ": " + convertedAmount);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        scanner.close();
    }
}
