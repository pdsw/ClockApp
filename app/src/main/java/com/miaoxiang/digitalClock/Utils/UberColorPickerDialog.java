package com.miaoxiang.digitalClock.Utils;

import android.app.Dialog;
import android.content.Context;
import android.graphics.*;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.*;

public class UberColorPickerDialog extends Dialog
{
    private static class ColorPickerView extends View
    {

        private static int PALETTE_RADIUS = 0;
        private static final int METHOD_HS_V_PALETTE = 0;
        private static int PALETTE_CENTER_X = PALETTE_RADIUS;
        private static int PALETTE_CENTER_Y = PALETTE_RADIUS;
        private static int PALETTE_DIM = 0;
        private static int PALETTE_POS_X = 0;
        private static int PALETTE_POS_Y = 0;
        private static final float PI = 3.141593F;
        private static final int SLIDER_THICKNESS = 40;
        private static final int SWATCH_HEIGHT = 60;
        private static int SWATCH_WIDTH = 0;
        private static int TEXT_HEX_POS[] = new int[2];
        private static int TEXT_HSV_POS[] = new int[2];
        private static int TEXT_RGB_POS[] = new int[2];
        private static final int TEXT_SIZE = 12;
        private static int TEXT_YUV_POS[] = new int[2];
        private static final int TRACKED_NONE = -1;
        private static final int TRACK_HS_PALETTE = 30;
        private static final int TRACK_SWATCH_NEW = 11;
        private static final int TRACK_SWATCH_OLD = 10;
        private static final int TRACK_VER_VALUE_SLIDER = 31;
        private static int VIEW_DIM_X = PALETTE_DIM;
        private static int VIEW_DIM_Y = 60;
        private int mCoord[];
        private int mFocusedControl;
        private float mHSV[];
        private boolean mHSVenabled;
        private String mHexStr;
        private boolean mHexenabled;
        private Bitmap mHorSlidersBM[];
        private Canvas mHorSlidersCv[];
        private OnColorChangedListener mListener;
        private int mMethod;
        private Rect mNewSwatchRect;
        private Rect mOldSwatchRect;
        private int mOriginalColor;
        private Paint mOvalHueSat;
        private Paint mOvalHueSatSmall;
        private Rect mPaletteRect;
        private Paint mPosMarker;
        private int mRGB[];
        private boolean mRGBenabled;
        private int mSpectrumColorsRev[] = {
                0xffff0000, -65281, 0xff0000ff, 0xff00ffff, 0xff00ff00, -256, 0xffff0000
        };
        private Paint mSwatchNew;
        private Paint mSwatchOld;
        private Paint mText;
        private int mTracking;
        private Paint mValDimmer;
        private Bitmap mVerSliderBM;
        private Canvas mVerSliderCv;
        private Rect mVerSliderRect;
        private float mYUV[];
        private boolean mYUVenabled;

        private int ave(int i, int j, float f)
        {
            return i + round(f * (float)(j - i));
        }

        private void changeHSPalette(float f, float f1, int i)
        {
            int k;
            int i1;
            int ai[];
            int ai1[];
            float f2;
            float f3;
            float f4;
            int j1;
            float af[];
            if (f < 0.0F)
            {
                k = -i;
            } else
            {
                int j =0;
                if(f != 0.0F){
                    j =1;
                }
                k = 0;
                if (j > 0)
                    k = i;
            }
            if (f1 < 0.0F)
            {
                i1 = -i;
            } else
            {
                int l = 0;
                if(f1 != 0.0F){
                    l = 1;
                }
                i1 = 0;
                if (l > 0)
                    i1 = i;
            }
            ai = mCoord;
            ai[0] = k + ai[0];
            ai1 = mCoord;
            ai1[1] = i1 + ai1[1];
            if (mCoord[0] < -PALETTE_RADIUS)
                mCoord[0] = -PALETTE_RADIUS;
            else
            if (mCoord[0] > PALETTE_RADIUS)
                mCoord[0] = PALETTE_RADIUS;
            if (mCoord[1] < -PALETTE_RADIUS)
                mCoord[1] = -PALETTE_RADIUS;
            else
            if (mCoord[1] > PALETTE_RADIUS)
                mCoord[1] = PALETTE_RADIUS;
            f2 = (float)Math.sqrt(mCoord[0] * mCoord[0] + mCoord[1] * mCoord[1]);
            if (f2 > (float)PALETTE_RADIUS)
                f2 = PALETTE_RADIUS;
            f3 = (float)Math.atan2(mCoord[1], mCoord[0]);
            f4 = f3 / 6.283185F;
            if (f4 < 0.0F)
                f4++;
            mCoord[0] = round(Math.cos(f3) * (double)f2);
            mCoord[1] = round(Math.sin(f3) * (double)f2);
            j1 = interpColor(mSpectrumColorsRev, f4);
            af = new float[3];
            Color.colorToHSV(j1, af);
            mHSV[0] = af[0];
            mHSV[1] = f2 / (float)PALETTE_RADIUS;
            updateAllFromHSV();
            mSwatchNew.setColor(Color.HSVToColor(mHSV));
            setVerValSlider();
            invalidate();
        }

        private void changeSlider(int i, boolean flag, int j)
        {
            if (mMethod == 0)
            {
                float af[] = mHSV;
                float f = af[2];
                int k;
                if (flag)
                    k = j;
                else
                    k = -j;
                af[2] = f + (float)k / 256F;
                mHSV[2] = pinToUnit(mHSV[2]);
                updateAllFromHSV();
                mCoord[2] = PALETTE_DIM - (int)(mHSV[2] * (float)PALETTE_DIM);
                mSwatchNew.setColor(Color.HSVToColor(mHSV));
                setOvalValDimmer();
                invalidate();
            }
        }

        private void drawHSV1Palette(Canvas canvas)
        {
            canvas.save();
            canvas.translate(PALETTE_POS_X, PALETTE_POS_Y);
            canvas.translate(PALETTE_CENTER_X, PALETTE_CENTER_Y);
            canvas.drawOval(new RectF(-PALETTE_RADIUS, -PALETTE_RADIUS, PALETTE_RADIUS, PALETTE_RADIUS), mOvalHueSat);
            canvas.drawOval(new RectF(-PALETTE_RADIUS, -PALETTE_RADIUS, PALETTE_RADIUS, PALETTE_RADIUS), mValDimmer);
            if (mFocusedControl == 0)
                hilightFocusedOvalPalette(canvas);
            mark2DPalette(canvas, mCoord[0], mCoord[1]);
            canvas.translate(-PALETTE_CENTER_X, -PALETTE_CENTER_Y);
            canvas.translate(PALETTE_DIM, 0.0F);
            canvas.drawBitmap(mVerSliderBM, 0.0F, 0.0F, null);
            if (mFocusedControl == 1)
                hilightFocusedVerSlider(canvas);
            markVerSlider(canvas, mCoord[2]);
            canvas.restore();
        }

        private void drawSwatches(Canvas canvas)
        {
            float af[] = new float[3];
            mText.setTextSize(16F);
            canvas.drawRect(mOldSwatchRect, mSwatchOld);
            Color.colorToHSV(mOriginalColor, af);
            if ((double)af[2] > 0.5D)
                mText.setColor(0xff000000);
            canvas.drawText("Revert", (float)(mOldSwatchRect.left + SWATCH_WIDTH / 2) - mText.measureText("Revert") / 2.0F, 16 + mOldSwatchRect.top, mText);
            mText.setColor(-1);
            canvas.drawRect(mNewSwatchRect, mSwatchNew);
            if ((double)mHSV[2] > 0.5D)
                mText.setColor(0xff000000);
            canvas.drawText("Accept", (float)(mNewSwatchRect.left + SWATCH_WIDTH / 2) - mText.measureText("Accept") / 2.0F, 16 + mNewSwatchRect.top, mText);
            mText.setColor(-1);
            mText.setTextSize(12F);
        }

        private void hilightFocusedOvalPalette(Canvas canvas)
        {
            mPosMarker.setColor(-1);
            canvas.drawOval(new RectF(-PALETTE_RADIUS, -PALETTE_RADIUS, PALETTE_RADIUS, PALETTE_RADIUS), mPosMarker);
            mPosMarker.setColor(0xff000000);
            canvas.drawOval(new RectF(2 + -PALETTE_RADIUS, 2 + -PALETTE_RADIUS, PALETTE_RADIUS - 2, PALETTE_RADIUS - 2), mPosMarker);
        }

        private void hilightFocusedVerSlider(Canvas canvas)
        {
            mPosMarker.setColor(-1);
            canvas.drawRect(new Rect(0, 0, 40, PALETTE_DIM), mPosMarker);
            mPosMarker.setColor(0xff000000);
            canvas.drawRect(new Rect(2, 2, 38, PALETTE_DIM - 2), mPosMarker);
        }

        private void initHSV1Palette()
        {
            setOvalValDimmer();
            setVerValSlider();
            float f = 6.283185F - mHSV[0] / 57.29578F;
            float f1 = mHSV[1] * (float)PALETTE_RADIUS;
            mCoord[0] = (int)(Math.cos(f) * (double)f1);
            mCoord[1] = (int)(Math.sin(f) * (double)f1);
            mCoord[2] = PALETTE_DIM - (int)(mHSV[2] * (float)PALETTE_DIM);
        }

        private void initUI()
        {
            initHSV1Palette();
            mFocusedControl = 0;
        }

        private int interpColor(int ai[], float f)
        {
            if (f <= 0.0F)
                return ai[0];
            if (f >= 1.0F)
            {
                return ai[ai.length - 1];
            } else
            {
                float f1 = f * (float)(ai.length - 1);
                int i = (int)f1;
                float f2 = f1 - (float)i;
                int j = ai[i];
                int k = ai[i + 1];
                return Color.argb(ave(Color.alpha(j), Color.alpha(k), f2), ave(Color.red(j), Color.red(k), f2), ave(Color.green(j), Color.green(k), f2), ave(Color.blue(j), Color.blue(k), f2));
            }
        }

        private void mark2DPalette(Canvas canvas, int i, int j)
        {
            mPosMarker.setColor(0xff000000);
            canvas.drawOval(new RectF(i - 5, j - 5, i + 5, j + 5), mPosMarker);
            mPosMarker.setColor(-1);
            canvas.drawOval(new RectF(i - 3, j - 3, i + 3, j + 3), mPosMarker);
        }

        private void markVerSlider(Canvas canvas, int i)
        {
            mPosMarker.setColor(0xff000000);
            canvas.drawRect(new Rect(0, i - 2, 40, i + 3), mPosMarker);
            mPosMarker.setColor(-1);
            canvas.drawRect(new Rect(0, i, 40, i + 1), mPosMarker);
        }

        private float pin(float f, float f1)
        {
            if (f < 0.0F)
                f = 0.0F;
            else
            if (f > f1)
                return f1;
            return f;
        }

        private float pin(float f, float f1, float f2)
        {
            if (f < f1)
                f = f1;
            else
            if (f > f2)
                return f2;
            return f;
        }

        private float pinToUnit(float f)
        {
            if (f < 0.0F)
                f = 0.0F;
            else
            if (f > 1.0F)
                return 1.0F;
            return f;
        }

        private int round(double d)
        {
            return (int)Math.round(d);
        }

        private void setOvalValDimmer()
        {
            float af[] = new float[3];
            af[0] = mHSV[0];
            af[1] = 0.0F;
            af[2] = mHSV[2];
            int i = Color.HSVToColor(af);
            mValDimmer.setColor(i);
        }

        private void setVerValSlider()
        {
            float af[] = new float[3];
            af[0] = mHSV[0];
            af[1] = mHSV[1];
            af[2] = 1.0F;
            int ai[] = {
                    Color.HSVToColor(af), 0xff000000
            };
            GradientDrawable gradientdrawable = new GradientDrawable(android.graphics.drawable.GradientDrawable.Orientation.TOP_BOTTOM, ai);
            gradientdrawable.setDither(true);
            gradientdrawable.setLevel(10000);
            gradientdrawable.setBounds(0, 0, 40, PALETTE_DIM);
            gradientdrawable.draw(mVerSliderCv);
        }

        private void updateAllFromHSV()
        {
            if (mRGBenabled || mYUVenabled)
                updateRGBfromHSV();
            if (mYUVenabled)
                updateYUVfromRGB();
            if (mRGBenabled)
                updateHexFromHSV();
        }

        private void updateHexFromHSV()
        {
            mHexStr = Integer.toHexString(Color.HSVToColor(mHSV)).toUpperCase();
            mHexStr = mHexStr.substring(2, mHexStr.length());
        }

        private void updateRGBfromHSV()
        {
            int i = Color.HSVToColor(mHSV);
            mRGB[0] = Color.red(i);
            mRGB[1] = Color.green(i);
            mRGB[2] = Color.blue(i);
        }

        private void updateYUVfromRGB()
        {
            float f = (float)mRGB[0] / 255F;
            float f1 = (float)mRGB[1] / 255F;
            float f2 = (float)mRGB[2] / 255F;
            ColorMatrix colormatrix = new ColorMatrix();
            colormatrix.setRGB2YUV();
            float af[] = colormatrix.getArray();
            mYUV[0] = f * af[0] + f1 * af[1] + f2 * af[2];
            mYUV[0] = pinToUnit(mYUV[0]);
            mYUV[1] = f * af[5] + f1 * af[6] + f2 * af[7];
            mYUV[1] = pin(mYUV[1], -0.5F, 0.5F);
            mYUV[2] = f * af[10] + f1 * af[11] + f2 * af[12];
            mYUV[2] = pin(mYUV[2], -0.5F, 0.5F);
        }

        private void writeColorParams(Canvas canvas)
        {
            if (mHSVenabled)
            {
                canvas.drawText((new StringBuilder("H: ")).append(Integer.toString((int)(255F * (mHSV[0] / 360F)))).toString(), TEXT_HSV_POS[0], 12 + TEXT_HSV_POS[1], mText);
                canvas.drawText((new StringBuilder("S: ")).append(Integer.toString((int)(255F * mHSV[1]))).toString(), TEXT_HSV_POS[0], 24 + TEXT_HSV_POS[1], mText);
                canvas.drawText((new StringBuilder("V: ")).append(Integer.toString((int)(255F * mHSV[2]))).toString(), TEXT_HSV_POS[0], 36 + TEXT_HSV_POS[1], mText);
            }
            if (mRGBenabled)
            {
                canvas.drawText((new StringBuilder("R: ")).append(mRGB[0]).toString(), TEXT_RGB_POS[0], 12 + TEXT_RGB_POS[1], mText);
                canvas.drawText((new StringBuilder("G: ")).append(mRGB[1]).toString(), TEXT_RGB_POS[0], 24 + TEXT_RGB_POS[1], mText);
                canvas.drawText((new StringBuilder("B: ")).append(mRGB[2]).toString(), TEXT_RGB_POS[0], 36 + TEXT_RGB_POS[1], mText);
            }
            if (mYUVenabled)
            {
                canvas.drawText((new StringBuilder("Y: ")).append(Integer.toString((int)(255F * mYUV[0]))).toString(), TEXT_YUV_POS[0], 12 + TEXT_YUV_POS[1], mText);
                canvas.drawText((new StringBuilder("U: ")).append(Integer.toString((int)(255F * (0.5F + mYUV[1])))).toString(), TEXT_YUV_POS[0], 24 + TEXT_YUV_POS[1], mText);
                canvas.drawText((new StringBuilder("V: ")).append(Integer.toString((int)(255F * (0.5F + mYUV[2])))).toString(), TEXT_YUV_POS[0], 36 + TEXT_YUV_POS[1], mText);
            }
            if (mHexenabled)
                canvas.drawText((new StringBuilder("#")).append(mHexStr).toString(), TEXT_HEX_POS[0], 12 + TEXT_HEX_POS[1], mText);
        }

        public boolean dispatchTrackballEvent(MotionEvent motionevent)
        {
            float f;
            float f1;
            int i;
            f = motionevent.getX();
            f1 = motionevent.getY();
            i = 1 + motionevent.getHistorySize();
            motionevent.getAction();
//            JVM INSTR tableswitch 0 2: default 48
//            //		               0 48
//            //		               1 48
//            //		               2 50;
//            goto _L1 _L1 _L1 _L2
//            _L1:
//            return true;
//            _L2:
            switch (mMethod)
            {
                default:
                    return true;

                case 0: // '\0'
                    break;
            }
            if (mFocusedControl == 0)
            {
                changeHSPalette(f, f1, i);
                return true;
            }
            if (mFocusedControl == 1)
            {
                if (f1 < 0.0F)
                {
                    changeSlider(mFocusedControl, true, i);
                    return true;
                }
                if (f1 > 0.0F)
                {
                    changeSlider(mFocusedControl, false, i);
                    return true;
                }
            }
            return false;
//            if (true) goto _L1; else goto _L3
//            _L3:
        }

        protected void onDraw(Canvas canvas)
        {
            drawSwatches(canvas);
            writeColorParams(canvas);
            if (mMethod == 0)
                drawHSV1Palette(canvas);
        }

        protected void onMeasure(int i, int j)
        {
            setMeasuredDimension(VIEW_DIM_X, VIEW_DIM_Y);
        }

        public boolean onTouchEvent(MotionEvent motionevent)
        {
            int i;
            float f2;
            float f3;
            boolean flag;
            boolean flag1;
            float f4;
            boolean flag2;
            boolean flag3;
            float f = motionevent.getX();
            float f1 = motionevent.getY();
            i = (int)pin(round(f1 - (float)PALETTE_POS_Y), PALETTE_DIM);
            f2 = f - (float)PALETTE_POS_X - (float)PALETTE_CENTER_X;
            f3 = f1 - (float)PALETTE_POS_Y - (float)PALETTE_CENTER_Y;
            flag = ptInRect(round(f), round(f1), mOldSwatchRect);
            flag1 = ptInRect(round(f), round(f1), mNewSwatchRect);
            f4 = (float)Math.sqrt(f2 * f2 + f3 * f3);
            if (f4 <= (float)PALETTE_RADIUS)
                flag2 = true;
            else
                flag2 = false;
            if (f4 > (float)PALETTE_RADIUS)
                f4 = PALETTE_RADIUS;
            flag3 = ptInRect(round(f), round(f1), mVerSliderRect);
            motionevent.getAction();
//            JVM INSTR tableswitch 0 2: default 204
//            //		               0 212
//            //		               1 522
//            //		               2 228;
//            goto _L1 _L2 _L3 _L4
//            _L1:
//            return true;
//            _L2:
//            mTracking = -1;
//            if (!flag) goto _L6; else goto _L5
//            _L5:
//            mTracking = 10;
//            _L4:
//            if (mTracking != 30)
//                break; /* Loop/switch isn't completed */
//            float f6 = (float)Math.atan2(f3, f2);
//            float f7 = f6 / 6.283185F;
//            if (f7 < 0.0F)
//                f7++;
//            mCoord[0] = round(Math.cos(f6) * (double)f4);
//            mCoord[1] = round(Math.sin(f6) * (double)f4);
//            int j = interpColor(mSpectrumColorsRev, f7);
//            float af[] = new float[3];
//            Color.colorToHSV(j, af);
//            mHSV[0] = af[0];
//            mHSV[1] = f4 / (float)PALETTE_RADIUS;
//            updateAllFromHSV();
//            mSwatchNew.setColor(Color.HSVToColor(mHSV));
//            setVerValSlider();
//            invalidate();
//            continue; /* Loop/switch isn't completed */
//            _L6:
//            if (flag1)
//                mTracking = 11;
//            else
//            if (mMethod == 0)
//                if (flag2)
//                {
//                    mTracking = 30;
//                    mFocusedControl = 0;
//                } else
//                if (flag3)
//                {
//                    mTracking = 31;
//                    mFocusedControl = 1;
//                }
//            if (true) goto _L4; else goto _L7
//            _L7:
//            if (mTracking == 31 && mCoord[2] != i)
//            {
//                mCoord[2] = i;
//                float f5 = 1.0F - (float)i / (float)PALETTE_DIM;
//                mHSV[2] = f5;
//                updateAllFromHSV();
//                mSwatchNew.setColor(Color.HSVToColor(mHSV));
//                setOvalValDimmer();
//                invalidate();
//            }
//            continue; /* Loop/switch isn't completed */
//            _L3:
//            if (mTracking != 10 || !flag)
//                break; /* Loop/switch isn't completed */
//            Color.colorToHSV(mOriginalColor, mHSV);
//            mSwatchNew.setColor(mOriginalColor);
//            initUI();
//            invalidate();
//            _L10:
//            mTracking = -1;
//            if (true) goto _L1; else goto _L8
//            _L8:
//            if (mTracking != 11 || !flag1) goto _L10; else goto _L9
//            _L9:
//            mListener.colorChanged(mSwatchNew.getColor());
//            invalidate();
//            goto _L10
            return false;
        }

        public boolean ptInRect(int i, int j, Rect rect)
        {
            return i > rect.left && i < rect.right && j > rect.top && j < rect.bottom;
        }

        static
        {
            SWATCH_WIDTH = 95;
            PALETTE_POS_X = 0;
            PALETTE_POS_Y = 60;
            PALETTE_DIM = 2 * SWATCH_WIDTH;
            PALETTE_RADIUS = PALETTE_DIM / 2;
        }

        ColorPickerView(Context context, OnColorChangedListener oncolorchangedlistener, int i, int j, int k)
                throws Exception
        {
            super(context);
            mMethod = 0;
            mTracking = -1;
            mHorSlidersBM = new Bitmap[3];
            mHorSlidersCv = new Canvas[3];
            mOldSwatchRect = new Rect();
            mNewSwatchRect = new Rect();
            mPaletteRect = new Rect();
            mVerSliderRect = new Rect();
            mOriginalColor = 0;
            mHSV = new float[3];
            mRGB = new int[3];
            mYUV = new float[3];
            mHexStr = "";
            mHSVenabled = true;
            mRGBenabled = true;
            mYUVenabled = true;
            mHexenabled = true;
            mCoord = new int[3];
            mFocusedControl = -1;
            setFocusable(true);
            mListener = oncolorchangedlistener;
            mOriginalColor = k;
            Color.colorToHSV(k, mHSV);
            updateAllFromHSV();
            ComposeShader composeshader;
            int l;
            if (i <= j)
            {
                SWATCH_WIDTH = (40 + PALETTE_DIM) / 2;
                PALETTE_POS_X = 0;
                PALETTE_POS_Y = 108;
                mOldSwatchRect.set(0, 48, SWATCH_WIDTH, 108);
                mNewSwatchRect.set(SWATCH_WIDTH, 48, 2 * SWATCH_WIDTH, 108);
                mPaletteRect.set(0, PALETTE_POS_Y, PALETTE_DIM, PALETTE_POS_Y + PALETTE_DIM);
                mVerSliderRect.set(PALETTE_DIM, PALETTE_POS_Y, 40 + PALETTE_DIM, PALETTE_POS_Y + PALETTE_DIM);
                TEXT_HSV_POS[0] = 3;
                TEXT_HSV_POS[1] = 0;
                TEXT_RGB_POS[0] = 50 + TEXT_HSV_POS[0];
                TEXT_RGB_POS[1] = TEXT_HSV_POS[1];
                TEXT_YUV_POS[0] = 100 + TEXT_HSV_POS[0];
                TEXT_YUV_POS[1] = TEXT_HSV_POS[1];
                TEXT_HEX_POS[0] = 150 + TEXT_HSV_POS[0];
                TEXT_HEX_POS[1] = TEXT_HSV_POS[1];
                VIEW_DIM_X = 40 + PALETTE_DIM;
                VIEW_DIM_Y = 48 + (60 + PALETTE_DIM);
            } else
            {
                SWATCH_WIDTH = 110;
                PALETTE_POS_X = SWATCH_WIDTH;
                PALETTE_POS_Y = 0;
                mOldSwatchRect.set(0, 84, SWATCH_WIDTH, 144);
                mNewSwatchRect.set(0, 144, SWATCH_WIDTH, 204);
                mPaletteRect.set(SWATCH_WIDTH, PALETTE_POS_Y, SWATCH_WIDTH + PALETTE_DIM, PALETTE_POS_Y + PALETTE_DIM);
                mVerSliderRect.set(SWATCH_WIDTH + PALETTE_DIM, PALETTE_POS_Y, 40 + (SWATCH_WIDTH + PALETTE_DIM), PALETTE_POS_Y + PALETTE_DIM);
                TEXT_HSV_POS[0] = 3;
                TEXT_HSV_POS[1] = 0;
                TEXT_RGB_POS[0] = TEXT_HSV_POS[0];
                TEXT_RGB_POS[1] = (int)(42D + (double)TEXT_HSV_POS[1]);
                TEXT_YUV_POS[0] = 50 + TEXT_HSV_POS[0];
                TEXT_YUV_POS[1] = (int)(42D + (double)TEXT_HSV_POS[1]);
                TEXT_HEX_POS[0] = 50 + TEXT_HSV_POS[0];
                TEXT_HEX_POS[1] = TEXT_HSV_POS[1];
                VIEW_DIM_X = 40 + (PALETTE_POS_X + PALETTE_DIM);
                VIEW_DIM_Y = Math.max(mNewSwatchRect.bottom, PALETTE_DIM);
            }
            mSwatchOld = new Paint(1);
            mSwatchOld.setStyle(android.graphics.Paint.Style.FILL);
            mSwatchOld.setColor(Color.HSVToColor(mHSV));
            mSwatchNew = new Paint(1);
            mSwatchNew.setStyle(android.graphics.Paint.Style.FILL);
            mSwatchNew.setColor(Color.HSVToColor(mHSV));
            composeshader = new ComposeShader(new SweepGradient(0.0F, 0.0F, mSpectrumColorsRev, null), new RadialGradient(0.0F, 0.0F, PALETTE_CENTER_X, -1, 0xff000000, android.graphics.Shader.TileMode.CLAMP), android.graphics.PorterDuff.Mode.SCREEN);
            mOvalHueSat = new Paint(1);
            mOvalHueSat.setShader(composeshader);
            mOvalHueSat.setStyle(android.graphics.Paint.Style.FILL);
            mOvalHueSat.setDither(true);
            mVerSliderBM = Bitmap.createBitmap(40, PALETTE_DIM, android.graphics.Bitmap.Config.RGB_565);
            mVerSliderCv = new Canvas(mVerSliderBM);
            l = 0;
            do
            {
                if (l >= 3)
                {
                    mValDimmer = new Paint(1);
                    mValDimmer.setStyle(android.graphics.Paint.Style.FILL);
                    mValDimmer.setDither(true);
                    mValDimmer.setXfermode(new PorterDuffXfermode(android.graphics.PorterDuff.Mode.MULTIPLY));
                    ComposeShader composeshader1 = new ComposeShader(new SweepGradient(0.0F, 0.0F, mSpectrumColorsRev, null), new RadialGradient(0.0F, 0.0F, PALETTE_DIM / 2, -1, 0xff000000, android.graphics.Shader.TileMode.CLAMP), android.graphics.PorterDuff.Mode.SCREEN);
                    mOvalHueSatSmall = new Paint(1);
                    mOvalHueSatSmall.setShader(composeshader1);
                    mOvalHueSatSmall.setStyle(android.graphics.Paint.Style.FILL);
                    mPosMarker = new Paint(1);
                    mPosMarker.setStyle(android.graphics.Paint.Style.STROKE);
                    mPosMarker.setStrokeWidth(2.0F);
                    mText = new Paint(1);
                    mText.setTextSize(12F);
                    mText.setColor(-1);
                    initUI();
                    return;
                }
                mHorSlidersBM[l] = Bitmap.createBitmap(PALETTE_DIM, 40, android.graphics.Bitmap.Config.RGB_565);
                mHorSlidersCv[l] = new Canvas(mHorSlidersBM[l]);
                l++;
            } while (true);
        }
    }

    public static interface OnColorChangedListener
    {

        public abstract void colorChanged(int i);
    }


    private int mInitialColor;
    private OnColorChangedListener mListener;

    public UberColorPickerDialog(Context context, OnColorChangedListener oncolorchangedlistener, int i)
    {
        super(context);
        mListener = oncolorchangedlistener;
        mInitialColor = i;
    }

    protected void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
        OnColorChangedListener oncolorchangedlistener = new OnColorChangedListener() {

            final UberColorPickerDialog this$0 = UberColorPickerDialog.this;

            public void colorChanged(int k)
            {
                mListener.colorChanged(k);
                dismiss();
            }
        };
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindow().getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int i = displaymetrics.widthPixels;
        int j = displaymetrics.heightPixels;
        setTitle("Pick a color (try the trackball)");
        try
        {
            setContentView(new ColorPickerView(getContext(), oncolorchangedlistener, i, j, mInitialColor));
            return;
        }
        catch (Exception exception)
        {
            dismiss();
        }
    }

}
