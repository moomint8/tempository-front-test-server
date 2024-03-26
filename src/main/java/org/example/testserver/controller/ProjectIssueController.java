package org.example.testserver.controller;

import org.example.testserver.aggregate.entity.Issue;
import org.example.testserver.aggregate.vo.issue.ResponseIssueListVO;
import org.example.testserver.aggregate.vo.issue.ResponseIssueVO;
import org.example.testserver.service.ProjectIssueService;
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
