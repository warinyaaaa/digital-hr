package th.co.geniustree.digitalhr.validator;

import org.springframework.beans.factory.annotation.Autowired;
import th.co.geniustree.digitalhr.model.Department;
import th.co.geniustree.digitalhr.model.Employee;
import th.co.geniustree.digitalhr.repo.DepartmentRepo;
import th.co.geniustree.digitalhr.repo.EmployeeRepo;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.List;
import java.util.Optional;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Constraint(validatedBy = EmployeeValid.EmployeeValidator.class)
public @interface EmployeeValid {

    String message() default "Username นี้ได้ทำการบันทึกไปแล้ว";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    class EmployeeValidator implements ConstraintValidator<EmployeeValid, Employee> {

        @Autowired
        EmployeeRepo repo;

        @Override
        public void initialize(EmployeeValid constraintAnnotation) {

        }

        @Override
        public boolean isValid(Employee employee, ConstraintValidatorContext constraintValidatorContext) {
            Optional<Employee> employeeList = this.repo.findByUsername(employee.getUsername());
            if (employeeList.isEmpty()){
                return true;
            } else {
                return false;
            }
        }
    }
}
