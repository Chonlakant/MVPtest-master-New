package mvp.com.mvptest.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxStatus;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import mvp.com.mvptest.R;
import mvp.com.mvptest.adapter.RecyclerAdapter;
import mvp.com.mvptest.adapter.RecyclerAdapterHori;
import mvp.com.mvptest.model.Comment;
import mvp.com.mvptest.model.Comment_view;
import mvp.com.mvptest.model.PostStory;
import mvp.com.mvptest.presenter.FeedView;
import mvp.com.mvptest.widget.PullScrollView;
import mvp.com.mvptest.wiget.RoundedTransformation;


public class ActivityComment extends AppCompatActivity {

    RecyclerAdapter recyclerAdapter;
    private AQuery aq;
    String url = "http://192.168.1.113:8080/test.php";
    ArrayList<Comment_view> listComment = new ArrayList<>();
    RelativeLayout holder;
    public RecyclerView mRecyclerView,rvFeed2,rvFeedList;
    String name;
    String imageProfile;
    String imagePhoto;
    ImageView thumb, profile_avatar;
    TextView profile_name, text;
    String textComment;
    String type;
    ImageView imageView2,imageView3,imageView4,imageView5,imageView6;
    LinearLayout text_view, photo_view,ln_comment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        text_view = (LinearLayout) findViewById(R.id.text_view);
        photo_view = (LinearLayout) findViewById(R.id.photo_view);
        ln_comment = (LinearLayout) findViewById(R.id.ln_comment);
        mRecyclerView = (RecyclerView) findViewById(R.id.rvFeed);
        rvFeed2 = (RecyclerView) findViewById(R.id.rvFeed2);
        imageView2 = (ImageView) findViewById(R.id.imageView2);
        imageView3 = (ImageView) findViewById(R.id.imageView3);
        imageView4 = (ImageView) findViewById(R.id.imageView4);
        imageView5 = (ImageView) findViewById(R.id.imageView5);
        imageView6 = (ImageView) findViewById(R.id.imageView6);

        text = (TextView) findViewById(R.id.text);
        type = getIntent().getStringExtra("type");
        aq = new AQuery(getApplicationContext());
        thumb = (ImageView) findViewById(R.id.thumb);
        profile_name = (TextView) findViewById(R.id.profile_name);
        profile_avatar = (ImageView) findViewById(R.id.profile_avatar);
        name = getIntent().getStringExtra("name");
        imageProfile = getIntent().getStringExtra("imageProfile");
        imagePhoto = getIntent().getStringExtra("imagePhoto");
        textComment = getIntent().getStringExtra("text");
        if (type.equals("text")) {
            aq.ajax(url, JSONObject.class, this, "jsonCallback");
            text_view.setVisibility(View.VISIBLE);
            photo_view.setVisibility(View.GONE);
            ln_comment.setVisibility(View.VISIBLE);
            text.setText(textComment);
            profile_name.setText(name);
            Picasso.with(getApplicationContext())
                    .load(imagePhoto)
                    .fit().centerCrop()
                    .into(thumb);

            Picasso.with(getApplicationContext())
                    .load(imageProfile)
                    .centerCrop()
                    .resize(200, 200)
                    .transform(new RoundedTransformation(100, 4))
                    .into(profile_avatar);
        }
        if (type.equals("photo")) {
            aq.ajax(url, JSONObject.class, this, "jsonCallback");
            text_view.setVisibility(View.GONE);
            photo_view.setVisibility(View.VISIBLE);
            profile_name.setText(name);
            Picasso.with(getApplicationContext())
                    .load(imagePhoto)
                    .fit().centerCrop()
                    .into(thumb);

            Picasso.with(getApplicationContext())
                    .load(imageProfile)
                    .centerCrop()
                    .resize(200, 200)
                    .transform(new RoundedTransformation(100, 4))
                    .into(profile_avatar);
        }


        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(llm);

        LinearLayoutManager llm2= new LinearLayoutManager(this);
        llm2.setOrientation(LinearLayoutManager.VERTICAL);
        rvFeed2.setLayoutManager(llm2);
    }

    public void jsonCallback(String url, JSONObject json, AjaxStatus status) {
        if (json != null) {
            try {
                JSONArray ja = json.getJSONArray("post");
                for (int i = 0; i < ja.length(); i++) {

                    JSONObject jo = ja.getJSONObject(i);
                    String name = jo.optString("name");
                    String msg = jo.optString("msg");
                    String img = jo.optString("img");

                    if(jo.optString("img") != null){
                        String img2 = "https://www.siamzone.com/board/upload/4053/4053442.jpg";
                        String img3 = "http://www.oknation.net/blog/home/blog_data/357/21357/images/f2.jpg";
                        String img4 = "http://fbi.dek-d.com/27/0419/5690/119612338";
                        String img5 = "http://image.dek-d.com/25/3058659/110441341";
                        String img6 = jo.optString("img");

                        Picasso.with(getApplicationContext())
                                .load(img2)
                                .centerCrop()
                                .resize(200, 200)
                                .transform(new RoundedTransformation(100, 4))
                                .into(imageView2);

                        Picasso.with(getApplicationContext())
                                .load(img3)
                                .centerCrop()
                                .resize(200, 200)
                                .transform(new RoundedTransformation(100, 4))
                                .into(imageView3);

                        Picasso.with(getApplicationContext())
                                .load(img4)
                                .centerCrop()
                                .resize(200, 200)
                                .transform(new RoundedTransformation(100, 4))
                                .into(imageView4);

                        Picasso.with(getApplicationContext())
                                .load(img5)
                                .centerCrop()
                                .resize(200, 200)
                                .transform(new RoundedTransformation(100, 4))
                                .into(imageView5);

                        Picasso.with(getApplicationContext())
                                .load(img6)
                                .centerCrop()
                                .resize(200, 200)
                                .transform(new RoundedTransformation(100, 4))
                                .into(imageView6);

                        Picasso.with(getApplicationContext())
                                .load(img2)
                                .centerCrop()
                                .resize(200, 200)
                                .transform(new RoundedTransformation(100, 4))
                                .into(imageView2);

                    }

                    Log.e("ddddd", name + msg + img + "");
                    Comment_view comment = new Comment_view();
                    comment.setName(name);
                    comment.setMsg(msg);
                    comment.setImg(img);
                    listComment.add(comment);
                    recyclerAdapter = new RecyclerAdapter(getApplicationContext(), listComment);
                    mRecyclerView.setAdapter(recyclerAdapter);
                    rvFeed2.setAdapter(recyclerAdapter);
                    recyclerAdapter.notifyDataSetChanged();


                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }


}
