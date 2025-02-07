package main;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class MouseHandler implements MouseListener, MouseMotionListener {
    GamePanel gp;
    public int mouseX;
    public int mouseY;
    public boolean mouseClickSentInventory = false;
    public MouseHandler(GamePanel gp){
        this.gp = gp;
        gp.addMouseMotionListener(this);
    }
    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        int centerX = gp.getWidth() / 2;
        int centerY = gp.getHeight() / 2;
        mouseX = e.getX();
        mouseY = e.getY();

        System.out.println(e.getX() + ", " + e.getY());

        int button = e.getButton();

        if (button == MouseEvent.BUTTON1) {
            if(gp.gameState == gp.characterState){
                if(gp.player.unSpentStats > 0) {
                    if (mouseAreaAbs(185, 185 + 45, 118, 118 + 45)) {
                        gp.playSE(18);
                        gp.player.unSpentStats--;
                        gp.player.strengthFromLvl++;
                        gp.player.refreshPlayerStatsNoItems();
                        gp.player.playerTalents.updateTalentAndPlayer();
                        gp.player.refreshPlayerStatsNoItems();
                    } else if (mouseAreaAbs(185, 185 + 45, 178, 178 + 45)) {
                        gp.playSE(18);
                        gp.player.unSpentStats--;
                        gp.player.dexterityFromLvl++;
                        gp.player.refreshPlayerStatsNoItems();
                        gp.player.playerTalents.updateTalentAndPlayer();
                        gp.player.refreshPlayerStatsNoItems();
                    } else if (mouseAreaAbs(185, 185 + 45, 238, 238 + 45)) {
                        gp.playSE(18);
                        gp.player.unSpentStats--;
                        gp.player.enduranceFromLvl++;
                        gp.player.refreshPlayerStatsNoItems();
                        gp.player.playerTalents.updateTalentAndPlayer();
                        gp.player.refreshPlayerStatsNoItems();

                    } else if (mouseAreaAbs(185, 185 + 45, 298, 298 + 45)) {
                        gp.playSE(18);
                        gp.player.unSpentStats--;
                        gp.player.intelligenceFromLvl++;
                        gp.player.refreshPlayerStatsNoItems();
                        gp.player.playerTalents.updateTalentAndPlayer();
                        gp.player.refreshPlayerStatsNoItems();
                    }
                }
            }

            else if (gp.gameState == gp.playState && gp.player.canAttack && !gp.player.stunned) {
                gp.player.attacking = true;
                double angle = Math.atan2(mouseY - centerY, mouseX - centerX);
                angle = Math.toDegrees(angle);
                if (angle < 0) {
                    angle += 360;
                }
                angle += 90;
                if (angle < 0) {
                    angle += 360;
                }
                int slice = (int) Math.round(angle / 45.0) % 8;

                switch (slice) {
                    case 0 -> gp.player.attackDirection = 1;
                    case 1 -> gp.player.attackDirection = 2;
                    case 2 -> gp.player.attackDirection = 3;
                    case 3 -> gp.player.attackDirection = 4;
                    case 4 -> gp.player.attackDirection = 5;
                    case 5 -> gp.player.attackDirection = 6;
                    case 6 -> gp.player.attackDirection = 7;
                    case 7 -> gp.player.attackDirection = 8;
                }
            }

            else if(gp.gameState == gp.puzzleStateHarryPotter){
                harryPotterPuzzleClick();
            }
            else if(gp.gameState == gp.inventoryState){
                mouseClickSentInventory = true;
                if(gp.inventorySubState > 510 && gp.inventorySubState < 515) {
                    if(mouseAreaAbs(115,197,85,150)){
                        gp.inventorySubState = gp.inventoryStorageState1;
                    } else if(mouseAreaAbs(207,289,85,150)){
                        gp.inventorySubState = gp.inventoryStorageState2;
                    } else if(mouseAreaAbs(229,381,85,150)) {
                        gp.inventorySubState = gp.inventoryStorageState3;
                    } else if(mouseAreaAbs(391,473,85,150)) {
                        gp.inventorySubState = gp.inventoryStorageState4;
                    }
                }
            }
            else if (gp.gameState == gp.skillPageState){
                mouseSkillPageState1();
            }

            else if (gp.gameState == gp.talentPageState){
                mouseTalentPageState();

            } else if (gp.gameState == gp.titleState){
                clickTitleState();
            } else if (gp.gameState == gp.mainMenuState){
                clickMainMenu();
            }

        }
        else if (button == MouseEvent.BUTTON3){
            if(gp.gameState == gp.playState && gp.player.canCast && gp.player.equippedSpellList[5] != null && gp.player.canCast && !gp.player.stunned){
                if(gp.player.haveEnoughManaToCast(gp.player.equippedSpellList[5].uniqueSpellID, gp.player.equippedSpellList[5].currentPointsOnSpell)) {
                    gp.player.casting = true;
                    gp.player.currentlyCastingSpellSlot = 5;
                    double angle = Math.atan2(mouseY - centerY, mouseX - centerX);
                    angle = Math.toDegrees(angle);
                    if (angle < 0) {
                        angle += 360;
                    }
                    int slice = (int) Math.round(angle / 90) % 4;
                    switch (slice) {
                        case 0 -> gp.player.direction = "right";
                        case 1 -> gp.player.direction = "down";
                        case 2 -> gp.player.direction = "left";
                        case 3 -> gp.player.direction = "up";
                    }
                }
            }
            if(gp.gameState == gp.skillPageState){
                mouseSkillPageStateRightClick();
            }
        }
    }

    private void harryPotterPuzzleClick(){
        int x = 50;
        int y1 = 205;
        int y2 = 420;

        if(mouseAreaAbs(x,x + 490, y1, y2)){
            if(gp.ui.commandNum != 7){
                if(gp.harryPotterPuzzle.potions[gp.ui.commandNum].drank){
                    gp.playSE(30);
                } else {
                    gp.harryPotterPuzzle.drinkEffect(gp.harryPotterPuzzle.potions[gp.ui.commandNum]);
                }
            }
        } else if(mouseAreaAbs(3,134, 470,550)) {
            gp.playSE(4);
            gp.gameState = gp.playState;
        } else if(mouseAreaAbs(537,675,44,69)){
            gp.harryPotterPuzzle.changeLetterImage(0);
            gp.playSE(18);
        } else if(mouseAreaAbs(537 + 290,675 + 290,44,69)){
            gp.harryPotterPuzzle.changeLetterImage(1);
            gp.playSE(18);
        }
    }

    private void clickMainMenu() {
        if(gp.mainMenuSubState == gp.mainMenuSubState0) {
            if(mouseAreaAbs(326, 703, 166, 384)) {
                if (gp.ui.commandNum == 4) {
                    gp.gameState = gp.playState;
                    gp.startSinging(gp.currentMap);
                    gp.playSE(4);
                } else if (gp.ui.commandNum == 0) {
                    gp.playSE(4);
                    gp.gameState = gp.titleState;
                    gp.ui.titleScreenSubState = 0;
                    gp.saveLoad.save();
                    gp.playMusic(74);
                    gp.ui.getSaveSlots();
                    gp.clearDataWhenExit();
                } else if (gp.ui.commandNum == 1) {
                    gp.mainMenuSubState = gp.mainMenuHelpState;
                    gp.playSE(4);
                } else if (gp.ui.commandNum == 2) {
                    gp.playSE(4);
                    gp.mainMenuSubState = gp.mainMenuOptionsState;
                    gp.ui.commandNum = 0;
                    gp.ui.previousCommandNum = gp.ui.commandNum;
                } else if (gp.ui.commandNum == 3) {
                    gp.playSE(4);
                    gp.mainMenuSubState = gp.mainMenuShowControlsState;
                }
            }
        } else if (gp.mainMenuSubState == gp.mainMenuOptionsState){
            if(mouseAreaAbs(320,700,540,575)){
                gp.ui.testMusicPlaying = false;
                gp.stopMusic();
                gp.mainMenuSubState = gp.mainMenuSubState0;
                gp.playSE(30);

            //visibility options
            } else if(mouseAreaAbs(639,675,298,333)) {
                gp.visibleHitBox = !gp.visibleHitBox;
                gp.playSE(4);
            } else if(mouseAreaAbs(639,675,298 + 64,333 + 64)) {
                gp.visibleExpValue = !gp.visibleExpValue;
                gp.playSE(4);
            } else if(mouseAreaAbs(639,675,298 + 64 * 2,333 + 64 * 2)) {
                gp.visibleDamageNumbersDoneToYou = !gp.visibleDamageNumbersDoneToYou;
                gp.playSE(4);
            } else if(mouseAreaAbs(639,675,298 + 64 * 3,333 + 64 * 3)) {
                gp.visibleDamageNumbersDoneByYou = !gp.visibleDamageNumbersDoneByYou;
                gp.playSE(4);
            }

                // music:
            else if (mouseAreaAbs(445 - 23,445, 167,167 + 38)){
                gp.music.volumeScale = 1;
                gp.music.checkVolume();
            } else if (mouseAreaAbs(445,445 + 23, 167,167 + 38)){
                gp.music.volumeScale = 2;
                gp.music.checkVolume();
            } else if (mouseAreaAbs(445 + 23,445 + 2 * 23, 167,167 + 38)){
                gp.music.volumeScale = 3;
                gp.music.checkVolume();
            } else if (mouseAreaAbs(445 + 2 * 23,445 + 3 * 23, 167,167 + 38)){
                gp.music.volumeScale = 4;
                gp.music.checkVolume();
            } else if (mouseAreaAbs(445 + 3 * 23,445 + 4 * 23, 167,167 + 38)){
                gp.music.volumeScale = 5;
                gp.music.checkVolume();
            } else if (mouseAreaAbs(445 + 4 * 23,445 + 5 * 23, 167,167 + 38)){
                gp.music.volumeScale = 6;
                gp.music.checkVolume();
            } else if (mouseAreaAbs(445 + 5 * 23,445 + 6 * 23, 167,167 + 38)){
                gp.music.volumeScale = 7;
                gp.music.checkVolume();
            } else if (mouseAreaAbs(445 + 6 * 23,445 + 7 * 23, 167,167 + 38)){
                gp.music.volumeScale = 8;
                gp.music.checkVolume();
            } else if (mouseAreaAbs(445 + 7 * 23,445 + 8 * 23, 167,167 + 38)){
                gp.music.volumeScale = 9;
                gp.music.checkVolume();
            } else if (mouseAreaAbs(445 + 8 * 23,445 + 9 * 23, 167,167 + 38)){
                gp.music.volumeScale = 10;
                gp.music.checkVolume();
            } else if (mouseAreaAbs(445 + 9 * 23,445 + 10 * 23, 167,167 + 38)){
                gp.music.volumeScale = 11;
                gp.music.checkVolume();

            } else if (mouseAreaAbs(445 - 23,445, 231,231 + 38)){
                gp.se.volumeScale = 1;
                gp.playSE(3);
            } else if (mouseAreaAbs(445,445 + 23, 231,231 + 38)){
                gp.se.volumeScale = 2;
                gp.playSE(3);
            } else if (mouseAreaAbs(445 + 23,445 + 2 * 23, 231,231 + 38)){
                gp.se.volumeScale = 3;
                gp.playSE(3);
            } else if (mouseAreaAbs(445 + 2 * 23,445 + 3 * 23, 231,231 + 38)){
                gp.se.volumeScale = 4;
                gp.playSE(3);
            } else if (mouseAreaAbs(445 + 3 * 23,445 + 4 * 23, 231,231 + 38)){
                gp.se.volumeScale = 5;
                gp.playSE(3);
            } else if (mouseAreaAbs(445 + 4 * 23,445 + 5 * 23, 231,231 + 38)){
                gp.se.volumeScale = 6;
                gp.playSE(3);
            } else if (mouseAreaAbs(445 + 5 * 23,445 + 6 * 23, 231,231 + 38)){
                gp.se.volumeScale = 7;
             gp.playSE(3);
            } else if (mouseAreaAbs(445 + 6 * 23,445 + 7 * 23, 231,231 + 38)){
                gp.se.volumeScale = 8;
                gp.playSE(3);
            } else if (mouseAreaAbs(445 + 7 * 23,445 + 8 * 23, 231,231 + 38)){
                gp.se.volumeScale = 9;
                gp.playSE(3);

            } else if (mouseAreaAbs(445 + 8 * 23,445 + 9 * 23, 231,231 + 38)){
                gp.se.volumeScale = 10;
                gp.playSE(3);
            } else if (mouseAreaAbs(445 + 9 * 23,445 + 10 * 23, 231,231 + 38)) {
                gp.se.volumeScale = 11;
                gp.playSE(3);
        }

        } else if (gp.mainMenuSubState == gp.mainMenuHelpState){
            if(mouseAreaAbs(320,700,546,570)) {
                gp.mainMenuSubState = gp.mainMenuSubState0;
                gp.playSE(30);
            }
        } else if (gp.mainMenuSubState == gp.mainMenuShowControlsState) {
            if(mouseAreaAbs(320,700,567,592)) {
                gp.mainMenuSubState = gp.mainMenuSubState0;
                gp.playSE(30);
            }
        }
    }


    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();

        if(gp.gameState == gp.titleState){
            moveTitleMenu();
        }
        else if(gp.gameState == gp.characterState){
           mouseCharacterStateMoved();
        }
        else if(gp.gameState == gp.talentPageState){
            mouseTalentPageStateMove();
        } else if(gp.gameState == gp.skillPageState){
            mouseSkillPageStateMove();
        } else if(gp.gameState == gp.mainMenuState){
            moveMainMenu();
        } else if (gp.gameState == gp.puzzleStateHarryPotter){
            movePuzzleHarryPotter();
        }
    }

    private void movePuzzleHarryPotter(){
        int x = 50;
        int y1 = 205;
        int y2 = 420;

        if(mouseAreaAbs(x,x + 70, y1, y2)){
            gp.ui.commandNum = 0;
        } else if(mouseAreaAbs(x + 70,x + 140, y1, y2)) {
            gp.ui.commandNum = 1;
        } else if(mouseAreaAbs(x + 140,x + 210, y1, y2)) {
            gp.ui.commandNum =2;
        } else if(mouseAreaAbs(x + 210,x + 280, y1, y2)) {
            gp.ui.commandNum = 3;
        } else if(mouseAreaAbs(x + 280,x + 350, y1, y2)) {
            gp.ui.commandNum = 4;
        } else if(mouseAreaAbs(x + 350,x + 420, y1, y2)) {
            gp.ui.commandNum = 5;
        } else if(mouseAreaAbs(x + 420,x + 490, y1, y2)) {
            gp.ui.commandNum = 6;
        } else if(mouseAreaAbs(3,134, 470,550)) {
            gp.ui.commandNum = 7;
        }
    }

    private void moveMainMenu() {
        if(gp.mainMenuSubState == gp.mainMenuSubState0){
            if(mouseX > 326 && mouseX < 703){
                if(mouseY > 168 && mouseY < 208) {
                    gp.ui.commandNum = 0;
                } else if( mouseY > 208 && mouseY < 250){
                    gp.ui.commandNum = 1;
                } else if (mouseY >= 250 && mouseY < 293){
                    gp.ui.commandNum = 2;
                } else if (mouseY >= 293 && mouseY < 336) {
                    gp.ui.commandNum = 3;
                } else if (mouseY >= 336 && mouseY < 378) {
                    gp.ui.commandNum = 4;
                }
            }
        } else if(gp.mainMenuSubState == gp.mainMenuOptionsState) {
            if(mouseX > 326 && mouseX < 703) {
                if(mouseY > 161 && mouseY < 218){
                    gp.ui.commandNum = 0;
                } else if (mouseY >= 218 && mouseY < 280){
                    gp.ui.commandNum = 1;
                } else if (mouseY >= 280 && mouseY < 344) {
                    gp.ui.commandNum = 2;
                } else if (mouseY >= 344 && mouseY < 408) {
                    gp.ui.commandNum = 3;
                } else if (mouseY >= 408 && mouseY < 472) {
                    gp.ui.commandNum = 4;
                } else if (mouseY >= 472 && mouseY < 536) {
                    gp.ui.commandNum = 5;
                }
                if (mouseY >= 540 && mouseY < 575){
                    gp.ui.commandNum = 6;
                }
            }
        }
    }
    public void moveTitleMenu(){
        if(gp.ui.titleScreenSubState == 0){
            if (mouseY >  336 && mouseY < 400) {
                gp.ui.commandNum = 0;
            }
            else if (mouseY >=  400 && mouseY < 464) {
                gp.ui.commandNum = 1;
            }
            else if (mouseY >=  464 && mouseY < 528) {
                gp.ui.commandNum = 2;
            }
            else if (mouseY >=  528) {
                gp.ui.commandNum = 3;
            }

            // LOAD:
        } else if(gp.ui.titleScreenSubState == 1){
            if(mouseY > 316 && mouseY < 380){
                gp.ui.commandNum = 0;
            } else if (mouseY >= 380 && mouseY < 444){
                gp.ui.commandNum = 1;
            } else if (mouseY >=  444 && mouseY < 508){
                gp.ui.commandNum = 2;
            } else if (mouseY >= 508 && mouseY < 572){
                gp.ui.commandNum = 3;
            } else if (mouseY >= 572 && mouseY < 636){
                gp.ui.commandNum = 4;
            } else if (mouseY >= 636 && mouseY < 700){
                gp.ui.commandNum = 5;
            }

            // new game:
        } else if(gp.ui.titleScreenSubState == 3){
            if(mouseY > 316 && mouseY < 380){
                gp.ui.commandNum = 0;
            } else if (mouseY >= 380 && mouseY < 444){
                gp.ui.commandNum = 1;
            } else if (mouseY >=  444 && mouseY < 508){
                gp.ui.commandNum = 2;
            } else if (mouseY >= 508 && mouseY < 572){
                gp.ui.commandNum = 3;
            } else if (mouseY >= 572 && mouseY < 636){
                gp.ui.commandNum = 4;
            } else if (mouseY >= 636 && mouseY < 700){
                gp.ui.commandNum = 5;
            }
        }
    }

    public boolean mouseAreaAbs(int x, int x2, int y, int y2){
        return mouseX > x && mouseX < x2 && mouseY > y && mouseY < y2;
    }

    private void mouseSkillPageState1() {
        if(mouseAreaAbs(771, 1021, 44, 84)){
            gp.player.allSpellList.updateDescription();
            gp.gameState = gp.talentPageState;
        }
            //0
        else if (mouseAreaAbs(598, 658, 327, 387)) {
            skillPointUpgrade(0);
            //1
        } else if(mouseAreaAbs(598, 658, 392, 452)){
            skillPointUpgrade(1);
            //2
        }else if(mouseAreaAbs(598, 658, 547, 607)){
            skillPointUpgrade(2);
            //3
        }else if(mouseAreaAbs(598, 658, 703, 763)){
            skillPointUpgrade(3);
            //4
        }else if(mouseAreaAbs(718, 778, 327, 387)){
            skillPointUpgrade(4);
            //5
        }else if(mouseAreaAbs(718, 778, 470, 530)){
            skillPointUpgrade(5);
            //6
        }else if(mouseAreaAbs(718, 778, 625, 685)){
            skillPointUpgrade(6);
            //7
        }else if(mouseAreaAbs(718, 778, 703, 763)){
            skillPointUpgrade(7);
            //8
        }else if(mouseAreaAbs(838, 898, 392, 452)){
            skillPointUpgrade(8);
            //9
        }else if(mouseAreaAbs(838, 898, 470, 530)){
            skillPointUpgrade(9);
            //10
        }else if(mouseAreaAbs(838, 898, 547, 607)){
            skillPointUpgrade(10);
            //11
        }else if(mouseAreaAbs(838, 898, 625, 685)){
            skillPointUpgrade(11);
        }

    }
    private void mouseSkillPageStateMove() {
        //0
        int number = -1;
        if (mouseAreaAbs(598, 658, 327, 387)) {
            number = 0;
            //gp.player.allSpellList.allPlayerAvailableSpells[0].showHelp = true;
            //1
        }else if(mouseAreaAbs(598, 658, 392, 452)){
            number = 1;
            //gp.player.allSpellList.allPlayerAvailableSpells[1].showHelp = true;
            //2
        }else if(mouseAreaAbs(598, 658, 547, 607)){
            number = 2;
            //gp.player.allSpellList.allPlayerAvailableSpells[2].showHelp = true;
            //3
        }else if(mouseAreaAbs(598, 658, 703, 763)){
            number = 3;
            //gp.player.allSpellList.allPlayerAvailableSpells[3].showHelp = true;
            //4
        }else if(mouseAreaAbs(718, 778, 327, 387)){
            number = 4;
            //gp.player.allSpellList.allPlayerAvailableSpells[4].showHelp = true;
            //5
        }else if(mouseAreaAbs(718, 778, 470, 530)){
            number = 5;
            //gp.player.allSpellList.allPlayerAvailableSpells[5].showHelp = true;
            //6
        }else if(mouseAreaAbs(718, 778, 625, 685)){
            number = 6;
            //gp.player.allSpellList.allPlayerAvailableSpells[6].showHelp = true;
            //7
        }else if(mouseAreaAbs(718, 778, 703, 763)){
            number = 7;
            //gp.player.allSpellList.allPlayerAvailableSpells[7].showHelp = true;
            //8
        }else if(mouseAreaAbs(838, 898, 392, 452)){
            number = 8;
            //gp.player.allSpellList.allPlayerAvailableSpells[8].showHelp = true;
            //9
        }else if(mouseAreaAbs(838, 898, 470, 530)){
            number = 9;
            //gp.player.allSpellList.allPlayerAvailableSpells[9].showHelp = true;
            //10
        }else if(mouseAreaAbs(838, 898, 547, 607)){
            number = 10;
            //gp.player.allSpellList.allPlayerAvailableSpells[10].showHelp = true;
            //11
        }else if(mouseAreaAbs(838, 898, 625, 685)){
            number = 11;
            //gp.player.allSpellList.allPlayerAvailableSpells[11].showHelp = true;
        }

        for (int i = 0; i < 12; i++) {
            if(i == number){
                gp.player.allSpellList.allPlayerAvailableSpells[i].showHelp = true;
            } else {
                gp.player.allSpellList.allPlayerAvailableSpells[i].showHelp = false;
            }
        }
    }

    private void mouseSkillPageStateRightClick() {
        for (int i = 0; i < gp.player.allSpellList.allPlayerAvailableSpells.length; i++) {
            gp.player.tryEquipThisSpell(i,5);
        }
    }

    private void mouseTalentPageStateMove(){
        //0
        if (mouseAreaAbs(545, 640, 240, 335)) {
            gp.player.playerTalents.talentList[0].showHelp = true;
            gp.player.playerTalents.talentList[1].showHelp = false;
        }

        //1
        else if(mouseAreaAbs(710,805,240,335)){
            gp.player.playerTalents.talentList[1].showHelp = true;
            gp.player.playerTalents.talentList[0].showHelp = false;
        }
        //2
        else if(mouseAreaAbs(875,970,240,335)){
            gp.player.playerTalents.talentList[2].showHelp = true;
        }

        //3
        else if(mouseAreaAbs(545,640,405,500)){
            gp.player.playerTalents.talentList[3].showHelp = true;
        }
        //4
        else if(mouseAreaAbs(710,805,405,500)){
            gp.player.playerTalents.talentList[4].showHelp = true;
        }
        //5
        else if(mouseAreaAbs(875,970,405,500)){
            gp.player.playerTalents.talentList[5].showHelp = true;
        }
        //6
        else if(mouseAreaAbs(545,640,570,665)){
            gp.player.playerTalents.talentList[6].showHelp = true;
        }
        else{
            for (int i = 0; i < 7; i++) {
                gp.player.playerTalents.talentList[i].showHelp = false;
            }
        }
    }

    private void mouseTalentPageState() {
        if(mouseAreaAbs(515, 765, 44, 84)){
            gp.gameState = gp.skillPageState;
        }

        //UPGRADE TALENT:
        //0
        else if (mouseAreaAbs(545, 640, 240, 335)) {
            if(gp.player.unSpentTalentPoints > 0){
                updateTalentHelpMethod(0);
            } else gp.playSE(3);
        }

        //1
        else if(mouseAreaAbs(710,805,240,335)){
            if(gp.player.unSpentTalentPoints > 0){
                updateTalentHelpMethod(1);
            }
            else {
                gp.playSE(3);
            }
        }
        //2
        else if(mouseAreaAbs(875,970,240,335)){
            if(gp.player.unSpentTalentPoints > 0){
                updateTalentHelpMethod(2);
            }
            else {
                gp.playSE(3);
            }
        }

        //3
        else if(mouseAreaAbs(545,640,405,500)){
            if(gp.player.unSpentTalentPoints > 0){
                updateTalentHelpMethod(3);
            }
            else {
                gp.playSE(3);
            }
        }
        //4
        else if(mouseAreaAbs(710,805,405,500)){
            if(gp.player.unSpentTalentPoints > 0){
                updateTalentHelpMethod(4);
            }
            else {
                gp.playSE(3);
            }
        }
        //5
        else if(mouseAreaAbs(875,970,405,500)){
            if(gp.player.unSpentTalentPoints > 0){
                updateTalentHelpMethod(5);
            }
            else {
                gp.playSE(3);
            }
        }
        //6
        else if(mouseAreaAbs(545,640,570,665)){
            if(gp.player.unSpentTalentPoints > 0){
                updateTalentHelpMethod(6);
            }
            else {
                gp.playSE(3);
            }
        }
    }

    public void updateTalentHelpMethod(int i){
        gp.player.unSpentTalentPoints --;
        gp.player.playerTalents.talentList[i].currentPointsOnTalent ++;
        gp.player.playerTalents.updateTalentList();
        gp.player.refreshPlayerStatsNoItems();
        gp.player.playerTalents.updateTalentList();
        gp.player.refreshPlayerStatsNoItems();
        gp.playSE(18);
    }

    public void skillPointUpgrade(int i){
        if(gp.player.unSpentSkillPoints > 0
                && gp.player.level >= gp.player.allSpellList.allPlayerAvailableSpells[i].requiredLevel + gp.player.allSpellList.allPlayerAvailableSpells[i].currentPointsOnSpell
                && gp.player.allSpellList.allPlayerAvailableSpells[i].currentPointsOnSpell < 10){
            gp.player.unSpentSkillPoints --;
            gp.player.allSpellList.allPlayerAvailableSpells[i].currentPointsOnSpell ++;
            gp.playSE(18);
            gp.player.allSpellList.updateDescription();
            // TODO REFRESH PLAYER STATS
        }  else {
            gp.playSE(3);
        }
    }

    private void clickTitleState() {
        if(gp.ui.titleScreenSubState == 0){
            if (mouseY > 336 && mouseY < 400){
                gp.playSE(4);
                gp.ui.titleScreenSubState = 3;
                gp.ui.commandNum = 0;
                gp.ui.previousCommandNum = 0;
            } else if (mouseY >= 400 && mouseY < 464){
                gp.ui.commandNum = 0;
                gp.ui.previousCommandNum = 0;
                gp.playSE(4);
                gp.ui.titleScreenSubState = 1;
            } else if (mouseY >= 464 && mouseY < 528){
                gp.playSE(4);
                gp.ui.titleScreenSubState = 2;
                gp.ui.commandNum = 0;
                gp.ui.previousCommandNum = 0;
            } else if (mouseY >= 528 && mouseY < 592){
                gp.playSE(4);
                System.exit(0);
            }

        } else if(gp.ui.titleScreenSubState == 1){
            if(gp.ui.commandNum == 5){
                gp.ui.titleScreenSubState = 0;
                gp.playSE(30);
                gp.ui.commandNum = 0;
                gp.ui.previousCommandNum = 0;
            } else if(gp.ui.saveSlotUI[gp.ui.commandNum] != 0){ // hero lvl not 0, means save slot not taken
                gp.saveSlotNumber = gp.ui.commandNum;
                gp.gameState = gp.loadSavedGameLoadingState;
                gp.playSE(4);
            } else {
                gp.playSE(23);
            }

        } else if(gp.ui.titleScreenSubState == 2){
            if(mouseAreaAbs(320, 703, 565, 595)){
                gp.ui.titleScreenSubState = 0;
                gp.ui.commandNum = 0;
                gp.ui.previousCommandNum = 0;
                gp.playSE(30);
            }
            // NEW GAME
        } else if(gp.ui.titleScreenSubState == 3){
            if(gp.ui.commandNum == 5){
                gp.ui.titleScreenSubState = 0;
                gp.ui.commandNum = 0;
                gp.ui.previousCommandNum = 0;
                gp.playSE(30);
            } else {
                if(gp.ui.saveSlotUI[gp.ui.commandNum] == 0){ // means hero lvl start new game
                    gp.saveSlotNumber = gp.ui.commandNum;
                    gp.gameState = gp.newGameLoadingState;
                    gp.playSE(4);
                } else {
                    gp.playSE(23);
                }
            }
        }
    }

    public void mouseCharacterStateMoved(){
        if (mouseAreaAbs(30, 180, 120, 160)){
            gp.ui.showStrGuide  = true;
        } else if (mouseAreaAbs(30, 180, 180, 220)){
            gp.ui.showDexGuide  = true;
        } else if (mouseAreaAbs(30, 180, 240, 280)) {
            gp.ui.showEndGuide = true;
        } else if (mouseAreaAbs(30, 180, 300, 340)){
            gp.ui.showIntGuide  = true;
        } else {
            gp.ui.showIntGuide = false;
            gp.ui.showStrGuide = false;
            gp.ui.showDexGuide = false;
            gp.ui.showEndGuide = false;
        }
    }
}
