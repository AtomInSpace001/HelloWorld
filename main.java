import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@SpringBootApplication
@RestController
public class BirdApp {

    // ArrayList to hold bird info
    private List<Bird> birds = new ArrayList<>();

    public static void main(String[] args) {
        SpringApplication.run(BirdApp.class, args);
    }

    @PostMapping("/birds")
    public String addBird(@RequestBody Bird bird) {
        birds.add(bird);
        return "Bird added";
    }

    @GetMapping("/birds")
    public List<Bird> getBirds() {
        return birds;
    }
}

