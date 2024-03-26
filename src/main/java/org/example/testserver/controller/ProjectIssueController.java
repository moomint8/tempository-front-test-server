package org.example.testserver.controller;

import org.example.testserver.aggregate.entity.Issue;
import org.example.testserver.aggregate.vo.issue.RequestIssueVO;
import org.example.testserver.aggregate.vo.issue.ResponseIssueListVO;
import org.example.testserver.aggregate.vo.issue.ResponseIssueVO;
import org.example.testserver.service.ProjectIssueService;
import org.example.testserver.service.SessionService;
import org.example.testserver.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/issue")
public class ProjectIssueController {

    private final ProjectIssueService projectIssueService;
    private final UserService userService;
    private final SessionService sessionService;

    public ProjectIssueController(ProjectIssueService projectIssueService, UserService userService, SessionService sessionService) {
        this.projectIssueService = projectIssueService;
        this.userService = userService;
        this.sessionService = sessionService;
    }

    @GetMapping("/{projectId}")
    public ResponseEntity<ResponseIssueListVO> findIssueListByProjectId(@PathVariable("projectId") String projectId) throws Exception {

        ResponseIssueListVO response = new ResponseIssueListVO();

        if (sessionService.whoAmI() == null) {
            response.setMessage("로그인 이후 이용해주세요.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        ArrayList<Issue> issues = projectIssueService.findIssueByProjectId(Integer.parseInt(projectId));
        ArrayList<ResponseIssueVO> issueVOS = new ArrayList<>();

        for (Issue issue : issues) {
            issueVOS.add(changeIssueToResponseIssueVO(issue));
        }

        response.setMessage("조회 성공");
        response.setIssueList(issueVOS);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/add/{projectId}")
    public ResponseEntity<ResponseIssueVO> addIssue(@PathVariable("projectId") String projectId,
                                                    @RequestBody RequestIssueVO request) throws Exception {
        ResponseIssueVO response = new ResponseIssueVO();

        if (sessionService.whoAmI() == null) {
            response.setMessage("로그인 이후 이용해주세요.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        int myId = sessionService.whoAmI().getId();

        response = changeIssueToResponseIssueVO(projectIssueService.addIssue(Integer.parseInt(projectId),
                request.getName(), request.getStatus(), request.getContent(), myId, myId));

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("/modify/{projectId}")
    public ResponseEntity<ResponseIssueVO> modifyIssue(@PathVariable("projectId") String projectId,
                                                       @RequestBody RequestIssueVO request) throws Exception {

        ResponseIssueVO response = new ResponseIssueVO();

        if (sessionService.whoAmI() == null) {
            response.setMessage("로그인 이후 이용해주세요.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        response = changeIssueToResponseIssueVO(projectIssueService.modifyIssue(Integer.parseInt(projectId), request.getNo(), request.getName(),
                request.getStatus(), request.getContent(), request.getManagerId(), request.getWriterId()));

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    private ResponseIssueVO changeIssueToResponseIssueVO(Issue issue) throws Exception {
        ResponseIssueVO response = new ResponseIssueVO();
        response.setId(issue.getId());
        response.setNo(issue.getNo());
        response.setName(issue.getName());
        response.setStatus(issue.getStatus());
        response.setContent(issue.getContent());

        response.setManager(userService.findUserById(issue.getManagerId()));
        response.setWriter(userService.findUserById(issue.getWriterId()));

        response.setProjectId(issue.getProjectId());

        return response;
    }
}
