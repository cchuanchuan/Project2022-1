/**
 * Name: Cici Ao
 * Last Updated On: 5/31
 * Mrs. Kankelborg
 * APCS Period 2
 * Text Battle Project Part Two
 * 
 * This class is the represents a MagePotion object which is an Item. It must contain all of the fields
 * and methods detailed in the project spec. You 
 * may add additional fields and methods if you like.
 */
public class ManaPotion extends Item {
    public ManaPotion(String rarity) {
        super("Mana Potion", rarity);
     
    }
    //checks if the player is a mage, if so then determine the amount of mana restored, if not then display a message
    public void use(Player player){
        if (player instanceof Mage) {
            int points = getPoints();
            System.out.println(player.getName()+" used a "+getRarity()+" Mana Potion increasing their available mana by "+points+" points.");
            Mage mage = (Mage) player;
            mage.restoreMana(points);
        } else {
            System.out.println("Only a mage can use a Mana Potion!");
        }
    }
}
