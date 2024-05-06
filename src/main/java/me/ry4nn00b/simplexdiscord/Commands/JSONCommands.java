package me.ry4nn00b.simplexdiscord.Commands;

import me.ry4nn00b.simplexdiscord.Main;
import me.ry4nn00b.simplexdiscord.SQLite.Constructs;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class JSONCommands implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String s, @NotNull String[] args) {

        //Variables
        Player p = (Player) sender;
        Map<Player, String> pendingCancel = LinkMinecraft.pendingCancel;
        String prefix = Main.prefix;

        //Command
        if(cmd.getName().equalsIgnoreCase("sdaceitar")){
            if(pendingCancel.containsKey(p)){

                p.sendMessage(" ");
                p.sendMessage(prefix + "§aSua conta foi desvinculada com sucesso!");
                p.sendMessage(" ");
                Constructs.setMemberName(p, "Nenhuma");
                Constructs.setMemberID(p, "Nenhum");
                Constructs.setDateLink(p, "0");
                Constructs.setHoursLink(p, "0");
                pendingCancel.remove(p);

            }else return true;
        }

        if(cmd.getName().equalsIgnoreCase("sdrecusar")){
            if(pendingCancel.containsKey(p)){

                p.sendMessage(" ");
                p.sendMessage(prefix + "§fVocê recusou a desvinculação, sua conta ainda permanece vinculada.");
                p.sendMessage(" ");
                pendingCancel.remove(p);

            }else return true;
        }

        return false;
    }
}
