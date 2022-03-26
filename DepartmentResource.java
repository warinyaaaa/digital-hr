package th.co.geniustree.digitalhr.resource;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import th.co.geniustree.digitalhr.dto.DepartmentSearchRequest;
import th.co.geniustree.digitalhr.dto.EmployeeSearchRequest;
import th.co.geniustree.digitalhr.enums.StatusWork;
import th.co.geniustree.digitalhr.model.Department;
import th.co.geniustree.digitalhr.model.Employee;
import th.co.geniustree.digitalhr.repo.CompanyRepo;
import th.co.geniustree.digitalhr.repo.DepartmentRepo;
import th.co.geniustree.digitalhr.repo.spec.DepartmentSpec;
import th.co.geniustree.digitalhr.repo.spec.EmployeeSpec;
import th.co.geniustree.digitalhr.security.EmployeeAuthentication;
import th.co.geniustree.digitalhr.service.DepartmentService;
import th.co.geniustree.digitalhr.validator.group.DepartmentGroup;

import javax.validation.groups.Default;
import java.util.List;
import java.util.Optional;

import static th.co.geniustree.digitalhr.model.Employee_.department;

@RestController
public class DepartmentResource {
    private DepartmentRepo repo;
    private DepartmentService service;
    private CompanyRepo companyRepo;

    public DepartmentResource(DepartmentRepo repo, DepartmentService service, CompanyRepo companyRepo) {
        this.repo = repo;
        this.service = service;
        this.companyRepo = companyRepo;
    }

    @GetMapping("/department/{id}")
    public Optional<Department> find(@PathVariable Integer id) {
        return repo.findById(id);
    }

    @PostMapping("/department")
    public Department save(@Validated({Default.class, DepartmentGroup.class}) @RequestBody Department s) {
        s.setCompany(companyRepo.getById(1));
        return repo.save(s);
    }

    @PostMapping("/department-edit")
    public Department edit( @RequestBody Department s) {
        s.setCompany(companyRepo.getById(1));
        return repo.save(s);
    }

    @GetMapping("/department")
    public Page<Department> findAll(DepartmentSearchRequest searchDepartment, Pageable pageable) {
        Specification<Department> predicted = DepartmentSpec.departmentNameLike(searchDepartment.getName());
        Specification<Department> specification = Specification.where(predicted);
        return repo.findAll(specification, pageable);
    }

    @GetMapping("/department/search")
    public List<Department> findByKeyword(String keyword) {
        ;
        return service.findTop5BySearchTerm(keyword);
    }

    @GetMapping("/department-list")
    public List<Department> findAll() {
        return repo.findAll();
    }

    @GetMapping("/department-status")
    public List<Department> findStatus() {
        Specification<Department> predicted = DepartmentSpec.departmentStatusLike(true);
        Specification<Department> specification = Specification.where(predicted);
        return repo.findAll(specification);
    }

    @DeleteMapping("/department/{id}")
    public void delete(@PathVariable Integer id) {
        repo.deleteById(id);
    }
}
