package csc455.group4.drthl;

public class DRTHLErrorReport {
	String classf;
	String test;
	public DRTHLErrorReport(String failedClass, String failedTest){
		classf = failedClass;
		test = failedTest;
	}
	
	public void printResult(){
		System.out.println("Class:" + classf + "Failed:" + test);
	}
}
