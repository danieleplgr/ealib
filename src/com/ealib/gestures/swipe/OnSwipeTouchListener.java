package com.ealib.gestures.swipe;

import android.app.Activity; 
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

public abstract class OnSwipeTouchListener<A extends Activity> implements OnTouchListener {

	private final GestureDetector gestureDetector;
	private A activity;
	
	public OnSwipeTouchListener(A activity, SwipeConfiguration configParams) {
		gestureDetector = new GestureDetector(activity, new GestureListener(
				configParams));
		this.activity = activity;
	}

	public OnSwipeTouchListener(A activity) {
		gestureDetector = new GestureDetector(activity, new GestureListener());
		this.activity = activity;
	}

	public boolean onTouch(final View v, final MotionEvent event) {
		return gestureDetector.onTouchEvent(event);
	}

	private class GestureListener extends SimpleOnGestureListener {

		private static final int DEFAULT_SWIPE_THRESHOLD = 100;
		private static final int DEFAULT_SWIPE_VELOCITY_THRESHOLD = 100;
		private int swipeThreshold;
		private int swipeVelocityThreshold;

		public GestureListener() {
			this.swipeThreshold = DEFAULT_SWIPE_THRESHOLD;
			this.swipeVelocityThreshold = DEFAULT_SWIPE_VELOCITY_THRESHOLD;
		}

		public GestureListener(SwipeConfiguration configParams) {
			this.swipeThreshold = configParams.getSwipeThreshold();
			this.swipeVelocityThreshold = configParams
					.getSwipeVelocityThreshold();
		}

		@Override
		public boolean onDown(MotionEvent e) {
			return OnSwipeTouchListener.this.onDown( e);
		}

		@Override
		public boolean onDoubleTap(MotionEvent e) {
			return OnSwipeTouchListener.this.onDoubleTap( e);
		}
		
		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
				float velocityY) {
			boolean result = false;
			try {
				float diffY = e2.getY() - e1.getY();
				float diffX = e2.getX() - e1.getX();
				if (Math.abs(diffX) > Math.abs(diffY)) {
					if (Math.abs(diffX) > swipeThreshold
							&& Math.abs(velocityX) > swipeVelocityThreshold) {
						if (diffX > 0) {
							onSwipeRight(e1, e2, velocityX, velocityY);
						} else {
							onSwipeLeft(e1, e2, velocityX, velocityY);
						}
					}
				} else {
					if (Math.abs(diffY) > swipeThreshold
							&& Math.abs(velocityY) > swipeVelocityThreshold) {
						if (diffY > 0) {
							onSwipeBottom(e1, e2, velocityX, velocityY);
						} else {
							onSwipeTop(e1, e2, velocityX, velocityY);
						}
					}
				}
			} catch (Exception exception) {
				exception.printStackTrace();
			}
			return result;
		}
	}

	public abstract void onSwipeRight(MotionEvent e1, MotionEvent e2,
			float velocityX, float velocityY);

	public abstract boolean onDoubleTap(MotionEvent e);
	
	public abstract boolean onDown(MotionEvent e);

	public abstract void onSwipeLeft(MotionEvent e1, MotionEvent e2,
			float velocityX, float velocityY);

	public abstract void onSwipeBottom(MotionEvent e1, MotionEvent e2,
			float velocityX, float velocityY);

	public abstract void onSwipeTop(MotionEvent e1, MotionEvent e2,
			float velocityX, float velocityY);

	public GestureDetector getGestureDetector() {
		return gestureDetector;
	}

	public A getActivity() {
		return activity;
	}
}
