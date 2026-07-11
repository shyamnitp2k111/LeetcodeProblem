package company.altimetrix;

import java.util.*;
import java.util.stream.Collectors;


public class EmployeeSteamExample {

     String deptNumber;
     int salary;

    public EmployeeSteamExample() {
    }

    public EmployeeSteamExample(String deptNumber, int salary) {
        this.deptNumber = deptNumber;
        this.salary = salary;
    }

    public String getDeptNumber() {
        return deptNumber;
    }

    public void setDeptNumber(String deptNumber) {
        this.deptNumber = deptNumber;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }
}

class MainClasses {
    List<EmployeeSteamExample> list = new ArrayList<>();

    public static void main(String[] args) {
        MainClasses mainClass = new MainClasses();
        mainClass.list.add(new EmployeeSteamExample("IT", 10));
        mainClass.list.add(new EmployeeSteamExample("CS", 10));
        mainClass.list.add(new EmployeeSteamExample("CS", 10));
        mainClass.list.add(new EmployeeSteamExample("IT", 10));


        Map<String, Optional<EmployeeSteamExample>> maxSalaryByDept =
                mainClass.list.stream()
                        .collect(Collectors.groupingBy(
                                EmployeeSteamExample::getDeptNumber,
                                Collectors.maxBy(Comparator.comparingInt(EmployeeSteamExample::getSalary))
                        ));

        maxSalaryByDept.forEach((dept, emp) ->
                System.out.println(dept + " -> " + emp.orElse(null)));
    }
}
