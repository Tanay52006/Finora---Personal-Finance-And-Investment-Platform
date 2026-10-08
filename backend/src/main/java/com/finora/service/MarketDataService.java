package com.finora.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.finora.model.Stock;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class MarketDataService {
    private final ObjectMapper mapper;
    private final HttpClient client;
    private final DateTimeFormatter date = DateTimeFormatter.ofPattern("MMM d").withZone(ZoneId.of("Asia/Kolkata"));

    public MarketDataService(ObjectMapper mapper) {
        this.mapper = mapper;
        this.client = HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(4)).build();
    }

    public String yahooSymbol(String symbol) {
        return switch (symbol) {
            case "RELIANCE", "TCS", "INFY", "HDFCBANK", "TATAMOTORS", "ICICIBANK" -> symbol + ".NS";
            default -> symbol;
        };
    }

    public Map<String,Object> quote(Stock stock) {
        try {
            String url = "https://query1.finance.yahoo.com/v8/finance/chart/" +
                    yahooSymbol(stock.getSymbol()) + "?range=1d&interval=5m&includePrePost=false";
            JsonNode result = fetch(url).path("chart").path("result").path(0);
            JsonNode meta = result.path("meta");
            double price = meta.path("regularMarketPrice").asDouble(stock.getCurrentPrice());
            double previous = meta.path("chartPreviousClose").asDouble(stock.getPreviousPrice());
            return Map.of(
                    "symbol", stock.getSymbol(),
                    "price", price,
                    "previousClose", previous,
                    "change", price - previous,
                    "changePercent", previous == 0 ? 0 : ((price - previous) / previous) * 100,
                    "currency", meta.path("currency").asText(stock.getSymbol().equals("AAPL") || stock.getSymbol().equals("NVDA") ? "USD" : "INR"),
                    "marketState", meta.path("marketState").asText("UNKNOWN"),
                    "timestamp", Instant.now().toString()
            );
        } catch (Exception e) {
            return Map.of(
                    "symbol", stock.getSymbol(),
                    "price", stock.getCurrentPrice(),
                    "previousClose", stock.getPreviousPrice(),
                    "change", stock.getCurrentPrice() - stock.getPreviousPrice(),
                    "changePercent", stock.getPreviousPrice() == 0 ? 0 : ((stock.getCurrentPrice()-stock.getPreviousPrice())/stock.getPreviousPrice())*100,
                    "currency", stock.getSymbol().equals("AAPL") || stock.getSymbol().equals("NVDA") ? "USD" : "INR",
                    "marketState", "FALLBACK",
                    "timestamp", Instant.now().toString()
            );
        }
    }

    public List<Map<String,Object>> history(Stock stock, String range) {
        String r = switch (range == null ? "1M" : range.toUpperCase()) {
            case "1D" -> "1d";
            case "1W" -> "5d";
            case "6M" -> "6mo";
            case "1Y" -> "1y";
            case "5Y" -> "5y";
            default -> "1mo";
        };
        String interval = switch (range == null ? "1M" : range.toUpperCase()) {
            case "1D" -> "5m";
            case "1W" -> "15m";
            case "5Y" -> "1wk";
            default -> "1d";
        };
        try {
            String url = "https://query1.finance.yahoo.com/v8/finance/chart/" +
                    yahooSymbol(stock.getSymbol()) + "?range=" + r + "&interval=" + interval + "&includePrePost=false";
            JsonNode result = fetch(url).path("chart").path("result").path(0);
            JsonNode timestamps = result.path("timestamp");
            JsonNode closes = result.path("indicators").path("quote").path(0).path("close");
            List<Map<String,Object>> out = new ArrayList<>();
            for (int i = 0; i < timestamps.size() && i < closes.size(); i++) {
                if (closes.get(i).isNull()) continue;
                double price = closes.get(i).asDouble();
                out.add(Map.of(
                        "date", date.format(Instant.ofEpochSecond(timestamps.get(i).asLong())),
                        "timestamp", timestamps.get(i).asLong(),
                        "price", price
                ));
            }
            return out;
        } catch (Exception e) {
            // Keep the product usable even when a free public market endpoint is temporarily unavailable.
            List<Map<String,Object>> fallback = new ArrayList<>();
            double base = stock.getCurrentPrice();
            int n = switch (range == null ? "1M" : range.toUpperCase()) {
                case "1D" -> 24; case "1W" -> 7; case "6M" -> 26; case "1Y" -> 52; case "5Y" -> 60; default -> 30;
            };
            for (int i = 0; i < n; i++) {
                double drift = (i - n) * 0.0015;
                double wave = Math.sin(i * 0.72) * 0.012;
                fallback.add(Map.of("date", "Point " + (i + 1), "timestamp", i, "price", base * (1 + drift + wave)));
            }
            return fallback;
        }
    }

    public void refreshStock(Stock stock) {
        Map<String,Object> q = quote(stock);
        Object p = q.get("price");
        Object prev = q.get("previousClose");
        if (p instanceof Number) stock.setCurrentPrice(((Number)p).doubleValue());
        if (prev instanceof Number) stock.setPreviousPrice(((Number)prev).doubleValue());
        if (q.get("currency") != null) stock.setCurrency(String.valueOf(q.get("currency")));
    }

    public double usdToInr() {
        try {
            String url = "https://query1.finance.yahoo.com/v8/finance/chart/USDINR=X?range=1d&interval=5m";
            JsonNode result = fetch(url).path("chart").path("result").path(0);
            return result.path("meta").path("regularMarketPrice").asDouble(88.0);
        } catch (Exception e) {
            return 88.0;
        }
    }

    private JsonNode fetch(String url) throws Exception {
        HttpRequest req = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .timeout(Duration.ofSeconds(6))
                .header("User-Agent", "Finora/1.0 market-data")
                .GET().build();
        HttpResponse<String> response = client.send(req, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() < 200 || response.statusCode() >= 300) {
            throw new IllegalStateException("Market provider HTTP " + response.statusCode());
        }
        return mapper.readTree(response.body());
    }
}
