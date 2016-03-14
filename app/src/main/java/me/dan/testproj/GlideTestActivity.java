/*
 * GlideTestActivity
 * Version : 1.0
 * 2016-03-11
 * CopyRight (c) Huami Company 2015
 *      All rights reserved
 */
package me.dan.testproj;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

/**
 * @author DanLv
 */
public class GlideTestActivity extends Activity {
    private ImageView mImgView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_glide_test);

        mImgView = (ImageView) findViewById(R.id.img);

        //Glide.with(this)
        //        .load(R.drawable.ic_discovery_banner_loading)
        //        .preload();

        Glide.with(this.getApplicationContext())
                .load(R.drawable.ic_discovery_banner_loading)
                        //.asBitmap()
                        //.placeholder(R.mipmap.ic_launcher)
                        //.skipMemoryCache(true)
                .dontAnimate()
                        //.dontTransform()
                        //.thumbnail(0.1f)
                        //.override(800, 800)
                        //.into(new ImageViewTarget<Bitmap>(mImgView) {
                        //    @Override
                        //    protected void setResource(Bitmap resource) {
                        //        mImgView.setImageBitmap(resource);
                        //    }
                        //});
                .into(mImgView);
    }
}
