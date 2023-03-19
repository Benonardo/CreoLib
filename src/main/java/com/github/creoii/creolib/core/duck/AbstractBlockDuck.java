package com.github.creoii.creolib.core.duck;

import com.github.creoii.creolib.api.util.registry.content.DripSettings;
import com.github.creoii.creolib.api.util.registry.content.FireSettings;
import com.github.creoii.creolib.api.util.registry.content.TillingSettings;

public interface AbstractBlockDuck {
    FireSettings getFireSettings();

    DripSettings getDripSettings();

    TillingSettings getTillingSettings();
}
