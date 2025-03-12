package fi.tuni.prog3.sisu;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonElement;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

/**
 *
 * A class for fetching json data of university's degree programmes from Sisu API.
 * 
 */
public class UniversityData {
    
    private final static String PROGRAMME_ERROR = "Tutkinto-ohjelmia ei pystytty hakemaan";
        
    /**
     * Constructs a list of Programme objects from data that is fetched from Sisu API.
     * @return list of Programme objects.
     */
    public static List<Programme> getProgrammes () {
        
        try {           
            URL url = new URL("https://sis-tuni.funidata.fi/kori/api/"
                    + "module-search?curriculumPeriodId=uta-lvv-2021"
                    + "&universityId=tuni-university-root-id"
                    + "&moduleType=DegreeProgramme&limit=1000");
            InputStreamReader reader = new InputStreamReader(url.openStream(), StandardCharsets.UTF_8);
            List <Programme> programmes = new ArrayList<>();
            JsonObject root = JsonParser.parseReader(reader).getAsJsonObject();
            JsonArray results = root.getAsJsonArray("searchResults");

            for (JsonElement result : results){
                String id = result.getAsJsonObject().getAsJsonPrimitive("id").getAsString();

                if (id != null){
                    Programme programme = ProgrammeData.readBasicInformation(id);
                    programmes.add(programme);
                }
            }     
            return programmes;
        }
        catch(IOException e){
            StatusDisplayer.setStatus(PROGRAMME_ERROR, StatusDisplayer.DisplayMode.Error);
            throw new RuntimeException(PROGRAMME_ERROR);
        }    
    }
}