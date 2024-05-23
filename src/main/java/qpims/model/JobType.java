package qpims.model;

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
