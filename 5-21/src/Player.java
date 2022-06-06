import java.util.ArrayList;

/**
 * Name: Cici Ao
 * Last Updated On: 5/31
 * Mrs. Kankelborg
 * APCS Period 2
 * Text Battle Project Part Two
 * 
 * This class is the represents a Player object. It must contain all of the fields and
 * methods detailed in the project spec. You may add additional fields and methods if you
 * like.
 */
public class Player {
    protected final String name;
    protected int health;
    protected ArrayList<Item> inventory;
    protected int maxHealth;
    protected int minDmg;
    protected int maxDmg;

    //This is the Player constructor that sets health and maxhealth to 100, minimum damage to 1, and maximum damage to 10
    //as well as sets name, health, maxhealth, minDmg, and maxDmg to a passed name, health, maxhealth, minDmg, and maxDmg
    //This also set inventory to hold 2 Health Potions, 2 Strength Potions, and 1 Unknown Item. 
    public Player(String name) {
        this.name = name;
        this.health = 100;
        this.maxHealth = 100;
        this.minDmg = 1;
        this.maxDmg = 10;
        inventory = new ArrayList<>();
        inventory.add(new HealthPotion(Item.LESSER_RARITY));
        inventory.add(new HealthPotion(Item.LESSER_RARITY));
        inventory.add(new StrengthPotion(Item.LESSER_RARITY));
        inventory.add(new StrengthPotion(Item.LESSER_RARITY));
        inventory.add(new ManaPotion(Item.LESSER_RARITY));
    }
    //This is the Player constructor that sets name, health, minDmg, and maxDmg to a passed name, health, minDmg, and maxDmg
    //This will set the health to 100 if it's not greater than 0. And set maxHealth to health. 
    //If minDmg is not greater than 0 set it to 1. If maxDmg is not greater than minDmg set it to minDmg + 10. 
    //This will also set inventory to hold 2 Health Potions, 2 Strength Potions, and 1 item of any other type.
    public Player(String name, int health, int minDmg, int maxDmg) {
        this(name);
        this.health = health;
        this.minDmg = minDmg;
        this.maxDmg = maxDmg;

        if (this.health <= 0) {
            this.health = 100;
        }
        if (this.minDmg <= 0) {
            this.minDmg = 1;
        }
        if (this.maxDmg < this.minDmg) {
            this.maxDmg = this.minDmg + 10;
        }
        this.maxHealth = this.health;
    }
    // This is the Player constructor that sets name to passed name and set other private field to a set value 
    // It will set health and maxHealth to 100. And set minDmg to 1 and maxDmg to 10. 
    // Amd if inventory is null set inventory to hold 2 Health Potions, 2 Strength Potions, and 1 item of any other type.
    public Player(String name, ArrayList<Item> inventory) {
        this(name);

        this.health = 100;
        this.maxHealth = 100;

        if (inventory != null){
            this.inventory = inventory;
        }
        this.minDmg = 1;
        this.maxDmg = 10;

    }
    //This will return player's name
    public String getName() {
        return name;
    }
    //This will return player's health
    public int getHealth() {
        return health;
    }
    //This will return player's min damage
    public int getMinDamage() {
        return minDmg;
    }
  //This will return player's max damage
    public int getMaxDamage() {
        return maxDmg;
    }
    
    //This will calculates a random integer between minDmg and maxDmg inclusive and reduces the monster's health by the damage amount calculated.
    //And it will returns the amount of damage
    public int attack(Monster monster) {
        int damage = minDmg + (int) (Math.random() * ((maxDmg - minDmg) + 1));
        monster.takeDamage(damage);
        return damage;
    }
    
    //This method takes the amount of damage done as a parameter, and reduces the player's health by that much. And the health won't drop below 0 
    public void takeDamage(int damage) {
        if (health - damage < 0) {
            health = 0;
        } else{
			health = health - damage;
		}
    }
    //This useItem method will access the inventory at the appropriate index. If the element at that location is null or the index is out of bounds it will print “Invalid Selection – Missed Turn”
    //Otherwise, call the elements use method using this as the parameter and set the element in the array to null
    public void useItem(int index) {
        if (index >= 0 && index < inventory.size()) {
            inventory.get(index).use(this);
            inventory.remove(index);
        } else {
            System.out.println("Invalid Selection - Missed Turn");
        }
    }
    //This healDamage mathod will increase play's health by the parameter amount and doesn't let health pass maxHealth
    public void healDamage(int heal) {
        if (health + heal < maxHealth) {
            health += heal;
        } else {
            health = maxHealth;
        }
    }
    //This will increase the player’s minDmg and maxDmg by parameter amount
    public void getStronger(int stronger) {
        minDmg += stronger;
        maxDmg += stronger;
    }
    //This will returns a String representing the contents of a Player’s inventory.
    public String getInventory() {
        if (inventory.isEmpty()){
            return "No Items";
        }
        String entire = "";
        for (int i = 0; i < inventory.size(); i++) {
            entire += (i + 1) + ": ";
            entire += inventory.get(i) + " ";
        }
        
        return entire; 
    }
  //This will returns "The " + type + " has " + health + " health left." if health is greater than zero
    //And it will returns "The " + type + " is dead." if health is equal to zero.
    public String toString() {
        if (health > 0) {
            String ret = name + " has " + health + " health left.";
            return ret;
        } else if (health == 0) {
            String ret = name + " is dead.";
            return ret;
        }
        return "";
    }
    //takes a parameter item and add to the end of the inventory 
    public void receiveItem(Item item){
        this.inventory.add(item);
    }

}
