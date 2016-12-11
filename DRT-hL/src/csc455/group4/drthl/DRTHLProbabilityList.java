package csc455.group4.drthl;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Random;

public class DRTHLProbabilityList {
	Hashtable<String, DRTHLClass> probabilityList;
	double totalProbability;
	double changeRate;
	int maxDependencyLevels;
	Random rng ;
	
	public DRTHLProbabilityList(ArrayList<String> nonTestClasses,Hashtable<String, String> testClasses, double speedOfChange, int maxLevelsOfDependency){
		changeRate = speedOfChange;
		maxDependencyLevels = maxLevelsOfDependency; //number of levels deep to go when updating probabilities based on dependencies.
		for(String className:nonTestClasses){
			String testClass = testClasses.get(className);
			//initializeDRTHLClasses with probabilities of 1/# of test cases
			probabilityList.put(className, new DRTHLClass(className, 1.0/nonTestClasses.size(), testClass));
		}
		rng = new Random();
		

	}
	
	public Class selectTest(){
		double randomNumber = rng.nextDouble();
		double runningCount = 0;
		Enumeration<String> keys = probabilityList.keys();
		//increment running count for every item in the probability list. Select the one that makes running count exceed randomNumber
		while(keys.hasMoreElements()){
			String key = keys.nextElement();
			runningCount += probabilityList.get(key).getProbability();
			if(runningCount >= randomNumber){
				return probabilityList.get(key).getTestClass();
			}
		}
		System.out.println("Did not select test.");
		return null;
	}
	
	public void increaseProbabilities(String basisClass){
		//increases the basis class probability and dependencies up to maxDependencyLevels deep
		double totalIncrease = 0;
		int dependencyLevel = 1;
		totalIncrease += probabilityList.get(basisClass).increaseProbability(dependencyLevel, changeRate);
		ArrayList<String> currentLevel = new ArrayList<String>();
		ArrayList<String> nextLevel = new ArrayList<String>();
		currentLevel = probabilityList.get(basisClass).getDependencies();
		while(dependencyLevel < maxDependencyLevels){
			//increment current level probabilities
			for(String classString:currentLevel){
				nextLevel.clear();
				totalIncrease += probabilityList.get(classString).increaseProbability(dependencyLevel, changeRate);
				ArrayList<String> dependencyList = probabilityList.get(classString).getDependencies();
				for(String dependency:dependencyList){
					if(!nextLevel.contains(dependency)){
						nextLevel.add(dependency);
					}
				}
			}
			dependencyLevel++;
			currentLevel = nextLevel;
		}
		//disperse the total increase over all the classes to keep the total probability at 1.
		Enumeration<String> keys =probabilityList.keys();
		while(keys.hasMoreElements()){
			probabilityList.get(keys.nextElement()).decreaseProbability(totalIncrease / probabilityList.size());
		}
	}
	
	private void populateAllDependencies(){
		//TODO: Populate each TRTHLClass's dependencies
	}
}
