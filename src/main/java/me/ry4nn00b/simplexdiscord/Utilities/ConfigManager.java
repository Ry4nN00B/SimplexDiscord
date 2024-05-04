package me.ry4nn00b.dokkanranks.Utilities;

import me.ry4nn00b.dokkanranks.Main;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ConfigManager {
    private static Map<String, FileConfiguration> configs = new HashMap<>();

    private static File folder;

    public ConfigManager(File folder) {
        ConfigManager.folder = folder;
    }

    public static void loadConfig(String fileName) {
        File configFile = new File(Main.plugin.getDataFolder(), fileName + ".yml");
        if (!configFile.exists()) {
            configFile.getParentFile().mkdirs();
            Main.plugin.saveResource(fileName + ".yml", false);
            try {
                configFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Main.plugin.saveResource(fileName + ".yml", false);
        YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(configFile);
        configs.put(fileName, yamlConfiguration);
    }

    public static FileConfiguration getConfig(String fileName) {
        if (!configs.containsKey(fileName))
            loadConfig(fileName);
        return configs.get(fileName);
    }

    public static void saveConfig(String fileName) {
        FileConfiguration config = getConfig(fileName);
        File configFile = new File(Main.plugin.getDataFolder(), fileName + ".yml");
        if (config == null || configFile == null)
            return;
        try {
            config.save(configFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void reloadConfig(String fileName) {
        File configFile = new File(Main.plugin.getDataFolder(), fileName + ".yml");
        YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(configFile);
        configs.put(fileName, yamlConfiguration);
    }
}

