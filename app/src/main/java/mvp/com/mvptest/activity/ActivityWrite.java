package mvp.com.mvptest.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewStub;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.VideoView;

import com.hs.image.ImageIntentHandler;
import com.hs.image.ImageUtils;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import mvp.com.mvptest.ChatUtil;
import mvp.com.mvptest.ProgressDialogFragment;
import mvp.com.mvptest.R;
import mvp.com.mvptest.adapter.RecyclerViewTimelineListAdapter;
import mvp.com.mvptest.model.PostStory;
import mvp.com.mvptest.presenter.FeedView;
import mvp.com.mvptest.presenter.MainPresenter;
import mvp.com.mvptest.stickerNew.FragmentPageAdapter;
import mvp.com.mvptest.stickerNew.SS0Fragment;
import mvp.com.mvptest.stickerNew.SS1Fragment;
import mvp.com.mvptest.stickerNew.SS2Fragment;
import mvp.com.mvptest.stickerNew.SS3Fragment;
import mvp.com.mvptest.stickerNew.SS4EmojiFragment;

public class ActivityWrite extends AppCompatActivity implements FeedView, SS0Fragment.OnChildInteractionListener, SS1Fragment.OnChildInteractionListener2,
        SS2Fragment.OnChildInteractionListener3, SS3Fragment.OnChildInteractionListener4, SS4EmojiFragment.OnChildInteractionListener5 {

    RecyclerView rvFeed;
    EditText chat_edit_text1;
    boolean isLayoutStickerShow = false;
    boolean isLayoutStickerShowPhoto = false;
    boolean isLayoutStickerShowVideo = false;
    FragmentPageAdapter pageAdapter;
    private ImageView btn_sticker;
    TabLayout mTabs;
    private ViewPager mViewPager;
    private FragmentManager fragmentManager;
    RelativeLayout viewEdit;
    ViewStub viewStub;
    ViewStub viewStubPoto;
    ViewStub viewStub_video;
    ImageView img_sticker,img_photo,img_video;
    Button btn_choose_camera,btn_photo;
    //ImageView preview_photo;
    private Uri fileUri;
    Button btn_choose_video,btn_video;
    VideoView preview_video;
    ImageView preview_photo;
    private static final int CAMERA_CAPTURE_VIDEO_REQUEST_CODE = 300;
    private static final int CAMERA_IMAGE_VIDEO_REQUEST_CODE = 400;
    public static final int MEDIA_TYPE_IMAGE = 1;
    public static final int MEDIA_TYPE_VIDEO = 2;
    private static final int REQUEST_PICK_IMAGE = 10011;
    private static final int REQUEST_SAF_PICK_IMAGE = 10012;
    private static final String IMAGE_DIRECTORY_NAME = "Hello Camera";
    ImageIntentHandler.ImagePair mImagePair;
    View inflatedView;
    View inflatedViewPhoto;
    View inflatedViewVideo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_on_event);
        rvFeed = (RecyclerView) findViewById(R.id.rvFeed);
        viewEdit = (RelativeLayout) findViewById(R.id.viewEdit);
        chat_edit_text1 = (EditText) findViewById(R.id.chat_edit_text1);
        btn_sticker = (ImageView) findViewById(R.id.emojiButton);
        img_sticker = (ImageView) findViewById(R.id.img_sticker);
        img_photo = (ImageView) findViewById(R.id.img_photo);
        img_video = (ImageView) findViewById(R.id.img_video);


        fragmentManager = getSupportFragmentManager();

        viewStub = (ViewStub) findViewById(R.id.viewStub1);
        viewStubPoto = (ViewStub) findViewById(R.id.viewStub_photo);
        viewStub_video = (ViewStub) findViewById(R.id.viewStub_video);
        inflatedView = viewStub.inflate();
        inflatedViewPhoto = viewStubPoto.inflate();
        inflatedViewVideo = viewStub_video.inflate();
        inflatedView.setVisibility(View.GONE);
        inflatedViewPhoto.setVisibility(View.GONE);
        inflatedViewVideo.setVisibility(View.GONE);

        mViewPager = (ViewPager) inflatedView.findViewById(R.id.vpPager);
        mTabs = (TabLayout) inflatedView.findViewById(R.id.tabs);
        btn_choose_camera = (Button) inflatedViewPhoto.findViewById(R.id.btn_choose_camera);
        btn_photo = (Button) inflatedViewPhoto.findViewById(R.id.btn_photo);
        preview_photo = (ImageView) inflatedViewPhoto.findViewById(R.id.preview_photo);

        btn_choose_video = (Button) inflatedViewVideo.findViewById(R.id.btn_choose_video);
        btn_video = (Button) inflatedViewVideo.findViewById(R.id.btn_video);
        preview_video = (VideoView) inflatedViewVideo.findViewById(R.id.preview_video);

        setupViewPager(mViewPager);
        setupTabLayout(mTabs);


        img_sticker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeyboard(v);
                if (!isLayoutStickerShow) {
                    inflatedViewVideo.setVisibility(View.GONE);
                    inflatedViewPhoto.setVisibility(View.GONE);
                    inflatedView.setVisibility(View.VISIBLE);
                    isLayoutStickerShow = true;
                } else {
                    inflatedView.setVisibility(View.GONE);
                    isLayoutStickerShow = false;
                }
            }
        });
        img_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeyboard(v);
                if (!isLayoutStickerShowPhoto) {
                    inflatedView.setVisibility(View.GONE);
                    inflatedViewVideo.setVisibility(View.GONE);
                    inflatedViewPhoto.setVisibility(View.VISIBLE);
                    isLayoutStickerShowPhoto = true;
                    isLayoutStickerShowVideo = false;
                    isLayoutStickerShow = false;
                } else {
                    inflatedViewPhoto.setVisibility(View.GONE);
                    isLayoutStickerShowPhoto = false;
                }
            }
        });

        img_video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeyboard(v);
                if (!isLayoutStickerShowVideo) {
                    inflatedView.setVisibility(View.GONE);
                    inflatedViewPhoto.setVisibility(View.GONE);
                    inflatedViewVideo.setVisibility(View.VISIBLE);
                    isLayoutStickerShowVideo = true;
                    isLayoutStickerShowPhoto = false;
                    isLayoutStickerShow = false;
                } else {
                    inflatedViewVideo.setVisibility(View.GONE);
                    isLayoutStickerShowVideo = false;
                }
            }
        });

        //เลือกกล้อง
        btn_choose_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                File f = ImageUtils.createImageFile(ImageUtils.getPackageName(ActivityWrite.this));
                if ((f != null) && f.exists()) {
                    mImagePair = new ImageIntentHandler.ImagePair(preview_photo, f.getAbsolutePath());
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
                    startActivityForResult(takePictureIntent, ImageIntentHandler.REQUEST_CAPTURE);
                } else {
                    Toast.makeText(ActivityWrite.this, "Storage Error", Toast.LENGTH_LONG).show();
                }
            }
        });

        btn_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mImagePair = new ImageIntentHandler.ImagePair(preview_photo, null);
                Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, ImageIntentHandler.REQUEST_GALLERY);
            }
        });

        btn_choose_video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recordVideo();
            }
        });

        btn_video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pivkVideo();
            }
        });

        btn_sticker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeyboard(v);
                viewEdit.setVisibility(View.GONE);
                onClickStickerEvent();
            }
        });

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 200){
            ImageIntentHandler intentHandler =
                    new ImageIntentHandler(ActivityWrite.this, mImagePair)
                            .folder("IIH Sample")
                            .sizeDp(200);
            intentHandler.handleIntent(requestCode, resultCode, data);
        }if(requestCode == 100){
            ImageIntentHandler intentHandler =
                    new ImageIntentHandler(ActivityWrite.this, mImagePair)
                            .folder("IIH Sample")
                            .sizeDp(200);
            intentHandler.handleIntent(requestCode, resultCode, data);
        }


         if (requestCode == CAMERA_CAPTURE_VIDEO_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {

                previewVideo();
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(getApplicationContext(),
                        "User cancelled video recording", Toast.LENGTH_SHORT)
                        .show();
            } else {
                Toast.makeText(getApplicationContext(),
                        "Sorry! Failed to record video", Toast.LENGTH_SHORT)
                        .show();
            }
        }if(requestCode == CAMERA_IMAGE_VIDEO_REQUEST_CODE) {
            Uri uri = data.getData();


            if (uri != null) {
                String vdoThumb = ChatUtil.getThumbnailPathForLocalFile(ActivityWrite.this, uri);
                Log.e("sdsdsd", vdoThumb + "");
                preview_video.setVideoURI(uri);
                preview_video.start();
            }
        }
    }

    private void recordVideo() {
        Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);

        fileUri = getOutputMediaFileUri(MEDIA_TYPE_VIDEO);

        intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);

        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);

        startActivityForResult(intent, CAMERA_CAPTURE_VIDEO_REQUEST_CODE);
    }
    public void pivkVideo(){
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("video/*");
        startActivityForResult(photoPickerIntent, CAMERA_IMAGE_VIDEO_REQUEST_CODE);


    }

    public void setupViewPager(ViewPager viewPager) {
        pageAdapter = new FragmentPageAdapter(getApplicationContext(), fragmentManager);
        pageAdapter.addFragment(SS0Fragment.getInstance("set2"), "set2", R.drawable.ic_set_2, "");
        pageAdapter.addFragment(SS1Fragment.getInstance("set1"), "set1", R.drawable.ic_set_1, "");
        pageAdapter.addFragment(SS2Fragment.getInstance("set3"), "set3", R.drawable.ic_set_3, "");
        pageAdapter.addFragment(SS3Fragment.getInstance("set4"), "set4", R.drawable.ic_set_4, "");
        viewPager.setAdapter(pageAdapter);

    }

    public void setupViewPagerEmoji(ViewPager viewPager) {
        pageAdapter = new FragmentPageAdapter(getApplicationContext(), fragmentManager);
        pageAdapter.addFragment(SS4EmojiFragment.getInstance("null"), "null", android.R.drawable.star_off, "asdf");
        pageAdapter.addFragment(SS4EmojiFragment.getInstance("null"), "null", android.R.drawable.star_off, "asdf");
        viewPager.setAdapter(pageAdapter);
    }

    public void setupTabLayout(TabLayout tabLayout) {
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.setupWithViewPager(mViewPager);
        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            tab.setCustomView(pageAdapter.getTabView(i));
        }
        tabLayout.requestFocus();
    }

    @Override
    public void setPosts(List<PostStory> posts) {

    }

    @Override
    public Context getContext() {
        return null;
    }

    @Override
    public void onScrollDown(boolean isScrollDown) {

    }

    @Override
    public void resetCountUpAnimation() {

    }

    @Override
    public void clickOnItem(String i) {

    }

    @Override
    public void clickOnItem2(String pathset2) {

    }

    @Override
    public void clickOnItem3(String ii) {

    }

    @Override
    public void clickOnItem4(String pt) {

    }

    @Override
    public void clickOnItem5(String pt) {

    }
    private void previewVideo() {
        try {

            preview_video.setVisibility(View.VISIBLE);
            preview_video.setVideoPath(fileUri.getPath());
            // start playing
            preview_video.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //@NeedsPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
    public void pickImage() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            startActivityForResult(new Intent(Intent.ACTION_GET_CONTENT).setType("image/*"), REQUEST_PICK_IMAGE);
        } else {
            Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.setType("image/*");
            startActivityForResult(intent, REQUEST_SAF_PICK_IMAGE);
        }
    }

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public void onClickStickerEvent() {
        if (!isLayoutStickerShow) {
            isLayoutStickerShow = true;
        } else {
            isLayoutStickerShow = false;
        }
    }
    public Uri getOutputMediaFileUri(int type) {
        return Uri.fromFile(getOutputMediaFile(type));
    }

    private static File getOutputMediaFile(int type) {
        File mediaStorageDir = new File(
                Environment
                        .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                IMAGE_DIRECTORY_NAME);
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d(IMAGE_DIRECTORY_NAME, "Oops! Failed create "
                        + IMAGE_DIRECTORY_NAME + " directory");
                return null;
            }
        }

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                Locale.getDefault()).format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + "IMG_" + timeStamp + ".jpg");
        } else if (type == MEDIA_TYPE_VIDEO) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + "VID_" + timeStamp + ".mp4");
        } else {
            return null;
        }

        return mediaFile;
    }


}
