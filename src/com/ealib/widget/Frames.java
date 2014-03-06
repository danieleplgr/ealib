package com.ealib.widget;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import android.view.View;
import android.widget.FrameLayout;


public class Frames {

	public static boolean contain(FrameLayout frameLayout, View view) {
		if (frameLayout != null) {
			int childCount = frameLayout.getChildCount();
			for (int i = 0; i < childCount; i++) {
				View childAt = frameLayout.getChildAt(i);
				if (childAt != null) {
					if (childAt.equals(view)) {
						return true;
					}
				}
			}
		}
		return false;
	}
	
	public static void removeAllBut(FrameLayout frameLayout, View... views) {
		List<View> viewsToHold = Arrays.asList(views);

		List<View> allChilds = Frames.getAllChilds(frameLayout);
		for (View currentView : allChilds) {
			if(!viewsToHold.contains(currentView)) {
					Frames.removeView(frameLayout, currentView);
			}
		}
	}
	


	public static List<View> getAllChilds(FrameLayout frameLayout) {
		List<View> list = new ArrayList<View>();
		if (frameLayout != null) {
			int childCount = frameLayout.getChildCount();
			for (int i = 0; i < childCount; i++) {
				
				View childAt = frameLayout.getChildAt(i);
				if (childAt != null) {
					list.add(childAt);
				}
			}
		}
		return list;
	}
	
	public static boolean removeView(FrameLayout frameLayout, View view){
		if(Frames.contain(frameLayout, view)){
			frameLayout.removeView(view);
			return true;
		}
		return false;
	}
	
	public static boolean addView(FrameLayout frameLayout, View view){
		if(!Frames.contain(frameLayout, view)){
			frameLayout.addView(view);
			return true;
		}
		return false;
	}

}
