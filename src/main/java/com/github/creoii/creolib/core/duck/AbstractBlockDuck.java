package com.github.creoii.creolib.core.duck;

import com.github.creoii.creolib.api.util.registry.DripSettings;
import com.github.creoii.creolib.api.util.registry.FireSettings;
import com.github.creoii.creolib.api.util.registry.TillingSettings;

public interface AbstractBlockDuck {
    FireSettings getFireSettings();

    DripSettings getDripSettings();

    TillingSettings getTillingSettings();
}
