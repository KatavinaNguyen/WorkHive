package utils;

@FunctionalInterface
public interface InputValidator {
    boolean isValid(String input);
}