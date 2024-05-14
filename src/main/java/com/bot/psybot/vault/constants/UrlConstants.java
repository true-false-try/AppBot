package com.bot.psybot.vault.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UrlConstants {
    VAULT_URL("http://localhost:8095/vault/getMongoConfig");

    private final String url;
}
