package com.basecamp.campong.domain;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class Post {
    private long postid;

    private String catename;

    private String usernick;

    private String title;

    private String location;

    private long imageid;

    private LocalDateTime uploaddate;

    private long fee;

    public Post(PostList post){
        setPostid(post.getPostid());
        setCatename(post.getCategory().getCatename());
        setUsernick(post.getUser().getUsernick());
        setTitle(post.getTitle());
        setLocation(post.getLocation());
        if(post.getItemphoto() != null)
            setImageid(post.getItemphoto().getImageid());
        setUploaddate(post.getUploaddate());
        setFee(post.getFee());
    }
}
