package org.example.testserver.controller;

import org.example.testserver.aggregate.entity.Project;
import org.example.testserver.aggregate.vo.project.ResponseProjectListVO;
import org.example.testserver.aggregate.vo.project.ResponseProjectVO;
import org.example.testserver.service.ProjectService;
import org.example.testserver.service.SessionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
@RequestMapping("/project")
public class ProjectController {

    private final ProjectService projectService;
    private final SessionService sessionService;

    public ProjectController(ProjectService projectService, SessionService sessionService) {
        this.projectService = projectService;
        this.sessionService = sessionService;
    }

    @GetMapping("/myproject")
    public ResponseEntity<ResponseProjectListVO> myProject() {
        ResponseProjectListVO response = new ResponseProjectListVO();

        if (sessionService.whoAmI() == null) {
            response.setMessage("로그인 이후 이용해주세요.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        ArrayList<Project> projects = projectService.findMyProjects();
        ArrayList<ResponseProjectVO> responseProjectVOs = new ArrayList<>();

        for (Project project : projects) {

            responseProjectVOs.add(new ResponseProjectVO(
                    null, project.getId(), project.getName(), project.getStatus().toString(), project.getContent(), project.getMemberId()
            ));
            response.setProjects(responseProjectVOs);
        }

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
