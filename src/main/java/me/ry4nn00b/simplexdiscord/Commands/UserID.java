package me.ry4nn00b.simplexdiscord.Commands;

import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class UserID extends ListenerAdapter {

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent e) {

        String prefix = "**[SimplexDiscord]** ";
        User user = e.getUser();
        long userID = user.getIdLong();

        if(e.getName().equalsIgnoreCase("meuid")){

            e.reply(prefix + "*O ID da sua conta é: __" + userID + "__.*").setEphemeral(true).queue();

        }

    }
}
