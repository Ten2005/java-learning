package learning.demo;

/**
 * Demonstrates class creation concepts in an entertaining way using a
 * robot factory example. Shows instance variables, class variables,
 * constructors, methods, access modifiers and both inner and static
 * nested classes.
 */
public class RobotFactoryDemo {
    // class variables shared across all factories
    private static int factoriesCreated = 0;
    private static int robotsBuilt = 0;

    // instance variables
    private String name;
    private int nextId = 1;

    /**
     * Constructor initializes factory name and increments global count.
     */
    public RobotFactoryDemo(String name) {
        this.name = name;
        factoriesCreated++;
    }

    /**
     * Builds a robot with the given type and returns it.
     */
    public Robot buildRobot(String type) {
        Robot robot = new Robot(nextId++, type);
        robotsBuilt++;
        return robot;
    }

    /**
     * Class method returning how many factories have been created.
     */
    public static int getFactoriesCreated() {
        return factoriesCreated;
    }

    /**
     * Class method returning total robots built across all factories.
     */
    public static int getRobotsBuilt() {
        return robotsBuilt;
    }

    /**
     * Inner class representing a robot built by this factory.
     */
    public class Robot {
        private int id;
        private String type;

        private Robot(int id, String type) {
            this.id = id;
            this.type = type;
        }

        public void performDuty() {
            System.out.println(name + " built robot #" + id + " performing " + type);
        }
    }

    /**
     * Static nested class for displaying summary statistics.
     */
    public static class Statistics {
        public static void printSummary() {
            System.out.println("Factories created: " + factoriesCreated);
            System.out.println("Total robots built: " + robotsBuilt);
        }
    }

    /**
     * Entry point demonstrating the factory and robot classes.
     */
    public static void main(String[] args) {
        RobotFactoryDemo tokyoFactory = new RobotFactoryDemo("Tokyo Factory");
        RobotFactoryDemo osakaFactory = new RobotFactoryDemo("Osaka Factory");

        RobotFactoryDemo.Robot r1 = tokyoFactory.buildRobot("Welding");
        RobotFactoryDemo.Robot r2 = tokyoFactory.buildRobot("Assembly");
        RobotFactoryDemo.Robot r3 = osakaFactory.buildRobot("Painting");

        r1.performDuty();
        r2.performDuty();
        r3.performDuty();

        RobotFactoryDemo.Statistics.printSummary();
    }
}
