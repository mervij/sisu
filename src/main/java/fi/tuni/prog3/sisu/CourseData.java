package fi.tuni.prog3.sisu;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;

/**
 *
 * A class for fetching json data of a course from Sisu API.
 */
public class CourseData implements StudyElementData {
    
    private final static String COURSE_ERROR = "Kurssia ei löytynyt!";
    
    /**
     * 
     * Reads the basic information of the course from Sisu API.
     * @param courseId the id of the course.
     * @return a Course object.
     */
    public static Course readBasicInformation (String courseId) {
        
        try {
            URL url = new URL(
                "https://sis-tuni.funidata.fi/kori/api/course-units/by-group-id?groupId="
                + courseId + "&universityId=tuni-university-root-id");
            InputStreamReader reader = new InputStreamReader(url.openStream(), StandardCharsets.UTF_8);
            JsonObject root = JsonParser.parseReader(reader).getAsJsonArray().get(0).getAsJsonObject();
            JsonElement outcomesElement = root.get("outcomes");

            String nameFI = StudyElementData.readNameFI(root);
            String nameEN = StudyElementData.readNameEN(root);
            int creditsMin = StudyElementData.readCreditsMin(root, true);
            int creditsMax = StudyElementData.readCreditsMax(root, true);
            String learningOutcomes = StudyElementData.readLearningOutcomes(outcomesElement);

            Course course = new Course (courseId, nameFI, nameEN, 
                    creditsMin, creditsMax, learningOutcomes);
                 
            return course;     
        } 
        catch(IOException e){
            StatusDisplayer.setStatus(COURSE_ERROR, StatusDisplayer.DisplayMode.Error);
            throw new RuntimeException(COURSE_ERROR);    
        }
    }
}
