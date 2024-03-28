package org.example.testserver.controller;

import org.example.testserver.aggregate.entity.WBS;
import org.example.testserver.aggregate.vo.ResponseMessageVO;
import org.example.testserver.aggregate.vo.WBS.RequestProjectWBSListVO;
import org.example.testserver.aggregate.vo.WBS.RequestProjectWBSVO;
import org.example.testserver.aggregate.vo.WBS.ResponseProjectWBSListVO;
import org.example.testserver.aggregate.vo.WBS.ResponseProjectWBSVO;
import org.example.testserver.service.WBSService;
import org.example.testserver.service.SessionService;
import org.example.testserver.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/wbs")
public class WBSController {

    private final WBSService WBSService;
    private final SessionService sessionService;
    private final UserService userService;

    public WBSController(WBSService WBSService, SessionService sessionService, UserService userService) {
        this.WBSService = WBSService;
        this.sessionService = sessionService;
        this.userService = userService;
    }

    @GetMapping("/{projectId}")
    public ResponseEntity<ResponseProjectWBSListVO> findWBSListByProjectId(@PathVariable("projectId") String projectId) throws Exception {
        ResponseProjectWBSListVO response = new ResponseProjectWBSListVO();

        if (sessionService.whoAmI() == null) {
            response.setMessage("로그인 이후 이용해주세요.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        ArrayList<WBS> wbsList = WBSService.findWBSListByProjectId(Integer.parseInt(projectId));
        ArrayList<ResponseProjectWBSVO> wbsVOS = new ArrayList<>();

        for (WBS wbs : wbsList) {
            wbsVOS.add(changeWBSToResponseWBSVO(wbs));
        }

        response.setMessage("조회 성공");
        response.setResponseWBSVOList(wbsVOS);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/add/{projectId}")
    public ResponseEntity<ResponseProjectWBSVO> addWBS(@PathVariable("projectId") String projectId,
                                                       @RequestBody RequestProjectWBSVO request) throws Exception {
        ResponseProjectWBSVO response = new ResponseProjectWBSVO();

        if (sessionService.whoAmI() == null) {
            response.setMessage("로그인 이후 이용해주세요.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        if (request.getManagerId() == 0) {
            request.setManagerId(sessionService.whoAmI().getId());
        }

        response = changeWBSToResponseWBSVO(WBSService.addWBS(Integer.parseInt(projectId), request.getContent(),
                request.getStatus(), request.getStartDate(), request.getEndDate(), request.getManagerId()));

        response.setMessage("추가 성공");

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/save/{projectId}")
    public ResponseEntity<ResponseProjectWBSListVO> saveWBSList(@PathVariable("projectId") String projectId,
                                                                @RequestBody RequestProjectWBSListVO request) {
        ResponseProjectWBSListVO response = new ResponseProjectWBSListVO();

        if (sessionService.whoAmI() == null) {
            response.setMessage("로그인 이후 이용해주세요.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        ArrayList<WBS> wbsList = WBSService.findWBSListByProjectId(Integer.parseInt(projectId));

        for (WBS wbs : wbsList) {
            WBSService.removeWBS(Integer.parseInt(projectId), wbs.getNo());
        }

        ArrayList<ResponseProjectWBSVO> wbsVOList = new ArrayList<>();

        for (RequestProjectWBSVO wbsVO : request.getTarget()) {
            WBS wbs = WBSService.addWBS(Integer.parseInt(projectId), wbsVO.getContent(),
                    wbsVO.getStatus(), wbsVO.getStartDate(), wbsVO.getEndDate(), wbsVO.getManagerId());
            try {
                wbsVOList.add(changeWBSToResponseWBSVO(wbs));
            } catch (Exception e) {
                response.setMessage("저장 실패");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
        }

        response.setMessage("저장 성공");
        response.setResponseWBSVOList(wbsVOList);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("/modify/{projectId}")
    public ResponseEntity<ResponseProjectWBSVO> modifyWBS(@PathVariable("projectId") String projectId,
                                                          @RequestBody RequestProjectWBSVO request) {
        ResponseProjectWBSVO response = new ResponseProjectWBSVO();

        if (sessionService.whoAmI() == null) {
            response.setMessage("로그인 이후 이용해주세요.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        try {
            WBS wbs = WBSService.modifyWBS(Integer.parseInt(projectId), request.getNo(), request.getContent(),
                    request.getStatus(), request.getStartDate(), request.getEndDate(), request.getManagerId());

            response = changeWBSToResponseWBSVO(wbs);

        } catch (Exception e) {
            response.setMessage("수정 실패");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        response.setMessage("수정 성공");

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/remove/{projectId}")
    public ResponseEntity<ResponseMessageVO> removeWBS(@PathVariable("projectId") String projectId,
                                                       @RequestBody RequestProjectWBSVO request) {
        ResponseMessageVO response = new ResponseMessageVO();

        if (sessionService.whoAmI() == null) {
            response.setMessage("로그인 이후 이용해주세요.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        if (WBSService.removeWBS(Integer.parseInt(projectId), request.getNo())) {
            response.setMessage("삭제 성공");
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }

        response.setMessage("삭제 실패");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    private ResponseProjectWBSVO changeWBSToResponseWBSVO(WBS wbs) throws Exception {
        ResponseProjectWBSVO wbsVO = new ResponseProjectWBSVO();
        wbsVO.setId(wbs.getId());
        wbsVO.setNo(wbs.getNo());
        wbsVO.setContent(wbs.getContent());
        wbsVO.setStatus(wbs.getStatus());
        wbsVO.setStartDate(wbs.getStartDate());
        wbsVO.setEndDate(wbs.getEndDate());

        wbsVO.setManager(userService.findUserById(wbs.getManagerId()));

        wbsVO.setProjectId(wbs.getProjectId());

        return wbsVO;
    }
}
