/*package com.madss.association;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;

import com.bumptech.glide.Glide;

public class ImageDetailActivity extends AppCompatActivity {

    String imgPath;
    private ImageView imageView;
    private ScaleGestureDetector scaleGestureDetector;
    private float mScaleFactor = 1.0f;
    private float focusX, focusY;
    private float imageCenterX, imageCenterY;

    private LinearLayoutCompat topDesignLayout;
    private LinearLayoutCompat bottomDesignLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_detail);

        topDesignLayout = findViewById(R.id.topDesignLayout);
        bottomDesignLayout = findViewById(R.id.bottomDesignLayout);

        imgPath = getIntent().getStringExtra("imgPath");
       // imgPath = "https://m.media-amazon.com/images/I/71NXDOFEjpL.jpg";
        imageView = findViewById(R.id.idIVImage);
        scaleGestureDetector = new ScaleGestureDetector(this, new ScaleListener());

        // Load the image using Glide from the online URL
        Glide.with(this)
                .load(imgPath) // Provide the URL of the image here
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.myknwlogo)
                .into(imageView);

        imageView.post(() -> {
            // Get the center coordinates of the ImageView
            imageCenterX = imageView.getWidth() / 2f;
            imageCenterY = imageView.getHeight() / 2f;
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        scaleGestureDetector.onTouchEvent(motionEvent);
        return true;
    }

    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {

        @Override
        public boolean onScaleBegin(ScaleGestureDetector detector) {
            focusX = detector.getFocusX();
            focusY = detector.getFocusY();
            return true;
        }

        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            mScaleFactor *= detector.getScaleFactor();
            mScaleFactor = Math.max(0.1f, Math.min(mScaleFactor, 10.0f));

            // Calculate the focus point offset
            float focusOffsetX = (focusX - imageCenterX);
            float focusOffsetY = (focusY - imageCenterY);

            imageView.setPivotX(focusOffsetX);
            imageView.setPivotY(focusOffsetY);

            imageView.setScaleX(mScaleFactor);
            imageView.setScaleY(mScaleFactor);

            // Translate the image to keep it centered
            float translateX = (imageView.getWidth() * mScaleFactor - imageView.getWidth()) / 2;
            float translateY = (imageView.getHeight() * mScaleFactor - imageView.getHeight()) / 2;

            imageView.setTranslationX(-translateX);
            imageView.setTranslationY(-translateY);

            // Set click listener for the CardView
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   // toggleDesignLayouts();
                }
            });

            return true;
        }
    }

    private void toggleDesignLayouts() {
        // If the top layout is currently visible, hide it; otherwise, show it
        if (topDesignLayout.getVisibility() == View.VISIBLE) {
            hideDesignLayouts();
        } else {
            showDesignLayouts();
        }
    }

    private void showDesignLayouts() {
        topDesignLayout.setVisibility(View.VISIBLE);
        bottomDesignLayout.setVisibility(View.VISIBLE);

        // Slide in animation
        topDesignLayout.animate()
                .translationY(0)
                .setInterpolator(new AccelerateDecelerateInterpolator())
                .start();

        bottomDesignLayout.animate()
                .translationY(0)
                .setInterpolator(new AccelerateDecelerateInterpolator())
                .start();
    }

    private void hideDesignLayouts() {
        // Slide out animation
        topDesignLayout.animate()
                .translationY(-topDesignLayout.getHeight())
                .setInterpolator(new AccelerateDecelerateInterpolator())
                .withEndAction(new Runnable() {
                    @Override
                    public void run() {
                        topDesignLayout.setVisibility(View.GONE);
                    }
                })
                .start();

        bottomDesignLayout.animate()
                .translationY(bottomDesignLayout.getHeight())
                .setInterpolator(new AccelerateDecelerateInterpolator())
                .withEndAction(new Runnable() {
                    @Override
                    public void run() {
                        bottomDesignLayout.setVisibility(View.GONE);
                    }
                })
                .start();
    }
}*/

package com.madsssoft.laadsamaj;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;

import com.bumptech.glide.Glide;

public class ImageDetailActivity extends AppCompatActivity {
    String imgPath, imgName;
    private ImageView imageView;
    private TextView messageText;
    private ScaleGestureDetector scaleGestureDetector;
    private float mScaleFactor = 1.0f;
    private float focusX, focusY;
    private float imageCenterX, imageCenterY;
    private Button backBtnView;
    ImageView showBtnView, hideBtnView;
    private LinearLayoutCompat topDesignLayout;
    private LinearLayoutCompat bottomDesignLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_detail);

        imgPath = getIntent().getStringExtra("imgPath");
        imgName = getIntent().getStringExtra("imgName");
        // imgPath = "https://m.media-amazon.com/images/I/71NXDOFEjpL.jpg";
        messageText = findViewById(R.id.messageText);
        messageText.setText(imgName);

        imageView = findViewById(R.id.idIVImage);
        scaleGestureDetector = new ScaleGestureDetector(this, new ScaleListener());

        // Load the image using Glide from the online URL
        Glide.with(this)
                .load(imgPath) // Provide the URL of the image here
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.img_no_image)
                .into(imageView);

        imageView.post(() -> {
            // Get the center coordinates of the ImageView
            imageCenterX = imageView.getWidth() / 2f;
            imageCenterY = imageView.getHeight() / 2f;
        });


        backBtnView = findViewById(R.id.backBtnView);
        backBtnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Perform any additional actions you need before finishing the current activity

                // Finish the current activity to go back to the previous one
                finish();
            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        scaleGestureDetector.onTouchEvent(motionEvent);
        return true;
    }

    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {

        @Override
        public boolean onScaleBegin(ScaleGestureDetector detector) {
            focusX = detector.getFocusX();
            focusY = detector.getFocusY();
            return true;
        }

        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            mScaleFactor *= detector.getScaleFactor();
            mScaleFactor = Math.max(0.1f, Math.min(mScaleFactor, 10.0f));

            // Calculate the focus point offset
            float focusOffsetX = (focusX - imageCenterX);
            float focusOffsetY = (focusY - imageCenterY);

            imageView.setPivotX(focusOffsetX);
            imageView.setPivotY(focusOffsetY);

            imageView.setScaleX(mScaleFactor);
            imageView.setScaleY(mScaleFactor);

            // Translate the image to keep it centered
            float translateX = (imageView.getWidth() * mScaleFactor - imageView.getWidth()) / 2;
            float translateY = (imageView.getHeight() * mScaleFactor - imageView.getHeight()) / 2;

            imageView.setTranslationX(-translateX);
            imageView.setTranslationY(-translateY);

            return true;
        }
    }
}

/*
package com.madss.association;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.fragment.app.FragmentManager;

import com.bumptech.glide.Glide;
import java.io.File;

public class ImageDetailActivity extends AppCompatActivity {

    String imgPath;
    private ImageView imageView;
    private ImageView backBtnView,showBtnView,hideBtnView;
    private LinearLayoutCompat topDesignLayout;
    private LinearLayoutCompat bottomDesignLayout;
    private ScaleGestureDetector scaleGestureDetector;
    private float mScaleFactor = 1.0f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_detail);

        imgPath = getIntent().getStringExtra("imgPath");
        imageView = findViewById(R.id.idIVImage);
        scaleGestureDetector = new ScaleGestureDetector(this, new ScaleListener());

        // Load the image using Glide from the online URL
        Glide.with(this)
                .load(imgPath) // Provide the URL of the image here
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.myknwlogo)
                .into(imageView);



        backBtnView = findViewById(R.id.backBtnView);
        backBtnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Perform any additional actions you need before finishing the current activity

                // Finish the current activity to go back to the previous one
                finish();
            }
        });



        topDesignLayout = findViewById(R.id.topDesignLayout);
        bottomDesignLayout = findViewById(R.id.bottomDesignLayout);


        showBtnView = findViewById(R.id.showBtn);
        showBtnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                    topDesignLayout.setVisibility(View.VISIBLE);  // Make it visible
                    bottomDesignLayout.setVisibility(View.VISIBLE);  // Make it visible

                showBtnView.setVisibility(View.GONE);  // Make Button Visible




            }
        });
        hideBtnView = findViewById(R.id.hideBtn);
        hideBtnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    topDesignLayout.setVisibility(View.GONE);  // Make it hidden
                    bottomDesignLayout.setVisibility(View.GONE);  // Make it hidden
                    showBtnView.setVisibility(View.VISIBLE);  // Make Button Visible





            }
        });




    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        scaleGestureDetector.onTouchEvent(motionEvent);
        return true;
    }

    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        @Override
        public boolean onScale(ScaleGestureDetector scaleGestureDetector) {
            mScaleFactor *= scaleGestureDetector.getScaleFactor();
            mScaleFactor = Math.max(0.1f, Math.min(mScaleFactor, 10.0f));
            imageView.setScaleX(mScaleFactor);
            imageView.setScaleY(mScaleFactor);
            return true;
        }
    }
}

 */

