package org.example.testserver.controller;

import org.example.testserver.aggregate.entity.ProjectTestcase;
import org.example.testserver.aggregate.vo.projectTestcase.ResponseProjectTestcaseListVO;
import org.example.testserver.aggregate.vo.projectTestcase.ResponseProjectTestcaseVO;
import org.example.testserver.service.ProjectTestcaseService;
import org.example.testserver.service.SessionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
@RequestMapping("/testcase")
public class ProjectTestcaseController {

    private final ProjectTestcaseService projectTestcaseService;
    private final SessionService sessionService;

    public ProjectTestcaseController(ProjectTestcaseService projectTestcaseService, SessionService sessionService) {
        this.projectTestcaseService = projectTestcaseService;
        this.sessionService = sessionService;
    }

    @GetMapping("/{projectId}")
    public ResponseEntity<ResponseProjectTestcaseListVO> projectTestcaseList(@PathVariable("projectId") String projectId) {
        ResponseProjectTestcaseListVO response = new ResponseProjectTestcaseListVO();

        if (sessionService.whoAmI() == null) {
            response.setMessage("로그인 이후 이용해주세요.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        ArrayList<ProjectTestcase> testcases = projectTestcaseService.findProjectTestcaseByProjectId(Integer.parseInt(projectId));
        ArrayList<ResponseProjectTestcaseVO> testcaseVOArrayList = new ArrayList<>();

        for (ProjectTestcase testcase : testcases) {
            testcaseVOArrayList.add(
                    new ResponseProjectTestcaseVO(null, testcase.getNo(), testcase.getSeparate(), testcase.getContent(),
                            testcase.getExpectedValue(), testcase.getResult(), testcase.getNote(), testcase.getProjectId()));
        }

        response.setMessage("조회 성공");
        response.setProjectTestcases(testcaseVOArrayList);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
