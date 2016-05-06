package mvp.com.mvptest.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.otto.Subscribe;

import mvp.com.mvptest.BaseFragment;
import mvp.com.mvptest.FeedListFragment;
import mvp.com.mvptest.GridListFragment;
import mvp.com.mvptest.R;


public class MainFeedListAndGridFragment extends AppCompatActivity {
    Toolbar toolbar;
    ImageView img_more, img_list;
    TextView txt_write,txt_photo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_feed_view);
        img_more = (ImageView) findViewById(R.id.img_more);
        img_list = (ImageView) findViewById(R.id.img_list);
        txt_write = (TextView) findViewById(R.id.txt_write);
        txt_photo = (TextView) findViewById(R.id.txt_photo);
        txt_write.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(getApplicationContext(),ActivityWrite.class);
                startActivity(i);
            }
        });

        txt_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        FeedListFragment threeFragment = new FeedListFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, threeFragment);
        transaction.commit();


        img_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                FeedListFragment threeFragment = new FeedListFragment();
//                FragmentTransaction transaction = getApplicationContext().getSupportFragmentManager().beginTransaction();
//                transaction.replace(R.id.container, threeFragment);
//                transaction.commit();
                Toast.makeText(getApplicationContext(),"sds",Toast.LENGTH_SHORT).show();
                GridListFragment fragment = new GridListFragment();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.add(R.id.container, fragment);
                transaction.commit();
            }
        });

        img_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FeedListFragment threeFragment = new FeedListFragment();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.container, threeFragment);
                transaction.commit();
            }
        });
    }



}