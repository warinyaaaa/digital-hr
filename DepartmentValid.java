package th.co.geniustree.digitalhr.validator;

import org.springframework.beans.factory.annotation.Autowired;
import th.co.geniustree.digitalhr.model.Department;
import th.co.geniustree.digitalhr.model.TimeWork;
import th.co.geniustree.digitalhr.repo.DepartmentRepo;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Constraint(validatedBy = DepartmentValid.DepartmentValidator.class)
public @interface DepartmentValid {

    String message() default "แผนกนี้ได้ทำการบันทึกไปแล้ว";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    class DepartmentValidator implements ConstraintValidator<DepartmentValid, Department> {

        @Autowired
        DepartmentRepo repo;

        @Override
        public void initialize(DepartmentValid constraintAnnotation) {

        }

        @Override
        public boolean isValid(Department department, ConstraintValidatorContext constraintValidatorContext) {
            List<Department> departmentList = this.repo.findByName(department.getName());
            if (departmentList.isEmpty()){
                return true;
            } else {
                return false;
            }

        }

        /*@Override
        public boolean isValid(TimeWork timeWork, ConstraintValidatorContext constraintValidatorContext) {
            List<TimeWork> workList = this.timeWorkRepo.findByEmployeeId(timeWork.getEmployee().getId());
            LocalDate dateNow = LocalDate.now();
            // เช็ควันที่
            List<TimeWork> timeWorks = workList
                    .stream()
                    .filter(e -> e.getWorkDate().equals(dateNow))
                    .collect(Collectors.toList());

            if (timeWorks.isEmpty()) {
                return true;
            } else {
                return false;
            }
        }*/
    }
}
