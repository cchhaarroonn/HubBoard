package gay.charon.hubboard;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.*;

public final class HubBoard extends JavaPlugin implements Listener {

    public static HubBoard plugin;

    @Override
    public void onEnable() {
        System.out.println("[*] Starting HubBoard...");
        plugin = this;
        getConfig().options().copyDefaults(true);
        saveDefaultConfig();
        getServer().getPluginManager().registerEvents(this, this);
        System.out.println("[*] Started HubBoard...");
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e){
        Player player = e.getPlayer();
        String color = getConfig().getString("Scoreboard.Color");
        String name = getConfig().getString("Scoreboard.Name");
        String serverDomain = getConfig().getString("Scoreboard.Ip");
        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
            @Override
            public void run() {
                ScoreboardManager manager = Bukkit.getScoreboardManager();
                Scoreboard scoreboard = manager.getNewScoreboard();

                Objective objective = scoreboard.registerNewObjective("hubboard", "dummy");
                objective.setDisplayName(color + "§l" + name);
                objective.setDisplaySlot(DisplaySlot.SIDEBAR);

                Score spacing = objective.getScore("§7§m-------------------------");
                spacing.setScore(9);

                Score name = objective.getScore(color +"Name:");
                name.setScore(8);

                Score nameInfo = objective.getScore("§f" + player.getName());
                nameInfo.setScore(7);

                Score spacing2 = objective.getScore(" ");
                spacing2.setScore(6);

                Score online = objective.getScore(color + "Online:");
                online.setScore(5);

                Score onlineCount = objective.getScore("§f" + Bukkit.getServer().getOnlinePlayers().size());
                onlineCount.setScore(4);

                Score spacing3 = objective.getScore("  ");
                spacing3.setScore(3);

                Score domain = objective.getScore(color + serverDomain);
                domain.setScore(2);

                Score spacing4 = objective.getScore("§7§m------------------------");
                spacing4.setScore(1);

                player.setScoreboard(scoreboard);
            }
        }, 0L, 20L);
    }
}
