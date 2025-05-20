package learning.demo;

/**
 * Demonstrates class creation features such as instance variables, class
 * variables, constructors, methods, access modifiers and inner classes.
 */
public class OuterClassDemo {
    // class variable (static field) shared among all instances
    private static int instanceCount = 0;

    // instance variables
    private String name;
    private int value;

    /**
     * Constructor that initializes instance variables and increments the
     * class variable.
     */
    public OuterClassDemo(String name, int value) {
        this.name = name;
        this.value = value;
        instanceCount++;
    }

    /**
     * Instance method that prints information about this object.
     */
    public void display() {
        System.out.println("Name: " + name + ", Value: " + value);
    }

    /**
     * Class method that returns how many instances have been created.
     */
    public static int getInstanceCount() {
        return instanceCount;
    }

    /**
     * Inner class that can access the outer class's instance variables.
     */
    public class Multiplier {
        private int factor;

        public Multiplier(int factor) {
            this.factor = factor;
        }

        public int multiplyValue() {
            return value * factor; // access outer instance variable
        }
    }

    /**
     * Entry point demonstrating usage of the above features.
     */
    public static void main(String[] args) {
        OuterClassDemo demo1 = new OuterClassDemo("First", 10);
        OuterClassDemo demo2 = new OuterClassDemo("Second", 20);

        demo1.display();
        demo2.display();

        Multiplier multiplier = demo2.new Multiplier(3);
        System.out.println("Multiplier result: " + multiplier.multiplyValue());

        System.out.println("Total instances created: " + OuterClassDemo.getInstanceCount());
    }
}
