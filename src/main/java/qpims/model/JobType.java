package qpims.model;

// Enum class for JobType that defines the different types of jobs that can be assigned to a booking
public enum JobType {
    ELECTRICAL,
    PLUMBING,
    STRUCTURAL,
    CLEANING,
    GARDENING,
    PEST_CONTROL;
    // Mapping function to convert Java enum names to MySQL enum names
    public String toMySQLName() {
        return name().replace("_", " ");
    }
    // Mapping function to convert MySQL enum names to Java enum names
    public static JobType fromMySQLName(String name) {
        return valueOf(name.replace(" ", "_").toUpperCase());
    }
    
}
