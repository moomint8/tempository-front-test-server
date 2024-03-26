package org.example.testserver.controller;

import org.example.testserver.aggregate.entity.WBS;
import org.example.testserver.aggregate.vo.WBS.ResponseProjectWBSListVO;
import org.example.testserver.aggregate.vo.WBS.ResponseProjectWBSVO;
import org.example.testserver.service.ProjectWBSService;
import org.example.testserver.service.SessionService;
import org.example.testserver.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
@RequestMapping("/wbs")
public class ProjectWBSController {

    private final ProjectWBSService projectWBSService;
    private final SessionService sessionService;
    private final UserService userService;

    public ProjectWBSController(ProjectWBSService projectWBSService, SessionService sessionService, UserService userService) {
        this.projectWBSService = projectWBSService;
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

        ArrayList<WBS> wbsList = projectWBSService.findWBSListByProjectId(Integer.parseInt(projectId));
        ArrayList<ResponseProjectWBSVO> wbsVOS = new ArrayList<>();

        for (WBS wbs : wbsList) {
            wbsVOS.add(changeWBSToResponseWBSVO(wbs));
        }

        response.setMessage("조회 성공");
        response.setResponseWBSVOList(wbsVOS);

        return ResponseEntity.status(HttpStatus.OK).body(response);
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
