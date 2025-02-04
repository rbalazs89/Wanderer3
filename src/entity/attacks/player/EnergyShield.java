package entity.attacks.player;

import entity.Entity;
import main.GamePanel;

import java.awt.*;

public class EnergyShield extends Spells {
    private int counter = 0;

    public EnergyShield(GamePanel gp) {
        super(gp);
        maxLife = life;
        image = setup("/spell/energyshield/energyshield");
        for (int i = 0; i < gp.spells.size(); i++) {
            Entity currentSpell = gp.spells.get(i);
            if(currentSpell instanceof EnergyShield){
                gp.spells.remove(currentSpell);
                removeBuff();
            }
        }
        setValues();
        gp.spells.add(this);
    }

    public void update(){
        counter ++;
        if(counter >= 60){
            life --;
            counter = 0;
            checkRemoveBuffIfSkillRemoved();
            if(life <= 0){
                removeBuff();
            }
        }
    }

    public void draw(Graphics2D g2){
        g2.drawImage(image, gp.player.screenX + 17, gp.player.screenY - 30, null);
    }

    public void checkRemoveBuffIfSkillRemoved(){
        boolean spellFound = false;
        for (int i = 0; i < gp.player.equippedSpellList.length; i++) {
            if(gp.player.equippedSpellList[i] != null){
                if(gp.player.equippedSpellList[i].uniqueSpellID == 11){
                    spellFound = true;
                }
            }
        }
        if(!spellFound){
            life = 0;
        }
    }

    public void removeBuff(){
        gp.player.energyShieldLevel = 0;
        gp.spells.remove(this);
    }

    public void addBuff(){
        gp.player.energyShieldLevel = level;
    }

    public void setValues(){
        level = gp.player.allSpellList.allPlayerAvailableSpells[11].currentPointsOnSpell;
        life = 60 * 5;
        addBuff();
    }
}
