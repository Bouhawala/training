package training.training.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import training.training.entity.Department;
import training.training.error.DepartmentNotFoundException;
import training.training.repository.DepartmentRepository;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class DepartmentServiceTest {

    @Autowired
    private DepartmentService departmentService;

    @MockBean
    private DepartmentRepository departmentRepository;
    
    @BeforeEach
    void setUp() {
        Department department = 
                Department.builder()
                .departmentName("IT")
                .departmentAddress("Schifflange")
                .departmentCode("IT-06")
                .departmentId(1L)
                .build();

        Mockito.when(departmentRepository.findByDepartmentNameIgnoreCase("IT"))
                .thenReturn(department);
    }

    @Test
    @DisplayName("Get data based on Valid Department Name")
    public void whenValidDepartmentName_thenDepartmentShouldFound() {
        String departmentName = "IT";
        Department found = departmentService.fetchDepartmentByName(departmentName);

        assertEquals(departmentName, found.getDepartmentName());
    }

    @Test
    @DisplayName("JUnit test for save department")
    void should_save_one_department() {
        
        final var departmentToSave = Department.builder().departmentName("IT")
                .departmentAddress("Schifflange")
                .departmentCode("IT-06")
                .build();
        when(departmentRepository.save(any(Department.class))).thenReturn(departmentToSave);

        final var actual = departmentService.saveDepartment(Department.builder().build());

        assertThat(actual).usingRecursiveComparison().isEqualTo(departmentToSave);
        verify(departmentRepository, times(1)).save(any(Department.class));
        verifyNoMoreInteractions(departmentRepository);
    }

    @Test
    @DisplayName("JUnit test for delete department")
    void should_delete_one_department() {

        doNothing().when(departmentRepository).deleteById(anyLong());

        departmentService.deleteDepartmentById(getRandomLong());
        verify(departmentRepository, times(1)).deleteById(anyLong());
        verifyNoMoreInteractions(departmentRepository);
    }

    private long getRandomLong() {
        return new Random().longs(1, 10).findFirst().getAsLong();
    }

    @Test
    @DisplayName("JUnit test for find one department")
    void should_find_and_return_one_department() throws DepartmentNotFoundException {

        final var expectedDepartment = Department.builder()
                .departmentName("IT")
                .departmentAddress("Schifflange")
                .departmentCode("IT-06")
                .build();
        when(departmentRepository.findById(anyLong())).thenReturn(Optional.of(expectedDepartment));

        final var actual = departmentService.fetchDepartmentById(getRandomLong());

        assertThat(actual).usingRecursiveComparison().isEqualTo(expectedDepartment);
        verify(departmentRepository, times(1)).findById(anyLong());
        verifyNoMoreInteractions(departmentRepository);
    }

    @Test
    @DisplayName("department not found")
    void should_not_found_a_department_that_doesnt_exists() {

        when(departmentRepository.findById(anyLong())).thenReturn(Optional.empty());

        Assertions.assertThrows(DepartmentNotFoundException.class, () -> departmentService.fetchDepartmentById(getRandomLong()));
        verify(departmentRepository, times(1)).findById(anyLong());
        verifyNoMoreInteractions(departmentRepository);
    }

    @Test
    @DisplayName("JUnit test for find all department")
    void should_find_and_return_all_departments() {

        when(departmentRepository.findAll()).thenReturn(List.of(Department.builder().build(), Department.builder().build()));

        assertThat(departmentService.fetchDepartmentList()).hasSize(2);
        verify(departmentRepository, times(1)).findAll();
        verifyNoMoreInteractions(departmentRepository);
    }

}
