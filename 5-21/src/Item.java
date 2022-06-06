/**
 * Name: Cici Ao
 * Last Updated On: 5/31
 * Mrs. Kankelborg
 * APCS Period 2
 * Text Battle Project Part Two
 * 
 * This class is the represents an Item object. It must contain all of the fields and
 * methods detailed in the project spec. You may add additional fields and methods if you
 * like.
 */

public class Item {
	protected String rarity;
	public static final String LESSER_RARITY = "Lesser";
	public static final String BASIC_RARITY = "Basic";
	public static final String GREATER_RARITY = "Greater";
	public static final String EPIC_RARITY = "Epic";

	private String type;
	//This is the Item constructor that sets item type to Unknown Item
	public Item() {
		this.rarity = LESSER_RARITY;
		type = "Unknown Item";
	}
	//This is the Item constructor that sets item to a certain Item that is passed as a parameter
	public Item (String type, String rarity) {
		this.type = type;
		if(rarity.equals(LESSER_RARITY)||rarity.equals(BASIC_RARITY)||rarity.equals(GREATER_RARITY)||rarity.equals(EPIC_RARITY)) {
			this.rarity = rarity;
		}
		else {
			this.rarity = LESSER_RARITY;
		}
		
	}
	//This will return the item type
	public String toString(){
		if(type == null) {
			return "empty slot";
		}
		return rarity+" "+type;
	}
	//This is the use method that will create a random number from 1-10, if it's a health potion or strength potion, it will heal or gain strength
	//with the random number that user chose. If it's niether, it will print unclear result.
	public void use(Player player){
		System.out.println(player.getName()+" used an item with an unclear result.");
	}
	
	//This returns a random number base on the rarity
	public int getPoints(){
		int num = (int) (Math.random()*5);
		if (rarity.equals(LESSER_RARITY)){
			num+=1;
		}
		else if (rarity.equals(BASIC_RARITY)){
			num+=6;
		}
		else if (rarity.equals(GREATER_RARITY)){
			num+=11;
		}
		else if (rarity.equals(EPIC_RARITY)){
			num+=16;
		}
		else {
			num = 0;
		}
		return num;
	}

	public String getRarity() {
		return rarity;
	}


	public String getType() {
		return type;
	}

}
