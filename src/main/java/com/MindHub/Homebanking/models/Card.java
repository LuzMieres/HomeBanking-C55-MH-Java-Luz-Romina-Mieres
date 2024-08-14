package com.MindHub.Homebanking.models;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Random;

@Entity
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String cardholder;
    @Enumerated(EnumType.STRING)
    private CardType type;
    @Enumerated(EnumType.STRING)
    private ColorType color;
    private String number;
    private String cvv;
    private LocalDate fromDate;
    private LocalDate thruDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "client_id")
    private Client client;

    public Card() {
    }

    public Card(CardType type, ColorType color, LocalDate fromDate, LocalDate thruDate) {
        this.type = type;
        this.color = color;
        this.fromDate = fromDate;
        this.thruDate = thruDate;
    }

    public long getId() {
        return id;
    }

    public String getCardholder() {
        return cardholder;
    }

    public void setCardHolder(String cardholder) {
        this.cardholder = cardholder;
    }

    public CardType getType() {
        return type;
    }

    public void setType(CardType type) {
        this.type = type;
    }

    public ColorType getColor() {
        return color;
    }

    public void setColor(ColorType color) {
        this.color = color;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    public LocalDate getFromDate() {
        return fromDate;
    }

    public void setFromDate(LocalDate fromDate) {
        this.fromDate = fromDate;
    }

    public LocalDate getThruDate() {
        return thruDate;
    }

    public void setThruDate(LocalDate thruDate) {
        this.thruDate = thruDate;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    @Override
    public String toString() {
        return "Card{" +
                "id=" + id +
                ", cardHolder='" + cardholder + '\'' +
                ", type=" + type +
                ", color=" + color +
                ", number='" + number + '\'' +
                ", cvv=" + cvv +
                ", fromDate=" + fromDate +
                ", thruDate=" + thruDate +
                '}';
    }
    // Método para generar un número de tarjeta de crédito completo
    public  String generarNumeroTarjeta() {
        Random random = new Random(); // Crea una instancia de Random para generar números aleatorios
        StringBuilder numeroTarjeta = new StringBuilder(""); // Crea un StringBuilder para construir el número de tarjeta de crédito

        // Generar los primeros 12 dígitos de la tarjeta
        for (int i = 0; i < 16; i++) {
            int digito = random.nextInt(10); // Genera un dígito aleatorio entre 0 y 9
            numeroTarjeta.append(digito); // Agrega el dígito al StringBuilder
        }

//        // Generar el siguiente dígito de la tarjeta (el dígito de verificación)
//        int digitoVerificacion = calcularDigitoVerificacion(numeroTarjeta.toString()); // Calcula el dígito de verificación utilizando el método calcularDigitoVerificacion
//        numeroTarjeta.append(digitoVerificacion); // Agrega el dígito de verificación al StringBuilder
        return numeroTarjeta.toString(); // Devuelve el número de tarjeta de crédito generado como una cadena de texto
    }

    // Método para generar un CVV aleatorio
    public String generarCVV() {
        Random random = new Random();
        int cvv = random.nextInt(1000);

        // Completa el número con ceros si tiene solo una o dos cifras
        if (cvv < 10) {
            cvv = cvv * 100;
        } else if (cvv < 100) {
            cvv = cvv * 10;
        }

        return String.valueOf(cvv);
    }

//    // Método privado para calcular el dígito de verificación de un número de tarjeta de crédito
//    private int calcularDigitoVerificacion(String numeroTarjeta) {
//        int suma = 0; // Inicializa la suma a 0
//        int multiplicador = 1; // Inicializa el multiplicador a 1
//
//        // Recorre los dígitos del número de tarjeta de crédito en orden inverso
//        for (int i = numeroTarjeta.length() - 1; i >= 0; i--) {
//            int digito = Character.getNumericValue(numeroTarjeta.charAt(i)); // Convierte el dígito en un número entero
//            suma += digito * multiplicador; // Calcula la suma de los dígitos multiplicados por el multiplicador
//            multiplicador = (multiplicador == 1) ? 2 : 1; // Cambia el multiplicador a 2 si es 1, o a 1 si es 2
//        }
//
//        int digitoVerificacion = (suma * 9) % 10; // Calcula el dígito de verificación como el resto de la suma multiplicada por 9 dividida por 10
//        return (digitoVerificacion == 0) ? 0 : (10 - digitoVerificacion); // Devuelve el dígito de verificación o su complemento si es 0
//    }
}
