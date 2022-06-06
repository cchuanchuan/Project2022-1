package q17;

public class Test {
    private static <K, V, D> void printObject(K keyObject, V valueObject, D descriptionObject) {
        System.out.print(keyObject + ": " + valueObject);
        System.out.print(keyObject+": "+valueObject+", "+descriptionObject);
    }

    public static void main(String[] args) {

        printObject("StringKey",101,"new third argument");
    }
}