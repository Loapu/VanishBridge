# VanishBridge
![](https://repo.loapu.dev/api/badge/latest/releases/dev/loapu/vanishbridge/vanishbridge-api?color=40c14a&name=API&prefix=v)

VanishBridge is a simple plugin that provides information about vanished players on a proxy level. It is designed to be used by other plugins to provide a better experience for players.

Currently, VanishBridge supports the following proxy software:
- [Velocity](https://velocitypowered.com/)

And the following backend server software:
- [Paper](https://papermc.io/)

If you miss your proxy or backend server software, feel free to open an issue or create a pull request.

## For Server Owners

### Installation
The installation of VanishBridge could not be easier. 

1. Download the version required for your proxy software (e.g. Velocity) from the [releases page](https://github.com/Loapu/VanishBridge/releases).
2. Drop the downloaded jar into your proxy's `plugins` folder.
3. Restart your proxy.
4. Download the version required for your backend server software (e.g. Paper) from the [releases page](https://github.com/Loapu/VanishBridge/releases).
5. Drop the downloaded jar into every server's `plugins` folder.
6. Restart your server's.
7. Enjoy!

### Configuration
No configuration needed!

## For Developers

### Installation

#### Maven
```xml
<repository>
    <id>loapu-releases</id>
    <name>Loapu Maven Repository</name>
    <url>https://repo.loapu.dev/releases</url>
</repository>

<dependency>
    <groupId>dev.loapu.vanishbridge</groupId>
    <artifactId>vanishbridge-api</artifactId>
    <version>1.0</version>
</dependency>
```

#### Gradle
```
repositories {
    maven("https://repo.loapu.dev/releases")
}
dependencies {
    implementation("dev.loapu.vanishbridge:vanishbridge-api:VERSION")e
}
```

### [JavaDocs](https://repo.loapu.dev/javadoc/releases/dev/loapu/vanishbridge/vanishbridge-api/latest)


### Usage

#### Checking if VanishBridge is installed (Velocity)

```java
@Plugin(
    id = "velocityexample",
    name = "VelocityExample",
    version = "1.0.0",
    dependencies = {
        @Dependency(id = "vanishbridge", optional = true)
    }
)
public class VelocityExample
{
    // Make sure to add this to your plugin class if you declared VanishBridge as optional
    public boolean isVanishBridgeInstalled() 
    {
        return proxyServer.getPluginManager().isLoaded("vanishbridge");
    }
	
    // Now you can use VanishBridge
    public void example()
    {
	    if (!isVanishBridgeInstalled()) return;
	    VanishBridge vanishBridge = VanishBridgeProvider.get();
    }
}
```

#### Getting a list of vanished players
```java
import dev.loapu.vanishbridge.api.VanishBridge;
import dev.loapu.vanishbridge.api.model.VanishBridgePlayer;
import dev.loapu.vanishbridge.api.VanishBridgeProvider;

public class Example {
    // It's really that easy
    public List<VanishBridgePlayer> vanishedPlayers = VanishBridgeProvider.get().vanishedPlayers();
}
```

That's it! You can now use VanishBridge in your plugin.