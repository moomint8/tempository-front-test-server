package org.example.testserver.controller;

import org.example.testserver.aggregate.entity.ProjectTestcase;
import org.example.testserver.aggregate.vo.projectTestcase.RequestProjectTestcaseVO;
import org.example.testserver.aggregate.vo.projectTestcase.ResponseProjectTestcaseListVO;
import org.example.testserver.aggregate.vo.projectTestcase.ResponseProjectTestcaseVO;
import org.example.testserver.service.ProjectTestcaseService;
import org.example.testserver.service.SessionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/add/{projectId}")
    public ResponseEntity<ResponseProjectTestcaseVO> addProjectTestcase(@PathVariable("projectId") String projectId,
                                                                        @RequestBody RequestProjectTestcaseVO request) {
        ResponseProjectTestcaseVO response = new ResponseProjectTestcaseVO();

        if (sessionService.whoAmI() == null) {
            response.setMessage("로그인 이후 이용해주세요.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        ProjectTestcase testcase = projectTestcaseService.addProjectTestcase(Integer.parseInt(projectId),
                request.getSeparate(), request.getContent(), request.getExpectedValue(), request.getNote());
        response.setMessage("추가 성공");
        response.setNo(testcase.getNo());
        response.setSeparate(testcase.getSeparate());
        response.setContent(testcase.getContent());
        response.setExpectedValue(testcase.getExpectedValue());
        response.setResult(testcase.getResult());
        response.setNote(testcase.getNote());
        response.setProjectId(testcase.getProjectId());

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("/modify/{projectId}")
    public ResponseEntity<ResponseProjectTestcaseVO> modifyProjectTestcase(@PathVariable("projectId") String projectId,
                                                                        @RequestBody RequestProjectTestcaseVO request) {
        ResponseProjectTestcaseVO response = new ResponseProjectTestcaseVO();

        if (sessionService.whoAmI() == null) {
            response.setMessage("로그인 이후 이용해주세요.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        try {
            ProjectTestcase testcase = projectTestcaseService.modifyProjectTestcase(Integer.parseInt(projectId), request.getNo(), request.getSeparate(), request.getContent(), request.getExpectedValue(), request.getResult(), request.getNote());

            response.setMessage("변경 성공");
            response.setNo(testcase.getNo());
            response.setSeparate(testcase.getSeparate());
            response.setContent(testcase.getContent());
            response.setExpectedValue(testcase.getExpectedValue());
            response.setResult(testcase.getResult());
            response.setNote(testcase.getNote());
            response.setProjectId(testcase.getProjectId());

            return ResponseEntity.status(HttpStatus.OK).body(response);

        } catch (Exception e) {
            response.setMessage("잘못된 요청입니다.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }
}
