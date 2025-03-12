package fi.tuni.prog3.sisu;

/**
 *
 * A class for representing information of a course.
 */
public class Course extends StudyElement<Course> {

    /**
     * Constructs a Course object that is initialized
     * with a id, names and credits.
     * @param id id of the course.
     * @param nameFI name of the course in Finnish.
     * @param nameEN name of the course in English.
     * @param creditsMIN minimum credits of the course.
     * @param creditsMAX maximum credits of the course.
     * @param learningOutcomes learning outcomes of the course.
     */
    public Course (String id, String nameFI, String nameEN, 
            int creditsMIN, int creditsMAX, String learningOutcomes){
        super(id, nameFI, nameEN, creditsMIN, creditsMAX, learningOutcomes);
    }
    
    /**
     * Returns course's name and credits as a string. Default is a Finnish name, 
     * if it doesn't exist, an English name is returned. 
     * @return the name and credits of the course
     */
    @Override
    public String toString() {
        
        String name_credits = getNameEN();
        
        if (getNameFI() != null) {
            name_credits = getNameFI();
        }
        
        if (getCreditsMIN() == getCreditsMAX()){
            name_credits += " " + String.valueOf(getCreditsMIN()) + " op";
        }
        
        else {
            name_credits += " " + String.valueOf(getCreditsMIN()) + "-"
                    + String.valueOf(getCreditsMAX()) + " op";
        }

        return name_credits;
    }

    /**
     * Prints course as completed
     * @param credits -
     * @return returns same string as toString, but with \u2713 check mark
     */
    @Override
    public String toString(int credits) {
        return "\u2713" + this;
    }
}
