package com.twanl.oregenreloaded;


import com.twanl.oregenreloaded.other.Metrics;
import com.twanl.oregenreloaded.other.Strings;
import com.twanl.oregenreloaded.other.UpdateChecker;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class OreGenReloaded extends JavaPlugin {

    private UpdateChecker checker;
    public final Logger log = Logger.getLogger("Minecraft");
    protected PluginDescriptionFile pdfFile = getDescription();
    private final String PluginVersionOn = ChatColor.GREEN + "(" + pdfFile.getVersion() + ")";
    private final String PluginVersionOff = ChatColor.RED + "(" + pdfFile.getVersion() + ")";



    public void onEnable() {
        this.checker = new UpdateChecker(this);
        if (this.checker.isConnected()) {
            if (this.checker.hasUpdate()) {
                getServer().getConsoleSender().sendMessage(Strings.green + "------------------------");
                getServer().getConsoleSender().sendMessage(Strings.red + "OreGenReloaded is outdated!");
                getServer().getConsoleSender().sendMessage(Strings.white + "Newest version: " + this.checker.getLatestVersion());
                getServer().getConsoleSender().sendMessage(Strings.white + "Your version: " + Strings.green + this.getDescription().getVersion());
                getServer().getConsoleSender().sendMessage("Please download the new version at https://www.spigotmc.org/resources/oregenreloaded.51121/");
                getServer().getConsoleSender().sendMessage(Strings.green + "------------------------");
            } else {
                getServer().getConsoleSender().sendMessage(Strings.green + "---------------------------------");
                getServer().getConsoleSender().sendMessage(Strings.green + "OreGenReloaded is up to date.");
                getServer().getConsoleSender().sendMessage(Strings.green + "---------------------------------");
            }
        }

        if (!getDataFolder().exists()) {
            getConfig().options().copyDefaults(true);
            getConfig().set("Worlds", addWorlds());
            saveConfig();
        }

        Metrics metrics = new Metrics(this);
        LOAD();
        Bukkit.getConsoleSender().sendMessage(Strings.logName + "Has been enabled " + PluginVersionOn);
    }



    public void onDisable() {
        Bukkit.getConsoleSender().sendMessage(Strings.logName + ChatColor.RED + "Has been disabled " + PluginVersionOff);
    }


    public List<String> addWorlds() {
        List<String> worldList = new ArrayList();
        for (World w : getServer().getWorlds()) {
            worldList.add(w.getName());
        } return worldList;
    }




    public void LOAD() {
        // Register listeners
        getServer().getPluginManager().registerEvents(new Listeners(), this);

        // Register commands
        Commands commands = new Commands();
        getCommand("og").setExecutor(commands);

        //LoadConfig
        getConfig().options().copyDefaults(true);
        saveConfig();


    }




}
