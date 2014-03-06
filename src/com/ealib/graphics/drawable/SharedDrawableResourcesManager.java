package com.ealib.graphics.drawable;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.ealib.graphics.drawable.shared.SharedBitmapResourceNotFoundException;
import com.ealib.graphics.drawable.shared.SharedDrawableResourceNotFoundException;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;

public class SharedDrawableResourcesManager {
	private HashMap<String, Bitmap> mBitmaps;
    private HashMap<String, Drawable> mDrawables;
    private Context mContext;

    private boolean mActive = true;

    public SharedDrawableResourcesManager(Context c) {
        mBitmaps = new HashMap<String, Bitmap>();
        mDrawables = new HashMap<String, Drawable>();
        mContext = c;
    }

    // We need to share and cache resources between objects to save on memory.
    public Bitmap getBitmap(String customId, InputStream inputStream) throws SharedBitmapResourceNotFoundException {
        if (mActive) {
            if (!mBitmaps.containsKey(customId)) {
            	Bitmap decodeResource = BitmapFactory.decodeStream(inputStream);
            	mBitmaps.put(customId,decodeResource);
            }
            return mBitmaps.get(customId);
        }
        throw new SharedBitmapResourceNotFoundException(customId);
    }
    
    public Bitmap getBitmap(String customId, int resourceId) throws SharedBitmapResourceNotFoundException {
        if (mActive) {
            if (!mBitmaps.containsKey(customId)) {
            	Bitmap decodeResource = BitmapFactory.decodeResource(mContext.getResources(), resourceId);
                mBitmaps.put(customId,decodeResource);
            }
            return mBitmaps.get(customId);
        }
        throw new SharedBitmapResourceNotFoundException(customId);
    }

    public Drawable getDrawable(String customId, int resourceId) throws SharedDrawableResourceNotFoundException {
        if (mActive) {
            if (!mDrawables.containsKey(customId)) {
            	Drawable drawable = mContext.getResources().getDrawable(resourceId);
                mDrawables.put(customId, drawable);
            }
            return mDrawables.get(customId);
        }
        throw new SharedDrawableResourceNotFoundException(customId);
    }

    public void recycleBitmaps() {
        Iterator<?> itr = mBitmaps.entrySet().iterator();
        while (itr.hasNext()) {
            @SuppressWarnings("rawtypes")
			Map.Entry e = (Map.Entry)itr.next();
            ((Bitmap) e.getValue()).recycle();
        }
        mBitmaps.clear();
    }

    public SharedDrawableResourcesManager setActive(boolean b) {
        mActive = b;
        return this;
    }

    public boolean isActive() {
        return mActive;
    }
}
