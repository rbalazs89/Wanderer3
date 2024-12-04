package main;

import java.io.*;

public class Config {
    GamePanel gp;

    public Config(GamePanel gp){
        this.gp = gp;
    }

    public void saveConfig() {

        try {
            BufferedWriter bw = new BufferedWriter( new FileWriter("config.txt"));

            bw.write(String.valueOf(gp.music.volumeScale));
            bw.newLine();

            bw.write(String.valueOf(gp.se.volumeScale));
            bw.newLine();

            if(gp.visibleHitBox){
                bw.write("respawnon");
            } else {
                bw.write("repsawnoff");
            }
            bw.newLine();

            if(gp.visibleExpValue) {
                bw.write("visibleexpon");
            } else {
                bw.write("visibleexpoff");
            }
            bw.newLine();

            if(gp.visibleDamageNumbersDoneToYou){
                bw.write("visibledamagetoyouon");
            } else {
                bw.write("visibledamagetoyouoff");
            }
            bw.newLine();

            if(gp.visibleDamageNumbersDoneByYou){
                bw.write("damagenumberson");
            } else {
                bw.write("damagenumbersoff");
            }

            bw.close();

        } catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    public void loadConfig(){
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader("config.txt"));
            String s = br.readLine();
            gp.music.volumeScale = Integer.parseInt(s);

            s = br.readLine();
            gp.se.volumeScale = Integer.parseInt(s);

            s = br.readLine();
            if(s.equals("respawnoff")){
                gp.visibleHitBox = false;
            } else if(s.equals("respawnon"))
                gp.visibleHitBox = true;

            s = br.readLine();
            if(s.equals("visibleexpon")){
                gp.visibleExpValue = true;
            } else if(s.equals("visibleexpoff"))
                gp.visibleExpValue = false;

            s = br.readLine();
            if(s.equals("visibledamagetoyouon")){
                gp.visibleDamageNumbersDoneToYou = true;
            } else if(s.equals("visibledamagetoyouoff"))
                gp.visibleDamageNumbersDoneToYou = false;

            s = br.readLine();
            if(s.equals("damagenumberson")){
                gp.visibleDamageNumbersDoneByYou = true;
            } else if(s.equals("damagenumbersoff"))
                gp.visibleDamageNumbersDoneByYou = false;


            br.close();
        } catch (FileNotFoundException e){
            throw new RuntimeException(e);
        } catch (IOException e){
            throw new RuntimeException(e);
        }
    }
}
