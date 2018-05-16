package ph.edu.uplb.ics.opruplb;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class SplashScreen extends Activity{
    SharedPreferences sharedpreferences;
    boolean splash;

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);

        sharedpreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        splash = sharedpreferences.getBoolean("Splash", false);

        setContentView(R.layout.splash_screen);

        ImageView imageView = (ImageView) findViewById(R.id.splashImageView);
        Animation alpha = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);

        imageView.startAnimation(alpha);

        alpha.setAnimationListener((new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putBoolean("Splash", true);
                Intent mainIntent = new Intent(SplashScreen.this, MainActivity.class);
                SplashScreen.this.startActivity(mainIntent);
                SplashScreen.this.finish();
            }
        }));
    }
}
