package org.GR8.core;

import org.aeonbits.owner.Config;

public interface TestInitValues extends Config {
    @Key("base.api.url")
    String baseApiUrl();
}
