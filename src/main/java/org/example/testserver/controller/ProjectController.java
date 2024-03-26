package org.example.testserver.controller;

import org.example.testserver.aggregate.entity.Project;
import org.example.testserver.aggregate.entity.User;
import org.example.testserver.aggregate.vo.project.RequestProjectVO;
import org.example.testserver.aggregate.vo.project.ResponseProjectListVO;
import org.example.testserver.aggregate.vo.project.ResponseProjectVO;
import org.example.testserver.service.ProjectService;
import org.example.testserver.service.SessionService;
import org.example.testserver.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/project")
public class ProjectController {

    private final ProjectService projectService;
    private final SessionService sessionService;
    private final UserService userService;

    public ProjectController(ProjectService projectService, SessionService sessionService, UserService userService) {
        this.projectService = projectService;
        this.sessionService = sessionService;
        this.userService = userService;
    }

    @GetMapping("/myproject")
    public ResponseEntity<ResponseProjectListVO> myProject() throws Exception {
        ResponseProjectListVO response = new ResponseProjectListVO();

        if (sessionService.whoAmI() == null) {
            response.setMessage("로그인 이후 이용해주세요.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        ArrayList<Project> projects = projectService.findMyProjects();
        ArrayList<ResponseProjectVO> responseProjectVOs = new ArrayList<>();

        for (Project project : projects) {

            responseProjectVOs.add(new ResponseProjectVO(
                    null, project.getId(), project.getName(), project.getStatus().toString(), project.getContent(), changeUserIdToUser(project.getMemberIds()))
               );
            response.setProjects(responseProjectVOs);
        }

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/add")
    public ResponseEntity<ResponseProjectVO> addProject(@RequestBody RequestProjectVO request) throws Exception {
        ResponseProjectVO response = new ResponseProjectVO();

        if (sessionService.whoAmI() == null) {
            response.setMessage("로그인 이후 이용해주세요.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        Project project = projectService.addProject(request.getName(), request.getContent());

        response.setMessage("프로젝트 추가 성공");
        response.setId(project.getId());
        response.setName(project.getName());
        response.setStatus(project.getStatus().toString());
        response.setContent(project.getContent());
        response.setMembers(List.of(userService.findUserById(project.getMemberIds().get(0))));

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("/modify/{projectId}")
    public ResponseEntity<ResponseProjectVO> modifyProject(@PathVariable("projectId") String projectId,
                                                           @RequestBody RequestProjectVO request) {
        ResponseProjectVO response = new ResponseProjectVO();

        if (sessionService.whoAmI() == null) {
            response.setMessage("로그인 이후 이용해주세요.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        try {
            Project project = projectService.modifyProject(Integer.parseInt(projectId), request.getName(), request.getStatus(), request.getContent());
            response = projectVOMapper(project);
            response.setMessage("프로젝트 수정 성공");

            return ResponseEntity.status(HttpStatus.OK).body(response);

        } catch (Exception e) {
            response.setMessage("수정 실패");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    private ArrayList<User> changeUserIdToUser(List<Integer> userIds) throws Exception {
        ArrayList<User> users = new ArrayList<>();
        for (int id : userIds) {
            users.add(userService.findUserById(id));
        }

        return users;
    }

    private ResponseProjectVO projectVOMapper(Project project) throws Exception {
        ResponseProjectVO response = new ResponseProjectVO();

        response.setId(project.getId());
        response.setName(project.getName());
        response.setStatus(project.getStatus().toString());
        response.setContent(project.getContent());
        response.setMembers(changeUserIdToUser(project.getMemberIds()));

        return response;
    }
}
