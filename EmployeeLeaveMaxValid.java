package th.co.geniustree.digitalhr.validator;

import org.springframework.beans.factory.annotation.Autowired;
import th.co.geniustree.digitalhr.model.Leave;
import th.co.geniustree.digitalhr.model.LeaveMaxSetting;
import th.co.geniustree.digitalhr.model.TimeWork;
import th.co.geniustree.digitalhr.model.view.VEmployeeLeaveMax;
import th.co.geniustree.digitalhr.repo.LeaveMaxSettingRepo;
import th.co.geniustree.digitalhr.repo.LeaveRepo;
import th.co.geniustree.digitalhr.repo.TimeWorkRepo;
import th.co.geniustree.digitalhr.repo.ViewEmployeeLeaveMaxRepo;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Constraint(validatedBy = EmployeeLeaveMaxValid.EmployeeLeaveMaxValidator.class)
public @interface EmployeeLeaveMaxValid {

    String message() default "ได้ทำการบันทึกเกินวันลาคงเหลือแล้ว";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    class EmployeeLeaveMaxValidator implements ConstraintValidator<EmployeeLeaveMaxValid, Leave> {

        @Autowired
        ViewEmployeeLeaveMaxRepo employeeLeaveMaxRepo;
        @Autowired
        LeaveRepo leaveRepo;

        @Autowired
        LeaveMaxSettingRepo leaveMaxSettingRepo;

        @Override
        public void initialize(EmployeeLeaveMaxValid constraintAnnotation) {

        }

        @Override
        public boolean isValid(Leave leave, ConstraintValidatorContext constraintValidatorContext) {
            if (leave.getLeaveType() == null) {
                return true;
            }
            if (leave.getReasonLeave() == null) {
                return true;
            }
            if (leave.getStartDate() == null) {
                return true;
            }
            if (leave.getStartTime() == null) {
                return true;
            }
            if (leave.getEndDate() == null) {
                return true;
            }
            if (leave.getEndTime() == null) {
                return true;
            }
            try {
                List<Leave> leaves = this.leaveRepo.findByEmployeeIdAndLeaveType(leave.getEmployee().getId(), leave.getLeaveType());
                List<VEmployeeLeaveMax> vEmployeeLeaveMax = this.employeeLeaveMaxRepo.findByEmployeeIdAndLeaveType(leave.getEmployee().getId(), leave.getLeaveType());
                if (leave.getId() != null) {
                    leaves = leaves.stream().filter(e -> !e.getId().equals(leave.getId())).collect(Collectors.toList());
                }
                if (leaves.isEmpty()) {
                    boolean checkedMaxLeave = false;
                    LeaveMaxSetting byLeaveType = leaveMaxSettingRepo.findFirstByLeaveType(leave.getLeaveType());
                    BigDecimal totalHoursLeave = leave.getTotalDateLeave().multiply(new BigDecimal(BigInteger.valueOf(8))).add(leave.getTotalTimeLeave());
                    BigDecimal maxLeave = BigDecimal.valueOf(byLeaveType.getLeaveMax() * 8);
                    if (maxLeave.compareTo(totalHoursLeave) >= 0) {
                        checkedMaxLeave = true;
                    }
                    return checkedMaxLeave;
                } else {
                    Integer leaveMax = vEmployeeLeaveMax.stream().map(VEmployeeLeaveMax::getLeaveMax).collect(Collectors.toList()).get(0);
                    List<BigDecimal> totalHours = leaves.stream().filter(e -> e.getTotalDateLeave() != null).map(e -> (e.getTotalDateLeave().multiply(new BigDecimal(BigInteger.valueOf(8))).add(e.getTotalTimeLeave()))).collect(Collectors.toList());
                    BigDecimal sumHours = totalHours.stream().reduce(BigDecimal.ZERO, (a, b) -> a.add(b));
                    sumHours = sumHours.add((leave.getTotalDateLeave().multiply(new BigDecimal(BigInteger.valueOf(8))).add(leave.getTotalTimeLeave())));
                    BigDecimal bigDecimal = BigDecimal.valueOf(leaveMax * 8);
                    if (bigDecimal.compareTo(sumHours) >= 0) {
                        return true;
                    } else {
                        return false;
                    }
                }
            }catch (Exception e){
                return true;
            }
        }
    }
}
