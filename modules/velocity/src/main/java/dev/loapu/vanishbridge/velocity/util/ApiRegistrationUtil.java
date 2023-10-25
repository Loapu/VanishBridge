package dev.loapu.vanishbridge.velocity.util;

import dev.loapu.vanishbridge.api.VanishBridge;
import dev.loapu.vanishbridge.api.VanishBridgeProvider;

import java.lang.reflect.Method;

public class ApiRegistrationUtil
{
	private static final Method REGISTER;
	private static final Method UNREGISTER;
	static {
		try {
			REGISTER = VanishBridgeProvider.class.getDeclaredMethod("register", VanishBridge.class);
			REGISTER.setAccessible(true);
			
			UNREGISTER = VanishBridgeProvider.class.getDeclaredMethod("unregister");
			UNREGISTER.setAccessible(true);
		} catch (NoSuchMethodException e) {
			throw new ExceptionInInitializerError(e);
		}
	}
	
	public static void registerProvider(VanishBridge vanishBridgeApi) {
		try {
			REGISTER.invoke(null, vanishBridgeApi);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void unregisterProvider() {
		try {
			UNREGISTER.invoke(null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
