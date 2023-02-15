package training.training.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import training.training.entity.Department;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class DepartmentRepositoryTest {

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private TestEntityManager entityManager;

    @BeforeEach
    void setUp() {
        Department department = 
                Department.builder()
                .departmentName("IT")
                .departmentCode("ME-011")
                .departmentAddress("Esch/Alzette")
                .build();

        entityManager.persist(department);
    }    

    @Test
    public void whenFindById_thenReturnDepartment() {
        Department department = departmentRepository.findById(1L).get();

        assertEquals(department.getDepartmentName(), "IT");
    }

    @Test
    public void findAll_success() {
        List<Department> allDepartments = departmentRepository.findAll();
        assertThat(allDepartments.size()).isGreaterThanOrEqualTo(1);
    }

    @Test
    public void should_save_department() {
      Department department = departmentRepository.save(Department.builder()
            .departmentName("IT")
            .departmentAddress("Schifflange")
            .departmentCode("IT-06")
            .departmentId(1L)
            .build());
  
      assertThat(department).hasFieldOrPropertyWithValue("departmentName", "IT");
      assertThat(department).hasFieldOrPropertyWithValue("departmentAddress", "Schifflange");
      assertThat(department).hasFieldOrPropertyWithValue("departmentCode", "IT-06");
    }

    @Test
    public void should_update_department_by_id() {
      Department department1 = Department.builder()
        .departmentName("IT")
        .departmentAddress("Schifflange")
        .departmentCode("IT-06")
        .build();
      entityManager.persist(department1);
  
      Department department2 = Department.builder()
        .departmentName("EEE")
        .departmentAddress("Esch/Alzette")
        .departmentCode("EEE-011")
        .departmentId(1L)
        .build();
  
      Department updatedDepartment = Department.builder()
        .departmentName("Updated-EEE")
        .departmentAddress("Updated-Esch/Alzette")
        .departmentCode("Updated-EEE-011")
        .departmentId(1L)
        .build();

      Department dep = departmentRepository.findById(department2.getDepartmentId()).get();
      dep.setDepartmentName(updatedDepartment.getDepartmentName());
      dep.setDepartmentAddress(updatedDepartment.getDepartmentAddress());
      dep.setDepartmentCode(updatedDepartment.getDepartmentCode());
      departmentRepository.save(dep);
  
      Department checkDep = departmentRepository.findById(department2.getDepartmentId()).get();
      
      assertThat(checkDep.getDepartmentId()).isEqualTo(department2.getDepartmentId());
      assertThat(checkDep.getDepartmentName()).isEqualTo(updatedDepartment.getDepartmentName());
      assertThat(checkDep.getDepartmentAddress()).isEqualTo(updatedDepartment.getDepartmentAddress());
      assertThat(checkDep.getDepartmentCode()).isEqualTo(updatedDepartment.getDepartmentCode());
    }

}
