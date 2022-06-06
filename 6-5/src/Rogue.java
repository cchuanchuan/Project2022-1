/**
 * Name: Cici Ao
 * Last Updated On: 5/31
 * Mrs. Kankelborg
 * APCS Period 2
 * Text Battle Project Part Two
 * 
 * This class is the represents a Rogue object which is a Player. It must contain all of the fields and 
 * methods detailed in the project spec. You 
 * may add additional fields and methods if you like.
 */
public class Rogue extends Player {
    private double critChance;
 
    public Rogue(String name, double critChance) {
        super(name);
        this.health = 75;
        this.maxHealth = 75;
        this.minDmg = 1;
        this.maxDmg = 10;
        if (critChance >0 && critChance<1){
            this.critChance = critChance;
        }else {
            this.critChance = 0.1;
        }
    }

    @Override
    //Uses the critchance to determine if there's critical damage. If so, display a message then attack, if no attack
    public int attack(Monster monster) {
        int damage = minDmg + (int) (Math.random() * ((maxDmg - minDmg) + 1));
        boolean hasCrit = Math.random() <= this.critChance;
        if (hasCrit) {
            System.out.println("Name gets a critical hit!");
            damage = damage*2;
        }
        monster.takeDamage(damage);
        return damage;
    }
    public double getCrit() {
    	return this.critChance;
    }
}
