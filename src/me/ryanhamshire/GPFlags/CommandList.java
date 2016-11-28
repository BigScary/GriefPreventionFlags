package me.ryanhamshire.GPFlags;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.concurrent.ConcurrentHashMap;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

class CommandList
{
    private ConcurrentHashMap<String, Boolean> seenCommands = new ConcurrentHashMap<String, Boolean>();
    private ArrayList<String> commandsFromConfig = new ArrayList<String>();
    
    CommandList(String commandNamesList)
    {
        String [] commandNames = commandNamesList.split(";");
        for(int i = 0; i < commandNames.length; i++)
        {
            String commandName = commandNames[i].trim().toLowerCase();
            if(commandName.startsWith("/")) commandName = commandName.substring(1);
            commandsFromConfig.add(commandName);
        }
    }
    
    boolean Contains(String commandName)
    {
        commandName = commandName.toLowerCase().trim();
        if(commandName.startsWith("/")) commandName = commandName.substring(1);
        
        Boolean result = this.seenCommands.get(commandName);
        if(result != null) return result;
        
        //otherwise build a list of all the aliases of this command across all installed plugins
        HashSet<String> aliases = new HashSet<String>();
        aliases.add(commandName);
        aliases.add("minecraft:" + commandName);
        for(Plugin plugin : Bukkit.getServer().getPluginManager().getPlugins())
        {
            JavaPlugin javaPlugin = (JavaPlugin)plugin;
            Command command = javaPlugin.getCommand(commandName);
            if(command != null)
            {
                aliases.add(command.getName().toLowerCase());
                aliases.add(plugin.getName().toLowerCase() + ":" + command.getName().toLowerCase());
                for(String alias : command.getAliases())
                {
                    aliases.add(alias.toLowerCase());
                    aliases.add(plugin.getName().toLowerCase() + ":" + alias.toLowerCase());
                }
            }
        }
        
        //also consider vanilla commands
        Command command = Bukkit.getServer().getPluginCommand(commandName);
        if(command != null)
        {
            for(String alias : command.getAliases())
            {
                aliases.add(alias.toLowerCase());
                aliases.add("minecraft:" + alias.toLowerCase());
            }
        }
        
        //if any of those aliases are in the explicit list from the config, then all the aliases are considered in the list
        result = false;
        for(String alias : aliases)
        {
            if(this.commandsFromConfig.contains(alias))
            {
                result = true;
                break;
            }
        }
        
        //remember the result for later
        for(String alias : aliases)
        {
            this.seenCommands.put(alias, result);
        }
        
        return result;
    }
}
