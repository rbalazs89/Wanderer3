package entity.npc;

import entity.NPC;
import main.GamePanel;
import tool.FadingParticlesSuper;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class NPC_CampTortoise extends NPC {
    private boolean tellMeJoke;
    private boolean playerIsNear = false;
    private int speedCounter = 0;
    private String currentJoke = "";
    private FadingParticlesSuper hearts;
    private boolean showHearts = false;
    private int heartCounter = 0;
    public NPC_CampTortoise(GamePanel gp){
        super(gp);
        direction = "down";
        defaultSpeed = 1;
        speed = defaultSpeed;
        solidArea = new Rectangle(GamePanel.tileSize / 16,
                GamePanel.tileSize / 16,
                GamePanel.tileSize * 14 / 16,
                GamePanel.tileSize * 14 / 16);

        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        collisionEntity = false;
        getImage();

        spriteChangeFrameValue = 20;
        headDialogueString = "I like turtles";
    }

    //TODO placeholder dog images for now
    public void getImage() {
        hearts = new FadingParticlesSuper(setup("/vfxparticles/heart"), this,6);
        walkImages = new BufferedImage[4][3];
        maxWalkSpriteArrayLength = 3;

        for (int i = 0; i < 3; i++) {
            //down
            walkImages[2][i] = setupSheet("/entity/npcturtle/turtle", i * 48, 0, 48, 48, 64, 64);
        }
        for (int i = 0; i < 3; i++) {
            //left
            walkImages[3][i] = setupSheet("/entity/npcturtle/turtle", i * 48, 48, 48, 48, 64, 64);
        }
        for (int i = 0; i < 3; i++) {
            //right
            walkImages[1][i] = setupSheet("/entity/npcturtle/turtle", i * 48, 96, 48, 48, 64, 64);
        }
        for (int i = 0; i < 3; i++) {
            walkImages[0][i] = setupSheet("/entity/npcturtle/turtle", i * 48, 144, 48, 48, 64, 64);
        }
        image = walkImages[0][0];
    }

    public void setActionAI(){
        // act1:
        if(worldX > 22 * GamePanel.tileSize){
            targetPathFollowed = true;
            goalCol = 10;
            goalRow = 10;
        }
        tortoiseSpeed();
        decideIfPlayerNear();
        if(!tellMeJoke && !targetPathFollowed){
            setDirectionFromRandomMovement();
        } else if (targetPathFollowed && !tellMeJoke){
            searchPath(goalCol, goalRow, true);
        }

        handleHearts();
    }

    public void handleHearts(){
        if(showHearts){
            heartCounter ++;
            if(heartCounter > 300){
                showHearts = false;
            }
            hearts.update();
        }
    }

    public void decideIfPlayerNear() {
        if(!playerIsNear){
            if(middleDistance(gp.player) < GamePanel.tileSize * 3 / 2){
                playerIsNear = true;
                tellMeJoke = true;
                walking = false;
                getRandomJoke();
            }
        }


        if(playerIsNear){
            actionWhenNearCounter ++;
            if(actionWhenNearCounter > 300){
                if(middleDistance(gp.player) > GamePanel.tileSize * 2){
                    playerIsNear = false;
                    tellMeJoke = false;
                    walking = true;
                }
            }
        }
    }

    public void tortoiseSpeed(){
        speedCounter ++;
        speed = 0;
        if(!tellMeJoke) {
            if (speedCounter >= 10) {
                speed = defaultSpeed;
                speedCounter = 0;
            }
        }
    }

    public void drawSpecial(Graphics2D g2){
        if(tellMeJoke){
            drawJoke(g2);
        }
    }
    public void drawSpecial(Graphics2D g2, int x, int y) {
        if(showHearts){
            hearts.draw(g2,x,y);
        }
    }

    public void drawJoke(Graphics2D g2){
        g2.setFont(talkingFont);
        FontMetrics fm = g2.getFontMetrics();
        ArrayList<String> lines = splitTextIntoLines(currentJoke, fm, 130 - 2 * 10);
        int textHeight = fm.getHeight();
        int boxHeight = textHeight * lines.size() + 2 * 10;
        drawHoverGuide(lines, screenMiddleX() - 65,screenMiddleY() - boxHeight - 30, 130, g2);
    }

    public void getRandomJoke(){
        if(!gp.progress.act1InteractedObjects[5]){
            currentJoke = "Thanks for rescuing me the other day! I decided to find your camp and from now on, I will be your eternally loyal turtle pet. Come to me anytime you want to hear something wise!";
            gp.progress.act1InteractedObjects[5] = true;
            showHearts = true;
            return;
        }
        //nextInt(x) -> 0 incl, x excl
        int randomNumber = ThreadLocalRandom.current().nextInt(67);
        switch (randomNumber){
            // turtle related:
            case 0:
                currentJoke = "Why did the turtle cross the road? … Actually, I’m still working on that. Been halfway there for an hour!";
                break;
            case 1:
                currentJoke = "People always tell me to come out of my shell… but have you seen the rent prices out there?";
                break;
            case 2:
                currentJoke = "I tried speed dating once… I got through one introduction before the event ended.";
                break;
            case 3:
                currentJoke = "Patience is a virtue. And trust me, I’ve got a lot of virtue.";
                break;
            case 4:
                currentJoke = "You think I’m slow? I’m just dramatically building suspense.";
                break;
            case 5:
                currentJoke = "Racing a rabbit? Pfft. I let that guy win last time just to keep his ego in check.";
                break;
            case 6:
                currentJoke = "You know what’s funny? Watching humans try to carry my shell. It’s heavier than it looks!";
                break;
            case 7:
                currentJoke = "I thought about getting a skateboard… but then I realized I am the skateboard.";
                break;
            case 8:
                currentJoke = "Why did I get kicked out of the zoo? Turns out, I wasn’t part of the exhibit. Just got lost";
                break;
            case 9:
                currentJoke = "I have two speeds: slow and… nope, just slow.";
                break;
            case 10:
                currentJoke = "You ever get that feeling like someone’s watching you? No? Must be nice.";
                break;
            case 11:
                currentJoke = "I don’t always move fast, but when I do… it’s probably downhill by accident.";
                break;
            case 12:
                currentJoke = "I have a strict schedule. It consists of naps, snacks, and judging impatient people.";
                break;
            case 13:
                currentJoke = "You call it 'procrastination,' I call it 'deliberation.";
                break;
            case 14:
                currentJoke = "I’m not stubborn—I’m just committed to my current position.";
                break;
            case 15:
                currentJoke = "You ever seen a turtle sprint? Me neither. And I am one.";
                break;
            case 16:
                currentJoke = "I could move faster… but that would ruin the brand.";
                break;
            case 17:
                currentJoke = "I may be slow, but at least I never stub my toes. Can’t relate, huh?";
                break;
            case 18:
                currentJoke = "You can trust me… I always keep things .... 'shell-secured.'";
                break;
            case 19:
                currentJoke = "If I had a dollar for every time someone called me slow… I’d take my time counting it.";
                break;
            case 20:
                currentJoke = "I don’t run from danger. I just gradually exit the situation.";
                break;
            case 21:
                currentJoke = "Slow and steady wins the race. Unless there’s a shortcut. Then I take a nap instead.";
                break;
            case 22:
                currentJoke = "Good things come to those who wait. Which is great, because waiting is all I do.";
                break;
            case 23:
                currentJoke = "You don’t need to move fast when you carry your home on your back.";
                break;
            case 24:
                currentJoke = "You say I’m slow, but I say I’m efficient. I never take unnecessary steps.";
                break;
            case 25:
                currentJoke = "What’s my workout routine? Lift my head, stretch my legs, and call it a day.";
                break;
            case 26:
                currentJoke = "I once tried to run. My shell had other plans.";
                break;

                // not turtle related:
            case 27:
                currentJoke = "I once met a mathematician at a party. He said, ‘Let’s divide and conquer!’ Then he just left.";
                break;

            case 28:
                currentJoke = "Parallel lines have so much in common… it’s a shame they’ll never meet.";
                break;

            case 29:
                currentJoke = "I went to the zoo, and the sign said ‘Please don’t feed the animals.’ So I skipped lunch out of solidarity.";
                break;

            case 30:
                currentJoke = "Once I tried to take a selfie with a vampire… but it turned out to be just me and a confused mirror.";
                break;

            case 31:
                currentJoke = "I adopted a chameleon once, but I could never find him when I needed emotional support.";
                break;

            case 32:
                currentJoke = "I met an owl who only spoke in riddles… turns out it was just a very smug pigeon.";
                break;

            case 33:
                currentJoke = "Once I got a map to buried treasure… turns out it was just a napkin from a pirate-themed restaurant.";
                break;

            case 34:
                currentJoke = "Once I met a bard who only plays one song… turns out he’s stuck in a loop because of a cursed lute.";
                break;

            case 35:
                currentJoke = "Long time ago a  wizard cast a spell to ‘make me wiser'... now I can just annoy people.";
                break;

            case 36:
                currentJoke = "I don’t trust stairs. They’re always up to something.";
                break;

            case 37:
                currentJoke = "I used to be addicted to eating soap, but I'm clean now.";
                break;

            case 38:
                currentJoke = "What do you call a woman who only has one leg? Ilene.";
                break;

            case 39:
                currentJoke = "What’s brown and sticky? A stick.";
                break;

            case 40:
                currentJoke = "Why did the scarecrow win an award? He was out standing in his field";
                break;

            case 41:
                currentJoke = "What do you call a fish with no eye. A fsh";
                break;

            case 42:
                currentJoke = "Why do you never see hippos hiding in trees? Because they're very good at it.";
                break;

            case 43:
                currentJoke = "Two fish are in a tank. One turns to the other and says: I don't know how to drive this thing";
                break;

            case 44:
                currentJoke = "What's red and bad for your teeth? A brick";
                break;

            case 45:
                currentJoke = "What's orange and sounds like a parrot? A carrot.";
                break;

            case 46:
                currentJoke = "What does the fish say after running into a wall? Damn.";
                break;

            case 47:
                currentJoke = "Why do cows wear bells? Because their horns don't work.";
                break;

            case 48:
                currentJoke = "I'm on a seafood diet. I see food, and I eat it.";
                break;

            case 49:
                currentJoke = "Why don't skeletons fight each other? They don’t have the guts!";
                break;

            case 50:
                currentJoke = "A minstrel told me to follow my dreams, so I started sleeping all day.";
                break;
            case 51:
                currentJoke = "Why’d the bard bring a ladder? To reach the high notes!";
                break;
            case 52:
                currentJoke = "What do you call a nervous knight? Sir Renders!";
                break;
            case 53:
                currentJoke = "Why’d the dwarf hate archery? Kept missing the point!";
                break;
            case 54:
                currentJoke = "What’s a vampire’s favorite fruit? A blood orange!";
                break;
            case 55:
                currentJoke = "Why’d the wizard flunk potions class? Kept stirring up trouble!";
                break;
            case 56:
                currentJoke = "What do you call a fake gemstone? A sham-rock!";
                break;
            case 57:
                currentJoke = "You think you’re old? My first birthday cake was a dinosaur cupcake!";
                break;
            case 58:
                currentJoke = "Why cross the road? To prove to the chicken I’m not chicken. Took a year.";
                break;
            case 59:
                currentJoke = "I’m a master of disguise. Yesterday I pretended to be a rock. Got sunburnt.";
                break;
            case 60:
                currentJoke = "I offered to babysit a dragon’s egg. They said no—apparently, ‘centuries’ isn’t a valid naptime";
                break;
            case 61:
                currentJoke = "I threw a party once. Guests left before I finished saying, ‘Welcome!’";
                break;
            case 62:
                currentJoke = "I challenged a wizard to a staring contest. He aged out of it.";
                break;
            case 63:
                currentJoke = "I asked a fairy for a wish. She said, ‘Speed?’ I said, ‘No, a sandwich.’ Now I’m still slow, but I’m full.";
                break;
            case 64:
                currentJoke = "I told a joke to a rock. It laughed. Turns out it was a golem with very basic humor";
                break;
            case 65:
                currentJoke = "The knight bought a dog to guard his armor. Now it just barks at its own reflection.";
                break;
            case 66:
                currentJoke = "The witch’s love potion worked too well. Now her cat won’t stop serenading the broomstick.";
                break;
            default:
                currentJoke = "I like turtles";
                break;
        }
    }
}
