package th.co.geniustree.digitalhr.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.CreatedDate;
import th.co.geniustree.digitalhr.enums.Position;
import th.co.geniustree.digitalhr.enums.StatusWork;
import th.co.geniustree.digitalhr.validator.DepartmentValid;
import th.co.geniustree.digitalhr.validator.EmployeeAdminValid;
import th.co.geniustree.digitalhr.validator.EmployeeHeadValid;
import th.co.geniustree.digitalhr.validator.EmployeeValid;
import th.co.geniustree.digitalhr.validator.group.DepartmentGroup;
import th.co.geniustree.digitalhr.validator.group.EmployeeGroup;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "EMPLOYEE")
@EmployeeValid(groups = {EmployeeGroup.class})
@EmployeeHeadValid
@EmployeeAdminValid
public class Employee {
    @SequenceGenerator(name = "Employee", sequenceName = "EMPLOYEE_SEQ", allocationSize = 1, initialValue = 2)
    @GeneratedValue(generator = "Employee", strategy = GenerationType.SEQUENCE)
    @Id
    @Column(name = "ID")
    private Integer id;

    @NotNull(message = "กรุณาระบุ")
    @Column(name = "FIRST_NAME")
    private String firstName;

    @NotNull(message = "กรุณาระบุ")
    @Column(name = "LAST_NAME")
    private String lastName;

    @NotNull(message = "กรุณาระบุ")
    @Column(name = "GENDER")
    private String gender;

    @NotNull(message = "กรุณาระบุ")
    @Column(name = "DATE_BORN")
    private LocalDate dateBorn;

    @NotNull(message = "กรุณาระบุ")
    @Column(name = "ADDRESS")
    private String address;

    @NotNull(message = "กรุณาระบุ")
    @Column(name = "TEL")
    private String tel;

    @NotNull(message = "กรุณาระบุ")
    @Column(name = "POSITION")
    @Enumerated(EnumType.STRING)
    private Position position;

    @Column(name = "STATUS_WORK")
    @Enumerated(EnumType.STRING)
    private StatusWork statusWork;

    @NotNull(message = "กรุณาระบุ")
    @Column(name = "START_WORK")
    private LocalDate startWork;

    @NotNull(message = "กรุณาระบุ")
    @Column(name = "USERNAME")
    private String username;

    @Column(name = "PASSWORD")
    private String password;

    @NotNull(message = "กรุณาระบุ")
    @Pattern(regexp = "^\\s*([\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}(\\s*,{0,1}\\s*[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4})*)*$", message = "รูปแบบ E-Mail ไม่ถูกต้อง")
    @Lob
    @Column(name = "EMAIL")
    private String email;

    @NotNull(message = "กรุณาระบุ")
    @Column(name = "ID_NUMBER")
    private String idNumber;

    @NotNull(message = "กรุณาระบุ")
    @ManyToOne
    private Department department;

    @ManyToMany
    private Set<AuthorityEntity> authorities;

    @ManyToMany
    private List<LeaveMaxSetting> leaveMaxSettings;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }


    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public LocalDate getDateBorn() {
        return dateBorn;
    }

    public void setDateBorn(LocalDate dateBorn) {
        this.dateBorn = dateBorn;
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

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public StatusWork getStatusWork() {
        return statusWork;
    }

    public void setStatusWork(StatusWork statusWork) {
        this.statusWork = statusWork;
    }

    public LocalDate getStartWork() {
        return startWork;
    }

    public void setStartWork(LocalDate startWork) {
        this.startWork = startWork;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public Set<AuthorityEntity> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<AuthorityEntity> authorities) {
        this.authorities = authorities;
    }

    public List<LeaveMaxSetting> getLeaveMaxSettings() {
        return leaveMaxSettings;
    }

    public void setLeaveMaxSettings(List<LeaveMaxSetting> leaveMaxSettings) {
        this.leaveMaxSettings = leaveMaxSettings;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return id.equals(employee.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", gender='" + gender + '\'' +
                ", dateBorn=" + dateBorn +
                ", address='" + address + '\'' +
                ", tel='" + tel + '\'' +
                ", position=" + position +
                ", startWork=" + startWork +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", idNumber='" + idNumber + '\'' +
                ", department=" + department +
                ", authorities=" + authorities +
                '}';
    }
}
