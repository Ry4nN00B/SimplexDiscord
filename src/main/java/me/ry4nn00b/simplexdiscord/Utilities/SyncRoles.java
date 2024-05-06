package me.ry4nn00b.simplexdiscord.Utilities;

import me.ry4nn00b.simplexdiscord.Discord.DiscordConnect;
import me.ry4nn00b.simplexdiscord.Managers.ConfigManager;
import me.ry4nn00b.simplexdiscord.SQLite.Constructs;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Role;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

public class SyncRoles {

    public static void addRoleMember(Player player) {
        String guildID = ConfigManager.getConfig("Config").getString("Discord.IDServidor");
        Guild guild = DiscordConnect.jda.getGuildById(guildID);
        if (guild == null) {
            Bukkit.getConsoleSender().sendMessage("§b[SimplexDiscord] §cServidor do Discord não encontrado.");
            return;
        }

        String memberID = Constructs.getMemberID(player);

        if (player.hasPermission("*")) {
            player.sendMessage(" ");
            player.sendMessage("§b[SimplexDiscord] §cVocê possui todas as permissões e não pode receber cargos no Discord.");
            player.sendMessage(" ");
            return;
        }

        ConfigurationSection cargosConfig = ConfigManager.getConfig("Config").getConfigurationSection("Discord.Cargos");
        if (cargosConfig == null) {
            Bukkit.getConsoleSender().sendMessage("§b[SimplexDiscord] §cConfiguração de cargos não encontrada.");
            return;
        }

        boolean hasPermissionForAnyRole = false;

        for (String roleID : cargosConfig.getKeys(false)) {
            String discordID = cargosConfig.getString(roleID + ".DiscordID");
            String permission = cargosConfig.getString(roleID + ".Permissão");

            if (player.hasPermission(permission)) {
                hasPermissionForAnyRole = true;
                Role role = guild.getRoleById(discordID);
                if (role != null) {
                    guild.retrieveMemberById(memberID).queue(member -> {
                        if (!member.getRoles().contains(role)) {
                            guild.addRoleToMember(member, role).queue();
                        }
                    });
                }
            }
        }

        if (!hasPermissionForAnyRole) {
            player.sendMessage(" ");
            player.sendMessage("§b[SimplexDiscord] §fSeus cargos já estão sincronzados.");
            player.sendMessage(" ");
        } else {
            player.sendMessage(" ");
            player.sendMessage("§b[SimplexDiscord] §fSeus cargos foram sincronizados com o Discord.");
            player.sendMessage(" ");
            player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 8, 10);
        }
    }

}
