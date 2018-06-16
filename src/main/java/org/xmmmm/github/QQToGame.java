package org.xmmmm.github;

import fr.moribus.imageonmap.image.Renderer;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.map.MapCanvas;
import org.bukkit.map.MapRenderer;
import org.bukkit.map.MapView;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class QQToGame extends JavaPlugin implements Listener,CommandExecutor{

    public static Plugin plugin=null;
    FileConfiguration configuration=null;

    List<String> groups =new ArrayList<>();
    List<String> friends=new ArrayList<>();

    SmartQQ smartQQ=null;

    @Override
    public void onEnable() {
        plugin=this;

        getServer().getPluginManager().registerEvents(this,this);
        getCommand("loginqq").setExecutor(this);
        setConfig();
        groups=configuration.getStringList("groups");
        friends=configuration.getStringList("friends");
        smartQQ=new SmartQQ();
        try {
            smartQQ.test(plugin.getDataFolder().getPath()+"code.png");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        try {
//            smartQQ.instanceSmartQQ(this.getDataFolder().getPath()+"code.png");
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player))return false;
        smartQQ=new SmartQQ();
        try {
            smartQQ.test(plugin.getDataFolder().getPath()+"code.png");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        MapView mapView= Bukkit.createMap(((Player) sender).getWorld());
        mapView.getRenderers().forEach(mapView::removeRenderer);
        try {
            BufferedImage image = ImageIO.read(new File(this.getDataFolder().getPath()+"code.png"));

            int width = image.getWidth();
            int height = image.getHeight();
            //压缩倍率
            float resizeTimes=0.4f;

            int toWidth = (int) (width * resizeTimes);
            int toHeight = (int) (height * resizeTimes);

            BufferedImage result = new BufferedImage(toWidth, toHeight,
                    BufferedImage.TYPE_INT_RGB);
            result.getGraphics().drawImage(
                    image.getScaledInstance(toWidth, toHeight,
                            java.awt.Image.SCALE_SMOOTH), 0, 0, null);

            System.out.println();
            mapView.addRenderer(new MapRenderer() {
                private boolean rendered;

                public void render(MapView map, MapCanvas canvas, Player player) {
                    if (!this.rendered) {
                        canvas.drawImage(0, 0, result);
                        this.rendered = true;
                    }

                }
            });
            ItemStack itemStack = new ItemStack(Material.MAP,1,mapView.getId());
            ((Player) sender).getInventory().addItem(itemStack);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }



    @EventHandler
    public void onPlayerTalk(PlayerChatEvent event) throws IOException {
        StringBuilder stringBuilder=new StringBuilder();
        if(event.getPlayer().getCustomName()==null)
        {
            if (event.getPlayer().getDisplayName()==null){
                stringBuilder.append(event.getPlayer().getDisplayName());
            }else {
                stringBuilder.append(event.getPlayer().getName());
            }
        }else {
            stringBuilder.append(event.getPlayer().getCustomName());
        }
        stringBuilder.append(event.getMessage());
        String message=stringBuilder.toString();
        Logger.info(message);
        sendFriends(message);
        sendGroups(message);

    }

    public void sendFriends(String msg) throws IOException {
        if(!smartQQ.isCreated()){
            Logger.info("没有登陆qq，无法发送信息");
            return;
        }
        for (String friendname:
             friends) {
            smartQQ.sendMessageToFriend(msg,friendname);
        }
    }

    public void sendGroups(String msg) throws IOException {
        if(!smartQQ.isCreated()){
            Logger.info("没有登陆qq，无法发送信息");
            return;
        }
        for (String groupName:
                groups) {
            smartQQ.sendMessageToGroup(msg,groupName);
        }
    }


    public void setConfig(){
        File file = new File(this.getDataFolder(),"config.yml");
        if(!file.exists()){
            this.saveDefaultConfig();
        }
        file = new File(this.getDataFolder(),"config.yml");
        configuration= YamlConfiguration.loadConfiguration(file);
    }

    @Override
    public void onDisable() {
        try {
            smartQQ.closeClient();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
