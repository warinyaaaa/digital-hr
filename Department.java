package th.co.geniustree.digitalhr.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import th.co.geniustree.digitalhr.validator.DepartmentValid;
import th.co.geniustree.digitalhr.validator.group.DepartmentGroup;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Objects;

@Table(name = "DEPARTMENT")
@DepartmentValid(groups = {DepartmentGroup.class})
@Entity
public class Department {
    @SequenceGenerator(name = "Department", sequenceName = "DEPARTMENT_SEQ", allocationSize = 1)
    @GeneratedValue(generator = "Department", strategy = GenerationType.SEQUENCE)
    @Id
    @Column(name = "ID")
    private Integer id;

    @NotNull(message = "กรุณาระบุ")
    @Column(name = "NAME")
    private String name;

    @Column(name = "STATUS")
    private Boolean status = Boolean.TRUE;

    @JoinColumn(name = "COMPANY_ID")
    @ManyToOne
    private Company company;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Department that = (Department) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
