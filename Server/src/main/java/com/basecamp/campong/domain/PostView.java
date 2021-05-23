package com.basecamp.campong.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Subselect;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@Immutable
@Getter
@Subselect("SELECT postid, catename, usernick, title, location, post.imageid as imageid, uploaddate, fee, contents, lat, lon  FROM post_list as post join user join category on post.cateid = category.cateid where deletestate = 0")
public class PostView {

    @Id
    private long postid;

    private String catename;

    private String usernick;

    private String title;

    private String location;

    private long imageid;

    private LocalDateTime uploaddate;

    private long fee;

    private String contents;

    private String lat;

    private String lon;
}
