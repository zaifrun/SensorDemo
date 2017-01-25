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

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);

		System.out.println("pacx : "+pacx+", pacy: "+pacy);
	}

	//In the onDraw we put all our code that should be
	//drawn whenever we update the screen.
	@Override
	protected void onDraw(Canvas canvas) {
		//Here we get the height and weight
		h = canvas.getHeight();
		w = canvas.getWidth();
		if (firstDraw)
		{
			pacx = w/2-(bitmap.getScaledWidth(canvas)/2);
			pacy = h/2-(bitmap.getScaledHeight(canvas)/2);
			bitmapHalfWidth = bitmap.getScaledWidth(canvas)/2;
			bitmapHalfHeight = bitmap.getScaledHeight(canvas)/2;
		}
		firstDraw = false;
		System.out.println("h = "+h+", w = "+w);
		//Making a new paint object
		Paint paint = new Paint();
		paint.setColor(Color.RED);
		Rect r = new Rect(0,0,w,h);
		canvas.drawRect(r,paint);

		/*
		//setting the color
		paint.setColor(Color.RED);
		canvas.drawColor(Color.WHITE); //clear entire canvas to white color
		//drawing a line from (0,0) -> (300,200)
		canvas.drawLine(0,0,300,200,paint);
		paint.setColor(Color.GREEN);
		canvas.drawLine(0,200,300,0,paint);
		
		//setting the color using the format: Transparency, Red, Green, Blue
		paint.setColor(0xff000099);
		
		//drawing a circle with radius 20, and center in (100,100) 
		canvas.drawCircle(100,100,20,paint); */
		
		
		canvas.drawBitmap(bitmap, pacx, pacy, paint);
		super.onDraw(canvas);
	}

}
