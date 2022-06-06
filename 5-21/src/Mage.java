/**
 * Name: Cici Ao
 * Last Updated On: 5/31
 * Mrs. Kankelborg
 * APCS Period 2
 * Text Battle Project Part Two
 * 
 * This class is the represents a Mage object which is a Player. It must contain all of the fields and methods 
 * detailed in the project spec. You 
 * may add additional fields and methods if you like.
 */

public class Mage extends Player {
    private int mana;
    private int maxMana;

    
    public Mage(String name, int mana) {
        super(name);
        this.health = 50;
        this.maxHealth = 50;
        this.minDmg = 5;
        this.maxDmg = 10;
        if (mana <= 0){
            this.mana = 50;
            this.maxMana = 50;
        }else {
            this.mana = mana;
            this.maxMana = mana;
        }
    }

    @Override
    //This calculates the mana that cost to attack and display the remaining mana then attack
    public int attack(Monster monster) {
        int damage = minDmg + (int) (Math.random() * ((maxDmg - minDmg) + 1));
        int useMage = (int) (Math.random() * (maxMana/2)+ 1);
        if (mana < useMage){
            System.out.println("Not enough mana!");
            return 0;
        }
        mana = mana - useMage;
        System.out.println(name+" has "+mana+" mana left.");
        monster.takeDamage(damage);
        return damage;
    }
    //restore the mana by the parameter amount
    public void restoreMana(int mana){
        if (mana + this.mana <= maxMana) {
            this.mana = mana + this.mana;
        }else {
            this.mana = maxMana;
        }
    }
    public int getMana() {
    	return mana;
    }
    // calculate the amount of mana by the parameter
    public void useMana(int mana) {
    	this.mana -= mana;
    	if (this.mana < 0) {
    		this.mana = 0;
    	}
    }
}
