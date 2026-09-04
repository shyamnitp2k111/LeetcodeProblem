package company;

import java.util.TreeSet;

public class InterviewCoding {

    //emp -> id, name, salary
    // sort based salary

    static void main() {
        TreeSet<Employee> employeeList = new TreeSet<>();
        employeeList.add(new Employee(1, "Shyam", 100));
        employeeList.add(new Employee(2, "Kishor", 300));
        employeeList.add(new Employee(3, "Ram", 200));
        employeeList.add(new Employee(4, "abc", 100));
        employeeList.add(new Employee(5, "abc", 100));



        //employeeList.sort( (e1, e2) -> e1.getSalary() - e2.getSalary());

        employeeList.forEach(System.out::println);



    }
}

class Employee implements Comparable<Employee>{
    private int empId;
    private String empName;
    private int salary;

    public Employee(int empId, String empName, int salary) {
        this.empId = empId;
        this.empName = empName;
        this.salary = salary;
    }

    public int getEmpId() {
        return empId;
    }

    public void setEmpId(int empId) {
        this.empId = empId;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    @Override
    public String toString() {
        return "company.Employee{" +
                "empId=" + empId +
                ", empName='" + empName + '\'' +
                ", salary=" + salary +
                '}';
    }

    @Override
    public int compareTo(Employee o) {

        if(this.getSalary() == o.getSalary()) {
            return this.getEmpName().compareTo(o.getEmpName());
        } else {
            return this.getSalary() - o.getSalary();
        }

    }
}


