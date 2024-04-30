# WhitelistCheck
  A Minecraft Spigot/Bukkit Plugin that permanently bans anyone attempting to join who isn't on the server whitelist.

## Information

  It runs on 1.20.4, it probably runs on other versions, but they haven't been tested.

  This plugin is designed to help combat scanner bots, griefers, or other malicious users who may try to join a Minecraft Server without being on the server whitelist.
  While this doesn't stop the bots entirely, it does give you another barrier of defense against them.

<a href="https://github.com/4rft5/WhitelistCheck/releases/download/1.1JarFile/WhitelistCheck.v1.1.jar">Download</a>

## Commands:
  | Command | Description |
  | --- | --- |
  | /wc reload | reloads the configuration file |
  | /wc disable | disables the plugin until the next server restart |
  | /wc help | lists the previous two commands |

## Configuration:
  The plugin ban message can be configured in config.yml. It supports color codes and basic formatting using \n for new lines. 
  
  The default ban message is [WhitelistCheck]: Banned by WhitelistCheck.

## Notes:
  It should be noted that when reload is used, it will not change the ban message of prior bans. It only will apply to new bans for users that are not currently banned.

  I guess that's about it. Enjoy my plugin designed just to be a "screw you in particular" kind of thing. I find it funny.

  This is also my first ever proper coding project, first GitHub project, and first Minecraft Plugin. So that's cool.


Example ban screen:<br>
![image](https://github.com/4rft5/WhitelistCheck/assets/74219775/3f5212d7-effb-4fe7-83e2-219b2a21786a)

## Future Ideas:
  I would like to make the listed user who performed the ban configurable in config. As of right now, I just have it set to "Console".

  I would also like to make the comments actually show in config.yml, but I couldn't figure out how to before I got too annoyed at it.

  One last thing is I want the plugin to be functional with tab complete when writing a command, but again, I couldn't figure out how to.

## Contributions

Contributions are welcome! After all this is the first Java project I've ever taken, and I'm sure it's not perfect. 

