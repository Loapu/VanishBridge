package dev.loapu.vanishbridge.api.model;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.UUID;

/**
 * Represents a player on the network.
 */
public interface VanishBridgePlayer
{
	/**
	 * Gets the name of the player.
	 *
	 * @return the name of the player
	 */
	@NonNull String name();
	
	/**
	 * Gets the UUID of the player.
	 *
	 * @return the UUID of the player
	 */
	@NonNull UUID uuid();
}
