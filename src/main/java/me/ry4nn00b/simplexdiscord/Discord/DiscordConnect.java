package me.ry4nn00b.simplexdiscord.Discord;

import me.ry4nn00b.simplexdiscord.Commands.UserID;
import me.ry4nn00b.simplexdiscord.Main;
import me.ry4nn00b.simplexdiscord.Managers.ConfigManager;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import org.bukkit.Bukkit;

public class DiscordConnect {

    public static JDA jda;
    public static String prefix = Main.prefix;
    public static String token = ConfigManager.getConfig("Config").getString("Discord.Token");

    public static void Connect(Main plugin){

        try {

            jda = JDABuilder.createDefault(token).build();

            jda.addEventListener(new UserID());
            jda.updateCommands().addCommands(
                    Commands.slash("meuid", "Usado para ver seu ID da conta.")).queue();

            Bukkit.getConsoleSender().sendMessage(prefix + "§aA aplicação foi iniciada com sucesso!");

        } catch (Exception e) {
            Bukkit.getConsoleSender().sendMessage(prefix + "§cA aplicação não pode ser iniciada...");
            throw new RuntimeException(e);
        }

        if(jda == null){
            Bukkit.getConsoleSender().sendMessage(prefix + "§fNão foi encontrada a dependência do DiscordBotAPI.");
            plugin.getServer().getPluginManager().disablePlugin(plugin);
        }

    }

}
