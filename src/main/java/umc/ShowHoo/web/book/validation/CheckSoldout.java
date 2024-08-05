package umc.ShowHoo.web.book.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = CheckSoldoutValidator.class)
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface CheckSoldout {
    String message() default "해당 공연의 표는 매진되었습니다.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
