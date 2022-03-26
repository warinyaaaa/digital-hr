package th.co.geniustree.digitalhr.repo;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import th.co.geniustree.digitalhr.model.Department;
import th.co.geniustree.digitalhr.model.TimeWork;

import java.util.List;
import java.util.Optional;


public interface DepartmentRepo extends JpaRepository<Department, Integer>, JpaSpecificationExecutor<Department> {

    Optional<Department> findById(String id);
    List<Department> findByName(String name);

    @Query("select department " +
            " from Department department " +
            " where :keyword is null or " +
            " (:keyword is not null and" +
            " (department.name like %:keyword%))")
    Page<Department> findByKeyword(@Param("keyword") String term, Pageable pageable);

}
