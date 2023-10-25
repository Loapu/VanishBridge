package dev.loapu.vanishbridge.api;

import org.jetbrains.annotations.ApiStatus.Internal;

/**
 * Provides static access to the {@link VanishBridge} API.
 *
 * @since 1.0
 */
public final class VanishBridgeProvider
{
	private static VanishBridge instance;
	
	/**
	 * Gets an instance of the {@link VanishBridge} API,
	 * throwing an {@link IllegalStateException} if the API has not been initialized yet.
	 *
	 * @return an instance of the {@link VanishBridge} API
	 * @throws IllegalStateException if the API has not been initialized yet
	 * @since 1.0
	 */
	public static VanishBridge get()
	{
		var instance = VanishBridgeProvider.instance;
		if (instance == null)
		{
			throw new IllegalStateException("VanishBridge has not been initialized yet!");
		}
		return instance;
	}
	
	@Internal
	static void register(VanishBridge instance)
	{
		VanishBridgeProvider.instance = instance;
	}
	@Internal
	static void unregister()
	{
		VanishBridgeProvider.instance = null;
	}
	
	@Internal
	private VanishBridgeProvider()
	{
		throw new UnsupportedOperationException("This class cannot be instantiated!");
	}
}
