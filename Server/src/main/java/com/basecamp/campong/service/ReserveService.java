package com.basecamp.campong.service;

import com.basecamp.campong.domain.PostList;
import com.basecamp.campong.domain.ReserveState;
import com.basecamp.campong.domain.Reservelist;
import com.basecamp.campong.domain.User;
import com.basecamp.campong.repository.PostRepository;
import com.basecamp.campong.repository.ReserveRepository;
import com.basecamp.campong.repository.ReserveStateRepository;
import com.basecamp.campong.repository.UserRepository;
import com.basecamp.campong.util.JsonMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ReserveService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ReserveRepository reserveRepository;

    @Autowired
    ReserveStateRepository reserveStateRepository;

    @Autowired
    PostRepository postRepository;

    public JsonMap reserveinit(Reservelist body){
        System.out.println("RESERVE-INIT: START");
        JsonMap result = new JsonMap();
        PostList post = postRepository.findById(body.getPostid()).orElse(null);
        if(post == null){
            System.out.println("RESERVE-INIT: ERROR - NO-POST");
            return result.setError(3001, "게시물을 찾을 수 없습니다");
        }
        if(reserveRepository.findByPost(post).isPresent()){
            System.out.println("RESERVE-INIT: ERROR - PRESENT RESERVE");
            return result.setError(3002, "이미 예약기록이 있는 게시물입니다.");
        }
        System.out.println("RESERVE-INIT: END");
        return result;
    }

    public JsonMap reserveRequest(long userid, Reservelist body){
        System.out.println("RESERVE-REQUEST: START");
        JsonMap result = new JsonMap();
        JsonMap init = reserveinit(body);
        if(init.isError()){
            return init;
        }

        User user = userRepository.findByUserid(userid);

        PostList post = postRepository.findById(body.getPostid()).get();
        body.setPost(post);
        body.setUser(user);
        body = reserveRepository.save(body);

        ReserveState state = ReserveState.builder().reserve(body).build();
        reserveStateRepository.save(state);

        System.out.println("RESERVE-REQUEST: END");
        return result;
    }




}
