package fi.tuni.prog3.sisu;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

/**
 *
 * A class for fetching json data of a module from Sisu API.
 */
public class ModuleData implements StudyElementData {
    
    private final static String URL_START = 
            "https://sis-tuni.funidata.fi/kori/api/modules/by-group-id?groupId=";
    private final static String URL_END = "&universityId=tuni-university-root-id";
    private final static String MODULE_ERROR = "Moduulia ei löytynyt!";
    private final static String COURSE_ERROR = "Kurssia ei löytynyt!";
    
    /**
     * 
     * Reads the basic information of the module from Sisu API 
     * and saves it to a Module object.
     * @param moduleId the id of the module.
     * @return a Module object.
     */
    public static Module readBasicInformation (String moduleId) {
        
        try {
            URL url = new URL(URL_START + moduleId + URL_END);
            InputStreamReader reader = 
                    new InputStreamReader(url.openStream(), StandardCharsets.UTF_8);
            
            JsonObject root = JsonParser.parseReader(reader).getAsJsonArray().get(0).getAsJsonObject();
            JsonElement outcomesElement = root.get("outcomes");

            String nameFI = StudyElementData.readNameFI(root);
            String nameEN = StudyElementData.readNameEN(root);
            int creditsMin = StudyElementData.readCreditsMin(root, false);
            int creditsMax = StudyElementData.readCreditsMax(root, false);
            String learningOutcomes = StudyElementData.readLearningOutcomes(outcomesElement);
        
            return new Module (moduleId, nameFI, nameEN, 
                    creditsMin, creditsMax, learningOutcomes);
        } 
        catch(IOException e){
            StatusDisplayer.setStatus(MODULE_ERROR, StatusDisplayer.DisplayMode.Error);
            throw new RuntimeException(MODULE_ERROR);  
        }
    }
   
    /**
     * Reads the submodules from module's json data.
     * @param moduleId the id of the module
     * @return a list of Module objects.
     */
    public static ArrayList <Module> readSubmodules (String moduleId) {
        
        JsonObject root = getRoot(moduleId);

        ArrayList <Module> moduleList = new ArrayList<>();
        JsonArray modules = StudyElementData.getModuleArray(root);

        return StudyElementData.findModules(modules, moduleList);   

    }
    
    /**
     * Reads the courses from module's json data.
     * @param moduleId the id of the module
     * @return a list of Course objects.
     */
    public static ArrayList <Course> readCourses (String moduleId) {

        JsonObject root = getRoot(moduleId);

        ArrayList <Course> courseList = new ArrayList<>();
        JsonArray courses = StudyElementData.getModuleArray(root);
        
        return StudyElementData.findCourses(courses, courseList);
    }
    
    /**
     * 
     * @param moduleId the id of the module
     * @return module's json file root as a JsonObject. 
     */
    private static JsonObject getRoot (String moduleId) {

        try {
            URL url = new URL(URL_START + moduleId + URL_END);
            InputStreamReader reader = new InputStreamReader(url.openStream());
            return JsonParser.parseReader(reader).getAsJsonArray().get(0).getAsJsonObject();
            
        }
        catch(IOException e){
            StatusDisplayer.setStatus(MODULE_ERROR, StatusDisplayer.DisplayMode.Error);
            throw new RuntimeException(MODULE_ERROR);
        }    
    }
}
