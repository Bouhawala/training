package training.training;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import training.training.entity.Department;

@SpringBootApplication
public class TrainingApplication {

	static List<Department> departments = new ArrayList<>();
	static {
		departments.add(
			Department.builder()
				.departmentName("IT")
				.departmentAddress("Schifflange")
				.departmentCode("IT-06")
				.departmentId(1L)
				.build()
		);
		departments.add(
			Department.builder()
				.departmentName("EEE")
				.departmentAddress("Esch/Alzette")
				.departmentCode("EEE-011")
				.departmentId(1L)
				.build()
		);
		departments.add(
			Department.builder()
				.departmentName("ME")
				.departmentAddress("Belval")
				.departmentCode("ME-07")
				.departmentId(1L)
				.build()
		);
	}

	public static void main(String[] args) {
		//SpringApplication.run(TrainingApplication.class, args);

		//forEach
		departments.stream()
				   .forEach(department -> System.out.println(department));

		//map
		//collect
		List<Department> lowerCaseDepartment = departments.stream()
				   .map(department -> Department.builder()
				   .departmentName(department.getDepartmentName().toLowerCase())
				   .departmentAddress(department.getDepartmentAddress().toLowerCase())
				   .departmentCode(department.getDepartmentCode().toLowerCase())
				   .build())
				   .collect(Collectors.toList());
				
		System.out.println(lowerCaseDepartment);

		//filter
		List<Department> filterDepartment = departments.stream()
					.filter(department -> department.getDepartmentName().equals("IT"))
					.map(department -> Department.builder()
					.departmentName(department.getDepartmentName().toUpperCase())
					.departmentAddress(department.getDepartmentAddress().toUpperCase())
					.departmentCode(department.getDepartmentCode().toUpperCase())
					.build())
					.collect(Collectors.toList());

		System.out.println(filterDepartment);

		//findFirst
		Department firstDepartment = departments.stream()
					.filter(department -> department.getDepartmentName().equals("IT"))
					.map(department -> Department.builder()
					.departmentName(department.getDepartmentName().toUpperCase())
					.departmentAddress(department.getDepartmentAddress().toUpperCase())
					.departmentCode(department.getDepartmentCode().toUpperCase())
					.build())
					.findFirst()
					.orElse(null);

		System.out.println(firstDepartment);
	}

}
