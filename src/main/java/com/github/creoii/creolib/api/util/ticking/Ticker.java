package com.github.creoii.creolib.api.util.ticking;

import java.util.ArrayList;
import java.util.List;

public record Ticker(Environment environment, int interval, Tickable tickable) {
    public static final List<Ticker> TICKERS = new ArrayList<>();

    public static void register(Ticker ticker) {
        TICKERS.add(ticker);
    }

    public enum Environment {
        CLIENT,
        SERVER,
        BOTH
    }
}
