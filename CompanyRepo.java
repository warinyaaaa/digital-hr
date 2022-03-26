package th.co.geniustree.digitalhr.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import th.co.geniustree.digitalhr.model.Company;

public interface CompanyRepo extends JpaRepository<Company,Integer> {
}
