package me.ry4nn00b.simplexdiscord.Events;

import me.ry4nn00b.simplexdiscord.SQLite.Constructs;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;

public class PlayerJoin implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e){

        //Variables
        Player p = e.getPlayer();

        //Event
        if(Constructs.getMemberName(p).equalsIgnoreCase("Nenhuma")){

            p.sendMessage(" ");
            p.sendMessage("§6§m===========================================");
            p.sendMessage(" ");
            p.sendMessage(" §fOps... parece que você ainda não vinculou sua");
            p.sendMessage(" §fconta do §bDiscord §fa sua conta do §aServidor§f.");
            p.sendMessage(" ");
            p.sendMessage(" §fEntre em nosso §bDiscord §fcom o link abaixo:");
            p.sendMessage(" §fLink: §b§nlink");
            p.sendMessage(" §fCaso já esteja, digite o seguinte comando no minecraft:");
            p.sendMessage(" §a/vincular");
            p.sendMessage(" ");
            p.sendMessage("§6§m===========================================");
            p.sendMessage(" ");

        }

    }

    @EventHandler
    public void onMove(PlayerMoveEvent e){

        Player p = e.getPlayer();

        //Event
        if(Constructs.getMemberName(p).equalsIgnoreCase("Nenhuma")){
            e.setCancelled(true);
        }

    }

}
