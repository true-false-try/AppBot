package com.bot.psybot.vault.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
public class VaultData {
    private String database;
    private String password;
    private String url;
    private String username;
}
