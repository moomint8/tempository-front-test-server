package org.example.testserver.controller;

import org.example.testserver.aggregate.entity.Project;
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
    public ResponseEntity<ArrayList<ResponseProjectVO>> myProject() {
        ArrayList<ResponseProjectVO> response = new ArrayList<>();

        if (sessionService.whoAmI() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        ArrayList<Project> projects = projectService.findMyProjects();

        for (Project project : projects) {
            response.add(new ResponseProjectVO(
                    project.getId(), project.getName(), project.getStatus().toString(), project.getContent(), project.getMemberId()
            ));
        }

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
