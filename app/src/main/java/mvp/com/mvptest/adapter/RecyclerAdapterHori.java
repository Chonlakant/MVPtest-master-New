package mvp.com.mvptest.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import mvp.com.mvptest.R;
import mvp.com.mvptest.model.Comment_view;
import mvp.com.mvptest.wiget.RoundedTransformation;

public class RecyclerAdapterHori extends RecyclerView.Adapter<RecyclerAdapterHori.ContactViewHolder> {

    Context context;
    ArrayList<Comment_view> list = new ArrayList<>();
    public static OnItemClickListener mItemClickListener;

    public RecyclerAdapterHori(Context context, ArrayList<Comment_view> list) {
        this.context = context;
        this.list = list;

    }


    @Override
    public int getItemCount() {
        return 5;
    }

    @Override
    public void onBindViewHolder(ContactViewHolder contactViewHolder, int i) {
        Comment_view item = list.get(i);
        contactViewHolder.title_tv.setText(item.getName());
        contactViewHolder.textView2.setText(item.getMsg());
        Log.e("xxxxx", item.getName());


        Picasso.with(context)
                .load(item.getImg())
                .centerCrop()
                .resize(200, 200)
                .transform(new RoundedTransformation(100, 4))
                .into(contactViewHolder.imageView2);
    }

    @Override
    public ContactViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.item_comment_view_hori, viewGroup, false);

        return new ContactViewHolder(itemView);
    }

    public static class ContactViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView imageView2;
        TextView title_tv, textView2;
        CardView card_view;

        public ContactViewHolder(View v) {
            super(v);
            imageView2 = (ImageView) v.findViewById(R.id.imageView2);
            title_tv = (TextView) v.findViewById(R.id.textView);
            textView2 = (TextView) v.findViewById(R.id.textView2);
            v.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
//            switch (v.getId()) {
//                case R.id.card_view:
//                    if (mItemClickListener != null) {
//                        mItemClickListener.onItemClick(v, getPosition());
//                    }
//
//            }
        }
    }

    public interface OnItemClickListener {
        public void onItemClick(View view, int position);

    }

    public void SetOnItemVideiosClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

}