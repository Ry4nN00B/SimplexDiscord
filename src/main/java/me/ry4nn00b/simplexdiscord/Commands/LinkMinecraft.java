package me.ry4nn00b.simplexdiscord.Commands;

import me.ry4nn00b.simplexdiscord.Main;
import me.ry4nn00b.simplexdiscord.Managers.DiscordManager;
import me.ry4nn00b.simplexdiscord.SQLite.Constructs;
import me.ry4nn00b.simplexdiscord.Managers.ConfigManager;
import me.ry4nn00b.simplexdiscord.Utilities.SyncRoles;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class LinkMinecraft implements CommandExecutor {

    public static Map<Player, String> pendingLinks = new HashMap<>();
    public static Map<Player, Long> memberLink = new HashMap<>();
    public static Map<Player, String> pendingCancel = new HashMap<>();

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String s, @NotNull String[] args) {

        //Variables
        BukkitScheduler scheduler = Main.scheduler;
        Player p = (Player) sender;
        Main plugin = Main.plugin;
        String prefix = "§b[SimplexDiscord] ";

        //Command
        if(cmd.getName().equalsIgnoreCase("vincular")){
            if(args.length == 0){
                if(Constructs.getMemberID(p).equalsIgnoreCase("Nenhum")){
                    for (String message : noLinked()){
                        p.sendMessage(message);
                    }
                    p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 8, 10);
                }else {
                    for (String message : linkedAccount(p)){
                        p.sendMessage(message);
                    }
                    p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 8, 10);
                }
                return true;
            }

            if(args[0].equalsIgnoreCase("cancelar")){
                if(!Constructs.getMemberID(p).equalsIgnoreCase("Nenhum")){
                    p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 8, 10);
                    pendingCancel.put(p, "pending");
                    unLinked(p);
                }else p.sendMessage(prefix + "§fVocê não possui §bdiscord §fvinculado!");
                return true;
            }

            if(args[0].equalsIgnoreCase("atualizar")){
                if(!Constructs.getMemberID(p).equalsIgnoreCase("Nenhum")){
                    SyncRoles.addRoleMember(p);
                }else p.sendMessage(prefix + "§fVocê não possui §bdiscord §fvinculado!");
                return true;
            }

            if(pendingLinks.containsKey(p)){

                String codePlayer = pendingLinks.get(p);
                String code = args[0];

                long memberID = memberLink.get(p);

                if(code.equals(codePlayer)){

                    DiscordManager.sendMessageConfirmation(memberID, p);
                    p.sendMessage(prefix + "§fSua conta foi vinculada com sucesso, use: §a/vincular");
                    pendingLinks.remove(p);
                    memberLink.remove(p);

                }else {
                    p.sendMessage(prefix + "§aO código digitado está incorreto, vamos cancelar sua operação!");
                    pendingLinks.remove(p);
                    return true;
                }

                return true;

            }

            long memberID = Long.parseLong(args[0]);

            memberLink.put(p, memberID);
            DiscordManager.getMemberById(memberID, p);

            scheduler.scheduleSyncRepeatingTask(plugin, new Runnable() {
                int time = 60;
                @Override
                public void run() {

                    time--;
                    if(time == 0){
                        if(pendingLinks.containsKey(p)){
                            p.sendMessage(" ");
                            p.sendMessage(prefix + "§fA solicitação foi recusada.");
                            p.sendMessage(" ");
                            pendingLinks.remove(p);
                            memberLink.remove(p);
                        }
                    }

                }
            },20L, 20L);

        }

        return false;
    }

    private List<String> noLinked(){

        String link = ConfigManager.getConfig("Config").getString("Discord.Link");

        List<String> message = new ArrayList<>();
        message.add(" ");
        message.add("§6§m===========================================");
        message.add(" ");
        message.add(" §fVocê ainda não possui nenhum §bdiscord §fvinculado.");
        message.add(" ");
        message.add(" §fEntre em nosso §bdiscord §fpelo link abaixo.");
        message.add(" §bLink Discord:§a " + link);
        message.add(" ");
        message.add(" §fApós entrar no §bdiscord§f, volte aqui e digite:");
        message.add(" §a/vincular <ID da conta>");
        message.add(" ");
        message.add("§6§m===========================================");
        message.add(" ");

        return message;

    }

    private List<String> linkedAccount(Player player){

        String memberName = Constructs.getMemberName(player);
        String memberID = Constructs.getMemberID(player);
        String dateLink = Constructs.getDateLink(player);
        String hoursLink = Constructs.getHoursLink(player);

        List<String> message = new ArrayList<>();
        message.add(" ");
        message.add("§6§m===========================================");
        message.add(" ");
        message.add(" §b§nDiscord vinculado:§7 " + memberName);
        message.add(" §b§nID do Discord:§7 " + memberID);
        message.add(" §a§nData da vinculação:§7 " + dateLink);
        message.add(" §a§nHora da vinculação:§7 " + hoursLink);
        message.add(" ");
        message.add(" §fPara desvincular sua conta, utilize:");
        message.add(" §a/vincular cancelar");
        message.add(" §fPara sincronizar seus cargos, utilize:");
        message.add(" §a/vincular atualizar");
        message.add(" ");
        message.add("§6§m===========================================");
        message.add(" ");

        return message;

    }

    private void unLinked(Player player) {
        BaseComponent[] messageComponents = new ComponentBuilder()
                .append("\n§6§m===========================================\n").color(ChatColor.GOLD).bold(true)
                .append("\nVocê tem certeza que deseja desvincular sua conta?\n").color(ChatColor.WHITE)
                .append("Clique em ").color(ChatColor.WHITE)
                .append("[SIM]").color(ChatColor.GREEN).bold(true)
                .event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/sdaceitar"))
                .append(" ou ").color(ChatColor.WHITE)
                .append("[NÃO]").color(ChatColor.RED).bold(true)
                .event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/sdrecusar"))
                .append("\n\n§6§m===========================================\n").color(ChatColor.GOLD).bold(true)
                .create();

        player.spigot().sendMessage(messageComponents);
    }

}
