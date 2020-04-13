package sh.broy.lpuhms.Animation;

import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;

public class LoginAnimation {
    public Animation FadeInAnimation(int delay) {
        Animation fadeIn = new AlphaAnimation(0, 1);
        fadeIn.setDuration(600);
        fadeIn.setStartOffset(delay);
        return fadeIn;
    }
}
