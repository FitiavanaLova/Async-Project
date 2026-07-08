package hei.school.demo.endpoint.rest.model;

import java.util.UUID;

public record SubscribeUserRequest(
        UUID id, String firstName, String lastName, String userName, String email) {}