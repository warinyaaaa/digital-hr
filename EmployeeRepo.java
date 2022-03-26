package th.co.geniustree.digitalhr.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import th.co.geniustree.digitalhr.enums.Position;
import th.co.geniustree.digitalhr.model.Department;
import th.co.geniustree.digitalhr.model.Employee;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepo extends JpaRepository<Employee, Integer>, JpaSpecificationExecutor<Employee> {
    Optional<Employee> findByUsername(String username);
    List<Employee> findByPosition(Position position);
    List<Employee> findByDepartment(Department department);

    @Query("select employee " +
            " from Employee employee " +
            " where :keyword is null or " +
            " (:keyword is not null and" +
            " (employee.firstName like %:keyword% or " +
            "  employee.lastName like %:keyword% )) ")
    Page<Employee> findByKeyword(@Param("keyword") String term, Pageable pageable);
}
