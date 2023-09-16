package rgo.tt.user.persistence.storage.utils;

import rgo.tt.user.persistence.storage.entity.Client;

import static rgo.tt.common.utils.RandomUtils.randomPositiveLong;
import static rgo.tt.common.utils.RandomUtils.randomString;

public final class EntityGenerator {

    private EntityGenerator() {
    }

    public static Client randomClient() {
        return randomClientBuilder().build();
    }

    public static Client.Builder randomClientBuilder() {
        return Client.builder()
                .setEntityId(randomPositiveLong())
                .setEmail(randomString())
                .setPassword(randomString());
    }
}
