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
 * A class for fetching json data of a degree programme from Sisu API.
 */
public class ProgrammeData implements StudyElementData{
    
    private final static String URL_BASE = 
            "https://sis-tuni.funidata.fi/kori/api/modules/";
    private final static String PROG_ERROR = "Tutkinto-ohjelmaa ei löytynyt!";
        
    /**
     * 
     * Reads the basic information of the degree programme from Sisu API 
     * and saves it to a Programme object.
     * @param programmeId the id of the programme.
     * @return a Programme object.
     */
    public static Programme readBasicInformation (String programmeId) {
        
        try {
            URL url = new URL(URL_BASE + programmeId);
            InputStreamReader reader = 
                    new InputStreamReader(url.openStream(), StandardCharsets.UTF_8);
            JsonObject root = JsonParser.parseReader(reader).getAsJsonObject();
            JsonElement outcomesElement = root.get("learningOutcomes");

            String nameFI = StudyElementData.readNameFI(root);
            String nameEN = StudyElementData.readNameEN(root);
            int creditsMin = StudyElementData.readCreditsMin(root, false);
            int creditsMax = StudyElementData.readCreditsMax(root, false);
            String learningOutcomes = StudyElementData.readLearningOutcomes(outcomesElement);

            Programme programme = new Programme (programmeId, nameFI, nameEN, 
                    creditsMin, creditsMax, learningOutcomes);

            return programme;   
        } 
        catch(IOException e){
            StatusDisplayer.setStatus(PROG_ERROR, StatusDisplayer.DisplayMode.Error);
            throw new RuntimeException(PROG_ERROR);     
        }           
    }
    
    /**
     * Reads the modules of the degree programme from Sisu API. 
     * @param programmeId the id of the programme.
     * @return a list of Module objects
     */
    public static ArrayList <Module> readModules(String programmeId) {
        
        try {
            URL url = new URL(URL_BASE + programmeId);
            InputStreamReader reader = new InputStreamReader(url.openStream());
            JsonObject root = JsonParser.parseReader(reader).getAsJsonObject();
            
            ArrayList <Module> moduleList = new ArrayList<>();
            JsonArray modules = StudyElementData.getModuleArray(root);

            return StudyElementData.findModules(modules, moduleList);  
            
        }
        catch(IOException e){
            StatusDisplayer.setStatus(PROG_ERROR, StatusDisplayer.DisplayMode.Error);
            throw new RuntimeException(PROG_ERROR);     
        }  
    }
}
