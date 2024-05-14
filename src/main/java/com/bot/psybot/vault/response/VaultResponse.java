package com.bot.psybot.vault.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
public class VaultResponse {
    @JsonProperty("request_id")
    private String requestId;
    @JsonProperty("lease_id")
    private String leaseId;
    private boolean renewable;
    @JsonProperty("lease_duration")
    private long leaseDuration;
    private VaultData data;
    private Object wrapInfo;
    private Object warnings;
    private Object auth;

}

