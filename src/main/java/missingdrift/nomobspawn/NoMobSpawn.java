package missingdrift.nomobspawn;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.List;

public final class NoMobSpawn extends JavaPlugin implements Listener {
    FileConfiguration config;
    File configFile;

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
        configFile = new File(getDataFolder(), "config.yml");
        config = YamlConfiguration.loadConfiguration(configFile);
        saveDefaultConfig();
        System.out.println("  _  _  ___  __  __  ___  ___ ___ ___  ___      ___  _ \n" +
                " | \\| |/ _ \\|  \\/  |/ _ \\| _ ) __| _ \\/_\\ \\    / / \\| |\n" +
                " | .` | (_) | |\\/| | (_) | _ \\__ \\  _/ _ \\ \\/\\/ /| .` |\n" +
                " |_|\\_|\\___/|_|  |_|\\___/|___/___/_|/_/ \\_\\_/\\_/ |_|\\_|\n" +
                "                                                       " +
                "" +
                "Version: 1.0\n" +
                "Author: MissingDrift\n");
    }

    @EventHandler
    public void onEntitySpawn(EntitySpawnEvent event) {
        String mobType = event.getEntity().getType().toString();
        String configOption = "disable-" + mobType.toLowerCase();
        if(config.contains(configOption)){
            if(config.getBoolean(configOption)){
                event.setCancelled(true);
            }
        }
    }
    @Override
    public void saveDefaultConfig() {
        if (!configFile.exists()) {
            configFile.getParentFile().mkdirs();
            saveResource("config.yml", false);

            //Crea la sezione per ogni tipo di mob presente in minecraft
            config.options().header("CONFIGURATION");
            for (EntityType type : EntityType.values()) {
                config.set("disable-" + type.name().toLowerCase(), false);
            }
            try {
                config.save(configFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}