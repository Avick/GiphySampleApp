package giphyimage.gifmos.avick.com.giphyimage.Activities;

import android.Manifest;
import android.animation.Animator;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by avick on 11/2/16.
 */

public class StartActivity extends BaseActivity {

    final static int REQUEST_ID_MULTIPLE_PERMISSIONS = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getBackgoundLayout().setVisibility(View.VISIBLE);
        if(checkPermission()) {
            getBackgoundLayout().animate().alpha(0).setDuration(3000).setListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animator) {

                }

                @Override
                public void onAnimationEnd(Animator animator) {
                    getBackgoundLayout().setVisibility(View.GONE);
                    startHome();
                }

                @Override
                public void onAnimationCancel(Animator animator) {

                }

                @Override
                public void onAnimationRepeat(Animator animator) {

                }
            });
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_ID_MULTIPLE_PERMISSIONS:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startHome();
                } else {
                    Toast.makeText(this, "app won't work", Toast.LENGTH_SHORT);
                    finish();
                }

                break;
        }
    }


    public void startHome(){
        Intent intent = new Intent(this, HomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    public boolean checkPermission() {
        if(Build.VERSION.SDK_INT >= 23) {
//            int hasLocationPermission = checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION);
//            int hasReadPermission = checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE);
//            int hasWritePermission = checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);

            List<String> listPermissionsNeeded = new ArrayList<>();


//            if(hasLocationPermission != PackageManager.PERMISSION_GRANTED) {
//                listPermissionsNeeded.add(Manifest.permission.ACCESS_FINE_LOCATION);
//            }
//
//            if(hasReadPermission != PackageManager.PERMISSION_GRANTED) {
//                listPermissionsNeeded.add(Manifest.permission.READ_EXTERNAL_STORAGE);
//            }
//
//            if(hasWritePermission != PackageManager.PERMISSION_GRANTED) {
//                listPermissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
//            }

            if (!listPermissionsNeeded.isEmpty())
            {
                requestPermissions(listPermissionsNeeded.toArray
                        (new String[listPermissionsNeeded.size()]), REQUEST_ID_MULTIPLE_PERMISSIONS);
                return false;
            }

            return true;

        } else {
            return true;
        }
    }

}
