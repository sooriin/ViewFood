package es.fdi.tmi.viewfood;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

public class HackyProblematicViewPager extends ViewPager
{
    public HackyProblematicViewPager(Context context, @Nullable AttributeSet attrs)
    {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev)
    {
        try
        {
            return super.onInterceptTouchEvent(ev);
        }
        catch(Exception ignored)
        {
            return false;
        }
    }
}