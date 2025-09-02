import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.ChatColor;

public class WhitelistCheckCommand implements CommandExecutor{
    private final JavaPlugin plugin;

    public WhitelistCheckCommand(JavaPlugin plugin){
        this.plugin = plugin;
    }

    private String translateColorCodes(String message){
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args){
        if (command.getName().equalsIgnoreCase("wc")){

            if (!sender.hasPermission("whitelistcheck.use")) {
                sender.sendMessage(translateColorCodes("&7[&4WhitelistCheck&7]&r: You do not have permission to use this command."));
                return true;
            }

            if (args.length == 0){
                sender.sendMessage(translateColorCodes("Usage: /wc <&7help&r|&7reload&r|&7disable>"));
                return true;
            }
            String subcommand = args[0].toLowerCase();
            if (args.length > 0 && args[0].equalsIgnoreCase("reload")) {
                plugin.reloadConfig();
                sender.sendMessage(translateColorCodes("&7[&4WhitelistCheck&7]&r: Configuration reloaded."));
                return true;
            }
            else if (subcommand.equals("disable")){
                plugin.getServer().getPluginManager().disablePlugin(plugin);
                sender.sendMessage(translateColorCodes("&7[&4WhitelistCheck&7]:&r Plugin Disabled. (Restart to re-enable)"));
                return true;
            }
            else if (subcommand.equals("help")){
                sender.sendMessage(translateColorCodes("&7-------[&4WhitelistCheck&7]-------\n\n &7reload&r - Reload Config \n &7disable&r - Disable WhitelistCheck"));
                return true;
            }
            else {
                sender.sendMessage(translateColorCodes("&7Unknown subcommand: &r" + subcommand));
                return true;
            }
        }
        return false;
    }
}