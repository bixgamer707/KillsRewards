package bixgamer707.kr.utils;

import java.io.File;

import org.bukkit.configuration.file.YamlConfiguration;

import bixgamer707.kr.KillsRewards;

public class Files extends YamlConfiguration {

    private File file = null;
    private KillsRewards main;

    public Files(String fileName){
    	main = KillsRewards.getPlugin(KillsRewards.class);
        if(file == null){
            file = new File(main.getDataFolder(), fileName);
        }

        if(!file.exists()){
            saveDefaultFile();
            reloadFile();
        }
        reloadFile();

    }

    public void reloadFile(){
        try{
            super.load(file);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void saveFile(){
        try{
            super.save(file);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void saveDefaultFile(){
        main.saveResource(file.getName(), false);
    }
}
