package th.co.geniustree.digitalhr.resource;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import th.co.geniustree.digitalhr.dto.ChangePasswordRequest;
import th.co.geniustree.digitalhr.dto.EmployeeSearchRequest;
import th.co.geniustree.digitalhr.dto.ResetPasswordRequest;
import th.co.geniustree.digitalhr.enums.Position;
import th.co.geniustree.digitalhr.enums.StatusWork;
import th.co.geniustree.digitalhr.model.AuthorityEntity;
import th.co.geniustree.digitalhr.model.Employee;
import th.co.geniustree.digitalhr.repo.AuthorityRepo;
import th.co.geniustree.digitalhr.repo.EmployeeRepo;
import th.co.geniustree.digitalhr.repo.spec.EmployeeSpec;
import th.co.geniustree.digitalhr.security.EmployeeAuthentication;
import th.co.geniustree.digitalhr.service.EmployeeService;
import th.co.geniustree.digitalhr.validator.EmployeeHeadValid;
import th.co.geniustree.digitalhr.validator.group.EmployeeGroup;

import javax.validation.groups.Default;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
public class EmployeeResource {
    private EmployeeRepo repo;
    private EmployeeService service;
    private PasswordEncoder passwordEncoder;
    private AuthorityRepo authorityRepo;

    public EmployeeResource(EmployeeRepo repo, EmployeeService service, PasswordEncoder passwordEncoder, AuthorityRepo authorityRepo) {
        this.repo = repo;
        this.service = service;
        this.passwordEncoder = passwordEncoder;
        this.authorityRepo = authorityRepo;
    }

    @GetMapping("/employee/{id}")
    public Optional<Employee> find(@PathVariable Integer id) {
        return repo.findById(id);
    }

    @PostMapping("/employee")
    public Employee save(@Validated({Default.class, EmployeeGroup.class}) @RequestBody Employee s, EmployeeAuthentication emp) {
        if (s.getPosition() == Position.STAFF) {
            //ตำแนห่งเป็น พนักงาน
            Set<AuthorityEntity> authoritySet = new HashSet<>();
            authoritySet.add(authorityRepo.getById(2));
            s.setAuthorities(authoritySet);
        } else if (s.getPosition() == Position.HEAD) {
            //ตำแหน่งเป็น หัวหน้าแผนก
            Set<AuthorityEntity> authoritySet = new HashSet<>();
            authoritySet.add(authorityRepo.getById(2));
            authoritySet.add(authorityRepo.getById(3));
            s.setAuthorities(authoritySet);
        } else if (s.getPosition() == Position.ADMIN) {
            //ตำแหน่งเป็น เจ้าหน้าที่
            Set<AuthorityEntity> authoritySet = new HashSet<>();
            authoritySet.add(authorityRepo.getById(1));
            s.setAuthorities(authoritySet);
        }
        s.setStatusWork(StatusWork.WORKING);
        // set default password
        s.setPassword(passwordEncoder.encode("password"));
        return repo.save(s);
    }

    @PostMapping("/employee-save")
    public Employee edit(@Validated({Default.class}) @RequestBody Employee s) {
        if (s.getPosition() == Position.STAFF) {
            //ตำแนห่งเป็น พนักงาน
            Set<AuthorityEntity> authoritySet = new HashSet<>();
            authoritySet.add(authorityRepo.getById(2));
            s.setAuthorities(authoritySet);
        } else if (s.getPosition() == Position.HEAD) {
            //ตำแหน่งเป็น หัวหน้าแผนก
            Set<AuthorityEntity> authoritySet = new HashSet<>();
            authoritySet.add(authorityRepo.getById(2));
            authoritySet.add(authorityRepo.getById(3));
            s.setAuthorities(authoritySet);
        } else if (s.getPosition() == Position.ADMIN) {
            //ตำแหน่งเป็น เจ้าหน้าที่
            Set<AuthorityEntity> authoritySet = new HashSet<>();
            authoritySet.add(authorityRepo.getById(1));
            s.setAuthorities(authoritySet);
        }
        if (s.getStatusWork().equals(StatusWork.LEAVE)){
            s.setUsername(s.getId().toString());
        }
        return repo.save(s);
    }

    @GetMapping("/employee")
    public Page<Employee> findAll(EmployeeSearchRequest searchEmployee, Pageable pageable) {
        Specification<Employee> predicted = EmployeeSpec.employeeNameLike(searchEmployee.getFirstName())
                .and(EmployeeSpec.employeeLastNameLike(searchEmployee.getLastName()))
                .and(EmployeeSpec.employeeDeptLike(searchEmployee.getDepartment()))
                .and(EmployeeSpec.employeePositionLike(searchEmployee.getPosition()));
        Specification<Employee> specification = Specification.where(predicted);
        return repo.findAll(specification, pageable);
    }

    @GetMapping("/employee/search")
    public List<Employee> findByKeyword(String keyword) {
        ;
        return service.findTop5BySearchTerm(keyword);
    }

    @DeleteMapping("/employee/{id}")
    public void delete(@PathVariable Integer id) {
        repo.deleteById(id);

    }

    @PostMapping("/employee/change-password")
    public Employee changePassword(@Validated(Default.class) @RequestBody ChangePasswordRequest request, EmployeeAuthentication emp) {
        request.setId(emp.getPrincipal().getId());
        return service.changePassword(request);
        //TODO  Great
        //1. assert old password
        //2. check new password 1-2 is same
        //3. encode new password then set to employee.password
        //4. save employee
    }

    @PostMapping("/employee/reset-password")
    public Employee resetPassword(@Validated(Default.class) @RequestBody ResetPasswordRequest request) {
        return service.resetPassword(request);
    }

    @GetMapping("/employee-by-header")
    public Page<Employee> findAllByHeader(EmployeeSearchRequest searchEmployee, EmployeeAuthentication emp, Pageable pageable) {
        Specification<Employee> specification = Specification.where(EmployeeSpec.employeeNameLike(searchEmployee.getFirstName()))
                .and(EmployeeSpec.employeeLastNameLike(searchEmployee.getLastName()))
                .and(EmployeeSpec.position(emp.getPrincipal().getPosition()))
                .and(EmployeeSpec.department(emp.getPrincipal().getDepartment()))
                .and(EmployeeSpec.employeeStatusLike(StatusWork.WORKING));
        return repo.findAll(specification, pageable);
    }

}
