package company.wipro;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class IQW {
    static void main() {

        //Question 1

        //increase the salary by 10 percentage and return list of employee object if the freq is more than 5 in employee class(id,name,salary,freq)
        List<EmployeeDetails> employeeDetailsList = new ArrayList<>();
        employeeDetailsList.add(new EmployeeDetails(1,"shyam", 1000, 6));
        employeeDetailsList.add(new EmployeeDetails(2,"ram", 2000, 3));

        List<EmployeeDetails> employeeDetails = employeeDetailsList.stream().filter(emp -> emp.freq()>=5)
                .map(emp -> new EmployeeDetails(
                        emp.id(),
                        emp.name(),
                        emp.salary() + emp.salary()* 10/100,
                        emp.freq()
                )).toList();

        employeeDetails.forEach(System.out::println);


        //Question 2
        // remove empty value from list
        List<Optional<Integer>> optionalList = Arrays.asList(Optional.of(4), Optional.empty(), Optional.of(8));
        List<Integer> results = optionalList.stream().filter(Optional::isPresent).map(Optional::get).toList();

        results.forEach(System.out::println);
    }
}

record EmployeeDetails(int id, String name, int salary, int freq) {

}
