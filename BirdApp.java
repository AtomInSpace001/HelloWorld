import java.util.*;

public class BirdApp {

    // ArrayList to hold bird info
    private List<Bird> birds = new ArrayList<>();

    public static void main(String[] args) {
        BirdApp app = new BirdApp();
        app.run();
    }

    public void run() {
        // Add some sample birds
        Bird bird1 = new Bird();
        bird1.setColor("Red");
        bird1.setLocation("Forest");
        bird1.setSize(10);
        bird1.setActivity("Flying");
        bird1.setMonth(3);
        
        birds.add(bird1);
        
        // Display all birds
        System.out.println("All birds:");
        for (Bird bird : birds) {
            System.out.println("Color: " + bird.getColor() + 
                             ", Location: " + bird.getLocation() + 
                             ", Size: " + bird.getSize() + 
                             ", Activity: " + bird.getActivity() + 
                             ", Month: " + bird.getMonth());
        }
    }

    public String addBird(Bird bird) {
        birds.add(bird);
        return "Bird added: " + bird.getActivity();
    }

    public List<Bird> getBirds() {
        return birds;
    }
}
