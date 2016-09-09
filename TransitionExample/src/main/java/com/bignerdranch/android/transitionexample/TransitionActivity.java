package com.bignerdranch.android.transitionexample;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.transition.ChangeBounds;
import android.transition.Fade;
import android.transition.Scene;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.transition.TransitionManager;
import android.transition.TransitionSet;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;

public class TransitionActivity extends Activity {
    private static final String TAG = "TransitionActivity";
    int type = 0;
    private TransitionManager mTransitionManager;
    private Scene mScene1;
    private Scene mScene2;

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transition);

        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new TransitionFragment())
                    .commit();
        }
        ViewGroup container = (ViewGroup) findViewById(R.id.container);
        TransitionInflater transitionInflater = TransitionInflater.from(this);
        mTransitionManager = transitionInflater.inflateTransitionManager(R.transition.transition_manager, container);
        mScene1 = Scene.getSceneForLayout(container, R.layout.fragment_transition_scene_1, this);
        mScene2 = Scene.getSceneForLayout(container, R.layout.fragment_transition_scene_2, this);
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public void goToScene1(View view) {
        goToScene(mScene1);
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public void goToScene2(View view) {
        goToScene(mScene2);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.transition, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void goToScene(Scene scene) {
        type++;
        if (type > 3) {
            type = 1;
        }
        Log.d(TAG, "goToScene: type:" + type);
        switch (type) {
            case 1: {
                ChangeBounds changeBounds = new ChangeBounds();
                changeBounds.setInterpolator(new AccelerateDecelerateInterpolator());
                changeBounds.setDuration(1000);
                Fade fadeOut = new Fade(Fade.OUT);
                fadeOut.setDuration(1000);
                Fade fadeIn = new Fade(Fade.IN);
                fadeIn.setDuration(1000);
                TransitionSet transition = new TransitionSet();
                transition.setOrdering(TransitionSet.ORDERING_SEQUENTIAL);
                transition
                        .addTransition(fadeOut)
                        .addTransition(changeBounds)
                        .addTransition(fadeIn);
                TransitionManager.go(scene, transition);
            }
            break;
            case 2: {

                TransitionInflater transitionInflater = TransitionInflater.from(this);
                Transition transition = transitionInflater.inflateTransition(R.transition.slow_auto_transition);
                TransitionManager.go(scene, transition);
            }
            break;
            case 3: {
                mTransitionManager.transitionTo(scene);
                break;
            }
            default:
        }
    }


}
