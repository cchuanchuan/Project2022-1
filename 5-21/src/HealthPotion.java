/**
 * Name: Cici Ao
 * Last Updated On: 5/31
 * Mrs. Kankelborg
 * APCS Period 2
 * Text Battle Project Part Two
 * 
 * This class is the represents a HealthPotion object which is an Item. It must contain all of the fields and methods 
 * detailed in the project spec. You 
 * may add additional fields and methods if you like.
 */
public class HealthPotion extends Item {
    public HealthPotion(String rarity) {
        super("Health Potion",rarity);
    }

    @Override
    //Determines the amount of health restored
    public void use(Player player){
        int points = getPoints();
        player.healDamage(points);
        System.out.println(player.getName() + " used a " + rarity + " Health Potion increasing their available health by " + points + " points.");
    }
}
