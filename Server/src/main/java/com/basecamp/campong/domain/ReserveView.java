package com.basecamp.campong.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import net.minidev.json.annotate.JsonIgnore;
import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Subselect;

import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@Entity
@Immutable
@Subselect("SELECT reservelist.reserveid as reserveid, rentaldate, returndate, catename, title, location, post_list.imageid as imageid, fee, " +
        "reservelist.userid as borrowuserid, post_list.userid as lenduserid, usernick, state " +
        "FROM reservelist join " +
        "(select reserveid, max(state) as state from reserve_state group by reserveid) as recentstate " +
        "on reservelist.reserveid = recentstate.reserveid " +
        "join post_list on post_list.postid = reservelist.postid join category on post_list.cateid = category.cateid " +
        "join user on user.userid = reservelist.userid")
@JsonIgnoreProperties(value = {"lenduserid", "borrowuserid"})
public class ReserveView {
    @Id
    private long reserveid;

    private String catename;

    private String title;

    private String location;

    private String rentaldate;

    private String returndate;

    private long imageid;

    private long fee;

    private int state;

    // 빌려준 사람
    private long lenduserid;

    // 빌린 사람
    private long borrowuserid;

    private long usernick;
}
