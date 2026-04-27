package leetcode.slidingwindow;

class Parent {

	String var = "parent";

	void print() {
		System.out.println(var);
	}

}

class Child extends Parent {
	String var = "child";
	void print() {
		System.out.println(var);
	}
}

public class MySimpleTest {
	public static void main(String[] args) {
		Parent parent = new Child();
		System.out.println(parent.var);
		parent.print();
	}
}