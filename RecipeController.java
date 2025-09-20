import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin; 
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@RestController
public class RecipeController {

    // This is your "notepad" to store the last recipe for each user.
    private final Map<String, String> userRecipeCache = new HashMap<>();

    // IMPORTANT: Replace "YOUR_API_KEY" with the key you just generated!
    private final String geminiApiKey = "AIzaSyBPdVdySoWsjAmBkSFUBbIWvb5q6sBSzyM";
    private final String geminiApiUrl = "https://generativelanguage.googleapis.com/v1beta/models/gemini-pro:generateContent?key=" + geminiApiKey;

    @CrossOrigin(origins = "*")
    @GetMapping("/get-recipe")
    public String getRecipe(@RequestParam String ingredients) {
        try {
            // 1. Create a prompt for the AI
            String prompt = "Generate a simple recipe with difficulty (Easy, Medium, or Hard) that uses the following ingredients: "
                          + ingredients
                          + ". Format it clearly with a name, difficulty, and instructions.";

            // 2. Use RestTemplate to call the Gemini API
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            String requestBody = "{\"contents\":[{\"parts\":[{\"text\": \"" + prompt + "\"}]}]}";
            HttpEntity<String> request = new HttpEntity<>(requestBody, headers);

            String result = restTemplate.postForObject(geminiApiUrl, request, String.class);

            // 3. Extract just the recipe text from Gemini's complex response
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(result);
            String recipeText = root.path("candidates").get(0).path("content").path("parts").get(0).path("text").asText();
            
            // 4. THIS IS THE KEY STEP: Store the generated recipe in your HashMap
            // We'll use a hardcoded user ID for now.
            String userId = "user123";
            userRecipeCache.put(userId, recipeText);

            // 5. Return the clean recipe text to the frontend
            return recipeText;

        } catch (Exception e) {
            e.printStackTrace();
            return "Error: Could not generate a recipe. Please check the API key and backend logs.";
        }
    }

    // You will need another endpoint for the streak feature later
    @CrossOrigin(origins = "*")
    @GetMapping("/made-it")
    public String madeRecipe() {
        String userId = "user123";
        if (userRecipeCache.containsKey(userId)) {
            System.out.println("User " + userId + " made the recipe: " + userRecipeCache.get(userId));
            // Add your streak logic here!
            return "Streak updated!";
        }
        return "No recipe found for you to make!";
    }
}