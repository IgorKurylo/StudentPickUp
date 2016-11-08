package com.ipq.ipq.Utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 *  Circle Image view
 */

public class CircleImage extends ImageView
{

    private int x,y,width,height;
    public CircleImage(Context context) {
        super(context);
    }

    public CircleImage(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas)
    {

        Drawable drawable=getDrawable();
        if(drawable==null)
            return;
        if(getWidth()==0 || getHeight()==0)
            return;
        Bitmap b=((BitmapDrawable)drawable).getBitmap();
        Bitmap newb=b.copy(Bitmap.Config.ARGB_8888,true);
        width=getWidth();
        height=getHeight();
        Bitmap circle=DrawNewBitamp(newb,height);
        canvas.drawBitmap(circle,0,0,null);

    }
    public static Bitmap  DrawNewBitamp(Bitmap bitmap,int radious)
    {
        Bitmap finalImage=null;
        Bitmap output=null;
        if(bitmap.getHeight()!=radious || bitmap.getWidth()!=radious)
        {
            finalImage= Bitmap.createScaledBitmap(bitmap,radious,radious,false);

        }else{
            finalImage=bitmap;
        }
        output=Bitmap.createBitmap(finalImage.getWidth(),finalImage.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas=new Canvas(output);
        final Paint p=new Paint();
        final Rect rect=new Rect(0,0,finalImage.getWidth(),finalImage.getHeight());
        p.setAntiAlias(true);
        p.setFilterBitmap(true);
        p.setDither(true);
        canvas.drawARGB(0, 0, 0, 0);
        p.setColor(Color.parseColor("#BAB399"));
        canvas.drawCircle(finalImage.getWidth() / 2 + 0.7f, finalImage.getHeight() / 2 + 0.7f, finalImage.getWidth() / 2 + 0.1f, p);
        p.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(finalImage,rect,rect,p);
        return output;


    }
}
