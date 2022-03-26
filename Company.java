package th.co.geniustree.digitalhr.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalTime;
import java.util.Objects;

@Entity
@Table(name = "COMPANY")
public class Company {
    @SequenceGenerator(name = "Company", sequenceName = "COMPANY_SEQ", allocationSize = 1)
    @GeneratedValue(generator = "Company", strategy = GenerationType.SEQUENCE)
    @Id
    @Column(name = "ID")
    private Integer id;

    @NotNull(message = "กรุณาระบุ")
    @Column(name = "NAME")
    private String name;

    @NotNull(message = "กรุณาระบุ")
    @Column(name = "ADDRESS")
    private String address;

    @NotNull(message = "กรุณาระบุ")
    @Column(name = "TEL")
    private String tel;

    @Column(name = "TIMEIN_DEADLINE")
    private LocalTime timeInDeadLine;

    @Column(name = "TIMEOUT_DEADLINE")
    private LocalTime timeOutDeadLine;


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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public LocalTime getTimeInDeadLine() {
        return timeInDeadLine;
    }

    public void setTimeInDeadLine(LocalTime timeInDeadLine) {
        this.timeInDeadLine = timeInDeadLine;
    }

    public LocalTime getTimeOutDeadLine() {
        return timeOutDeadLine;
    }

    public void setTimeOutDeadLine(LocalTime timeOutDeadLine) {
        this.timeOutDeadLine = timeOutDeadLine;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Company company = (Company) o;
        return id.equals(company.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
