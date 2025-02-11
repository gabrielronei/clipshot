package br.com.fiap.clipshot.core.infra.queue;

import java.util.UUID;

public record VideoFailedCommand(
        UUID id,
        Long userId) {}