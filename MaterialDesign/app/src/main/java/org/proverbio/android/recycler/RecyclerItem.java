package org.proverbio.android.recycler;

import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.widget.ImageView;

/**
 * @author Juan Pablo Proverbio <proverbio@nowcreatives.co>
 */
public class RecyclerItem
{
    private String url;
    private String name;
    private String email;
    private Drawable iconDrawable;
    private String count;
    private boolean countVisible;

    //Social
    private String likeCount;
    private String commentCount;
    private String shareCount;

    private ImageView imageView;

    public RecyclerItem(String name, String email, String url)
    {
        this.name = name;
        this.email = email;
        this.url = url;
    }

    public RecyclerItem(String url, String likeCount, String commentCount, String shareCount)
    {
        this.url = url;
        this.likeCount = likeCount;
        this.commentCount = commentCount;
        this.shareCount = shareCount;
    }

    public RecyclerItem(String name, String count)
    {
        this.name = name;
        this.count = count;
        this.countVisible = !TextUtils.isEmpty(count);
    }

    public String getUrl()
    {
        return url;
    }

    public void setUrl(String url)
    {
        this.url = url;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public Drawable getIconDrawable()
    {
        return iconDrawable;
    }

    public void setIconDrawable(Drawable iconDrawable)
    {
        this.iconDrawable = iconDrawable;
    }

    public String getCount()
    {
        return count;
    }

    public void setCount(String count)
    {
        this.count = count;
    }

    public boolean isCountVisible()
    {
        return countVisible;
    }

    public void setCountVisible(boolean countVisible)
    {
        this.countVisible = countVisible;
    }

    public ImageView getImageView()
    {
        return imageView;
    }

    public void setImageView(ImageView imageView)
    {
        this.imageView = imageView;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getLikeCount()
    {
        return likeCount;
    }

    public void setLikeCount(String likeCount)
    {
        this.likeCount = likeCount;
    }

    public String getCommentCount()
    {
        return commentCount;
    }

    public void setCommentCount(String commentCount)
    {
        this.commentCount = commentCount;
    }

    public String getShareCount()
    {
        return shareCount;
    }

    public void setShareCount(String shareCount)
    {
        this.shareCount = shareCount;
    }
}
