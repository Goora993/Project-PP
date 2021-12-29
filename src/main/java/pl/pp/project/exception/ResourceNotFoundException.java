package pl.pp.project.exception;

public class ResourceNotFoundException extends NullPointerException {

    public ResourceNotFoundException(String name, String field, Integer value) {
        super("Cannot find " + name + " with " + field + ": " + value);
    }
}
