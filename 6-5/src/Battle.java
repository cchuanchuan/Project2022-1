import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

/**
 * Name: Cici Ao Last Updated On: 5/31 Mrs. Kankelborg APCS Period 2 Text Battle
 * Project Part Two
 * <p>
 * This class is the application class. You can use the main method in this
 * class for testing purposes. My test code will call your startBattle method
 * directly with my own version of the code you will write in main.
 */
public class Battle {

    static List<Level> levelList;
    static Rank currentRank;
    static int currentLevel = 0;
    static List<Rank> rankList;

    static {
        levelList = new ArrayList<>();

        levelList.add(new Level("Level1", "You met monster1, please be carefully"
                , new Monster("Monster1", 10, 1, 5), 10));
        levelList.add(new Level("Level2", "You met monster1, please be carefully"
                , new Monster("Monster2", 15, 2, 6), 10));
        levelList.add(new Level("Level3", "You met monster1, please be carefully"
                , new Monster("Monster3", 20, 3, 7), 10));
        levelList.add(new Level("Level4", "You met monster1, please be carefully"
                , new Monster("Monster4", 25, 4, 8), 10));
        levelList.add(new Level("Level5", "You met monster1, please be carefully"
                , new Monster("Monster5", 30, 5, 9), 10));
        levelList.add(new Level("Level6", "You met monster1, please be carefully"
                , new Monster("Monster6", 35, 6, 10), 10));
        levelList.add(new Level("Level7", "You met monster1, please be carefully"
                , new Monster("Monster7", 40, 7, 11), 10));
        levelList.add(new Level("Level8", "You met monster1, please be carefully"
                , new Monster("Monster8", 45, 8, 12), 10));
        levelList.add(new Level("Level9", "You met monster1, please be carefully"
                , new Monster("Monster9", 50, 9, 13), 10));

        rankList = FileUtils.readFile();
    }

    /**
     * Use this method for playing your game. I will bypass this in my test code,
     * but I will be looking at its contents when I grade for internal correctness.
     */
    // This is the main method that takes a class. By invoking the startBattle
    // method, the combat will begin.
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        boolean endTheGame = false;
        while (!endTheGame) {
            System.out.println("Please choose menu:");
            System.out.println("1. Check the rank board");
            System.out.println("2. Start the game");
            System.out.println("3. Quit");

            String choose = input.nextLine();
            switch (choose.trim()) {
                case "1": {
                    showRankMenu(input);
                    break;
                }
                case "2": {

                    System.out.print("Choose your class (Rogue, Warrior, or Mage)");
                    String chooseClass = input.nextLine();
                    System.out.print("What is your name?");
                    String name = input.nextLine();
                    currentRank = new Rank(name,0);
                    Player player = null;
                    if (chooseClass.equals("Rogue")) {
                        player = new Rogue(name, -1);
                    } else if (chooseClass.equals("Warrior")) {
                        player = new Warrior(name, -1);
                    } else {
                        player = new Mage(name, -1);
                    }
                    showGameMenu(input, player);
                    break;
                }
                case "3": {
                    endTheGame = true;
                    System.out.println("Quit success!");
                }
                default: {
                    System.out.println("Wrong input!");
                    continue;
                }
            }
        }

        input.close();


    }

    public static void showGameMenu(Scanner input, Player player) {

        while (currentLevel < levelList.size()) {
            Level level = levelList.get(currentLevel);
            System.out.println("You are going to new level " + (currentLevel + 1) + ": " + level.getLevelName());
            System.out.println(level.getLevelStory());
            Monster monster = level.getMonster();

            startBattle(player, monster, input);
            if (player.getHealth() > 0) {
                System.out.println("Congratulation to pass " + level.getLevelName());
                System.out.println();
                currentLevel++;
                currentRank.setPoint(currentRank.getPoint()+level.getPoint());
            } else {
                System.out.println("Sorry you fail to pass " + level.getLevelName());
                break;
            }
        }
        System.out.println("Game over, you get "+currentRank.getPoint()+" points");
        System.out.println();
        System.out.println();

        rankList.add(currentRank);
        Collections.sort(rankList);
        FileUtils.writeToFile(rankList);
    }

    public static void showRankMenu(Scanner input) {
        System.out.println("Play name\tpoint");
        for (Rank r :
                rankList) {
            System.out.println(r.getPlayerName()+"\t"+r.getPoint());
        }
        System.out.println();
        System.out.println();

    }

    /**
     * This is the method that my test cases will call directly. Do not modify the
     * header of this method.
     *
     * @param player
     * @param monster
     * @param input
     */
    // This is the same as text battle 1 but it reward the player if they win
    public static void startBattle(Player player, Monster monster, Scanner input) {
        int round = 1;
        System.out.println(player.getName() + " has encountered a " + monster.getType() + "!");
//        System.out.println();
//        System.out.println();
        while (monster.getHealth() > 0 && player.getHealth() > 0) {
            System.out.println();
            System.out.println(
                    "********************************** ROUND " + round + " **********************************");
            System.out.println();
            System.out.println("Your inventory holds: " + player.getInventory());
            System.out.print("Type an inventory slot number or 0 to attack: ");
            int collect = Integer.parseInt(input.nextLine());
            System.out.println();
            if (collect == 0) {
                System.out.println(player.getName() + " attacks the " + monster.getType() + " for "
                        + player.attack(monster) + " damage.");
                System.out.print(monster);
                System.out.println();
            } else {
                player.useItem(collect - 1);
            }
            if (monster.getHealth() > 0) {
                System.out.println();
                System.out.println("The " + monster.getType() + " attacks " + player.getName() + " for "
                        + monster.attack(player) + " damage.");
                System.out.println(player);

            }

            // System.out.println();
            System.out.println();

            round++;
        }

        if (monster.getHealth() == 0) {
            // System.out.println();
            System.out.println(player.getName() + " has defeated the " + monster.getType());

            int health = (int) (Math.random() * 20 + 1);
            player.healDamage(health);
            System.out.println(player.getName() + " has been rewarded with " + health + " points of health back.");

            Item item = getLoot(player);
            System.out.println(player.getName() + " has been rewarded with a " + item + ".");
            player.receiveItem(item);
        } else {
            // System.out.println();
            System.out.println("The " + monster.getType() + " has defeated " + player.getName());
        }

    }

    public static Item getLoot(Player player) {
        double rarityRandom = Math.random();
        String rarity = "";
        if (rarityRandom <= 0.05) {
            rarity = "Epic";
        } else if (rarityRandom <= 0.15) {
            rarity = "Greater";
        } else if (rarityRandom <= 0.35) {
            rarity = "Basic";
        } else {
            rarity = "Lesser";
        }

        double itemRandom = Math.random();

        Item item = null;
        if (player instanceof Mage) {
            if (itemRandom <= 0.2) {
                item = new ManaPotion(rarity);
            } else if (itemRandom <= 0.4) {
                item = new StrengthPotion(rarity);
            } else {
                item = new HealthPotion(rarity);
            }
        } else {
            if (itemRandom <= 0.2) {
                item = new StrengthPotion(rarity);
            } else {
                item = new HealthPotion(rarity);
            }
        }

//         if (itemRandom <= 0.2) {
//        	 item = new ManaPotion(rarity);
//         }else if(itemRandom <= 0.4 && player instanceof Mage) {
//        	 item = new StrengthPotion(rarity);
//         }else {
//             item = new HealthPotion(rarity);
//         }

        return item;
    }
}