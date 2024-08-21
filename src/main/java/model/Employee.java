package model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Employee {
    @Builder.Default private Integer id = null;
    private String name;
    private double salary;
    private String division;
    private String jobTitle;
    private String ssn;
}
