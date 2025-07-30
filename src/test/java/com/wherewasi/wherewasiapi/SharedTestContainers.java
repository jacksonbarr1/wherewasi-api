package com.wherewasi.wherewasiapi;

import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.MongoDBContainer;

public class SharedTestContainers {

    public static final MongoDBContainer INSTANCE_MONGO =
            new MongoDBContainer("mongo:latest")
                    .withReuse(true)
                    .withExposedPorts(27017);

    public static final GenericContainer<?> INSTANCE_REDIS =
            new GenericContainer<>("redis:latest")
                    .withReuse(true)
                    .withExposedPorts(6379);

    static {
        INSTANCE_MONGO.start();
        INSTANCE_REDIS.start();
    }


}
