package dev.loapu.vanishbridge.velocity.api;

import dev.loapu.vanishbridge.api.VanishBridge;
import dev.loapu.vanishbridge.velocity.VanishBridgePlugin;
import dev.loapu.vanishbridge.api.model.VanishBridgePlayer;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.List;

public class VanishBridgeApiProvider implements VanishBridge
{
	private final VanishBridgePlugin plugin;
	
	public VanishBridgeApiProvider(VanishBridgePlugin plugin)
	{
		this.plugin = plugin;
	}
	
	@Override
	public @NonNull List<VanishBridgePlayer> vanishedPlayers()
	{
		return plugin.vanishedPlayers();
	}
	
}
