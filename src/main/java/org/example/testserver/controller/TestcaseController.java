package org.example.testserver.controller;

import org.example.testserver.aggregate.entity.Testcase;
import org.example.testserver.aggregate.vo.ResponseMessageVO;
import org.example.testserver.aggregate.vo.projectTestcase.RequestProjectTestcaseVO;
import org.example.testserver.aggregate.vo.projectTestcase.ResponseProjectTestcaseListVO;
import org.example.testserver.aggregate.vo.projectTestcase.ResponseProjectTestcaseVO;
import org.example.testserver.service.TestcaseService;
import org.example.testserver.service.SessionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/testcase")
public class TestcaseController {

    private final TestcaseService testcaseService;
    private final SessionService sessionService;

    public TestcaseController(TestcaseService testcaseService, SessionService sessionService) {
        this.testcaseService = testcaseService;
        this.sessionService = sessionService;
    }

    @GetMapping("/{projectId}")
    public ResponseEntity<ResponseProjectTestcaseListVO> projectTestcaseList(@PathVariable("projectId") String projectId) {
        ResponseProjectTestcaseListVO response = new ResponseProjectTestcaseListVO();

        if (sessionService.whoAmI() == null) {
            response.setMessage("로그인 이후 이용해주세요.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        ArrayList<Testcase> testcases = testcaseService.findProjectTestcaseByProjectId(Integer.parseInt(projectId));
        ArrayList<ResponseProjectTestcaseVO> testcaseVOArrayList = new ArrayList<>();

        for (Testcase testcase : testcases) {
            testcaseVOArrayList.add(new ResponseProjectTestcaseVO(null, testcase.getNo(), testcase.getSeparate(), testcase.getContent(), testcase.getExpectedValue(), testcase.getResult(), testcase.getNote(), testcase.getProjectId()));
        }

        response.setMessage("조회 성공");
        response.setProjectTestcases(testcaseVOArrayList);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/add/{projectId}")
    public ResponseEntity<ResponseProjectTestcaseVO> addProjectTestcase(@PathVariable("projectId") String projectId, @RequestBody RequestProjectTestcaseVO request) {
        ResponseProjectTestcaseVO response = new ResponseProjectTestcaseVO();

        if (sessionService.whoAmI() == null) {
            response.setMessage("로그인 이후 이용해주세요.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        Testcase testcase = testcaseService.addProjectTestcase(Integer.parseInt(projectId), request.getSeparate(), request.getContent(), request.getExpectedValue(), request.getNote());
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
    public ResponseEntity<ResponseProjectTestcaseVO> modifyProjectTestcase(@PathVariable("projectId") String projectId, @RequestBody RequestProjectTestcaseVO request) {
        ResponseProjectTestcaseVO response = new ResponseProjectTestcaseVO();

        if (sessionService.whoAmI() == null) {
            response.setMessage("로그인 이후 이용해주세요.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        try {
            Testcase testcase = testcaseService.modifyProjectTestcase(Integer.parseInt(projectId), request.getNo(), request.getSeparate(), request.getContent(), request.getExpectedValue(), request.getResult(), request.getNote());

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

    @DeleteMapping("/remove/{projectId}")
    public ResponseEntity<ResponseMessageVO> removeProjectTestcase(@PathVariable("projectId") String projectId, @RequestBody RequestProjectTestcaseVO request) {
        ResponseMessageVO response = new ResponseMessageVO();

        if (sessionService.whoAmI() == null) {
            response.setMessage("로그인 이후 이용해주세요.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        if (testcaseService.removeTestcase(Integer.parseInt(projectId), request.getNo())) {
            response.setMessage("삭제 성공!");
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }

        response.setMessage("삭제 실패");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
}
