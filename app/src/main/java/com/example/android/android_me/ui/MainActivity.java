package com.example.android.android_me.ui;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import com.example.android.android_me.R;
import com.example.android.android_me.data.AndroidImageAssets;

public class MainActivity extends AppCompatActivity implements MasterListFragment.OnImageClickListener {

    private int headIndex;
    private int bodyIndex ;
    private int legIndex ;

    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(findViewById(R.id.android_me_linear_layout) != null){
            mTwoPane = true;

            Button nextButton = findViewById(R.id.next_button);
            nextButton.setVisibility(View.GONE);

            GridView gridView = findViewById(R.id.images_grid_view);
            gridView.setNumColumns(2);

            if(savedInstanceState == null){

                FragmentManager fragmentManager = getSupportFragmentManager();

                BodyPartFragment headFragment = new BodyPartFragment();
                headFragment.setmImageIds(AndroidImageAssets.getHeads());

                headFragment.setmListIndex(headIndex);
                fragmentManager
                        .beginTransaction()
                        .add(R.id.head_container, headFragment)
                        .commit();

                BodyPartFragment bodyFragment = new BodyPartFragment();
                bodyFragment.setmImageIds(AndroidImageAssets.getBodies());
                bodyFragment.setmListIndex(bodyIndex);
                fragmentManager
                        .beginTransaction()
                        .add(R.id.body_container, bodyFragment)
                        .commit();


                BodyPartFragment legFragment = new BodyPartFragment();
                legFragment.setmImageIds(AndroidImageAssets.getLegs());
                legFragment.setmListIndex(legIndex);
                fragmentManager
                        .beginTransaction()
                        .add(R.id.leg_container, legFragment)
                        .commit();
            }
        } else {
            mTwoPane = false;
        }
    }

    @Override
    public void onImageClicked(int position) {
        int bodyPartNumber = position/12;
        int listIndex = position - 12*bodyPartNumber;

        if(mTwoPane){
            BodyPartFragment newFragment = new BodyPartFragment();

            switch (bodyPartNumber){
                case 0:
                    newFragment.setmImageIds(AndroidImageAssets.getHeads());
                    newFragment.setmListIndex(listIndex);
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.head_container, newFragment)
                            .commit();
                    break;
                case 1:
                    newFragment.setmImageIds(AndroidImageAssets.getBodies());
                    newFragment.setmListIndex(listIndex);
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.body_container, newFragment)
                            .commit();
                    break;
                case 2:
                    newFragment.setmImageIds(AndroidImageAssets.getLegs());
                    newFragment.setmListIndex(listIndex);
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.leg_container, newFragment)
                            .commit();
                    break;
                default:
                    break;
            }
        }else {
            switch (bodyPartNumber){
                case 0: headIndex = listIndex;
                    break;
                case 1: bodyIndex = listIndex;
                    break;
                case 2: legIndex = listIndex;
                    break;
                default: break;

            }
        }

        Bundle bundle = new Bundle();
        bundle.putInt("headIndex", headIndex);
        bundle.putInt("bodyIndex", bodyIndex);
        bundle.putInt("legIndex", legIndex);

        Toast.makeText(this, "Position clicked = " + position, Toast.LENGTH_SHORT).show();

        final Intent intent = new Intent(this, AndroidMeActivity.class);
        intent.putExtras(bundle);

        Button next = findViewById(R.id.next_button);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent);
            }
        });
    }
}
