package th.co.geniustree.digitalhr.resource;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import th.co.geniustree.digitalhr.model.Company;
import th.co.geniustree.digitalhr.repo.CompanyRepo;
import th.co.geniustree.digitalhr.service.CompanyService;

import java.util.List;
import java.util.Optional;

@RestController
public class CompanyResource {
    private CompanyRepo repo;
    private CompanyService service;

    public CompanyResource(CompanyRepo repo, CompanyService service) {
        this.repo = repo;
        this.service = service;
    }

    @GetMapping("/company/{id}")
    public Optional<Company> find(@PathVariable Integer id) {
        return repo.findById(id);
    }

    @PostMapping("/company")
    public Company save(@RequestBody @Validated  Company s) {
        return repo.save(s);
    }

    @PostMapping("/company/{id}")
    public Company edit(@PathVariable Integer id) {
        Company company = repo.findById(id).orElseThrow();
        return service.save(company);
    }


    @GetMapping("/company")
    public Page<Company> findAll(Pageable pageable) {
        return repo.findAll(pageable);
    }
}
