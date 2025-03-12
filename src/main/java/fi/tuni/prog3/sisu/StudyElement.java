package fi.tuni.prog3.sisu;

public abstract class StudyElement<T extends StudyElement<?>> {
    private String id;
    private String nameFI;
    private String nameEN;
    private int creditsMIN;
    private int creditsMAX;
    private String learningOutcomes;

    /**
     * Default constructor
     */
    public StudyElement () {
        this.id = "";
        this.nameFI = "";
        this.nameEN = "";
        this.creditsMIN = 0;
        this.creditsMAX = 0;
        this.learningOutcomes = "";
    }

    /**
     * Constructs a StudyElement(Course, Module, Programme) object that 
     * is initialized with a id, names, credits and learning outcomes.
     * @param id id of the study element.
     * @param nameFI name of the study element in Finnish.
     * @param nameEN name of the study element in English.
     * @param creditsMIN minimum credits of the study element.
     * @param creditsMAX maximum credits of the study element.
     * @param learningOutcomes learning outcomes of the study element.
     */
    public StudyElement (String id, String nameFI, String nameEN, 
            int creditsMIN, int creditsMAX, String learningOutcomes){
        this.id = id;
        this.nameFI = nameFI;
        this.nameEN = nameEN;
        this.creditsMIN = creditsMIN;
        this.creditsMAX = creditsMAX;
        this.learningOutcomes = learningOutcomes;
    }

    /**
     * Returns the id of the study element.
     * @return the id of the study element.
     */
    public String getId(){
        return this.id;
    }

    /**
     * Returns the name of the study element in Finnish.
     * @return the name of the study element in Finnish.
     */
    public String getNameFI(){
        return this.nameFI;
    }

    /**
     * Returns the name of the study element in English.
     * @return the name of the study element in English.
     */
    public String getNameEN(){
        return this.nameEN;
    }

    /**
     * Returns the minimum credits of the study element.
     * @return the minimum credits of the study element.
     */
    public int getCreditsMIN(){
        return this.creditsMIN;
    }

    /**
     * Returns the maximum credits of the study element.
     * @return the maximum credits of the study element.
     */
    public int getCreditsMAX(){
        return this.creditsMAX;
    }
    
    /**
     * Returns the learning outcomes of the study element.
     * @return the learning outcomes of the study element.
     */
    public String getLearningOutcomes(){
        return this.learningOutcomes;
    }

    /**
     * Offers base how object is printed, (JavaFX uses this as default)
     * @return object as string
     */
    @Override
    public abstract String toString();

    /**
     * Offers base how object is printed, (JavaFX uses this as default), 
     * used when credits or checkmark needed to be printed
     * @param i credits/truthvalue
     * @return object as string
     */
    public abstract String toString(int i);
}
