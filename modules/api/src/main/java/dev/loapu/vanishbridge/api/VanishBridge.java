package dev.loapu.vanishbridge.api;

import dev.loapu.vanishbridge.api.model.VanishBridgePlayer;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.List;

/**
 * The VanishBridge API.
 *
 * <p>The API allows proxy-plugins to see which players are vanished on the network.</p>
 *
 * @since 1.0
 */
public interface VanishBridge
{
	/**
	 * Gets a list of all vanished players on the network.
	 *
	 * @return a list of all vanished players on the network
	 * @since 1.0
	 */
	@NonNull List<VanishBridgePlayer> vanishedPlayers();
}
