package csc455.group4.drthl;

import java.util.ArrayList;

public class DRTHLClass {
	private String name;
	private double probability;
	String test;
	ArrayList<String> dependencies;
	
	public DRTHLClass(String className, double initialProbability, String testClass){
		name = className;
		probability = initialProbability;
		test = testClass;
	}
	
	public String getTestClass(){
		return test;
	}
	
	public double getProbability(){
		return probability;
	}
	
	public void manyErrorsIncrease(double remainingProbabilityDomain){
		probability = remainingProbabilityDomain;
		
	}
	
	public double manyErrorsDecrease(int dependencyLevel, double changeRate,int maxDependencyLevels, int numberOfClasses){
		double decreaseAmount = 0;
		if(probability > (changeRate * ((1/dependencyLevel) /(numberOfClasses-1)))){
			decreaseAmount =(changeRate * ((1/dependencyLevel) /(numberOfClasses-1)));
			probability = probability - decreaseAmount;
		}
		else{
			decreaseAmount = probability;
			probability = 0;
		}
		return decreaseAmount;
	}
	public void noErrorsIncrease(double changeRate, int numberOfClasses, double probabilityi){
		if(probability >= changeRate){
			probability = probability + (changeRate/(numberOfClasses-1));
		}
		else{
			probability = probability + (probabilityi/(numberOfClasses-1));
		}
	}
	public void noErrorsDecrease(double changeRate, int numberOfClasses){
		if(probability > changeRate){
			probability = probability - (changeRate);
		}
		else{
			probability = 0;
		}
		
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
