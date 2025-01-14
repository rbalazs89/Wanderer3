package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

    public boolean upPressed, downPressed, leftPressed, rightPressed, enterPressed, lPressed, fPressed, qPressed;
    public boolean checkDrawTime;
    //Only for dashing:
    public boolean upPressed2, downPressed2, leftPressed2, rightPressed2;
    GamePanel gp;
    public KeyHandler(GamePanel gp){
        this.gp = gp;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        //TITLE STATE
        if(gp.gameState == gp.titleState){
            titleScreen(code);
        }

        //PLAY STATE
        else if (gp.gameState == gp.playState){
            if(code == KeyEvent.VK_SPACE){
                gp.player.dashing();
            }

            if(code == KeyEvent.VK_E){
                gp.player.interactingButtonPressed = true;
            }

            else if(code == KeyEvent.VK_L){
                lPressed = true;
            }
            else if(code == KeyEvent.VK_Q){
                qPressed = true;
            }
            else if(code == KeyEvent.VK_F){
                fPressed = true;
            }
            /*else if (code == KeyEvent.VK_M){
                gp.player.life += 10;
                gp.player.mana += 10;
            }
            else if (code == KeyEvent.VK_N){
                gp.player.life -= 10;
                gp.player.mana -= 10;
            }*/
            if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP){
                upPressed = true;
                upPressed2 = true;
            }
            else if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN){
                downPressed = true;
                downPressed2 = true;
            }
            else if (code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT){
                leftPressed = true;
                leftPressed2 = true;
            }
            else if (code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT){
                rightPressed = true;
                rightPressed2 = true;
            }
            if(code == KeyEvent.VK_ENTER){
                enterPressed = true;
            }
            if(code == KeyEvent.VK_O){
                checkDrawTime = !checkDrawTime;
            }
            else if(code == KeyEvent.VK_P){
                gp.gameState = gp.pauseState;
            }

            else if(code == KeyEvent.VK_U){
                gp.gameState = gp.characterState;
                gp.player.refreshPlayerStatsNoItems();
                gp.player.playerTalents.updateTalentAndPlayer();
            }

            else if(code == KeyEvent.VK_I){
                gp.gameState = gp.inventoryState;
                gp.inventorySubState = 500;
            }

            if(code == KeyEvent.VK_ESCAPE){
                gp.gameState = gp.mainMenuState;
                gp.ui.commandNum = 0;
                gp.ui.previousCommandNum = 0;
                gp.ui.previousMusicScale = gp.music.volumeScale;
            }

            else if(code == KeyEvent.VK_M){
                gp.gameState = gp.mapState;
            }

            else if(code == KeyEvent.VK_SHIFT){
                gp.map.miniMapOn = !gp.map.miniMapOn;
            }

            else if(code == KeyEvent.VK_T){
                gp.gameState = gp.skillPageState;
                gp.player.playerTalents.updateTalentList();
                gp.player.playerTalents.updatePlayerStatsFromTalent();
                gp.player.refreshPlayerStatsNoItems();
                gp.player.playerTalents.updateTalentAndPlayer();
                gp.player.refreshPlayerStatsNoItems();
                gp.player.allSpellList.updateDescription();
            }
            handleSpellCasting(code);

        }
        else if(gp.gameState == gp.mainMenuState){
            mainMenuState(code);
        }
        else if(gp.gameState == gp.pauseState){
            if(code == KeyEvent.VK_P){
                gp.gameState = gp.playState;
            }
        }
        else if(gp.gameState == gp.dialogueState) {
            if(code == KeyEvent.VK_ENTER){
                gp.gameState = gp.playState;
            }
        }
        else if(gp.gameState == gp.characterState) {
            if(code == KeyEvent.VK_U || code == KeyEvent.VK_ESCAPE){
                gp.gameState = gp.playState;
            }
        }
        else if(gp.gameState == gp.inventoryState){
            if(code == KeyEvent.VK_I || code == KeyEvent.VK_ESCAPE){
                gp.gameState = gp.playState;
                gp.inventorySubState = 0;
                gp.allInventoryPages.handleClosingInventory();
            }
        }
        else if(gp.gameState == gp.skillPageState){
            skillPageState(code);
        }
        else if(gp.gameState == gp.talentPageState){
            talentPageState(code);
        }

        // MAP STATE:
        else if(gp.gameState == gp.mapState){
            mapState(code);
        }
        else if (gp.gameState == gp.deathState){
            deathState(code);

        } else if (gp.gameState == gp.puzzleStateHarryPotter){
            harryPotter(code);
        }
    }


    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();

        if (code == KeyEvent.VK_ENTER){
            enterPressed = false;
        }

        if(code == KeyEvent.VK_L ){
            lPressed = false;
        }

        if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP){
            upPressed = false;
            upPressed2 = false;
        }

        else if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN){
            downPressed = false;
            downPressed2 = false;
        }
        else if (code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT){
            leftPressed = false;
            leftPressed2 = false;
        }
        else if (code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT){
            rightPressed = false;
            rightPressed2 = false;
        }
        if(code == KeyEvent.VK_Q){
            qPressed = false;
        }
    }
    private void mapState(int code) {
        if(code == KeyEvent.VK_M || code == KeyEvent.VK_ESCAPE){
            gp.gameState = gp.playState;
        }
    }

    public void mainMenuState(int code) {
        if (code == KeyEvent.VK_ENTER) {
            enterPressed = true;
        }
        //BASE MAIN MENU
        if (gp.mainMenuSubState == gp.mainMenuSubState0) {
            if (code == KeyEvent.VK_ESCAPE) {
                gp.gameState = gp.playState;
                gp.playSE(4);
                gp.startSinging(gp.currentMap);
            }

            else if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
                gp.ui.commandNum--;
            }
            else if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
                gp.ui.commandNum++;
            }

            else if (code == KeyEvent.VK_ENTER) {
                enterPressed = false;
                gp.playSE(4);
                switch (gp.ui.commandNum) {
                    case 0: {
                        gp.gameState = gp.titleState;
                        gp.ui.titleScreenSubState = 0;
                        gp.saveLoad.save();
                        gp.ui.getSaveSlots();
                        gp.clearDataWhenExit();
                        gp.playMusic(74);
                        break;
                    }
                    case 1: {
                        gp.mainMenuSubState = gp.mainMenuHelpState;
                        break;
                    }
                    case 2: {
                        gp.mainMenuSubState = gp.mainMenuOptionsState;
                        gp.ui.commandNum = 0;
                        gp.ui.previousCommandNum = gp.ui.commandNum;
                        break;
                    }
                    case 3: {
                        gp.mainMenuSubState = gp.mainMenuShowControlsState;
                        break;
                    }
                    case 4: {
                        gp.gameState = gp.playState;
                        gp.startSinging(gp.currentMap);
                        break;
                    }
                }
            }

            if (gp.ui.commandNum < 0) {
                gp.ui.commandNum = 4;
            }
            else if (gp.ui.commandNum > 4) {
                gp.ui.commandNum = 0;
            }
        }

        // OPTIONS MENU:
        if (gp.mainMenuSubState == gp.mainMenuOptionsState) {

            if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
                gp.ui.commandNum--;
            }
            else if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
                gp.ui.commandNum++;
            }
            if (gp.ui.commandNum < 0) {
                gp.ui.commandNum = 6;
            }
            else if (gp.ui.commandNum > 6) {
                gp.ui.commandNum = 0;
            }
            if(gp.ui.commandNum == 0){
                if(code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT){
                    gp.music.volumeScale --;
                    gp.music.checkVolume();
                }
                if(code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT){
                    gp.music.volumeScale ++;
                    gp.music.checkVolume();
                }
                gp.music.volumeScale = Math.min(11, Math.max(1, gp.music.volumeScale));
            }
            if(gp.ui.commandNum == 1){
                if(code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT){
                    gp.se.volumeScale --;
                    gp.playSE(3);
                }
                if(code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT){
                    gp.se.volumeScale ++;
                    gp.playSE(3);
                }
                gp.se.volumeScale = Math.min(11, Math.max(1, gp.se.volumeScale));
            }
        }

        //EXIT FROM SUBSTATE
        if (gp.mainMenuSubState != gp.mainMenuSubState0) {
            if (code == KeyEvent.VK_ESCAPE) {
                gp.ui.testMusicPlaying = false;
                gp.stopMusic();
                gp.mainMenuSubState = gp.mainMenuSubState0;
                gp.playSE(30);
            }
        }
    }

    public void deathState(int code){
        if(gp.ui.canRespawn){
            if(code == KeyEvent.VK_ESCAPE || code == KeyEvent.VK_E || code == KeyEvent.VK_W || code == KeyEvent.VK_UP || code == KeyEvent.VK_ENTER){
                gp.ui.canRespawn = false;
                gp.player.respawn(gp.dataBase1.getRespawnData());
                gp.gameState = gp.playState;
            }
        }
    }

    private void talentPageState(int code) {
        if(code == KeyEvent.VK_ESCAPE || code == KeyEvent.VK_T){
            gp.gameState = gp.playState;
        }
    }

    private void skillPageState(int code) {
        if(code == KeyEvent.VK_ESCAPE || code == KeyEvent.VK_T){
            gp.gameState = gp.playState;
        }
        else if(code == KeyEvent.VK_1){
            for (int i = 0; i < 12; i++) {
                gp.player.tryEquipThisSpell(i,0);
            }
        }
        else if(code == KeyEvent.VK_2){
            for (int i = 0; i < 12; i++) {
                gp.player.tryEquipThisSpell(i,1);
            }
        }
        else if(code == KeyEvent.VK_3){
            for (int i = 0; i < 12; i++) {
                gp.player.tryEquipThisSpell(i,2);
            }
        }
        else if(code == KeyEvent.VK_4){
            for (int i = 0; i < 12; i++) {
                gp.player.tryEquipThisSpell(i,3);
            }
        }
        else if(code == KeyEvent.VK_5){
            for (int i = 0; i < 12; i++) {
                gp.player.tryEquipThisSpell(i,4);
            }
        }
    }

    private void handleSpellCasting(int code) {
        if(code == KeyEvent.VK_1){
            castSpell(0);
        }
        else if(code == KeyEvent.VK_2){
            castSpell(1);
        }
        else if(code == KeyEvent.VK_3){
            castSpell(2);
        }
        else if(code == KeyEvent.VK_4){
            castSpell(3);
        }
        else if(code == KeyEvent.VK_5){
            castSpell(4);
        }
    }

    private void castSpell(int i){
        if(gp.player.canCast && gp.player.equippedSpellList[i] != null && gp.player.canCast && gp.player.canCastFromCoolDown && !gp.player.stunned){
            if(gp.player.haveEnoughManaToCast(gp.player.equippedSpellList[i].uniqueSpellID, gp.player.equippedSpellList[i].currentPointsOnSpell)) {

                int mouseY = gp.mouseH.mouseY;
                int mouseX = gp.mouseH.mouseX;
                int centerX = gp.getWidth() / 2;
                int centerY = gp.getHeight() / 2;
                gp.player.casting = true; // this starts casting in the update method
                gp.player.currentlyCastingSpellSlot = i; //from this I get which spell to cast
                double angle = Math.atan2(mouseY - centerY, mouseX - centerX);
                angle = Math.toDegrees(angle);

                if (angle < 0) {
                    angle += 360;
                }

                int slice = (int) Math.round(angle / 90) % 4;
                switch (slice) {
                    case 0:
                        gp.player.direction = "right";
                        break;
                    case 1:
                        gp.player.direction = "down";
                        break;
                    case 2:
                        gp.player.direction = "left";
                        break;
                    case 3:
                        gp.player.direction = "up";
                        break;
                }
            }
        }
    }

    public void titleScreen(int code){
        if(gp.ui.titleScreenSubState == 0) {

            if(code == KeyEvent.VK_W || code == KeyEvent.VK_UP){
                gp.ui.commandNum--;
            }
            else if(code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
                gp.ui.commandNum++;
            }
            else if(code == KeyEvent.VK_ENTER) {
                if(gp.ui.commandNum == 0){
                    gp.playSE(4);
                    gp.ui.titleScreenSubState = 3;
                    gp.ui.commandNum = 0;
                    gp.ui.previousCommandNum = 0;
                }
                else if(gp.ui.commandNum == 1) {
                    gp.ui.commandNum = 0;
                    gp.ui.previousCommandNum = 0;
                    gp.playSE(4);
                    gp.ui.titleScreenSubState = 1;
                }

                else if(gp.ui.commandNum == 2){
                    gp.playSE(4);
                    gp.ui.titleScreenSubState = 2;
                    gp.ui.commandNum = 0;
                    gp.ui.previousCommandNum = 0;
                }

                else if(gp.ui.commandNum == 3){
                    gp.playSE(4);
                    System.exit(0);
                }
            }
            if(gp.ui.commandNum < 0){
                gp.ui.commandNum = 3;
            }
            else if(gp.ui.commandNum > 3){
                gp.ui.commandNum = 0;
            }
        }
        else if (gp.ui.titleScreenSubState == 1){
            if(code == KeyEvent.VK_W || code == KeyEvent.VK_UP){
                gp.ui.commandNum--;
            }

            else if(code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN){
                gp.ui.commandNum++;
            }

            else if(code == KeyEvent.VK_ESCAPE) {
                gp.ui.titleScreenSubState = 0;
                gp.playSE(30);
                gp.ui.commandNum = 0;
                gp.ui.previousCommandNum = 0;
            }

            else if(code == KeyEvent.VK_ENTER){
                if(gp.ui.commandNum == 5){
                    gp.ui.titleScreenSubState = 0;
                    gp.playSE(30);
                    gp.ui.commandNum = 0;
                    gp.ui.previousCommandNum = 0;
            //// Load game:
                } else if(gp.ui.saveSlotUI[gp.ui.commandNum] != 0){
                    gp.saveSlotNumber = gp.ui.commandNum;
                    gp.gameState = gp.loadSavedGameLoadingState;
                    gp.playSE(4);
                } else {
                    gp.playSE(23);
                }
            }

            if(gp.ui.commandNum < 0){
                gp.ui.commandNum = 5;
            }

            else if(gp.ui.commandNum > 5){
                gp.ui.commandNum = 0;
            }


        } else if (gp.ui.titleScreenSubState == 2){
            if(code == KeyEvent.VK_ENTER || code == KeyEvent.VK_ESCAPE) {
                gp.ui.titleScreenSubState = 0;
                gp.ui.commandNum = 0;
                gp.ui.previousCommandNum = 0;
                gp.playSE(30);
            }

        } else if (gp.ui.titleScreenSubState == 3){
            if(code == KeyEvent.VK_W || code == KeyEvent.VK_UP){
                gp.ui.commandNum--;
            }

            else if(code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN){
                gp.ui.commandNum++;
            }

            else if(code == KeyEvent.VK_ESCAPE) {
                gp.ui.titleScreenSubState = 0;
                gp.ui.commandNum = 0;
                gp.ui.previousCommandNum = 0;
                gp.playSE(30);
            }

            else if(code == KeyEvent.VK_ENTER){
                if(gp.ui.commandNum == 5){
                    gp.ui.titleScreenSubState = 0;
                    gp.ui.commandNum = 0;
                    gp.ui.previousCommandNum = 0;
                    gp.playSE(30);
                } else {
                    if(gp.ui.saveSlotUI[gp.ui.commandNum] == 0){ // means hero lvl NEW GAME
                        gp.saveSlotNumber = gp.ui.commandNum;
                        gp.gameState = gp.newGameLoadingState;
                        gp.playSE(4);
                    } else {
                        gp.playSE(23);
                    }
                }
            }
            if(gp.ui.commandNum < 0){
                gp.ui.commandNum = 5;
            }

            else if(gp.ui.commandNum > 5){
                gp.ui.commandNum = 0;
            }
        }
    }

    public void harryPotter(int code) {
        if(code == KeyEvent.VK_ESCAPE){
            gp.gameState = gp.playState;
            gp.playSE(4);
        } else if(code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT){
            gp.ui.commandNum ++;
        } else if(code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT){
            gp.ui.commandNum --;
        } if (gp.ui.commandNum > 7){
            gp.ui.commandNum = 0;
        } if(gp.ui.commandNum < 0){
            gp.ui.commandNum = 7;
        }
        if(code == KeyEvent.VK_ENTER){
            if(gp.ui.commandNum == 7){
                gp.playSE(4);
                gp.gameState = gp.playState;
            }
            else if(gp.harryPotterPuzzle.potions[gp.ui.commandNum].drank){
                gp.playSE(30);
            } else {
                gp.harryPotterPuzzle.drinkEffect(gp.harryPotterPuzzle.potions[gp.ui.commandNum]);
            }
        }
    }
}
