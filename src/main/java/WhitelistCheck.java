import org.bukkit.BanEntry;
import org.bukkit.BanList;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.OfflinePlayer;
import java.time.Instant;
import java.util.Set;
import org.bukkit.profile.PlayerProfile;
import org.bukkit.ChatColor;
import org.bukkit.command.PluginCommand;

public class WhitelistCheck extends JavaPlugin implements Listener {
    private final JavaPlugin plugin = this;
    private String banMessage;
    private boolean checkExistingBans;
    private FileConfiguration config;

    public WhitelistCheck() {
        loadConfig();
        plugin.getConfig().options().copyDefaults(true);
        plugin.saveConfig();
    }

    @Override
    public void onEnable() {
        PluginCommand command = plugin.getCommand("wc");
        if (command != null) {
            command.setExecutor(new WhitelistCheckCommand(this));
            command.setTabCompleter(null);
        } else {
            plugin.getLogger().warning("Command 'wc' doesn't exist. Is the plugin installed correctly?");
        }

        plugin.getLogger().info("Plugin has been enabled.");

        plugin.getServer().getPluginManager().registerEvent(
                PlayerLoginEvent.class,
                this,
                EventPriority.HIGHEST, // Set to HIGHEST priority
                (listener, event) -> ((WhitelistCheck) listener).onPlayerLogin((PlayerLoginEvent) event),
                plugin,
                true
        );
    }

    @Override
    public void onDisable() {
        getLogger().info("Plugin has been disabled.");
    }

    @Override
    public void reloadConfig() {
        super.reloadConfig();
        config = getConfig();
        config.options().copyDefaults(true);
        saveConfig();
        loadConfig();
        plugin.getLogger().info("Configuration Reloaded");
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerLogin(PlayerLoginEvent event) {
        Player player = event.getPlayer();
        String playerName = player.getName();

        if (!isPlayerWhitelisted(playerName)) {
            BanList<PlayerProfile> banList = player.getServer().getBanList(BanList.Type.PROFILE);

            // Check if player is already banned and get the ban entry
            BanEntry<PlayerProfile> banEntry = banList.getBanEntry(player.getPlayerProfile());
            if (checkExistingBans && banEntry != null) {
                event.setResult(PlayerLoginEvent.Result.KICK_OTHER);
                event.setKickMessage(getCustomKickMessage(banEntry.getReason()));
                return;
            }

            event.setResult(PlayerLoginEvent.Result.KICK_OTHER);
            event.setKickMessage(getCustomKickMessage(banMessage));

            Instant distantFuture = Instant.ofEpochMilli(Long.MAX_VALUE);
            banList.addBan(player.getPlayerProfile(), banMessage, distantFuture, "Console");
            plugin.getLogger().info("WhitelistCheck banned " + playerName + " for not being on the whitelist.");
        }
    }

    private String getCustomKickMessage(String customBanMessage) {
        return ChatColor.translateAlternateColorCodes('&', customBanMessage).replace("\\n", "\n");
    }

    private void loadConfig() {
        plugin.getConfig().options().copyDefaults(true);
        plugin.saveConfig();
        config = plugin.getConfig();

        // Add default value for check_existing_bans if it doesn't exist
        if (!config.contains("check_existing_bans")) {
            config.set("check_existing_bans", true);
            plugin.saveConfig(); // Save the config with the new default value
        }

        // Load ban_message from config
        String defaultBanMessage = "&7[&4WhitelistCheck&7]&r: You are not whitelisted on this server!";
        String configBanMessage = config.getString("ban_message");
        if (configBanMessage != null && !configBanMessage.isEmpty()) {
            banMessage = ChatColor.translateAlternateColorCodes('&', configBanMessage).replace("\\n", "\n");
        } else {
            banMessage = ChatColor.translateAlternateColorCodes('&', defaultBanMessage).replace("\\n", "\n");
        }

        // Load check_existing_bans from config
        checkExistingBans = config.getBoolean("check_existing_bans", true);
    }

    private boolean isPlayerWhitelisted(String playerName) {
        Set<OfflinePlayer> whitelist = plugin.getServer().getWhitelistedPlayers();

        for (OfflinePlayer player : whitelist) {
            String playerNameInWhitelist = player.getName();
            if (playerNameInWhitelist != null && playerNameInWhitelist.equalsIgnoreCase(playerName)) {
                return true;
            }
        }
        return false;
    }
}
