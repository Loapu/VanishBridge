package dev.loapu.vanishbridge.velocity.api.implementation;

import dev.loapu.vanishbridge.api.model.VanishBridgePlayer;

import java.util.UUID;

public record VanishBridgePlayerImpl(UUID uuid, String name) implements VanishBridgePlayer
{
}
