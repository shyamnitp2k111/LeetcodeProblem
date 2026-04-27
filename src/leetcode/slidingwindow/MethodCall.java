package leetcode.slidingwindow;

public class MethodCall {
	
	public void simpleMethod(int a) {
		System.out.println("integer primitive called");
	}
	
	public void simpleMethod(Integer a) {
		System.out.println("Integer class called");
	}
	
	public void simpleMethod(Object a) {
		System.out.println("Object class called");
	}
	
	public void simpleMethod(double a) {
		System.out.println("double primitve called");
	}
	
	public static void main(String[] args) {
		new MethodCall().simpleMethod((byte)5);
		new MethodCall().simpleMethod(5);
		new MethodCall().simpleMethod(null);
		new MethodCall().simpleMethod(5.0f);
	}
}