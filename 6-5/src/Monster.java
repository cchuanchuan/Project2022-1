/**
 * Name: Cici Ao
 * Last Updated On: 5/31
 * Mrs. Kankelborg
 * APCS Period 2
 * Text Battle Project Part Two
 * 
 * This class is the represents a Monster object. It must contain all of the fields and
 * methods detailed in the project spec. You may add additional fields and methods if you
 * like.
 */
public class Monster {
    private final String type;
    private int health;
    private int minDmg;
    private int maxDmg;
    //This is the Monster constructor that sets health to 100, minimum damage to 1, and maximum damage to 10
    //as well as sets type to a passed type
    public Monster(String type) {
        this.type = type;
        health = 100;
        minDmg = 1;
        maxDmg = 10;
    }
    //This is the Monster constructor that  will sets type, health, minDmg, and maxDmg to a passed type, health, minDmg, and maxDmg
    //If health is not greater than 0 set it to 100, if minDmg is not positive set it to 1, if maxDmg is not greater than minDmg set it to minDmg + 10 
    public Monster(String type, int health, int minDmg, int maxDmg) {
        this.type = type;
        this.health = health;
        this.minDmg = minDmg;
        this.maxDmg = maxDmg;

        if (this.health <= 0) {
            this.health = 100;
        }
        if (this.minDmg <= 0) {
            this.minDmg = 1;
        }
        if (this.maxDmg <= this.minDmg) {
            this.maxDmg = this.minDmg + 10;
        }
    }
    //This will return the monster type
    public String getType() {
        return type;
    }
    //This will return the monster health
    public int getHealth() {
        return health;
    }
    //This method takes the amount of damage done as a parameter, and reduces the monster's health by that much. And the health won't drop below 0 
    public void takeDamage(int damage) {
        if (health - damage < 0) {
            health = 0;
        } else {
            health = health - damage;
        }
    }
    //This will calculates a random integer between minDmg and maxDmg inclusive and reduces the Player's health by the damage amount calculated.
    //And it will returns the amount of damage
    public int attack(Player player) {
        int damage = minDmg + (int) (Math.random() * ((maxDmg - minDmg) + 1));
        player.takeDamage(damage);
        return damage;
    }
    //This will returns "The " + type + " has " + health + " health left." if health is greater than zero
    //And it will returns "The " + type + " is dead." if health is equal to zero.
//    public String toString() {
//        if (health > 0) {
//            String outPut = "The " + type + " has " + health + " health left.";
//            return outPut;
//        } else if (health == 0) {
//            String outPut = "The " + type + " is dead.";
//            return outPut;
//        }
//        return "";
//    }
  //This will returns "The " + type + " has " + health + " health left." if health is greater than zero
  //And it will returns "The " + type + " is dead." if health is equal to zero.
    public String toString() {
    	if (health > 0) {
    		return "The " + type + " has " + health + " health left.";
    	} else {
    		return "The " + type + " is dead.";
    	}
    }
	public int getMinDamage() {
		return minDmg;
	}
	public int getMaxDamage() {
		return maxDmg;
	}
    
}
