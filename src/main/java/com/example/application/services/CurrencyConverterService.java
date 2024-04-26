//package com.example.application.services;
//
//import com.example.application.models.ExchangeRatesResponse;
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import dev.hilla.BrowserCallable;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Service;
//import org.springframework.web.client.RestClientException;
//import org.springframework.web.client.RestTemplate;
//
//import java.io.IOException;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.Optional;
//
//@BrowserCallable
//@Service
//public class CurrencyConverterService {
//    private static final Logger LOGGER = LoggerFactory.getLogger(CurrencyConverterService.class);
//    private final RestTemplate restTemplate;
//    private Map<String, Double> exchangeRates = new HashMap<>();
//
//    public CurrencyConverterService(RestTemplate restTemplate) {
//        this.restTemplate = restTemplate;
//    }
//
//    public Optional<Double> convertCurrencyFromTo(String countryIso1, String countryIso2, String amount) {
//        if (exchangeRates.isEmpty()) {
//            try {
//                this.exchangeRates = getExchangeRates();
//            } catch (RestClientException e) {
//                LOGGER.error("Error fetching exchange rates: {}", e.getMessage(), e);
//                this.exchangeRates = new HashMap<>();
//            }
//        }
//        Double rateFrom = exchangeRates.get(countryIso1);
//        Double rateTo = exchangeRates.get(countryIso2);
//
//        if (rateFrom == null || rateTo == null) {
//            LOGGER.warn("Rates for {} or {} are not available", countryIso1, countryIso2);
//            return Optional.empty();
//        }
//
//        try {
//            double convertedAmount = (Double.parseDouble(amount) / rateFrom) * rateTo;
//            return Optional.of(Math.round(convertedAmount * 100.0) / 100.0);
//        } catch (NumberFormatException e) {
//            LOGGER.error("Error parsing amount: {}", e.getMessage(), e);
//            return Optional.empty();
//        }
//    }
//
//    private Map<String, Double> getExchangeRates() {
//        String url = "https://v6.exchangerate-api.com/v6/c31455838a2dc73af09ca04c/latest/USD";
//        try {
//            String response = restTemplate.getForObject(url, String.class);
//            ExchangeRatesResponse exchangeRatesResponse = new ObjectMapper().readValue(response, ExchangeRatesResponse.class);
//            return exchangeRatesResponse != null ? exchangeRatesResponse.getRates() : new HashMap<>();
//        } catch (RestClientException | IOException e) {
//            LOGGER.error("Error fetching exchange rates: {}", e.getMessage(), e);
//            return new HashMap<>();
//        }
//    }

package com.example.application.services;
import com.example.application.models.ExchangeRatesResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import dev.hilla.BrowserCallable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
@AnonymousAllowed
    @BrowserCallable
    @Service
    public class CurrencyConverterService {
        private static final Logger LOGGER = LoggerFactory.getLogger(CurrencyConverterService.class);
    private Map<String, Double> exchangeRates = new HashMap<>();

    public Optional<Double> convertCurrencyFromTo(String countryIso1, String countryIso2, String amount) {
        if (exchangeRates.isEmpty()) {
            try {
                this.exchangeRates = getExchangeRates();
            } catch (RestClientException e) {
                LOGGER.error("Error fetching exchange rates: {}", e.getMessage(), e);
                this.exchangeRates = new HashMap<>();
            }
        }
        Double rateFrom = exchangeRates.get(countryIso1);
        Double rateTo = exchangeRates.get(countryIso2);

        if (rateFrom == null || rateTo == null) {
            LOGGER.warn("Rates for {} or {} are not available", countryIso1, countryIso2);
            return Optional.empty();
        }

        try {
            double amountValue = Double.parseDouble(amount);
            double convertedAmount = (amountValue * rateTo) / rateFrom;
            return Optional.of(Math.round(convertedAmount * 100.0) / 100.0);
        } catch (NumberFormatException e) {
            LOGGER.error("Error parsing amount: {}", e.getMessage(), e);
            return Optional.empty();
        }
    }


    public CurrencyConverterService(RestTemplate restTemplate) {
        try {
            this.exchangeRates = getExchangeRates();
        } catch (RestClientException e) {
            LOGGER.error("Error fetching exchange rates: {}", e.getMessage(), e);
            this.exchangeRates = new HashMap<>();
        }
    }



    public Map<String, Double> getExchangeRates() {
        String url = "https://v6.exchangerate-api.com/v6/c31455838a2dc73af09ca04c/latest/USD";
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            ExchangeRatesResponse exchangeRatesResponse = new ObjectMapper().readValue(response.body(), ExchangeRatesResponse.class);
            return exchangeRatesResponse != null ? exchangeRatesResponse.getConversion_rates() : new HashMap<>();
        } catch (IOException | InterruptedException e) {
            LOGGER.error("Error fetching exchange rates: {}", e.getMessage(), e);
            return new HashMap<>();
        }
    }

        public static Map<String, String> getCountryIsoOptions() {
        Map<String, String> countryIsoOptions = new HashMap<>();
        countryIsoOptions.put("AMD", "AMD - Armenian Dram");
        countryIsoOptions.put("ANG", "ANG - Netherlands Antillean Guilder");
        countryIsoOptions.put("AOA", "AOA - Angolan Kwanza");
        countryIsoOptions.put("ARS", "ARS - Argentine Peso");
        countryIsoOptions.put("AUD", "AUD - Australian Dollar");
        countryIsoOptions.put("AWG", "AWG - Aruban Florin");
        countryIsoOptions.put("AZN", "AZN - Azerbaijani Manat");
        countryIsoOptions.put("BAM", "BAM - Bosnia-Herzegovina Convertible Mark");
        countryIsoOptions.put("BBD", "BBD - Barbadian Dollar");
        countryIsoOptions.put("BDT", "BDT - Bangladeshi Taka");
        countryIsoOptions.put("BGN", "BGN - Bulgarian Lev");
        countryIsoOptions.put("BHD", "BHD - Bahraini Dinar");
        countryIsoOptions.put("BIF", "BIF - Burundian Franc");
        countryIsoOptions.put("BMD", "BMD - Bermudian Dollar");
        countryIsoOptions.put("BND", "BND - Brunei Dollar");
        countryIsoOptions.put("BOB", "BOB - Bolivian Boliviano");
        countryIsoOptions.put("BRL", "BRL - Brazilian Real");
        countryIsoOptions.put("BSD", "BSD - Bahamian Dollar");
        countryIsoOptions.put("BTN", "BTN - Bhutanese Ngultrum");
        countryIsoOptions.put("BWP", "BWP - Botswana Pula");
        countryIsoOptions.put("BYN", "BYN - Belarusian Ruble");
        countryIsoOptions.put("BZD", "BZD - Belize Dollar");
        countryIsoOptions.put("CAD", "CAD - Canadian Dollar");
        countryIsoOptions.put("CDF", "CDF - Congolese Franc");
        countryIsoOptions.put("CHF", "CHF - Swiss Franc");
        countryIsoOptions.put("CLP", "CLP - Chilean Peso");
        countryIsoOptions.put("CNY", "CNY - Chinese Yuan");
        countryIsoOptions.put("COP", "COP - Colombian Peso");
        countryIsoOptions.put("CRC", "CRC - Costa Rican Colón");
        countryIsoOptions.put("CUP", "CUP - Cuban Peso");
        countryIsoOptions.put("CVE", "CVE - Cape Verdean Escudo");
        countryIsoOptions.put("CZK", "CZK - Czech Koruna");
        countryIsoOptions.put("DJF", "DJF - Djiboutian Franc");
        countryIsoOptions.put("DKK", "DKK - Danish Krone");
        countryIsoOptions.put("DOP", "DOP - Dominican Peso");
        countryIsoOptions.put("DZD", "DZD - Algerian Dinar");
        countryIsoOptions.put("EGP", "EGP - Egyptian Pound");
        countryIsoOptions.put("ERN", "ERN - Eritrean Nakfa");
        countryIsoOptions.put("ETB", "ETB - Ethiopian Birr");
        countryIsoOptions.put("EUR", "EUR - Euro");
        countryIsoOptions.put("FJD", "FJD - Fijian Dollar");
        countryIsoOptions.put("FKP", "FKP - Falkland Islands Pound");
        countryIsoOptions.put("FOK", "FOK - Faroese Króna");
        countryIsoOptions.put("GBP", "GBP - British Pound Sterling");
        countryIsoOptions.put("GEL", "GEL - Georgian Lari");
        countryIsoOptions.put("GGP", "GGP - Guernsey Pound");
        countryIsoOptions.put("GHS", "GHS - Ghanaian Cedi");
        countryIsoOptions.put("GIP", "GIP - Gibraltar Pound");
        countryIsoOptions.put("GMD", "GMD - Gambian Dalasi");
        countryIsoOptions.put("GNF", "GNF - Guinean Franc");
        countryIsoOptions.put("GTQ", "GTQ - Guatemalan Quetzal");
        countryIsoOptions.put("GYD", "GYD - Guyanaese Dollar");
        countryIsoOptions.put("HKD", "HKD - Hong Kong Dollar");
        countryIsoOptions.put("HNL", "HNL - Honduran Lempira");
        countryIsoOptions.put("HRK", "HRK - Croatian Kuna");
        countryIsoOptions.put("HTG", "HTG - Haitian Gourde");
        countryIsoOptions.put("HUF", "HUF - Hungarian Forint");
        countryIsoOptions.put("IDR", "IDR - Indonesian Rupiah");
        countryIsoOptions.put("ILS", "ILS - Israeli New Shekel");
        countryIsoOptions.put("IMP", "IMP - Isle of Man Pound");
        countryIsoOptions.put("INR", "INR - Indian Rupee");
        countryIsoOptions.put("IQD", "IQD - Iraqi Dinar");
        countryIsoOptions.put("IRR", "IRR - Iranian Rial");
        countryIsoOptions.put("ISK", "ISK - Icelandic Króna");
        countryIsoOptions.put("JEP", "JEP - Jersey Pound");
        countryIsoOptions.put("JMD", "JMD - Jamaican Dollar");
        countryIsoOptions.put("JOD", "JOD - Jordanian Dinar");
        countryIsoOptions.put("JPY", "JPY - Japanese Yen");
        countryIsoOptions.put("KES", "KES - Kenyan Shilling");
        countryIsoOptions.put("KGS", "KGS - Kyrgyzstani Som");
        countryIsoOptions.put("KHR", "KHR - Cambodian Riel");
        countryIsoOptions.put("KID", "KID - Kiribati Dollar");
        countryIsoOptions.put("KMF", "KMF - Comorian Franc");
        countryIsoOptions.put("KRW", "KRW - South Korean Won");
        countryIsoOptions.put("KWD", "KWD - Kuwaiti Dinar");
        countryIsoOptions.put("KYD", "KYD - Cayman Islands Dollar");
        countryIsoOptions.put("KZT", "KZT - Kazakhstani Tenge");
        countryIsoOptions.put("LAK", "LAK - Laotian Kip");
        countryIsoOptions.put("LBP", "LBP - Lebanese Pound");
        countryIsoOptions.put("LKR", "LKR - Sri Lankan Rupee");
        countryIsoOptions.put("LRD", "LRD - Liberian Dollar");
        countryIsoOptions.put("LSL", "LSL - Lesotho Loti");
        countryIsoOptions.put("LYD", "LYD - Libyan Dinar");
        countryIsoOptions.put("MAD", "MAD - Moroccan Dirham");
        countryIsoOptions.put("MDL", "MDL - Moldovan Leu");
        countryIsoOptions.put("MGA", "MGA - Malagasy Ariary");
        countryIsoOptions.put("MKD", "MKD - Macedonian Denar");
        countryIsoOptions.put("MMK", "MMK - Myanmar Kyat");
        countryIsoOptions.put("MNT", "MNT - Mongolian Tugrik");
        countryIsoOptions.put("MOP", "MOP - Macanese Pataca");
        countryIsoOptions.put("MRU", "MRU - Mauritanian Ouguiya");
        countryIsoOptions.put("MUR", "MUR - Mauritian Rupee");
        countryIsoOptions.put("MVR", "MVR - Maldivian Rufiyaa");
        countryIsoOptions.put("MWK", "MWK - Malawian Kwacha");
        countryIsoOptions.put("MXN", "MXN - Mexican Peso");
        countryIsoOptions.put("MYR", "MYR - Malaysian Ringgit");
        countryIsoOptions.put("MZN", "MZN - Mozambican Metical");
        countryIsoOptions.put("NAD", "NAD - Namibian Dollar");
        countryIsoOptions.put("NGN", "NGN - Nigerian Naira");
        countryIsoOptions.put("NIO", "NIO - Nicaraguan Córdoba");
        countryIsoOptions.put("NOK", "NOK - Norwegian Krone");
        countryIsoOptions.put("NPR", "NPR - Nepalese Rupee");
        countryIsoOptions.put("NZD", "NZD - New Zealand Dollar");
        countryIsoOptions.put("OMR", "OMR - Omani Rial");
        countryIsoOptions.put("PAB", "PAB - Panamanian Balboa");
        countryIsoOptions.put("PEN", "PEN - Peruvian Nuevo Sol");
        countryIsoOptions.put("PGK", "PGK - Papua New Guinean Kina");
        countryIsoOptions.put("PHP", "PHP - Philippine Peso");
        countryIsoOptions.put("PKR", "PKR - Pakistani Rupee");
        countryIsoOptions.put("PLN", "PLN - Polish Zloty");
        countryIsoOptions.put("PYG", "PYG - Paraguayan Guarani");
        countryIsoOptions.put("QAR", "QAR - Qatari Riyal");
        countryIsoOptions.put("RON", "RON - Romanian Leu");
        countryIsoOptions.put("RSD", "RSD - Serbian Dinar");
        countryIsoOptions.put("RUB", "RUB - Russian Ruble");
        countryIsoOptions.put("RWF", "RWF - Rwandan Franc");
        countryIsoOptions.put("SAR", "SAR - Saudi Riyal");
        countryIsoOptions.put("SBD", "SBD - Solomon Islands Dollar");
        countryIsoOptions.put("SCR", "SCR - Seychellois Rupee");
        countryIsoOptions.put("SDG", "SDG - Sudanese Pound");
        countryIsoOptions.put("SEK", "SEK - Swedish Krona");
        countryIsoOptions.put("SGD", "SGD - Singapore Dollar");
        countryIsoOptions.put("SHP", "SHP - Saint Helena Pound");
        countryIsoOptions.put("SLL", "SLL - Sierra Leonean Leone");
        countryIsoOptions.put("SOS", "SOS - Somali Shilling");
        countryIsoOptions.put("SRD", "SRD - Surinamese Dollar");
        countryIsoOptions.put("SSP", "SSP - South Sudanese Pound");
        countryIsoOptions.put("STN", "STN - São Tomé and Príncipe Dobra");
        countryIsoOptions.put("SYP", "SYP - Syrian Pound");
        countryIsoOptions.put("SZL", "SZL - Swazi Lilangeni");
        countryIsoOptions.put("THB", "THB - Thai Baht");
        countryIsoOptions.put("TJS", "TJS - Tajikistani Somoni");
        countryIsoOptions.put("TMT", "TMT - Turkmenistani Manat");
        countryIsoOptions.put("TND", "TND - Tunisian Dinar");
        countryIsoOptions.put("TOP", "TOP - Tongan Pa'anga");
        countryIsoOptions.put("TRY", "TRY - Turkish Lira");
        countryIsoOptions.put("TTD", "TTD - Trinidad and Tobago Dollar");
        countryIsoOptions.put("TVD", "TVD - Tuvaluan Dollar");
        countryIsoOptions.put("TWD", "TWD - New Taiwan Dollar");
        countryIsoOptions.put("TZS", "TZS - Tanzanian Shilling");
        countryIsoOptions.put("UAH", "UAH - Ukrainian Hryvnia");
        countryIsoOptions.put("UGX", "UGX - Ugandan Shilling");
        countryIsoOptions.put("USD", "USD - United States Dollar");
        countryIsoOptions.put("UYU", "UYU - Uruguayan Peso");
        countryIsoOptions.put("UZS", "UZS - Uzbekistan Som");
        countryIsoOptions.put("VES", "VES - Venezuelan Bolívar");
        countryIsoOptions.put("VND", "VND - Vietnamese Dong");
        countryIsoOptions.put("VUV", "VUV - Vanuatu Vatu");
        countryIsoOptions.put("WST", "WST - Samoan Tala");
        countryIsoOptions.put("XAF", "XAF - Central African CFA Franc");
        countryIsoOptions.put("XCD", "XCD - East Caribbean Dollar");
        countryIsoOptions.put("XDR", "XDR - Special Drawing Rights");
        countryIsoOptions.put("XOF", "XOF - West African CFA Franc");
        countryIsoOptions.put("XPF", "XPF - CFP Franc");
        countryIsoOptions.put("YER", "YER - Yemeni Rial");
        countryIsoOptions.put("ZAR", "ZAR - South African Rand");
        countryIsoOptions.put("ZMW", "ZMW - Zambian Kwacha");
        countryIsoOptions.put("ZWL", "ZWL - Zimbabwean Dollar");

        return countryIsoOptions;
    }
}

