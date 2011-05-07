package com.mnemr.ui;

import java.util.Currency;

import com.mnemr.ui.animation.Rotate3dAnimation;
import com.mnemr.ui.animation.UpAnimation;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Animation.AnimationListener;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.CursorTreeAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

public class CardsView extends FrameLayout {

    private static final String TAG = "CardsView";
    private static final long SPEED = 1000;
    protected static final int SCROLLING = 1;
    private TextView mCurrentView;
    private TextView mOtherView;
    private ExpandableListAdapter mAdapter;
    private GestureDetector detector;
    public int mGroupPosition = 0;
    public int mChildPosition = -1;
    protected float mState;

    public CardsView(Context context) {
        super(context);
        
        setBackgroundColor(Color.WHITE);
        
        detector = new GestureDetector(new GestureDetector.SimpleOnGestureListener() {

            @Override
           public boolean onDoubleTap(MotionEvent e) {
                Log.d(TAG, "onDoubletap");
                
                mCurrentView.setTextColor(Color.RED);
//                Sound.play(((Cursor)mAdapter.getChild(mGroupPosition, mChildPosition)).getString(2), new OnCompletionListener(){
//
//                    @Override
//                    public void onCompletion(MediaPlayer mp) {
//                        mCurrentView.setTextColor(Color.LTGRAY);
//                    }});
                
               return super.onDoubleTap(e);
           }

            @Override
            public boolean onDown(MotionEvent e) {
                Log.d(TAG, "onDown");
                return super.onDown(e);
            }

            @Override
            public void onLongPress(MotionEvent e) {
                Log.d(TAG, "onLongpress");
                super.onLongPress(e);
            }

            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
//                Log.d(TAG, "onScroll:  x="+distanceX+" ("+e1.getX()+"|"+e2.getX()+")"+"y="+distanceY);
                
//                if (e1.getX() > 150) {
//                    if (mState == 0) {
//                        Log.d(TAG, "start SCROLL");
//                        if (mChildPosition < mAdapter.getChildrenCount(mGroupPosition)-1) {
//                            mChildPosition++;
//                            mOtherView = (TextView) mAdapter.getChildView(mGroupPosition, mChildPosition, false, mOtherView, CardsView.this);
//                            animateRotation(mState, mState = 1 - (e2.getX()/e1.getX()));
//                        }
//                    } else {
//                        animateRotation(mState, mState = 1 - (e2.getX()/e1.getX()));
//                    }
//                }
                
                return super.onScroll(e1, e2, distanceX, distanceY);
            }

            @Override
            public void onShowPress(MotionEvent e) {
                Log.d(TAG, "onShowPress");
                super.onShowPress(e);
            }

            @Override
            public boolean onSingleTapConfirmed(MotionEvent e) {
                Log.d(TAG, "onSingleTpConfirmed");
                return super.onSingleTapConfirmed(e);
                
            }

            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                Log.d(TAG, "onSingleTpUp");
                return super.onSingleTapUp(e);
            }
            

        @Override
           public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {

//               Toast.makeText(getContext(), "onFling", 1000).show();
               Log.d(TAG, "onFling");
               if (Math.abs(velocityX) > Math.abs(velocityY)) {
                   if (mState == 0) {
                       
                       if (velocityX < 1000) {
                           Log.d(TAG, "LEFT");
                           if (mChildPosition < mAdapter.getChildrenCount(mGroupPosition)-1) {
                               mChildPosition++;
                               mOtherView = (TextView) mAdapter.getChildView(mGroupPosition, mChildPosition, false, mOtherView, CardsView.this);
                               animateRotation(0, 1);
                           }
                       } else if (velocityX > 1000) {
                           Log.d(TAG, "RIGHT");
                           if (mChildPosition > -1) {
                               mChildPosition--;
                               if (mChildPosition > -1)
                                   mOtherView = (TextView) mAdapter.getChildView(mGroupPosition, mChildPosition, false, mOtherView, CardsView.this);
                               else
                                   mOtherView = (TextView) mAdapter.getGroupView(mGroupPosition, false, mOtherView, CardsView.this);
                               animateRotation(0, -1);
                           }
                       }
                   } else {
                       Log.d(TAG, "continue SCROLL");
                       if (mState > 0)
                           animateRotation(mState, 1);
                       else
                           animateRotation(mState, -1);
                   }
               } else {
                   mChildPosition = -1;
                   
                   if (velocityY > 1000) {
                       Log.d(TAG, "UP");
                       if (mGroupPosition < mAdapter.getGroupCount()-1)
                           mGroupPosition++;
                       else 
                           mGroupPosition = 0;
                       
                       mOtherView = (TextView) mAdapter.getGroupView(mGroupPosition, false, mOtherView, CardsView.this);
                       animateCurl(true);

                   } else if (velocityY < 1000) {

                       Log.d(TAG, "DOWN");
                       if (mGroupPosition > 0)
                           mGroupPosition--;
                       else 
                           mGroupPosition = mAdapter.getGroupCount()-1;
                       
                       mOtherView = (TextView) mAdapter.getGroupView(mGroupPosition, false, mOtherView, CardsView.this);
                       animateCurl(false);
                   }
               }
               // Toast.makeText(BrainR.this,
               // "Aha: "+velocityX+" - "+velocityY, 7000).show();
               return super.onFling(e1, e2, velocityX, velocityY);
           }
       });
        
    }
    
    public void setAdapter(CursorTreeAdapter adapter) {
        mAdapter = adapter;
        mCurrentView = (TextView) adapter.getGroupView(mGroupPosition, false, mCurrentView, this);
        mOtherView = (TextView) adapter.getGroupView(mGroupPosition, false, mOtherView, this);
        addView(mCurrentView);
        addView(mOtherView);
        mOtherView.setVisibility(View.INVISIBLE);
    }
    
    
    
    @Override
    protected ContextMenuInfo getContextMenuInfo() {
        Log.d(TAG, "CONTEXT MENU INFO");
        // TODO Auto-generated method stub
        return super.getContextMenuInfo();
    }
    
    

    @Override
    public boolean showContextMenu() {
        // TODO Auto-generated method stub
        Log.d(TAG, "SHOW CONTEXT MENU");
        return true;
    }

    @Override
    public boolean showContextMenuForChild(View originalView) {
        Log.d(TAG, "SHOW CONTEXT MENU FOR CHILD");
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
//        Log.d(TAG, "onTouch   "+event.getAction()+"  "+mState);
//        if (mState != 0 && event.getAction() == 1) {
//            Log.d(TAG, "stop SCROLL");
//            if (mState > 0)
//                animateRotation(mState, 1);
//            else
//                animateRotation(mState, -1);
//            mState = 1;
//        }
        return detector.onTouchEvent(event);
    }
    

    private void animateRotation(final float from, final float to) {
        // Find the center of the container
        final float centerX = getWidth() / 2.0f;
        final float centerY = getHeight() / 2.0f;
        final Rotate3dAnimation rotation;

//        if (to-from > 0)
        if (Math.abs(from)  < 0.5) {
            if (to-from > 0)
                rotation = new Rotate3dAnimation(from*-180, Math.max(-90, to*-180), centerX, centerY, 310.0f, true);
            else
                rotation = new Rotate3dAnimation(from*-180, Math.min(90, to*-180), centerX, centerY, 310.0f, true);
            rotation.setDuration(SPEED/2);
            rotation.setFillAfter(true);
            rotation.setInterpolator(new AccelerateInterpolator());
            mCurrentView.startAnimation(rotation);
            
                if (Math.abs(to) > 0.5) {
                    Log.d(TAG, "ANIMATE SWITCH");
                    TextView tmp = mCurrentView;
                    mCurrentView = mOtherView;
                    mOtherView = tmp;
                    rotation.setAnimationListener(new AnimationListener() {
                        
                        public void onAnimationStart(Animation animation) {}
                        
                        public void onAnimationRepeat(Animation animation) {}
                        
                        public void onAnimationEnd(Animation animation) {
                            final Rotate3dAnimation rotation;
                            
                            if (to-from > 0)
                                rotation = new Rotate3dAnimation(90, (1-to)*180, centerX, centerY, 310.0f, false);
                            else
                                rotation = new Rotate3dAnimation(-90, (-1-to)*-180, centerX, centerY, 310.0f, false);
                            
                            rotation.setDuration(SPEED/2);
                            rotation.setFillAfter(true);
                            rotation.setInterpolator(new DecelerateInterpolator());
                            
                            mOtherView.setVisibility(View.GONE);
                            mCurrentView.setVisibility(View.VISIBLE);
                            mCurrentView.startAnimation(rotation);
                            
                        }
                    });
                    
                }
        } else {
            if (to-from > 0)
                rotation = new Rotate3dAnimation(Math.min((1-from)*180, 90), (1-to)*180, centerX, centerY, 310.0f, false);
            else
                rotation = new Rotate3dAnimation(Math.max((-1-from)*-180, -90), (-1-to)*-180, centerX, centerY, 310.0f, false);
            
            rotation.setDuration(SPEED/2);
            rotation.setFillAfter(true);
            rotation.setInterpolator(new DecelerateInterpolator());
            mCurrentView.startAnimation(rotation);
            
        }
        

    }
    
    private void animateCurl(boolean up) {
        final float centerX = getWidth() / 2.0f;
        final float centerY = getHeight();
        UpAnimation rotation; 
        final UpAnimation otherRotation; 
        mOtherView.setVisibility(View.VISIBLE);
        if (up == true) {
            bringChildToFront(mCurrentView);
            rotation = new UpAnimation(0, -90, centerX, centerY, 310.0f, true);
            otherRotation = new UpAnimation(50, 0, centerX, centerY, 310.0f, false);
        } else {
            bringChildToFront(mOtherView);
            rotation = new UpAnimation(0, 70, centerX, 0, 310.0f, true);
            otherRotation = new UpAnimation(-90, 0, centerX, centerY, 310.0f, false);
        }
        rotation.setDuration(SPEED);
        rotation.setFillAfter(true);
        rotation.setInterpolator(new DecelerateInterpolator());
        otherRotation.setDuration(SPEED);
        otherRotation.setFillAfter(true);
        otherRotation.setInterpolator(new DecelerateInterpolator());
        
        rotation.setAnimationListener(new AnimationListener() {

            public void onAnimationStart(Animation animation) {
            }

            public void onAnimationRepeat(Animation animation) {
            }

            public void onAnimationEnd(Animation animation) {
                mCurrentView.setVisibility(View.GONE);
                
                TextView tmp = mCurrentView;
                mCurrentView = mOtherView;
                mOtherView = tmp;
            }
        });
        
        mCurrentView.startAnimation(rotation);
        mOtherView.startAnimation(otherRotation);
    }


}
