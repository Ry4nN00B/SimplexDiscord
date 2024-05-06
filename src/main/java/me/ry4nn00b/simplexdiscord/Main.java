package me.ry4nn00b.simplexdiscord;

import me.ry4nn00b.simplexdiscord.Discord.DiscordConnect;
import me.ry4nn00b.simplexdiscord.Managers.CommandsManager;
import me.ry4nn00b.simplexdiscord.Managers.EventsManager;
import me.ry4nn00b.simplexdiscord.SQLite.SQLiteConnect;
import me.ry4nn00b.simplexdiscord.Managers.ConfigManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

public final class Main extends JavaPlugin {

    public static Main plugin;
    public static BukkitScheduler scheduler;
    public static String prefix = "§b[SimplexDiscord] ";

    @Override
    public void onEnable() {
        plugin = this;
        scheduler = this.getServer().getScheduler();

        Bukkit.getConsoleSender().sendMessage("§b§l======================");
        Bukkit.getConsoleSender().sendMessage(" ");
        Bukkit.getConsoleSender().sendMessage("§f§lNome: §bSimplexDiscord");
        Bukkit.getConsoleSender().sendMessage("§f§lCriador: §bRy4nN00B");
        Bukkit.getConsoleSender().sendMessage("§f§lVersão: §b1.0");
        Bukkit.getConsoleSender().sendMessage("§f§lEstado: §aIniciando");
        Bukkit.getConsoleSender().sendMessage(" ");
        Bukkit.getConsoleSender().sendMessage("§b§l======================");


        //Managers
        CommandsManager.getCommands();
        EventsManager.getEvents(plugin);

        ConfigManager.loadConfig("Config");

        //Connections
        DiscordConnect.Connect(plugin);
        SQLiteConnect.open();

    }

    @Override
    public void onDisable() {

        Bukkit.getConsoleSender().sendMessage("§b§l======================");
        Bukkit.getConsoleSender().sendMessage(" ");
        Bukkit.getConsoleSender().sendMessage("§f§lNome: §bSimplexDiscord");
        Bukkit.getConsoleSender().sendMessage("§f§lCriador: §bRy4nN00B");
        Bukkit.getConsoleSender().sendMessage("§f§lVersão: §b1.0");
        Bukkit.getConsoleSender().sendMessage("§f§lEstado: §cDesligando");
        Bukkit.getConsoleSender().sendMessage(" ");
        Bukkit.getConsoleSender().sendMessage("§b§l======================");

        //Connections
        SQLiteConnect.close();

    }
}
