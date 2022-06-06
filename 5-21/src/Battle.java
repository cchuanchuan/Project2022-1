import java.util.Scanner;

/**
 * Name: Cici Ao
 * Last Updated On: 5/31
 * Mrs. Kankelborg
 * APCS Period 2
 * Text Battle Project Part Two
 * 
 * This class is the application class. You can use the main method in this class for
 * testing purposes. My test code will call your startBattle method directly with my own
 * version of the code you will write in main. 
 */
public class Battle {

    /**
     * Use this method for playing your game. I will bypass this in my test code, but I will
     * be looking at its contents when I grade for internal correctness.
     */
    //This is the main method that takes a class. By invoking the startBattle method, the combat will begin.
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.print("Choose your class (Rogue, Warrior, or Mage)");
        String chooseClass = input.nextLine();

        System.out.print("What is your name?");
        String name = input.nextLine();
        Player player = null;
        if (chooseClass.equals("Rogue")){
            player = new Rogue(name,-1);
        }else if(chooseClass.equals("Warrior")) {
            player = new Warrior(name,-1);
        }else {
            player = new Mage(name,-1);
        }

        String[] monsters = {"dragon", "dragon", "dragon"};

//        while (true) {
            int monsterType = (int) (Math.random() * 3);
            Monster monster = new Monster(monsters[monsterType]);
            startBattle(player, monster, input);
//            if (player.getHealth() < 0){
//                System.out.print("Do you want to quit?(y/n)");
//                String quit = input.nextLine();
//                if (quit.equals("y")){
//                    break;
//                }
//            }else {
//                break;
//            }
//
//        }

        input.close();
    }

    /**
	  * This is the method that my test cases will call directly.
	  * Do not modify the header of this method.
	  * @param player
	  * @param monster
	  * @param input
	  */
    //This is the same as text battle 1 but it reward the player if they win
    public static void startBattle(Player player, Monster monster, Scanner input) {
        int round = 1;
        System.out.println(player.getName() + " has encountered a " + monster.getType() + "!");
        System.out.println();
   
        while(monster.getHealth() > 0 && player.getHealth() > 0) {
        	System.out.println("********************************** ROUND " + round + " **********************************");
        	System.out.println();
        	System.out.println("Your inventory holds: " + player.getInventory());
            System.out.print("Type an inventory slot number or 0 to attack: ");
        	System.out.print("Type an inventory slot number or 0 to attack: ");
	        int collect = Integer.parseInt(input.nextLine());
//	        System.out.println();
	        if (collect == 0) {
	            System.out.println(player.getName() + " attacks the " + monster.getType() + " for " + player.attack(monster) + " damage.");
	            System.out.println(monster);
	        } else {
	            player.useItem(collect - 1);
	        }
	        if(monster.getHealth() > 0) {
	        	System.out.println();
		        System.out.println("The " + monster.getType() + " attacks " + player.getName() + " for " + monster.attack(player) + " damage.");
		        System.out.println(player);
	        }
	        System.out.println();
	        System.out.println();
	
	        round++;
        }
        if (monster.getHealth() == 0) {
        	System.out.println();
        	System.out.println(player.getName() + " has defeated the " + monster.getType());
	
        	int health = (int) (Math.random() * (20 + 1));
        	player.healDamage(health);
        	System.out.println(player.getName() + " has been rewarded with "+health+" points of health back.");
	
        	Item item = getLoot(player);
        	System.out.println(player.getName()+" has been rewarded with a "+item+".");
        	player.receiveItem(item);
        }
        else{ 
        	System.out.println();
        	System.out.println("The " + monster.getType() + " has defeated " + player.getName());
        }
	        
    }


    public static Item getLoot(Player player) {
    	 double rarityRandom = Math.random();
         String rarity = "";
         if (rarityRandom < 0.05) {
             rarity = Item.EPIC_RARITY;
         }else if (rarityRandom < 0.15) {
             rarity = Item.GREATER_RARITY;
         }else  if (rarityRandom < 0.35){
             rarity = Item.BASIC_RARITY;
         }else {
             rarity = Item.LESSER_RARITY;
         }

         double itemRandom = Math.random();
         
         Item item = null;
         if (itemRandom < 0.2) {
             item = new StrengthPotion(rarity);
         }else if(itemRandom < 0.4
             && player instanceof Mage) {
             item = new ManaPotion(rarity);
         }else {
             item = new HealthPotion(rarity);
         }
         
         return item;
    }
}