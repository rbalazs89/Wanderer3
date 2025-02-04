package entity.attacks.player;

import entity.Entity;
import main.GamePanel;

import java.awt.*;
public class FlameCloak extends Spells{
    private int counter = 0;
    public FlameCloak(GamePanel gp) {
        super(gp);
        maxLife = life;
        image = setup("/spell/firecloak/firecloak");
        gp.playSE(35);
        for (int i = 0; i < gp.spells.size(); i++) {
            Entity currentSpell = gp.spells.get(i);
            if(currentSpell instanceof FlameCloak){
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
                gp.spells.remove(this);
            }
        }
    }

    public void draw(Graphics2D g2){
        g2.drawImage(image, gp.player.screenX, gp.player.screenY, null);
    }

    public void checkRemoveBuffIfSkillRemoved(){
        boolean spellFound = false;
        for (int i = 0; i < gp.player.equippedSpellList.length; i++) {
            if(gp.player.equippedSpellList[i] != null){
                if(gp.player.equippedSpellList[i].uniqueSpellID == 2){
                    spellFound = true;
                }
            }
        }
        if(!spellFound){
            life = 0;
        }
    }

    public void removeBuff(){
        gp.player.lifeRegenPointFromSkill = 0;
        gp.player.fireResistFromSkill = 0;
        gp.player.refreshPlayerStatsNoItems();
    }

    public void setValues(){
        level = gp.player.allSpellList.allPlayerAvailableSpells[2].currentPointsOnSpell;
        gp.player.mana -= gp.dataBase1.getRequiredManaForSpell(2, level);

        int[] cloakData = gp.player.allSpellList.getFlameCloakData(level);
        gp.player.lifeRegenPointFromSkill = cloakData[0];
        gp.player.fireResistFromSkill = cloakData[1];
        life = cloakData[2];
    }
}
