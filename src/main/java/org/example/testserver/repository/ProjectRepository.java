package org.example.testserver.repository;

import org.example.testserver.aggregate.entity.Project;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ProjectRepository {
    private final ArrayList<Project> projectList = new ArrayList<>();

    public ProjectRepository() {
        projectList.add(new Project(1, "온라인 쇼핑몰 구축", Project.Status.IN_PROGRESS,
                "이 프로젝트는 온라인 쇼핑몰을 구축하기 위한 것입니다. 사용자들에게 편리한 쇼핑 경험을 제공하기 위해 노력하고 있습니다.",
                List.of(1, 2, 3, 4, 5)));
        projectList.add(new Project(2, "앱 개발", Project.Status.IN_PROGRESS,
                "안드로이드 및 iOS 플랫폼을 위한 앱을 개발 중입니다. 사용자들에게 편리한 기능과 사용성을 제공하는 것을 목표로 하고 있습니다.",
                List.of(1, 10, 13, 24, 25)));
        projectList.add(new Project(3, "빅데이터 분석 프로젝트", Project.Status.IN_PROGRESS,
                "이 프로젝트는 대량의 데이터를 분석하여 인사이트를 도출하는 데 초점을 맞추고 있습니다. 데이터 시각화 및 통계 분석을 통해 가치 있는 정보를 발굴하고 있습니다.",
                List.of(1, 12, 23, 14, 15)));
        projectList.add(new Project(4, "웹 개발 프로젝트", Project.Status.IN_PROGRESS,
                "이 프로젝트는 웹 애플리케이션을 개발하는 데 집중하고 있습니다. 사용자들에게 직관적이고 반응이 빠른 웹 경험을 제공하는 것을 목표로 하고 있습니다.",
                List.of(1, 11, 18, 19, 21)));
    }

    public ArrayList<Project> selectMyProjects(int memberId) {
        ArrayList<Project> projects = new ArrayList<>();

        for (Project project : projectList) {
            for (int participantId : project.getMemberId()) {
                if (participantId == memberId) {
                    projects.add(project);
                    break;
                }
            }
        }

        return projects;
    }
}
