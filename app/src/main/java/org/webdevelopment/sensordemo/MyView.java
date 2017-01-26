package org.webdevelopment.sensordemo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

public class MyView extends View {
	
	Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.pacman);
    //The coordinates for our dear pacman: (0,0) is the top-left corner
	//The coordinates refer to the CENTER of the bitmap
	int pacx = 50;
    int pacy = 400;
    int h,w; //used for storing our height and
	boolean firstDraw = true;
	int bitmapHalfWidth, bitmapHalfHeight;
    
    public void moveRight(int x)
    {
    	//still within our boundaries?
    	if (pacx+x+bitmapHalfWidth*2<w)
    		pacx=pacx+x;
    	invalidate(); //redraw everything
    }

	public void moveUp(int x)
	{
		//still within our boundaries?
		if (pacy-x>0)
			pacy=pacy-x;
		invalidate(); //redraw everything
	}

	public void moveDown(int x)
	{
		//still within our boundaries?
		if (pacy+x+bitmapHalfHeight*2<h)
			pacy=pacy+x;
		invalidate(); //redraw everything
	}

	public void moveLeft(int x)
	{
		//still within our boundaries?
		if (pacx-x>0)
			pacx=pacx-x;
		invalidate(); //redraw everything
	}

	/* The next 3 constructors are needed for the Android view system,
	when we have a custom view.
	 */
	public MyView(Context context) {
		super(context);
		
	}
	
	public MyView(Context context, AttributeSet attrs) {
		super(context,attrs);
	}
	
	
	public MyView(Context context, AttributeSet attrs, int defStyleAttr)
	{
		super(context,attrs,defStyleAttr);
	}

	//In the onDraw we put all our code that should be
	//drawn whenever we update the screen.
	@Override
	protected void onDraw(Canvas canvas) {
		//Here we get the height and weight
		h = canvas.getHeight();
		w = canvas.getWidth();
		//first time we draw, then set the pacman x and y
		//coordinates based on the width and height of the
		//available canvas size.
		if (firstDraw)
		{
			pacx = w/2-(bitmap.getScaledWidth(canvas)/2);
			pacy = h/2-(bitmap.getScaledHeight(canvas)/2);
			bitmapHalfWidth = bitmap.getScaledWidth(canvas)/2;
			bitmapHalfHeight = bitmap.getScaledHeight(canvas)/2;
		}
		firstDraw = false;
		//Making a new paint object
		Paint paint = new Paint();
		paint.setColor(Color.RED);
		Rect r = new Rect(0,0,w,h);
		canvas.drawRect(r,paint);
		//Draw the pacman bipmap
		canvas.drawBitmap(bitmap, pacx, pacy, paint);
		super.onDraw(canvas);
	}

}
