package main;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import java.net.URL;

public class Sound {

    Clip clip;
    URL[] soundURL = new URL[100];
    FloatControl fc;
    public int volumeScale = 8; //starting volume
    public float volume;

    //
    public int fadeOutCounter = 0;
    public int fadeOutOriginalVolumeScale;

    public Sound () {
        soundURL[0] = getClass().getResource("/sound/keypickup.wav");
        soundURL[1] = getClass().getResource("/sound/metalpickup.wav");
        soundURL[2] = getClass().getResource("/sound/forestambient.wav");
        soundURL[3] = getClass().getResource("/sound/ui/switch_option.wav");
        soundURL[4] = getClass().getResource("/sound/ui/accept_option.wav");
        soundURL[5] = getClass().getResource("/sound/sword/swordhitair/swordHitAir_1.wav");
        soundURL[6] = getClass().getResource("/sound/sword/swordhitair/swordHitAir_2.wav");
        soundURL[7] = getClass().getResource("/sound/sword/swordhitair/swordHitAir_3.wav");
        soundURL[8] = getClass().getResource("/sound/sword/swordhitair/swordHitAir_4.wav");
        soundURL[9] = getClass().getResource("/sound/sword/swordhitflesh/swordHitFlesh_1.wav");
        soundURL[10] = getClass().getResource("/sound/sword/swordhitflesh/swordHitFlesh_2.wav");
        soundURL[11] = getClass().getResource("/sound/sword/swordhitflesh/swordHitFlesh_3.wav");
        soundURL[12] = getClass().getResource("/sound/sword/swordhitflesh/swordHitFlesh_4.wav");
        soundURL[13] = getClass().getResource("/sound/sword/swordhitbarrel/swordHitBarrel_1.wav");
        soundURL[14] = getClass().getResource("/sound/sword/swordhitbarrel/swordHitBarrel_2.wav");
        soundURL[15] = getClass().getResource("/sound/movement/dashing.wav");
        soundURL[16] = getClass().getResource("/sound/death/slimedeath.wav");
        soundURL[17] = getClass().getResource("/sound/attacks/fireball.wav");
        soundURL[18] = getClass().getResource("/sound/ui/statbutton.wav");
        soundURL[19] = getClass().getResource("/sound/lvlup.wav");
        soundURL[20] = getClass().getResource("/sound/inventory/opennpcshop.wav");
        soundURL[21] = getClass().getResource("/sound/inventory/buyitemnpc.wav");
        soundURL[22] = getClass().getResource("/sound/inventory/sellitemnpc.wav");
        soundURL[23] = getClass().getResource("/sound/inventory/notenoughtgoldtobuynpc.wav");
        soundURL[24] = getClass().getResource("/sound/inventory/chestopen.wav");
        soundURL[25] = getClass().getResource("/sound/attacks/imageshatter.wav");
        soundURL[26] = getClass().getResource("/sound/attacks/teleport.wav");
        soundURL[27] = getClass().getResource("/sound/attacks/lightningattack.wav");
        soundURL[28] = getClass().getResource("/sound/attacks/nova.wav");
        soundURL[29] = getClass().getResource("/sound/attacks/novahit.wav");
        soundURL[30] = getClass().getResource("/sound/ui/menuback2.wav");
        soundURL[31] = getClass().getResource("/sound/ui/testsound.wav");
        soundURL[32] = getClass().getResource("/sound/interact/lever.wav");
        soundURL[33] = getClass().getResource("/sound/attacks/melee/club.wav");
        soundURL[34] = getClass().getResource("/sound/ui/spellslot.wav");
        soundURL[35] = getClass().getResource("/sound/attacks/flamecloak.wav");
        soundURL[36] = getClass().getResource("/sound/attacks/icebolt.wav");
        soundURL[37] = getClass().getResource("/sound/attacks/frozenorb.wav");
        soundURL[38] = getClass().getResource("/sound/attacks/lightning.wav");
        soundURL[39] = getClass().getResource("/sound/attacks/damageabsorbed.wav");
        soundURL[40] = getClass().getResource("/sound/attacks/totemsummon.wav");
        soundURL[41] = getClass().getResource("/sound/attacks/pikalightning.wav");
        soundURL[42] = getClass().getResource("/sound/attacks/greenbunny.wav");
        soundURL[43] = getClass().getResource("/sound/findenemyunique/angrybunny.wav");
        soundURL[44] = getClass().getResource("/sound/objects/talentbookpickup.wav");
        soundURL[45] = getClass().getResource("/sound/hurt.wav");
        soundURL[46] = getClass().getResource("/sound/death/rabbitdeath.wav");
        soundURL[47] = getClass().getResource("/sound/momodrink.wav");
        soundURL[48] = getClass().getResource("/sound/refillmomo.wav");
        soundURL[49] = getClass().getResource("/sound/refillmomo.wav");
        soundURL[50] = getClass().getResource("/sound/sword/swordbig.wav");
        soundURL[51] = getClass().getResource("/sound/findenemyunique/magelaugh.wav");
        soundURL[52] = getClass().getResource("/sound/death/wolf.wav");
        soundURL[53] = getClass().getResource("/sound/attacks/melee/wolfattack.wav");
        soundURL[54] = getClass().getResource("/sound/death/magedeath.wav");
        soundURL[55] = getClass().getResource("/sound/findenemyunique/growl.wav");
        soundURL[56] = getClass().getResource("/sound/interact/door.wav");
        soundURL[57] = getClass().getResource("/sound/interact/blessing.wav");
        soundURL[58] = getClass().getResource("/sound/music/cathedral.wav");
        soundURL[59] = getClass().getResource("/sound/music/shop.wav");
        soundURL[60] = getClass().getResource("/sound/interact/stairs.wav");
        soundURL[61] = getClass().getResource("/sound/death/skeletondeath.wav");
        soundURL[62] = getClass().getResource("/sound/attacks/melee/swordnormal.wav");
        soundURL[63] = getClass().getResource("/sound/attacks/melee/swordepic.wav");
        soundURL[64] = getClass().getResource("/sound/findenemyunique/skeletonlaugh.wav");
        soundURL[65] = getClass().getResource("/sound/attacks/melee/spear.wav");
        soundURL[66] = getClass().getResource("/sound/music/bossa1.wav");
        soundURL[67] = getClass().getResource("/sound/music/endingsong.wav");
        soundURL[68] = getClass().getResource("/sound/attacks/firedragonbite.wav");
        soundURL[69] = getClass().getResource("/sound/attacks/shadowprojectile.wav");
        soundURL[70] = getClass().getResource("/sound/attacks/shadowdagger_precast.wav");
        soundURL[71] = getClass().getResource("/sound/attacks/shadowdagger.wav");
        soundURL[72] = getClass().getResource("/sound/attacks/shadowscream.wav");
        soundURL[73] = getClass().getResource("/sound/respawn.wav");
        soundURL[74] = getClass().getResource("/sound/music/titlemusic.wav");
        soundURL[75] = getClass().getResource("/sound/music/caveambient.wav");
        soundURL[76] = getClass().getResource("/sound/music/wolvesambient.wav");
        soundURL[77] = getClass().getResource("/sound/music/villagechill.wav");
        soundURL[78] = getClass().getResource("/sound/inventory/placeItem.wav");
        soundURL[79] = getClass().getResource("/sound/inventory/grabItem.wav");
        soundURL[80] = getClass().getResource("/sound/npc/happyturtle.wav");
        soundURL[81] = getClass().getResource("/sound/music/icelandkindergarden.wav");
        soundURL[82] = getClass().getResource("/sound/attacks/arrowsound.wav");
        soundURL[83] = getClass().getResource("/sound/npc/butterflyhealing.wav");
        soundURL[84] = getClass().getResource("/sound/npc/slipandfall.wav");
        soundURL[85] = getClass().getResource("/sound/npc/childcry1.wav");
        soundURL[86] = getClass().getResource("/sound/npc/childcry2.wav");
        soundURL[87] = getClass().getResource("/sound/npc/childcry3.wav");
        soundURL[88] = getClass().getResource("/sound/objects/eating.wav");
        soundURL[89] = getClass().getResource("/sound/objects/eating.wav"); // TODO fleet mushroom book a1 enc
        soundURL[90] = getClass().getResource("/sound/book/statueenc.wav"); // statue in middle of the town, google cloud -> UK eng -> male B
        soundURL[91] = getClass().getResource("/sound/objects/eating.wav"); // TODO turtle enc
        soundURL[92] = getClass().getResource("/sound/objects/eating.wav"); // TODO TBD sea kingdom
        soundURL[93] = getClass().getResource("/sound/objects/eating.wav"); // TODO captain letter
        soundURL[94] = getClass().getResource("/sound/dialogue/bro/bro_cellar_dialogue.wav"); // cellar bro, hailuo.ai/audio -> male debater
    }

    public void setFile(int i){
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
            clip = AudioSystem.getClip();
            clip.open(ais);
            fc = (FloatControl)clip.getControl(FloatControl.Type.MASTER_GAIN);
            checkVolume();

        } catch (Exception ignored){

        }
    }


    public void play() {
        if (clip != null) {
            clip.start();
        }
    }

    /*
    public void play() {
        if (clip != null) {
            stop(); // Stop previous sound effect before playing a new one
            clip.setFramePosition(0);
            clip.start();
            activeClips.add(clip);  // Keep track of this clip
        }
    }*/

    public void loop() {
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }


    public void stop(){
        if (clip != null){
            clip.stop();
        }
    }

    /*public void stop() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
            clip.flush();
            clip.setFramePosition(0);
        }
    }*/

    public void checkVolume() {
        if(volumeScale < 1){
            volumeScale = 1;
        }
        switch (volumeScale) {
            case 1 -> volume = -80f;
            case 2 -> volume = -55f;
            case 3 -> volume = -41f;
            case 4 -> volume = -30f;
            case 5 -> volume = -22f;
            case 6 -> volume = -16f;
            case 7 -> volume = -10f;
            case 8 -> volume = -5f;
            case 9 -> volume = -1f;
            case 10 -> volume = 3f;
            case 11 -> volume = 6f;
        }
        fc.setValue(volume);
    }

    public void fadeOutMusic() {
        fadeOutCounter++;
        if(fadeOutCounter == 1){
            fadeOutOriginalVolumeScale = volumeScale;
        }
        if (clip != null && clip.isRunning()) {
            if (fadeOutCounter > 59) {
                volumeScale --;
                checkVolume();
                fadeOutCounter = 1;
            }
        }
    }

    public void fadeOutMusicChangeBack(){
        volumeScale = fadeOutOriginalVolumeScale;
        checkVolume();
        stop();
        fadeOutCounter = 0;
    }
}
