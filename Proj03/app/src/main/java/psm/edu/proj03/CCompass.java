package psm.edu.proj03;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

/**
 * Created by lewin on 17.04.2018.
 */

public class CCompass extends View {
    private Paint paint;
    private float direction = 0;

    public CCompass(Context context) {
        super(context);
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStrokeWidth(2);
        paint.setTextSize(25);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.BLACK);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int xP = getMeasuredWidth()/2;
        int yP = getMeasuredHeight()/2;
        float radius = (float) (Math.min(xP, yP) * 0.8);
        canvas.drawCircle(xP, yP, radius, paint);
        canvas.drawRect(0, 0, getMeasuredWidth(), getMeasuredHeight(), paint);
        canvas.drawLine(xP, yP, (float)(xP + radius * Math.sin(-direction)), (float)(yP - radius * Math.cos(-direction)), paint);
        canvas.drawText(String.format("%6.2f", 180* direction/Math.PI), xP, yP, paint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec), MeasureSpec.getSize(heightMeasureSpec));
    }

    public void updateDirection(float direction){
        this.direction = direction;
        invalidate();
    }
}
