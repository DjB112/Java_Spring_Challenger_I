package com.alura.libreria.service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ConsumoAPI {
    public String obtenerDatos(String url){
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();
        HttpResponse<String> response = null;
        try {
            response = client
                    .send(request, HttpResponse.BodyHandlers.ofString());

        } catch (IOException | InterruptedException e) {
            System.out.println("sadasdsadasdasdasd");
            throw new RuntimeException(e);

        }catch (NumberFormatException e){
            System.out.println("Ocurrio un error: ");

        }catch (IllegalArgumentException e){
            System.out.println("Error en la URI, Verifique el Nombre");
        }
        String json = response.body();
        return json;
    }
}
