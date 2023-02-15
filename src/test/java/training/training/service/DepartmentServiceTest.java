package training.training.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.nullable;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.Random;

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
import training.training.repository.DepartmentRepository;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class DepartmentServiceTest {

    @Autowired
    private DepartmentService departmentService;

    @MockBean
    private DepartmentRepository departmentRepository;

    private Department department;
    
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
    void should_delete_one_department() {
        // Arrange
        doNothing().when(departmentRepository).deleteById(anyLong());

        // Act & Assert
        departmentService.deleteDepartmentById(getRandomLong());
        verify(departmentRepository, times(1)).deleteById(anyLong());
        verifyNoMoreInteractions(departmentRepository);
    }

    private long getRandomLong() {
        return new Random().longs(1, 10).findFirst().getAsLong();
    }

}
