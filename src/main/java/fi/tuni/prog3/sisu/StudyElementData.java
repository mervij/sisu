package fi.tuni.prog3.sisu;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.ArrayList;

/**
 *
 * An interface for defining common methods for 
 * ProgrammeData, ModuleData and CourseData classes.
 */
public interface StudyElementData {
    
    /**
     * Returns the Finnish name of the study element.
     * @param root study element's json file root as a Json Object. 
     * @return the name of the study element in Finnish.
     */
    static String readNameFI (JsonObject root){
        String nameFI = null;  
        
        if (root != null){   
            JsonObject name = root.getAsJsonObject("name");

            if (name.getAsJsonPrimitive("fi") != null){
                nameFI = name.getAsJsonPrimitive("fi").getAsString();
            }
        }   
        return nameFI;
    }
    
    /**
     * Returns the English name of the study element.
     * @param root study element's json file root as a Json Object. 
     * @return the name of the study element in English.
     */
    static String readNameEN (JsonObject root){
        String nameEN = null;
        if (root !=  null){
            JsonObject name = root.getAsJsonObject("name");

            if (name.getAsJsonPrimitive("en") != null){
                nameEN = name.getAsJsonPrimitive("en").getAsString();
            }
        } 
        return nameEN;
    }
    
    /**
     * Returns the minimum credits of the study element.
     * @param root study element's json file root as a Json Object. 
     * @param isCourse true if credits to be fetched are for a course, otherwise false.  
     * @return the minimum credits of the study element.
     */
    static int readCreditsMin (JsonObject root, boolean isCourse){
        int creditsMin = -1;
        JsonObject credits = null;
        
        if (root != null){
            if (!isCourse  
                && root.get("targetCredits") != null 
                && !root.get("targetCredits").isJsonNull()){
                credits  = root.getAsJsonObject("targetCredits");   
            }
            else if (root.get("credits") != null 
                    && !root.get("credits").isJsonNull()){  
                
                credits  = root.getAsJsonObject("credits");  
            }
            
            if (credits != null && !credits.get("min").isJsonNull()){
                creditsMin = credits.getAsJsonPrimitive("min").getAsInt();
            } 
        }
        
        return creditsMin;
    }
    
    /**
     * Returns the maximum credits of the study element.
     * @param root study element's json file root as a Json Object.
     * @param isCourse true if credits to be fetched are for a course, otherwise false.  
     * @return the maximum credits of the study element.
     */
    static int readCreditsMax (JsonObject root, boolean isCourse){
        int creditsMax = -1;
        JsonObject credits = null;
        
        if (root != null){
            if (!isCourse  
                && root.get("targetCredits") != null 
                && !root.get("targetCredits").isJsonNull()){
            
                credits  = root.getAsJsonObject("targetCredits");   
            }
            else if (root.get("credits") != null 
                    && !root.get("credits").isJsonNull()){     
                credits  = root.getAsJsonObject("credits");  
            }
            
            if (credits != null && !credits.get("max").isJsonNull()){
                creditsMax = credits.getAsJsonPrimitive("max").getAsInt();
            } 
        }
        return creditsMax;
    }
    
    /**
     * Returns the learning outcomes of the study element. Default is 
     * in Finnish, but if not found then returns outcomes in English.
     * @param outcomesElement the Json element of the learning outcomes.  
     * @return the learning outcomes of the study element.
     */
    static String readLearningOutcomes (JsonElement outcomesElement){
        String outcomes = "Osaamistavoitteita ei ole määritelty.";
        if (outcomesElement != null && !outcomesElement.isJsonNull()){

            JsonObject outcomesObject = outcomesElement.getAsJsonObject();

            if (outcomesObject.getAsJsonPrimitive("fi") != null){
                outcomes = "Osaamistavoitteet: " 
                        + outcomesObject.getAsJsonPrimitive("fi").getAsString();
            }
            else if (outcomesObject.getAsJsonPrimitive("en") != null){
                outcomes = "Osaamistavoitteet: " 
                        + outcomesObject.getAsJsonPrimitive("en").getAsString();
            }  
        }
        return outcomes;
    }

     /**
     * Finds the modules from JsonArray by checking the type. 
     * @param modules a JsonArray of modules from Sisu API.
     * @param moduleList a list of Module objects.
     * @return an updated list of found modules.
     */
    static ArrayList<Module> findModules(JsonArray modules, 
           ArrayList<Module> moduleList) {
        
        ArrayList<Module> list = moduleList;
        
        for (JsonElement m : modules){
            String type = m.getAsJsonObject().getAsJsonPrimitive("type").getAsString();

            if (type.equals("CompositeRule")){
                list = findModules(m.getAsJsonObject().getAsJsonArray("rules"), list);
            }

            if (type.equals("ModuleRule")){
                String id = m.getAsJsonObject().getAsJsonPrimitive("moduleGroupId").getAsString();
                if (id != null){
                    list.add(ModuleData.readBasicInformation(id));
                }     
            }
        }
        return list;
    }   
       
    /**
     * Finds the courses from JsonArray by checking the type. 
     * @param courses a JsonArray of courses.
     * @param courseList a list of Course objects.
     * @return an updated list of found courses.
     */
    static ArrayList<Course> findCourses(JsonArray courses, 
           ArrayList<Course> courseList) {
        
        ArrayList<Course> list = courseList;
        
        for (JsonElement c : courses){
            String type = c.getAsJsonObject().getAsJsonPrimitive("type").getAsString();
            
            if (type.equals("CompositeRule")){
                list = findCourses(c.getAsJsonObject().getAsJsonArray("rules"), list);
            }
            
            if (type.equals("CourseUnitRule")){
                String id = c.getAsJsonObject().getAsJsonPrimitive("courseUnitGroupId").getAsString();
                if (id != null){
                    list.add(CourseData.readBasicInformation(id));
                }
            }  
        }
        return list;
    }
    
    /**
     * Finds the right JsonArray of modules/courses 
     * from module's json data by checking the type.
     * @param root study element's json file root as a Json Object.
     * @return a JsonArray of modules/courses.
     */
    static JsonArray getModuleArray(JsonObject root){
        JsonObject rule = root.getAsJsonObject("rule");     
        String ruletype = rule.getAsJsonPrimitive("type").getAsString();

        while (!ruletype.equals("CompositeRule")){
            rule = rule.getAsJsonObject("rule");
            ruletype = rule.getAsJsonPrimitive("type").getAsString();
        }

        return rule.getAsJsonArray("rules");
    }
}
