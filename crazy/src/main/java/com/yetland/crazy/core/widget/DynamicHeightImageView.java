package com.yetland.crazy.core.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * An {@link ImageView} layout that maintains a consistent width to height aspect ratio.
 */
public class DynamicHeightImageView extends android.support.v7.widget.AppCompatImageView {

	private double mHeightRatio;

	public DynamicHeightImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public DynamicHeightImageView(Context context) {
		super(context);
	}

	public void setHeightRatio(double ratio) {
		if (ratio != mHeightRatio) {
			mHeightRatio = ratio;
			requestLayout();
		}
	}

	public double getHeightRatio() {
		return mHeightRatio;
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(getDefaultSize(0, widthMeasureSpec), getDefaultSize(0, heightMeasureSpec));  

		int childWidthSize = getMeasuredWidth();  
		// 高度和宽度一样  
		heightMeasureSpec = widthMeasureSpec = MeasureSpec.makeMeasureSpec(childWidthSize, MeasureSpec.EXACTLY);  
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);  

	/*	if (mHeightRatio > 0.0) {
			// set the image views size
			int width = MeasureSpec.getSize(widthMeasureSpec);
			int height = (int) (width * mHeightRatio);
			setMeasuredDimension(width, height);
		}
		else {
			super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		}*/
	}
}
