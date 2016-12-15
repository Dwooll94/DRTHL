package csc455.group4.drthl;

import java.util.Timer;

public class drthl {
	
	public static void main(String[] args) {
		Long startTime = System.nanoTime();
		DRTHLobject drthl = new DRTHLobject("csc455.group4.example");
		drthl.run(1000000);
		Long endTime = System.nanoTime();
		System.out.println("Ran in " +(endTime - startTime) + "microseconds");
	}

}
