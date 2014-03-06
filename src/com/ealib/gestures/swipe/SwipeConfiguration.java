package com.ealib.gestures.swipe;

public class SwipeConfiguration {

	public SwipeConfiguration(int swipeThreshold, int swipeVelocityThreshold) {
		super();
		this.swipeThreshold = swipeThreshold;
		this.swipeVelocityThreshold = swipeVelocityThreshold;
	}

	private int swipeThreshold;
	private int swipeVelocityThreshold;
	
	public int getSwipeThreshold() {
		return swipeThreshold;
	}
	
	public int getSwipeVelocityThreshold() {
		return swipeVelocityThreshold;
	}
	
	public void setSwipeVelocityThreshold(int swipeVelocityThreshold) {
		this.swipeVelocityThreshold = swipeVelocityThreshold;
	}
	
	public void setSwipeThreshold(int swipeThreshold) {
		this.swipeThreshold = swipeThreshold;
	}
}
