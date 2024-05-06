package me.ry4nn00b.simplexdiscord.SQLite;

import me.ry4nn00b.simplexdiscord.Main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Constructs {

    public static String prefix = Main.prefix;
    public static Main plugin;

    public static void CreateTable() {

        PreparedStatement stm;

        try {

            stm = SQLiteConnect.con.prepareStatement("CREATE TABLE IF NOT EXISTS `discordDatabase`(Player_UUID VARCHAR(36), Player_Name VARCHAR(24), MemberID TEXT, MemberName TEXT, MemberCode TEXT, Date TEXT, Hours TEXT);");
            stm.executeUpdate();
            Bukkit.getConsoleSender().sendMessage(prefix + "§aA tabela do SQLite foi criada com sucesso!");

            stm.close();

        } catch (SQLException e) {

            e.printStackTrace();
            Bukkit.getConsoleSender().sendMessage(prefix + "§cNão foi possível criar a tabela no SQLite, desligando o plugin!");
            plugin.getPluginLoader().disablePlugin(plugin);

        }

    }

    public static boolean hasPlayerTable(Player player) {

        try {

            String hasPlayer = "SELECT `Player_UUID` FROM `discordDatabase` WHERE `Player_UUID` = '" + player.getUniqueId().toString() + "'";

            Connection con = SQLiteConnect.con;
            PreparedStatement statement = con.prepareStatement(hasPlayer);
            ResultSet resultSet = statement.executeQuery();

            boolean result = resultSet.next();

            return result;

        } catch (Exception e) {

            e.printStackTrace();

        }

        return false;

    }

    public static void addPlayerTable(Player player) {

        try {

            String addPlayer = "INSERT INTO `discordDatabase`(`Player_UUID`, `Player_Name`, `MemberID`, `MemberName`, `MemberCode`, `Date`, `Hours`) VALUES ('" + player.getUniqueId() + "','" + player.getName() + "', 'Nenhum', 'Nenhuma', '0', '0', '0')";

            Connection con = SQLiteConnect.con;
            PreparedStatement statement = con.prepareStatement(addPlayer);
            statement.executeUpdate();

        } catch (Exception e) {

            e.printStackTrace();

        }

    }

    public static String getMemberID(Player player) {

        try {

            String getMemberID = "SELECT `MemberID` FROM `discordDatabase` WHERE `Player_UUID` = '" + player.getUniqueId().toString() + "'";

            Connection con = SQLiteConnect.con;
            PreparedStatement statement = con.prepareStatement(getMemberID);
            ResultSet resultSet = statement.executeQuery();

            return resultSet.getString("MemberID");

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;

    }

    public static void setMemberID(Player player, String memberID) {

        try {

            String setMemberID = "UPDATE `discordDatabase` SET `MemberID`='" + memberID + "' WHERE `Player_UUID`='" + player.getUniqueId().toString() + "'";

            Connection con = SQLiteConnect.con;
            PreparedStatement statement = con.prepareStatement(setMemberID);
            statement.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static String getMemberName(Player player) {

        try {

            String getMemberName = "SELECT `MemberName` FROM `discordDatabase` WHERE `Player_UUID` = '" + player.getUniqueId().toString() + "'";

            Connection con = SQLiteConnect.con;
            PreparedStatement statement = con.prepareStatement(getMemberName);
            ResultSet resultSet = statement.executeQuery();

            return resultSet.getString("MemberName");

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;

    }

    public static void setMemberName(Player player, String memberName) {

        try {

            String setMemberName = "UPDATE `discordDatabase` SET `MemberName`='" + memberName + "' WHERE `Player_UUID`='" + player.getUniqueId().toString() + "'";

            Connection con = SQLiteConnect.con;
            PreparedStatement statement = con.prepareStatement(setMemberName);
            statement.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static String getDateLink(Player player) {

        try {

            String getDateLink = "SELECT `Date` FROM `discordDatabase` WHERE `Player_UUID` = '" + player.getUniqueId().toString() + "'";

            Connection con = SQLiteConnect.con;
            PreparedStatement statement = con.prepareStatement(getDateLink);
            ResultSet resultSet = statement.executeQuery();

            return resultSet.getString("Date");

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;

    }

    public static void setDateLink(Player player, String date) {

        try {

            String setDateLink = "UPDATE `discordDatabase` SET `Date`='" + date + "' WHERE `Player_UUID`='" + player.getUniqueId().toString() + "'";

            Connection con = SQLiteConnect.con;
            PreparedStatement statement = con.prepareStatement(setDateLink);
            statement.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static String getHoursLink(Player player) {

        try {

            String getHoursLink = "SELECT `Hours` FROM `discordDatabase` WHERE `Player_UUID` = '" + player.getUniqueId().toString() + "'";

            Connection con = SQLiteConnect.con;
            PreparedStatement statement = con.prepareStatement(getHoursLink);
            ResultSet resultSet = statement.executeQuery();

            return resultSet.getString("Hours");

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;

    }

    public static void setHoursLink(Player player, String hours) {

        try {

            String setDateLink = "UPDATE `discordDatabase` SET `Hours`='" + hours + "' WHERE `Player_UUID`='" + player.getUniqueId().toString() + "'";

            Connection con = SQLiteConnect.con;
            PreparedStatement statement = con.prepareStatement(setDateLink);
            statement.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}