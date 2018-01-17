package com.twanl.oregenreloaded;


import com.twanl.oregenreloaded.other.Strings;
import com.twanl.oregenreloaded.other.UpdateChecker;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFromToEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.List;
import java.util.Random;

public class Listeners implements Listener {


    private OreGenReloaded plugin = OreGenReloaded.getPlugin(OreGenReloaded.class);

    @EventHandler
    public void onFromTo(BlockFromToEvent event) {

        int id = event.getBlock().getTypeId();
        if ((id >= 8) && (id <= 11)) {
            Block b = event.getToBlock();
            int toid = b.getTypeId();
            if ((toid == 0) &&
                    (generatesCobble(id, b))) {
                List<String> worlds = this.plugin.getConfig().getStringList("Worlds");
                if (worlds.contains(event.getBlock().getLocation().getWorld().getName())) {
                    Random pick = new Random();
                    int chance = 0;
                    for (int counter = 1; counter <= 1; counter++) {
                        chance = 1 + pick.nextInt(100);
                    }

                    double coal = this.plugin.getConfig().getDouble("Chances.Coal");
                    double iron = this.plugin.getConfig().getDouble("Chances.Iron");
                    double gold = this.plugin.getConfig().getDouble("Chances.Gold");
                    double redstone = this.plugin.getConfig().getDouble("Chances.Redstone");
                    double lapis = this.plugin.getConfig().getDouble("Chances.Lapis");
                    double emerald = this.plugin.getConfig().getDouble("Chances.Emerald");
                    double diamond = this.plugin.getConfig().getDouble("Chances.Diamond");

                    if ((chance > 0 && chance <= coal)) {
                        b.setType(Material.COAL_ORE);
                    }
                    if ((chance > coal && chance <= iron)) {
                        b.setType(Material.IRON_ORE);
                    }
                    if ((chance > iron && chance <= gold)) {
                        b.setType(Material.GOLD_ORE);
                    }
                    if ((chance > gold && chance <= redstone)) {
                        b.setType(Material.REDSTONE_ORE);
                    }
                    if ((chance > redstone && chance <= lapis)) {
                        b.setType(Material.LAPIS_ORE);
                    }
                    if ((chance > lapis && chance <= emerald)) {
                        b.setType(Material.EMERALD_ORE);
                    }
                    if ((chance > emerald && chance <= diamond)) {
                        b.setType(Material.DIAMOND_ORE);
                    }
                    if ((chance > diamond && chance <= 100)) {
                        if (!plugin.getConfig().getBoolean("use_stone")) {
                            b.setType(Material.COBBLESTONE);
                        } else if (plugin.getConfig().getBoolean("use_stone")) {
                            b.setType(Material.STONE);
                        }
                    }

                }
            }
        }
    }

    private final BlockFace[] faces = { BlockFace.SELF, BlockFace.UP, BlockFace.DOWN, BlockFace.NORTH, BlockFace.EAST, BlockFace.SOUTH, BlockFace.WEST };

    public boolean generatesCobble(int id, Block b) {
        int mirrorID1 = (id == 8) || (id == 9) ? 10 : 8;
        int mirrorID2 = (id == 8) || (id == 9) ? 11 : 9;
        BlockFace[] arrayOfBlockFace;
        int j = (arrayOfBlockFace = this.faces).length;
        for (int i = 0; i < j; i++) {
            BlockFace face = arrayOfBlockFace[i];
            Block r = b.getRelative(face, 1);
            if ((r.getTypeId() == mirrorID1) || (r.getTypeId() == mirrorID2)) {
                return true;
            }
        } return false;
    }



    @EventHandler
    public void onJoin(PlayerJoinEvent event) {


        // let the player know if there is an update for the plugin.
        if (plugin.getConfig().getBoolean("check_for_updates")) {
            Player p = event.getPlayer();


            plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {

                public UpdateChecker checker;
                public void run() {
                    this.checker = new UpdateChecker(plugin);

                    if (p.hasPermission("og.update")) {
                        if (this.checker.isConnected()) {
                            if (this.checker.hasUpdate()) {
                                p.sendMessage(Strings.DgrayBIS + "------------------------\n" +
                                        Strings.red + "OreGenReloaded is outdated!\n" +
                                        Strings.gray + "Newest version: " + Strings.white + this.checker.getLatestVersion() + "\n" +
                                        Strings.gray + "Your version: " + Strings.white + plugin.getDescription().getVersion() + "\n" +
                                        Strings.DgrayBIS + "------------------------");
                            } else {
                                p.sendMessage(Strings.DgrayBIS + "------------------------\n" +
                                        Strings.green + "OreGenReloaded is up to date.\n" +
                                        Strings.DgrayBIS + "------------------------");
                            }
                        }
                    } else {
                        return;
                    }
                }
            }, 20);
        }
    }


}
