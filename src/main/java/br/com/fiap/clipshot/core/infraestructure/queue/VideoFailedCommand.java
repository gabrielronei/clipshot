package br.com.fiap.clipshot.core.infraestructure.queue;

import java.util.UUID;

public record VideoFailedCommand(
        UUID id,
        Long userId) {}