package me.ry4nn00b.simplexdiscord.Managers;

import me.ry4nn00b.simplexdiscord.Commands.LinkMinecraft;
import me.ry4nn00b.simplexdiscord.Discord.DiscordConnect;
import me.ry4nn00b.simplexdiscord.Main;
import me.ry4nn00b.simplexdiscord.SQLite.Constructs;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.requests.RestAction;
import org.bukkit.entity.Player;

import java.awt.*;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Random;

public class DiscordManager {

    public static Map<Player, String> pendingLinks = LinkMinecraft.pendingLinks;
    private static JDA jda = DiscordConnect.jda;
    public static String prefix = Main.prefix;

    public static void getMemberById(Long memberId, Player player) {
        String guildID = ConfigManager.getConfig("Config").getString("Discord.IDServidor");
        Guild guild = jda.getGuildById(guildID);
        if (guild == null) {
            player.sendMessage(prefix + "§cServidor do Discord não encontrado.");
            return;
        }

        RestAction<Member> retrieveAction = guild.retrieveMemberById(memberId);
        retrieveAction.queue(member -> {
            User user = member.getUser();

            try {
                String code = generateRandomCode();

                user.openPrivateChannel().flatMap(channel -> channel.sendMessageEmbeds(sendEmbedCode(player, member, code))).queue(
                        success -> {
                            player.sendMessage(" ");
                            player.sendMessage(prefix + "§fFoi enviada uma solicitação para: §a" + member.getUser().getName());
                            player.sendMessage(prefix + "§fVocê tem 60 segundos para confirmar.");
                            player.sendMessage(" ");
                            pendingLinks.put(player, code);
                        },
                        failure -> {
                            player.sendMessage(" ");
                            player.sendMessage(prefix + "§cVocê está com sua DM fechada, por favor, libere-a e use o comando novamente.");
                            player.sendMessage(" ");
                        });

            } catch (Exception e) {
                player.sendMessage(prefix + "§cOcorreu um erro ao processar a solicitação.");
                e.printStackTrace();
            }

        }, failure -> {
            player.sendMessage(prefix + "§cNão foi possível encontrar este membro em nosso servidor.");
        });
    }

    public static void sendMessageConfirmation(Long memberId, Player player) {
        String guildID = ConfigManager.getConfig("Config").getString("Discord.IDServidor");
        Guild guild = jda.getGuildById(guildID);
        if (guild == null) {
            player.sendMessage("§cServidor do Discord não encontrado.");
            return;
        }
        String roleID = ConfigManager.getConfig("Config").getString("Discord.IDVinculado");
        Role role = guild.getRoleById(roleID);
        if (role == null) {
            player.sendMessage("§cCargo não encontrado no servidor.");
            return;
        }

        guild.retrieveMemberById(memberId).queue(member -> {
            Constructs.setMemberID(player, String.valueOf(memberId));
            Constructs.setMemberName(player, member.getUser().getName());
            Constructs.setDateLink(player, getDate());
            Constructs.setHoursLink(player, getHours());
            guild.addRoleToMember(member, role).queue();
            member.getUser().openPrivateChannel().queue(channel -> channel.sendMessageEmbeds(sendEmbedConfirmation(player, member)).queue());
            member.modifyNickname(player.getName()).queue();
            }, failure -> player.sendMessage("§cNão foi possível encontrar este membro no servidor."));
    }

    private static MessageEmbed sendEmbedCode(Player player, Member member, String code){

        EmbedBuilder builder = new EmbedBuilder();

        builder.setTitle("Pedido de vinculação");
        builder.setDescription(
                "*Olá __" + member.getUser().getName() + "__, você está recebendo uma solicitação de vinculação, vincular seu discord ao minecraft.*\n" +
                        "\n" +
                        "**Jogador(a): ** *" + player.getName() + "*\n" +
                        "\n" +
                        "*Caso seja você mesmo, volte ao minecraft e digite o seguinte comando:*\n" +
                        "**/vincular " + code + "**\n" +
                        "\n" +
                        "*Caso não reconheça esta ação, em 60 segundos será cancelada.*");
        builder.setColor(Color.CYAN);
        builder.setFooter("Simplex©");

        return builder.build();

    }

    private static MessageEmbed sendEmbedConfirmation(Player player, Member member){

        String dateFormat = getDate();
        String hoursFormat = getHours();

        EmbedBuilder builder = new EmbedBuilder();

        builder.setTitle("Conta vinculada");
        builder.setDescription("*Sua vinculação foi um sucesso!* \n" +
                "*Aqui está algumas informações da vinculação:*\n" +
                "\n" +
                "- **Jogador(a) vinculado:** *" + player.getName() + "*\n" +
                "- **Discord vinculado:** *" + member.getUser().getName() + "*\n" +
                "- **Data da vinculação:** *" + dateFormat + "*\n" +
                "- **Horário da vinculação:** *" + hoursFormat + "*\n" +
                "\n" +
                "*Agradecemos por vincular sua conta ao nosso servidor!*");
        builder.setColor(Color.CYAN);
        builder.setFooter("Simplex©");

        return builder.build();

    }

    public static String generateRandomCode() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        int codeLength = 10;
        Random random = new Random();
        StringBuilder codeBuilder = new StringBuilder();
        for (int i = 0; i < codeLength; i++) {
            int index = random.nextInt(characters.length());
            codeBuilder.append(characters.charAt(index));
        }
        return codeBuilder.toString();
    }

    private static String getDate(){
        LocalDateTime dateTime = LocalDateTime.now();
        int year = dateTime.getYear();
        int month = dateTime.getMonthValue();
        int day = dateTime.getDayOfMonth();
        return day + "/" + month + "/" + year;
    }

    private static String getHours(){
        LocalDateTime dateTime = LocalDateTime.now();
        int hour = dateTime.getHour();
        int minute = dateTime.getMinute();
        int second = dateTime.getSecond();
        return hour + ":" + minute + ":" + second;
    }

}
