package com.basecamp.campong.service;

import com.basecamp.campong.domain.*;
import com.basecamp.campong.repository.*;
import com.basecamp.campong.util.JsonMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    @Autowired
    ReserveReadRepository reserveReadRepository;

    public JsonMap reserveinit(Reservelist body) {
        System.out.println("RESERVE-INIT: START");
        JsonMap result = new JsonMap();
        PostList post = postRepository.findById(body.getPostid()).orElse(null);
        if (post == null) {
            System.out.println("RESERVE-INIT: ERROR - NO-POST");
            return result.setError(3001, "게시물을 찾을 수 없습니다");
        }
        if (reserveRepository.findByPost(post).isPresent()) {
            System.out.println("RESERVE-INIT: ERROR - PRESENT RESERVE");
            return result.setError(3002, "이미 예약기록이 있는 게시물입니다.");
        }
        System.out.println("RESERVE-INIT: END");
        return result;
    }

    public JsonMap reserveRequest(long userid, Reservelist body) {
        System.out.println("RESERVE-REQUEST: START");
        JsonMap result = new JsonMap();
        JsonMap init = reserveinit(body);
        if (init.isError()) {
            return init;
        }

        User user = userRepository.findByUserid(userid);

        PostList post = postRepository.findById(body.getPostid()).get();
        body.setPost(post);
        body.setUser(user);
        body = reserveRepository.save(body);

        ReserveState state = ReserveState.builder().state(1).reserve(body).build();
        reserveStateRepository.save(state);

        System.out.println("RESERVE-REQUEST: END");
        return result;
    }

    public JsonMap reserveList(long userid, Reservelist body) {
        System.out.println("RESERVE-LIST: START");
        JsonMap result = new JsonMap();

        int requeststate = body.getRequeststate();
        List<ReserveView> reservelist =
                reserveReadRepository.findAllByLenduseridAndStateBetweenOrderByReserveidDesc(userid, requeststate, requeststate != 1 ? requeststate : requeststate + 1, PageRequest.of(body.getPagenum(), 10)).getContent();

        for(int i=1; i<=5; i++){
            result.put("num"+i, reserveReadRepository.countAllByLenduseridAndState(userid, i));
        }
        result.put("data", reservelist);

        System.out.println("RESERVE-LIST: END");
        return result;
    }

    public JsonMap reserveMyList(long userid, Reservelist body) {
        System.out.println("RESERVE-MyLIST: START");
        JsonMap result = new JsonMap();

        int requeststate = body.getRequeststate();
        List<ReserveView> reservelist =
                reserveReadRepository.findAllByBorrowuseridAndStateBetweenOrderByReserveidDesc(userid, requeststate, requeststate != 1 ? requeststate : requeststate + 1, PageRequest.of(body.getPagenum(), 10)).getContent();
        for(int i=1; i<=5; i++){
            result.put("num"+i, reserveReadRepository.countAllByBorrowuseridAndState(userid, i));
        }
        result.put("data", reservelist);

        System.out.println("RESERVE-MyLIST: END");
        return result;
    }


    public JsonMap reserveView(long userid, Reservelist body) {
        System.out.println("RESERVE-VIEW: START");
        JsonMap result = new JsonMap();
        ReserveView reserveView = reserveReadRepository.findById(body.getReserveid()).orElse(null);
        result.put("reserve", reserveView);
        System.out.println("RESERVE-VIEW: END");
        return result;
    }


    public JsonMap reserveState(long userid, Reservelist body, int statecode) {
        System.out.println("RESERVE-STATE: START");
        JsonMap result = new JsonMap();

        User user = userRepository.findByUserid(userid);

        // 예약 내역 확인
        Reservelist reserve = reserveRepository.findById(body.getReserveid()).orElse(null);
        if (reserve == null) {
            System.out.println("RESERVE-STATE: ERROR - NO-RESERVE");
            return result.setError(3001, "예약내역을 찾을 수 없습니다");
        }

        // 유저 확인
        if (reserve.getPost().getUser().getUserid() != user.getUserid()) {
            System.out.println("RESERVE-STATE: ERROR - NO-AUTH - POST WRITER IS NOT USER");
            return result.setError(3001, "권한이 없는 유저입니다.");
        }

        // 이전 상태 확인
        ReserveState recent = reserveStateRepository.findTopByReserveOrderByStateidDesc(reserve).get();

        switch (recent.getState()) {
            case 1:
                if (statecode != 2 && statecode != 5) {
                    System.out.println("RESERVE-STATE: ERROR - ALREADY REQUEST");
                    return result.setError(3005, "현재 예약 요청 상태입니다. (예약확정과 취소만 가능)");
                }
                break;
            case 2:
                if (statecode != 3 && statecode != 5) {
                    System.out.println("RESERVE-STATE: ERROR - ALREADY GRANT");
                    return result.setError(3005, "현재 예약 확정 상태입니다. (대여와 취소만 가능)");
                }
                break;
            case 3:
                if (statecode != 4) {
                    System.out.println("RESERVE-STATE: ERROR - ALREADY RENTAL");
                    return result.setError(3005, "현재 대여 상태입니다. (반납만 가능)");
                }
                break;
            case 4:
                System.out.println("RESERVE-STATE: ERROR - ALREADY RETURN");
                return result.setError(3005, "이미 반납이 완료되었습니다.");
            case 5:
                System.out.println("RESERVE-STATE: ERROR - ALREADY CANCEL");
                return result.setError(3005, "이미 취소된 예약입니다.");
        }

        ReserveState state = ReserveState.builder().state(statecode).reserve(reserve).build();
        reserveStateRepository.save(state);

        System.out.println("RESERVE-STATE: END");
        return result;
    }

}
