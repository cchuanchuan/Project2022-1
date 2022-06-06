/**
 * Name: Cici Ao
 * Last Updated On: 5/31
 * Mrs. Kankelborg
 * APCS Period 2
 * Text Battle Project Part Two
 * 
 * This class is the represents a Mage object which is a Player. It must contain all of the f
 * fields and methods detailed in the project spec. You 
 * may add additional fields and methods if you like.
 */
public class Warrior extends Player {

    private double shieldStrength;

    public Warrior(String name, double shieldStrength) {
        super(name, 125, 5, 15);
//        this.health = 125;
//        this.maxHealth = 125;
//        this.minDmg = 5;
//        this.maxDmg = 15;
        this.shieldStrength = shieldStrength;
        if (shieldStrength <=0 || shieldStrength >=1 ){
        	this.shieldStrength = 0.1;
        }
    }

    @Override
    //Reduces the incoming damage by shieldstrength*dmg and display a message. If shieldstrength*dmg is less than 1 then block 1 point of damage
    public void takeDamage(int damage) {
    	int  block = (int) (shieldStrength*damage);
    	if(block < 1) {
    		block = 1;
    	}
        damage = damage - block;
        System.out.println(getName()+" blocks "+block+" points of damage.");
        if (health - damage < 0) {
            health = 0;
        } else{
            health = health - damage;
        }
    }
    
    public double getBlock() {
    	return this.shieldStrength;
    }
}
