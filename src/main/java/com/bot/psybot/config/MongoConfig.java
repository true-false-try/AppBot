package com.bot.psybot.config;

import com.bot.psybot.vault.response.VaultResponse;
import com.bot.psybot.vault.service.VaultService;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.concurrent.TimeUnit;

@Configuration
@Setter
@RequiredArgsConstructor
public class MongoConfig {

    private final VaultService vaultService;

    private String uri;
    private String database;
    private Integer poolMaxLifeTime = 10000;
    private Integer poolMinLifeTime = 100;

    private void initializeMongoConfig() {
        try {
            // Fetch MongoDB configuration data from Vault
            VaultResponse mongoConfigData = vaultService.fetchMongoConfigFromVault();

            // Set MongoDB configuration properties
            this.uri = (String) mongoConfigData.getData().getUrl();
            this.database = (String) mongoConfigData.getData().getDatabase();
            // Set other configuration properties as needed

            System.out.println("MongoDB configuration initialized from Vault.");
        } catch (Exception e) {
            // Handle error condition (e.g., log error)
            System.err.println("Error initializing MongoDB configuration from Vault: " + e.getMessage());
        }
    }

    @Bean
    public MongoClient mongoClient() {
        initializeMongoConfig();
        if (uri == null || uri.isEmpty()) {
            throw new IllegalStateException("MongoDB URI is not set");
        }

        MongoClientSettings mongoClientSettings =
                MongoClientSettings.builder()
                        .applyConnectionString(new ConnectionString(uri))
                        .applyToConnectionPoolSettings(settings -> {
                            settings.maxConnectionLifeTime(poolMaxLifeTime, TimeUnit.MILLISECONDS)
                                    .minSize(poolMinLifeTime)
                                    .maxSize(poolMaxLifeTime)
                                    .maintenanceFrequency(1, TimeUnit.MINUTES)
                                    .maintenanceInitialDelay(11, TimeUnit.MILLISECONDS)
                                    .maxConnectionIdleTime(30, TimeUnit.SECONDS)
                                    .maxWaitTime(15, TimeUnit.MILLISECONDS);
                        })
                        .build();

        return MongoClients.create(mongoClientSettings);
    }

    @Bean
    public MongoTemplate mongoTemplate() {
        return new MongoTemplate(mongoClient(), database);
    }
}

