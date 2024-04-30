# WhitelistCheck
A Minecraft Plugin that bans anyone who isn't on the server whitelist.


This plugin is designed to help combat serverseeker bots, or other malicious users who may try to join a Minecraft Server without being on the server whitelist.
While this doesn't stop the bots entirely, it does give you another barrier of defense against them.

Commands:
/wc reload - reloads the configuration
/wc disable - disables the plugin
/wc help - lists the previous commands

Configuration:
The plugin ban message can be configured in config.yml. It supports color codes and basic formatting using \n for new lines. 
The default ban message is [WhitelistCheck]: Banned by WhitelistCheck.

It should be noted that when reload is used, it will not change the ban message of prior bans. It only will apply to new bans for users that are not currently banned.

I guess that's about it. Enjoy my plugin designed just to be a "screw you in particular" kind of thing. I find it funny.
