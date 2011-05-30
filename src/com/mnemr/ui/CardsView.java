/**
  * Copyright (c) 2011: mnemr.com contributors. All rights reserved.
  *
  * This program is free software: you can redistribute it and/or modify
  * it under the terms of the GNU General Public License as published 
  * the Free Software Foundation, either version 3 of the License, 
  * (at your option) any later version.
  *
  * program is distributed in the hope that it will be 
  * but WITHOUT ANY WARRANTY; without even the implied warranty 
  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See 
  * GNU General Public License for more details.
  *
  * You should have received a copy of the GNU General Public 
  * along with this program.  If not, see <http://www.gnu.org/licenses/>.
  *
  **/

package com.mnemr.ui;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.DecelerateInterpolator;
import android.widget.CursorTreeAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.mnemr.ui.animation.Rotate3dAnimation;
import com.mnemr.ui.animation.UpAnimation;
import com.mnemr.utils.MnemrUtil;

public class CardsView extends FrameLayout {

    private static final String TAG = "CardsView";
    private static final long SPEED = 170;
    protected static final int SCROLLING = 1;
    private View mCurrentView;
    private View mOtherView;
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
                
//                mCurrentView.setTextColor(Color.RED); 
               return super.onDoubleTap(e);
           }

            @Override
            public boolean onDown(MotionEvent e) {
//                Log.d(TAG, "onDown");
                return super.onDown(e);
            }

            @Override
            public void onLongPress(MotionEvent e) {
                Log.d(TAG, "onLongpress");
                super.onLongPress(e);
            }

            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) { 
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

               if (Math.abs(velocityX) > Math.abs(velocityY)) {
                   if (mState == 0) {

                	   if (velocityX < 1000) {
//                           Log.d(TAG, "fling LEFT");
                    	   flipCardRight();
                       } else if (velocityX > 1000) {
//                           Log.d(TAG, "fling RIGHT");
                    	   flipCardLeft();
                       }
                   } else {
//                       Log.d(TAG, "continue SCROLL");
//                       if (mState > 0)
//                           animateRotation(mState, 1);
//                       else
//                           animateRotation(mState, -1);
                   }
               } else {
                   mChildPosition = -1;
                   
                   if (velocityY > 1000) {
//                       Log.d(TAG, "fling UP");
                       nextCard();

                   } else if (velocityY < 1000) {

//                       Log.d(TAG, "fling DOWN");
                       prevCard();
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
        if (adapter.getGroupCount() == 0)
        	return;
        mCurrentView = adapter.getGroupView(mGroupPosition, false, mCurrentView, this);
        mOtherView = adapter.getGroupView(mGroupPosition, false, mOtherView, this);
        addView(mCurrentView);
        addView(mOtherView);
        mOtherView.setVisibility(View.INVISIBLE);
    }
    

    @Override
    public boolean onTouchEvent(MotionEvent event) { 
        return detector.onTouchEvent(event);
    }
    
    
    public void flipCardLeft() {
    	mChildPosition--;
    	if (mChildPosition == -2)
    		mChildPosition = mAdapter.getChildrenCount(mGroupPosition)-1;
    	if (mChildPosition > -1)
    		mOtherView = mAdapter.getChildView(mGroupPosition, mChildPosition, false, mOtherView, CardsView.this);
    	else
    		mOtherView = mAdapter.getGroupView(mGroupPosition, false, mOtherView, CardsView.this);
    	animateRotation(0, -1);
    }
    
    public void flipCardRight() {
    	mChildPosition++;
    	if (mChildPosition == mAdapter.getChildrenCount(mGroupPosition)) {
    		mChildPosition = -1;
    		mOtherView = mAdapter.getGroupView(mGroupPosition, false, mOtherView, CardsView.this);
    	} else {
    		mOtherView = mAdapter.getChildView(mGroupPosition, mChildPosition, false, mOtherView, CardsView.this);
    	}
    	animateRotation(0, 1);
    }
    
	public void nextCard() {
		if (mGroupPosition < mAdapter.getGroupCount()-1)
		       mGroupPosition++;
		   else 
		       mGroupPosition = 0;
		   
		   mOtherView = mAdapter.getGroupView(mGroupPosition, false, mOtherView, CardsView.this);
		   animateCurl(true);
	}
	
	public void prevCard() {
		if (mGroupPosition > 0)
			mGroupPosition--;
		else 
			mGroupPosition = mAdapter.getGroupCount()-1;
		
		refresh();
	}
    
    
    public void refresh() {
    	if (mAdapter.getGroupCount()==0) {
			Log.e(getClass().getSimpleName(), "count == 0");
			MnemrUtil.showToast("there are no mnemrs !  ", getContext());
			if (getContext() instanceof Activity) {
				Activity activity = (Activity) getContext();
				activity.finish();
				
			}	
		}else {
			mOtherView = mAdapter.getGroupView(mGroupPosition, false, mOtherView, CardsView.this);
			   animateCurl(false);
		}
		 
	}

    public void animateRotation(final float from, final float to) {
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
            rotation.setInterpolator(new DecelerateInterpolator());
            mCurrentView.startAnimation(rotation);
            
                if (Math.abs(to) > 0.5) {
//                    Log.d(TAG, "ANIMATE SWITCH");
                    View tmp = mCurrentView;
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
                            rotation.setInterpolator(new AccelerateInterpolator());
                            
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
    
    public void animateCurl(boolean up) {
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
        otherRotation.setInterpolator(new AccelerateInterpolator());
        
        rotation.setAnimationListener(new AnimationListener() {

            public void onAnimationStart(Animation animation) {
            }

            public void onAnimationRepeat(Animation animation) {
            }

            public void onAnimationEnd(Animation animation) {
                mCurrentView.setVisibility(View.GONE);
                
                View tmp = mCurrentView;
                mCurrentView = mOtherView;
                mOtherView = tmp;
            }
        });
        
        mCurrentView.startAnimation(rotation);
        mOtherView.startAnimation(otherRotation);
    }
    
    
    


}
