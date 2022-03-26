package th.co.geniustree.digitalhr.validator;

import org.springframework.beans.factory.annotation.Autowired;
import th.co.geniustree.digitalhr.enums.Position;
import th.co.geniustree.digitalhr.model.Employee;
import th.co.geniustree.digitalhr.model.TimeWork;
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
import java.util.stream.Collectors;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Constraint(validatedBy = EmployeeAdminValid.EmployeeValidator.class)
public @interface EmployeeAdminValid {

    String message() default "ตำแหน่งผู้ดูแลระบบเกินกำหนดแล้ว กรุณาระบุตำแหน่งอื่น";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    class EmployeeValidator implements ConstraintValidator<EmployeeAdminValid, Employee> {

        @Autowired
        EmployeeRepo repo;

        @Override
        public void initialize(EmployeeAdminValid constraintAnnotation) {

        }

        @Override
        public boolean isValid(Employee employee, ConstraintValidatorContext constraintValidatorContext) {
            if(employee.getPosition() == null){
                return true;
            }
            List<Employee> employeeList = this.repo.findByPosition(employee.getPosition());
            Position positions = Position.ADMIN;
            List<Employee> position = employeeList
                    .stream()
                    .filter(e -> e.getPosition().equals(positions))
                    .collect(Collectors.toList());

            if (position.isEmpty()){
                return true;
            } else {
                if (position.size() == 2) {
                    return false;
                } else {
                    return true;
                }
            }
        }
    }
}
