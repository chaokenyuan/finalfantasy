package net.game.finalfantasy.test;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class LombokTestClass {
    private final String name;
    private final int value;
    private String description;
    
    public static void main(String[] args) {
        // Test @RequiredArgsConstructor
        LombokTestClass test = new LombokTestClass("Test", 42);
        
        // Test @Data generated getters
        System.out.println("[DEBUG_LOG] Name: " + test.getName());
        System.out.println("[DEBUG_LOG] Value: " + test.getValue());
        
        // Test @Data generated setter
        test.setDescription("This is a test");
        System.out.println("[DEBUG_LOG] Description: " + test.getDescription());
        
        // Test @Data generated toString
        System.out.println("[DEBUG_LOG] toString: " + test.toString());
        
        // Test @Data generated equals and hashCode
        LombokTestClass test2 = new LombokTestClass("Test", 42);
        test2.setDescription("This is a test");
        System.out.println("[DEBUG_LOG] Equals: " + test.equals(test2));
        System.out.println("[DEBUG_LOG] HashCode same: " + (test.hashCode() == test2.hashCode()));
        
        System.out.println("[DEBUG_LOG] Lombok annotations are working correctly!");
    }
}