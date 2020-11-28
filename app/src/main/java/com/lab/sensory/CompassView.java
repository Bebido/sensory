package com.lab.sensory;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

public class CompassView extends View {

    Float azimuth;  // View to draw a compass
    Paint paint = new Paint();

    public CompassView(Context context) {
        super(context);
        paint.setColor(Color.BLUE);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(4);
        paint.setTextSize(40);
        paint.setAntiAlias(true);
    }

    protected void onDraw(Canvas canvas) {
        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;

        if (azimuth != null)
            canvas.rotate((float) (-azimuth * 360 / (2 * Math.PI)), centerX, centerY);

        paint.setColor(Color.BLUE);
        canvas.drawLine(centerX, centerY - 300, centerX, centerY + 300, paint);
        canvas.drawLine(centerX - 300, centerY, centerX + 300, centerY, paint);
        canvas.drawCircle(centerX, centerY, 250, paint);

        canvas.drawText("S", centerX, centerY + 350, paint);
        canvas.drawText("W", centerX - 350, centerY, paint);
        canvas.drawText("E", centerX + 350, centerY, paint);

        paint.setColor(Color.RED);
        canvas.drawText("N", centerX, centerY - 350, paint);
    }
}