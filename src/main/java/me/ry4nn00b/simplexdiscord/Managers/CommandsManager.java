package me.ry4nn00b.simplexdiscord.Managers;

import me.ry4nn00b.simplexdiscord.Commands.JSONCommands;
import me.ry4nn00b.simplexdiscord.Commands.LinkMinecraft;

import org.bukkit.Bukkit;

public class CommandsManager {

    public static void getCommands(){

        Bukkit.getPluginCommand("vincular").setExecutor(new LinkMinecraft());
        Bukkit.getPluginCommand("sdaceitar").setExecutor(new JSONCommands());
        Bukkit.getPluginCommand("sdrecusar").setExecutor(new JSONCommands());

    }

}
