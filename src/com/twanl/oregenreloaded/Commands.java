package com.twanl.oregenreloaded;


import com.twanl.oregenreloaded.other.Strings;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;

public class Commands implements CommandExecutor {

    private OreGenReloaded plugin = OreGenReloaded.getPlugin(OreGenReloaded.class);
    public File cF;


    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player p = (Player) sender;

        if (!(sender instanceof Player)) {
            sender.sendMessage(Strings.logName + ChatColor.RED + "Only a player can execute commands!");
            return true;
        }


        if (label.equalsIgnoreCase("og")) {
            if (args.length == 0) {
                if (p.hasPermission("og.og") && p.isOp()) {

                    p.sendMessage(Strings.DgrayBIS + "-----------------------------\n" +
                            Strings.goldB + "          OreGenReloaded\n" +
                            Strings.goldB + "          Version: " + plugin.getDescription().getVersion() + "\n" +
                            Strings.DgrayBIS + "-----------------------------\n" +
                            Strings.DgrayB + "» " + Strings.green + "/og reload - Reloads the config file." +
                            Strings.DgrayBIS + "-----------------------------\n");
                } else {
                    p.sendMessage(Strings.noPerm);
                }
                return true;

            } else if (args[0].equalsIgnoreCase("reload")) {
                if (p.hasPermission("og.reload") && p.isOp()) {

                    cF = new File(plugin.getDataFolder(), "config.yml");

                    if (!cF.exists()) {
                        p.sendMessage(Strings.red + "config.yml file is not found!");

                        try {
                            cF.createNewFile();
                            plugin.saveConfig();
                            p.sendMessage(Strings.green + "Created succsesfully a new config.yml file!");
                        } catch (IOException e) {
                            e.printStackTrace();
                            p.sendMessage(Strings.red + "failed to create a new config.yml file!");
                        }

                    } else {
                        plugin.reloadConfig();
                        p.sendMessage(Strings.green + "OreGenerator succsesfully reloaded!");
                    }


                } else {
                    p.sendMessage(Strings.noPerm);
                }
            }
            return true;

        }
        return true;
    }
}
