package mvp.com.mvptest.model;

import com.google.gson.annotations.Expose;

import org.parceler.Parcel;

/**
 * Created by root1 on 2/22/15.
 */
@Parcel
public class Comment {

    @Expose
    public String titleImage;
    @Expose
    public String name;
    @Expose
    public String time;
    @Expose
    public String loveCount;
    @Expose
    public String comment;
    @Expose
    public String id;


    public String getTitleImage() {
        return titleImage;
    }

    public void setTitleImage(String titleImage) {
        this.titleImage = titleImage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
