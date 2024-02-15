package com.bot.psybot.config;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.concurrent.TimeUnit;

@Configuration
public class MongoConfig {
    @Value("${mongo.uri}")
    private String uri;
    @Value("${mongo.database}")
    private String database;
    @Value("${mongo.pool-min-life-time}")
    private Integer poolMaxLifeTime;
    @Value("${mongo.pool-min-life-time}")
    private Integer poolMinLifeTime;

    @Bean
    public MongoClient mongoClient() {
        MongoClientSettings mongoClientSettings =
                MongoClientSettings.builder()
                        .applyConnectionString(new ConnectionString(uri))
                        .applyToConnectionPoolSettings(settings -> {
                            settings.maxConnectionLifeTime(poolMaxLifeTime, TimeUnit.MILLISECONDS)
                                    .minSize(Math.toIntExact(poolMinLifeTime))
                                    .maxSize(Math.toIntExact(poolMaxLifeTime))
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
