package me.ry4nn00b.simplexdiscord.Managers;

import me.ry4nn00b.simplexdiscord.Events.PlayerJoin;
import me.ry4nn00b.simplexdiscord.Events.PlayerRegister;
import me.ry4nn00b.simplexdiscord.Main;
import org.bukkit.Bukkit;

public class EventsManager {

    public static void getEvents(Main plugin){

        Bukkit.getPluginManager().registerEvents(new PlayerRegister(), plugin);
        Bukkit.getPluginManager().registerEvents(new PlayerJoin(), plugin);

    }

}
