package csc455.group4.drthl;

import java.util.ArrayList;

public class DRTHLClass {
	private String name;
	private double probability;
	String test;
	ArrayList<String> dependencies;
	private static boolean lToggle = true;
	
	public DRTHLClass(String className, double initialProbability, String testClass){
		name = className;
		probability = initialProbability;
		test = testClass;
	}
	
	public Class<? extends DRTHLTestClass> getTestClass(){
		Class<? extends DRTHLTestClass> testClass = null;
		try {
			
			testClass =  (Class<? extends DRTHLTestClass>) Class.forName(test);
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.exit(1);
		}
		return testClass;
	}
	
	public double getProbability(){
		return probability;
	}
	
	public double increaseProbability(int dependencyLevel, double changeRate){
		probability += changeRate/dependencyLevel;
		return changeRate/dependencyLevel;
	}
	
	public void decreaseProbability(double decreaseAmount){
		probability -= decreaseAmount;
	}
	
	
	public void addDependency(String dependency){
		dependencies.add(dependency);
	}
	
	public ArrayList<String> getDependencies(){
		return dependencies;
	}
	
	public void populateAllDependencies(ArrayList<String> dependencyList){
		dependencies = dependencyList;
	}
}
