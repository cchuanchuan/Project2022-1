package q18;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

class Employee {
    private String name;
    private int id;
    private String employer;

    Employee() {
        this.name = "";
        this.id = 0;
        this.employer = "";
    }

    Employee(String name, int id, String employer) {
        this.name = name;
        this.id = id;
        this.employer = employer;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getID() {
        return this.id;
    }

    public void setID(int id) {
        this.id = id;
    }

    public String getEmployer() {
        return this.employer;
    }

    public void setEmployer(String employer) {
        this.employer = employer;
    }

    public String toString() {
        return this.name + " (" + this.id + ") at " + this.employer;
    }
}

public class Test {
    public static void main(String[] args) {
        Employee e1 = new Employee("Janet Donovan", 101, "Company2");
        System.out.println("My employee: " + e1);
//        Class employeeClass = e1.getClass();

        Class employeeClass = null;
        try {
            employeeClass = Class.forName("Employee");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


        System.out.println("\nEmployee class methods:");
        Method[] employeeMethods = employeeClass.getMethods();
        for (Method method : employeeMethods) {
            System.out.println(method.getName());
        }
        System.out.println("\nEmployee class declared methods:");
        Method[] employeeDeclaredMethods = employeeClass.getDeclaredMethods();
        for (Method method : employeeDeclaredMethods) {
            System.out.println(method.getName());
        }
        System.out.println("\nEmployee class declared constructors:");
        Constructor[] employeeDeclaredConstructors =
                employeeClass.getDeclaredConstructors();
        for (Constructor method : employeeDeclaredConstructors) {
            System.out.println(method.getName());
        }
    }
}