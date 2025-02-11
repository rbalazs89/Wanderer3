package object;

import main.GamePanel;

import java.awt.*;

public class OBJ_BookInLibrary extends SuperObject {
    public int soundNumber;

    public OBJ_BookInLibrary(GamePanel gp) {
        super(gp);
        getImage();
        interactable = true;
        getGlitterImages();
        textToShow = "";
        soundNumber = -1;
    }

    public OBJ_BookInLibrary(GamePanel gp, int soundNumber){
        this.gp = gp;
        this.soundNumber = soundNumber;
        getImage();
        interactable = true;
        getGlitterImages();
        textToShow = "";
    }

    public void getImage(){
        image = setup("/objects/wall/lectern");
    }

    public void interact(){
        currentlyInteracting = true;
        if(soundNumber != -1){
            gp.playDialogue(soundNumber);
        }
        // TODO allow to stop dialogue
    }

    public void drawSpecial(Graphics2D g2){
        if(currentlyInteracting){
            drawBookTextScreen(g2);
        }
        if(middleDistance(gp.player) >= GamePanel.tileSize * 3 / 2){
            currentlyInteracting = false;
        }
        if(currentlyInteracting && gp.keyH.escapePressed){
            currentlyInteracting = false;
            gp.keyH.escapePressed = false;
        }
    }

    public void setTextTownStatue(){
        textToShow = """
                No one remembers a time before the statue stood at the heart of the town.It was here before the first stones of the town were laid, before the roads were carved through the hills. Some say it was always here, watching./n
                The statue is unlike any known craft. Its weathered stone bears no chisel marks, no signs of erosion, as if time dares not touch it./n
                A lingering tale persists in whispers: the statue is alive. Not in the way of men or beasts, but something else entirely. They say it watches when unseen, that its eyes, flicker open when one is looking from far away. Those who have tried to catch it in the act report an eerie sensation—the certainty that they are being observed, even when standing alone in the square./n
                But the oldest and strangest legend speaks of a secret, one locked away within the statue itself. If a soul were ever to touch its cold surface while its unseen eyes were open - a feat requiring both reckless courage and impossible timing. Something would awaken. What? No one knows. Some believe it would unleash something better left undisturbed./n
                Many have attempted to solve the riddle, yet none have succeeded. And so the statue remains, unmoving. Watching. Waiting. /n""";
    }

    public void setTextMushrooms(){
        textToShow = """
                Excerpt from Flora and Fungi of the Realm, Vol. III/n
                Lapsa Celeris, commonly referred to as the Fleetfoot Mushroom, is a bioluminescent fungal species found in damp forested regions and shaded glades. Despite its unassuming ochre cap and slender white stalk, the mushroom exhibits a remarkable effect when ingested./n
                Upon consumption, Lapsa Celeris releases a unique alkaloid compound, mycothrine, which rapidly binds to neuromuscular receptors. This results in a temporary but dramatic increase in reflex speed and locomotor efficiency. Studies suggest that the compound stimulates the adrenal response while simultaneously reducing lactic acid buildup, allowing for heightened movement without the usual onset of fatigue./n
                Contrary to folk myths, the Fleetfoot Mushroom does not grant supernatural speed, nor does it cause the consumer to levitate. However, excessive ingestion has been reported to induce temporary dizziness, an uncontrollable urge to sprint, and, in rare cases, spontaneous giggling./n""";
    }

    public void setTextTurtles(){
        textToShow = "The Domesticated Loyalist Turtle (Testudo fidelis) is a remarkable species known for its unwavering devotion to its chosen human companion. Once bonded, these turtles exhibit behaviors more akin to a loyal hound than a reptile—following their owners (at their own leisurely pace), responding to names, and even displaying protective instincts./n" +

        "Curiously, T. fidelis possesses a well-documented sense of humor, frequently engaging in what can only be described as self-deprecating comedy. These turtles have been observed tapping their shells and quipping such remarks as, \"I'll be there in a flash—give me an hour!\" or \"I would run, but I don’t want to cause a windstorm.\"/n" +
                "Despite their well-earned reputation for slowness, some scholars whisper of an ancient bloodline of exceptionally fast turtles, their true abilities concealed behind a façade of sluggishness. Witnesses describe \"green blurs\" and echoing laughter near sewer grates. The academic community dismisses these claims as urban legend, though Dr. Hamato Yoshi’s infamous paper mentions redacted information discussing \"Mutation, Pizza Residue, and Radical Reptilian Vigilantism\".";
    }

    public void setTextPumpkinTrade(){
        textToShow = "The lifeblood of this village is, without question, its pumpkin trade. Unlike the common varieties found elsewhere, the pumpkins cultivated here grow to remarkable sizes, with a rich, almost honeyed flavor prized by traders and chefs alike. From humble farmsteads to bustling markets in distant cities, these pumpkins fuel the village’s entire economy, making the success of each harvest a matter of survival./n\n" +
                "\n" +
                "Yet, troubling reports have begun to surface. Caravans laden with goods have vanished along the main trade route, their remnants—if found at all—twisted among gnarled, blackened vines. Some blame common banditry, others whisper of something far worse. The villagers speak of vile growths creeping ever closer to the road—twisting tendrils that seem almost alive, pulsing with unnatural energy. The traders are growing wary, and fewer risk the journey with each passing week./n\n" +
                "\n" +
                "If these disappearances continue, the village faces economic ruin. There are whispers that the origins of these vines are related to the caves nearby. Unless a solution is found soon, the village’s greatest strength—its pumpkin trade—may well become its downfall./n";
    }

    public void setTextUnderWater(){
        textToShow = "Excerpt from Field Notes on the Unseen Corners of the World – Prof. Edric Vael/n" +
                "During my fortnight in the Pumpkin growing village, I documented no fewer than seventeen accounts of \"aquatic phantoms.\" Fishermen insist they’ve glimpsed humanoid figures with bioluminescent markings gliding beneath the surface, while children swear they’ve seen \"fish-knights\" patrolling the shallows at dusk. Most compelling was an elderly weaver’s claim: she witnessed a towering, coral-crowned silhouette rise from the lake’s center, only to dissolve into spray./n" +
        "Regardless of what lies beneath, the village lacks the means to explore it. Their diving equipment is crude, suited only for shallow waters, and no air supply lasts long enough for true investigation. However, in the capital, scholars and alchemists have long experimented with advanced breathing apparatuses—devices said to allow extended underwater exploration. Perhaps, in time, someone properly equipped will descend into the depths and finally uncover what truly waits beneath the lake.";
    }

    public void setTextExorcism(){
        textToShow = "Ergo draco maledicte [*a]/n\n" +
                "et omnis legio diabolica/n\n" +
                "adjuramus te./n\n" +
                "Cessa decipere humanas creaturas,/n\n" +
                "eisque aeternae Perditionis venenum propinare./n\n" +
                "\n" +
                "Vade, Satana, inventor et magister/n\n" +
                "omnis fallaciae, hostis humanae salutis./n\n" +
                "Humiliare sub potenti manu dei,/n\n" +
                "contremisce et effuge, invocato a/n\n" +
                "nobis sancto et terribili nomine,/n\n" +
                "quem inferi tremunt.";
    }

    public void setTextLetterToMyFamilyFromCaptain() {
        textToShow = "Dear Evelyn, /nIt is with a heavy heart that I write to you. Our search party located the remnants of the missing trade caravan two days past. The wagons were torn asunder, their cargo lost beneath a mass of vile, blackened vines—twisting growths unlike anything we have seen before. Among the fallen, we identified many of the missing, their rest disturbed only to grant them the dignity of a proper burial. But your youngest, was not among them./n\n" +
                "\n" +
                "This does not mean he is lost. Tracks led away from the wreckage, hurried and uneven, as if some among them fled into the night. Whether they escaped or were taken, we cannot yet say. We are continuing the search, and I swear to you—we will not stop until we have answers./n\n" +
                "\n" +
                "But Evelyn, I must be honest. The evil that plagues your village is not yours alone to bear. The same creeping vines have begun to surface in towns across the land, even at the gates of the capital itself. We no longer believe these are isolated misfortunes. Something is moving in the shadows, something old and terrible. The high walls of the city do little to keep out the whispers. People disappear in the night, and there are rumors—of figures watching from the rooftops, of voices echoing from beneath the streets where no man should tread.\n" +
                "\n" +
                "I tell you this not to sow fear, but so that you may prepare. What lurks in your forests may be but the first sign of something far greater.\n" +
                "\n" +
                "Stay vigilant. Stay strong. And do not lose hope.\n" +
                "\n" +
                "/nCaptain Aldric Varne\n" +
                "Third Legion, Royal Guard of the capital";
    }
}
