import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Question {


    //happiest mind

/*    Group employees by department and then by age
"Alice", "HR", 25,
        "Bob", "IT", 30,
        "Charlie", "HR", 30,
        "Dave", "IT", 25

    Output: {HR={25=[Alice], 30=[Charlie]}, IT={30=[Bob], 25=[Dave]}}*/


    public static void main(String[] args) {

        List<Employee> employeeList = new ArrayList<>();
        employeeList.add(new Employee("Alice", "HR", 25));
        employeeList.add(new Employee("Bob", "IT", 30));
        employeeList.add(new Employee("Charlie", "HR", 30));
        employeeList.add(new Employee("Dave", "IT", 25));


        Map<String, Map<Integer, List<String>>> map = employeeList.stream().
                collect(Collectors.groupingBy(Employee::getDept,
                        Collectors.groupingBy(Employee::getAge,
                               Collectors.mapping(Employee::getName, Collectors.toList()))));

        for(Map.Entry<String, Map<Integer, List<String>>> entry : map.entrySet()) {
            System.out.println(entry.getKey() +" "+ entry.getValue());
        }

    }



}

class Employee {

    private String name;

    private String dept;

    private int age;

    public Employee(String name, String dept, int age) {
        this.name = name;
        this.dept = dept;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "name='" + name + '\'' +
                ", dept='" + dept + '\'' +
                ", age=" + age +
                '}';
    }
}
