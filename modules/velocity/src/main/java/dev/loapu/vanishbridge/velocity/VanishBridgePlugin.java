package dev.loapu.vanishbridge.velocity;

import com.google.common.io.ByteStreams;
import com.google.inject.Inject;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.connection.DisconnectEvent;
import com.velocitypowered.api.event.connection.PluginMessageEvent;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.event.proxy.ProxyShutdownEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.proxy.ProxyServer;
import com.velocitypowered.api.proxy.ServerConnection;
import com.velocitypowered.api.proxy.messages.MinecraftChannelIdentifier;
import dev.loapu.vanishbridge.api.VanishBridge;
import dev.loapu.vanishbridge.api.model.VanishBridgePlayer;
import dev.loapu.vanishbridge.velocity.api.VanishBridgeApiProvider;
import dev.loapu.vanishbridge.velocity.api.implementation.VanishBridgePlayerImpl;
import dev.loapu.vanishbridge.velocity.util.ApiRegistrationUtil;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Plugin(id = "vanishbridge", name = "VanishBridge", version = "1.0.0", authors = {"Loapu"})
public class VanishBridgePlugin
{
	private VanishBridge apiProvider;
	private final ProxyServer server;
	private final Logger logger;
	private static final MinecraftChannelIdentifier IDENTIFIER = MinecraftChannelIdentifier.from("vanishbridge:main");
	private List<VanishBridgePlayer> vanishedPlayers;
	
	
	@Inject
	public VanishBridgePlugin(ProxyServer server, Logger logger)
	{
		this.server = server;
		this.logger = logger;
		vanishedPlayers = new ArrayList<>();
		this.apiProvider = new VanishBridgeApiProvider(this);
	}
	
	@Subscribe
	public void onProxyInitialization(ProxyInitializeEvent event)
	{
		server.getChannelRegistrar().register(IDENTIFIER);
		ApiRegistrationUtil.registerProvider(this.apiProvider);
		logger.info("VanishBridge has been initialized!");
	}
	
	@Subscribe
	public void onProxyShutdown(ProxyShutdownEvent event)
	{
		server.getChannelRegistrar().unregister(IDENTIFIER);
		ApiRegistrationUtil.unregisterProvider();
		logger.info("VanishBridge has been shutdown!");
	}
	
	@Subscribe
	public void onPluginMessageFromBackend(PluginMessageEvent event)
	{
		if (!(event.getSource() instanceof ServerConnection)) return;
		if (event.getIdentifier() != IDENTIFIER) return;
		
		var in = ByteStreams.newDataInput(event.getData());
		var subChannel = in.readUTF();
		var uuid = in.readUTF();
		var name = in.readUTF();
		var player = new VanishBridgePlayerImpl(UUID.fromString(uuid), name);
		
		if (subChannel.equals("vanished"))
		{
			if (this.vanishedPlayers.contains(player))
				return;
			this.vanishedPlayers.add(player);
			
		}
		else if (subChannel.equals("unvanished"))
		{
			this.vanishedPlayers.remove(player);
		}
	}
	
	@Subscribe
	public void onDisconnect(DisconnectEvent event)
	{
		var player = event.getPlayer();
		var vanishBridgePlayer = new VanishBridgePlayerImpl(player.getUniqueId(), player.getUsername());
		this.vanishedPlayers.remove(vanishBridgePlayer);
	}
	
	public List<VanishBridgePlayer> vanishedPlayers()
	{
		return this.vanishedPlayers;
	}
}
