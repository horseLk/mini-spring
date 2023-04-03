package com.minis.core.env;

public interface Environment {
    String[] getActiveProfiles();

    String[] getDefaultProfiles(); boolean

    acceptsProfiles(String... profiles);
}
