package com.basecamp.campong.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Reserve {
    private long reserveid;

    private String catename;

    private String title;

    private String location;

    private String rentaldate;

    private String returndate;

    private long imageid;

    private long fee;

    private int state;

    public Reserve(Reservelist reservelist, int state){
        PostList post = reservelist.getPost();
        setReserveid(reservelist.getReserveid());
        setTitle(post.getTitle());
        setCatename(post.getCategory().getCatename());
        setLocation(post.getLocation());
        setRentaldate(reservelist.getRentaldate());
        setReturndate(reservelist.getReturndate());
        setFee(post.getFee());
        setState(state);
        setImageid(post.getItemphoto().getImageid());
    }
}
