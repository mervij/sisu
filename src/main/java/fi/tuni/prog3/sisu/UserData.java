package fi.tuni.prog3.sisu;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * A class for saving and fetching json data of a user.
 */
public class UserData {
    private static final String FILE = "users.json";
    private static List<User> users;
    private final static String USER_NOT_FOUND = "Käyttäjätietojen haku ei onnistunut!";
    private final static String SAVE_ERROR = "Tallennus ei onnistunut!";
    
    /**
     * 
     * Returns the User object of the student. 
     * The User object is fetched from the json of users. 
     * @param studentNumber the student number of the user
     * @return the User object of the student
     */
    public static User getUser(String studentNumber) {
        GsonBuilder builder = new GsonBuilder();

        try (Reader reader = Files.newBufferedReader(Paths.get(FILE), StandardCharsets.UTF_8)){
            users = builder.create().fromJson(reader, new TypeToken<List<User>>() {}.getType());
        }
        catch (IOException e) {
            StatusDisplayer.setStatus(USER_NOT_FOUND, StatusDisplayer.DisplayMode.Error);
            throw new RuntimeException(USER_NOT_FOUND);  
        }

        if (users != null)
        {
            for (User user : users) {
                if (user.getStudentNumber().equals(studentNumber)) {
                    return user;
                }
            }
        }
        
        return new User(studentNumber);
        
    }
    
    /**
     * Saves user's data to json file of users. If user already has saved data, 
     * the old User object will be removed and then updated User object is added 
     * to the file. 
     * @param user a User object
     */
    public static void saveUserData(User user) {
        
        if (hasUsersData(user.getStudentNumber())){
            removeUser(user.getStudentNumber());
        }
        
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        if (users == null)
        {
            users = new ArrayList<>();
        }

        users.add(user);
        
        try (Writer writer = new FileWriter(FILE, StandardCharsets.UTF_8)) {
            gson.toJson(users, writer);
            writer.flush(); 
            writer.close(); 
        }
        catch (IOException e) {
            StatusDisplayer.setStatus(SAVE_ERROR, StatusDisplayer.DisplayMode.Error);
            throw new RuntimeException(SAVE_ERROR);  
        }
    }
    
    /**
     * Removes the user from the list of users. 
     * @param studentNumber the student number of the user to be removed. 
     */
    private static void removeUser(String studentNumber){
        users.removeIf(user -> user.getStudentNumber().equals(studentNumber));
    }
    
    /**
     * Checks whether there is already any data for the specific user. 
     * @param studentNumber the student number of the user. 
     * @return true, if user already exists in the list, otherwise false. 
     */
    private static boolean hasUsersData(String studentNumber){
        if (users == null)
        {
            return false;
        }

        return users.stream().anyMatch(user -> (user.getStudentNumber().equals(studentNumber)));
    }
}
