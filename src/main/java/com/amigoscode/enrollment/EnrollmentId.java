package com.amigoscode.enrollment;


import jakarta.persistence.Embeddable;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
@Embeddable
public class EnrollmentId {

    private Long customerId;
    private Long courseId;
}
