package me.ry4nn00b.modes.SQLite;

import me.ry4nn00b.modes.Main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Constructs {

    public static String prefix = "[RStore-Modos] ";
    public static Main plugin;

    public static void CreateTable() {

        PreparedStatement stm;

        try {

            stm = SQLiteConnect.con.prepareStatement("CREATE TABLE IF NOT EXISTS `modesDatabase`(Player_UUID VARCHAR(36), Player_Name VARCHAR(24), Modos TEXT, ActiveModes TEXT);");
            stm.executeUpdate();
            Bukkit.getConsoleSender().sendMessage(prefix + "A tabela do SQLite foi criada com sucesso!");

            stm.close();

        } catch (SQLException e) {

            e.printStackTrace();
            Bukkit.getConsoleSender().sendMessage(prefix + "Não foi possível criar a tabela no SQLite, desligando o plugin!");
            plugin.getPluginLoader().disablePlugin(plugin);

        }

    }

    //Get or Add Player-------------------------------------------------------------------------------------------------

    public static boolean hasPlayerTable(Player player) {

        try {

            String hasPlayer = "SELECT `Player_UUID` FROM `modesDatabase` WHERE `Player_UUID` = '" + player.getUniqueId().toString() + "'";

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

            String addPlayer = "INSERT INTO `modesDatabase`(`Player_UUID`, `Player_Name`, `Modos`, `ActiveModes`) VALUES ('" + player.getUniqueId().toString() + "','" + player.getName() + "', 'Nenhum', '0')";

            Connection con = SQLiteConnect.con;
            PreparedStatement statement = con.prepareStatement(addPlayer);
            statement.executeUpdate();

        } catch (Exception e) {

            e.printStackTrace();

        }

    }

    //Modes_____________________________________________________________________________________________________________

    public static String getPlayerModo(Player player) {

        try {

            String getPlayerModos = "SELECT `Modos` FROM `modesDatabase` WHERE `Player_UUID` = '" + player.getUniqueId().toString() + "'";

            Connection con = SQLiteConnect.con;
            PreparedStatement statement = con.prepareStatement(getPlayerModos);
            ResultSet resultSet = statement.executeQuery();

            return resultSet.getString("Modos");

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;

    }

    public static void setPlayerModo(Player player, String modo) {

        try {

            String setPlayerModo = "UPDATE `modesDatabase` SET `Modos`='" + modo + "' WHERE `Player_UUID`='" + player.getUniqueId().toString() + "'";

            Connection con = SQLiteConnect.con;
            PreparedStatement statement = con.prepareStatement(setPlayerModo);
            statement.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //ActiveModes_______________________________________________________________________________________________________
    public static String getActiveModes(Player player) {

        try {

            String getPlayerModos = "SELECT `ActiveModes` FROM `modesDatabase` WHERE `Player_UUID` = '" + player.getUniqueId().toString() + "'";

            Connection con = SQLiteConnect.con;
            PreparedStatement statement = con.prepareStatement(getPlayerModos);
            ResultSet resultSet = statement.executeQuery();

            return resultSet.getString("ActiveModes");

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;

    }

    public static void setActiveModes(Player player, String modes) {

        try {

            String setPlayerModo = "UPDATE `modesDatabase` SET `ActiveModes`='" + modes + "' WHERE `Player_UUID`='" + player.getUniqueId().toString() + "'";

            Connection con = SQLiteConnect.con;
            PreparedStatement statement = con.prepareStatement(setPlayerModo);
            statement.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}