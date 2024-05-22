package com.bot.psybot.vault.service;

import com.bot.psybot.vault.response.VaultResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class VaultService {
    public VaultResponse fetchMongoConfigFromVault() {
        String url = "http://localhost:8095/vault/getMongoConfig?token-mongo=s.6x50Cm9Si8CXGGtz7dtabNO6&path-mongo=/mongodb/mental_bot";
        RestTemplate restTemplate = new RestTemplate();
        VaultResponse response = restTemplate.getForObject(url, VaultResponse.class);

        return response;
    }

}
