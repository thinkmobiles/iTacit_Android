package com.itacit.healthcare.presentation.base.widgets.picasso;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Shader;

/**
 * Created by root on 12.11.15.
 */
public class CircleTransformation implements com.squareup.picasso.Transformation {

    @Override
    public Bitmap transform(Bitmap source) {
        Bitmap bm = source.isMutable() ? source : source.copy(Bitmap.Config.ARGB_8888, true);

        int width = bm.getWidth();
        int height = bm.getHeight();

        Bitmap cropped_bitmap;

        if (width > height) {
            cropped_bitmap = Bitmap.createBitmap(bm,
                    (width / 2) - (height / 2), 0, height, height);
        } else {
            cropped_bitmap = Bitmap.createBitmap(bm, 0, (height / 2)
                    - (width / 2), width, width);
        }

        BitmapShader shader = new BitmapShader(cropped_bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);

        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setShader(shader);

        height = cropped_bitmap.getHeight();
        width = cropped_bitmap.getWidth();

        Bitmap output = Bitmap.createBitmap(width, height,
                Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(output);
        canvas.drawCircle(width / 2, height / 2, width / 2, paint);

        if (source != output) {
            source.recycle();
        }

        return output;
    }

    @Override
    public String key() {
        return "rounded";
    }
}
