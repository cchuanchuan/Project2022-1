package q19;

class Dog {
    private String name;
    private Double weight;

    public Dog() {
        this.name = "Unknown";
        this.weight = 0.0;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String n) {
        this.name = n;
    }

    public Double getWeight() {
        return this.weight;
    }

    public void setWeight(Double w) {
        this.weight = w;
    }
}



public class Test {
    public static void main(String[] args) {
        // your code goes in here; create a new instance of Dog class, call it "dog1"
        Class dogClass = Dog.class;
        try {
            Dog dog = (Dog) dogClass.newInstance();
            System.out.println(dog);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public <T> void printAnything(T anyObject){
        System.out.println(anyObject);
    }

    public <T,K> boolean isEqual(T o1, K o2){
        // not null
        if (o1 == null && o2 == null){
            return true;
        }
        if (o1 == null || o2 == null) {
            return false;
        }

        return o1.equals(o2);
    }
}