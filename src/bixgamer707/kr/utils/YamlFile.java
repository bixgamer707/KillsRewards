package bixgamer707.kr.utils;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

import bixgamer707.kr.KillsRewards;

public class YamlFile extends YamlConfiguration {
 
    private final String fileName;
 
    private final KillsRewards plugin;
 
    private File file;
 
    private File folder;
 
    public YamlFile(KillsRewards plugin, String fileName, File folder) {
        this.folder = folder;
        this.plugin = plugin;
        this.fileName = fileName + (fileName.endsWith(".yml") ? "" : ".yml");
        createFile();
    }
 
    public YamlFile(KillsRewards plugin, String fileName) {
 
        this(plugin, fileName, plugin.getDataFolder());
    }
 
    private void createFile() {
        try {
            this.file = new File(this.folder, this.fileName);
            if (file.exists()) {
                load(this.file);
                save(this.file);
                return;
            }
            if (this.plugin.getResource(this.fileName) != null) {
                this.plugin.saveResource(this.fileName, false);
            } else {
                save(this.file);
            }
            load(this.file);
        } catch (InvalidConfigurationException | IOException e) {
            this.plugin.getLogger().log(Level.SEVERE, "Creation of Configuration '" + this.fileName + "' failed.", e);
        }
    }
 
    public void delete() {
        if (this.file.delete()) {
        } else {
            this.plugin.getLogger().log(Level.SEVERE, "Error on delete the file '" + this.fileName + "'.");
        }
    }
 
    public void save() {
 
        if (folder == null) {
            folder = this.plugin.getDataFolder();
        }
        File file = new File(folder, this.fileName);
        try {
            save(file);
        } catch (IOException e) {
            this.plugin.getLogger().log(Level.SEVERE, "Save of the file '" + this.fileName + "' failed.", e);
        }
    }
 
    public void reload() {
        File folder = this.plugin.getDataFolder();
        File file = new File(folder, this.fileName);
        try {
            load(file);
        } catch (IOException | InvalidConfigurationException e) {
            this.plugin.getLogger().log(Level.SEVERE, "Reload of the file '" + this.fileName + "' failed.", e);
        }
    }
}