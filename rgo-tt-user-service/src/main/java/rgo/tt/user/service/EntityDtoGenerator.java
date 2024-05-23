package rgo.tt.user.service;

import rgo.tt.user.service.client.ClientDto;

import static rgo.tt.common.utils.RandomUtils.randomPositiveLong;
import static rgo.tt.common.utils.RandomUtils.randomString;

public class EntityDtoGenerator {

    private EntityDtoGenerator() {
    }

    public static ClientDto randomClient() {
        ClientDto dto = new ClientDto();
        dto.setEntityId(randomPositiveLong());
        dto.setEmail(randomString());
        dto.setPassword(randomString());
        return dto;
    }
}
