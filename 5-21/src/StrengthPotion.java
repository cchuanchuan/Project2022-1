/**
 * Name: Cici Ao
 * Last Updated On: 5/31
 * Mrs. Kankelborg
 * APCS Period 2
 * Text Battle Project Part Two
 * 
 * This class is the represents a StrenghPotion object which is an Item. It must contain all of the fields 
 * and methods detailed in the project spec. You 
 * may add additional fields and methods if you like.
 */
public class StrengthPotion extends Item {

    public StrengthPotion(String rarity) {
        super("Strength Potion",rarity);
      
    }

    @Override
    //Determines the amount mindmg and maxdmg are increased by
    public void use(Player player){
        int points = getPoints();
        player.getStronger(points);
        System.out.println(player.getName() + " used a " + rarity + " Strength Potion increasing their min and max damage by " + points + " points.");
    }
}
