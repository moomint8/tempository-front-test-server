package org.example.testserver.repository;

import org.example.testserver.aggregate.entity.Issue;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public class ProjectIssueRepository {
    private final ArrayList<Issue> issueList = new ArrayList<>();

    // 진행예정 진행중 진행완료 수정요청
    public ProjectIssueRepository() {
        issueList.add(new Issue(1, 1, "회원가입 오류", "진행완료",
                "회원가입 시스템에서 오류가 발생하여 사용자가 회원가입을 할 수 없습니다.",
                1, 1, 1));
        issueList.add(new Issue(2, 2, "상품 주문 오류", "진행완료",
                "상품을 주문할 때 오류가 발생하여 주문이 처리되지 않습니다.",
                2, 2, 1));
        issueList.add(new Issue(3, 3, "로그인 문제", "진행중",
                "일부 사용자가 로그인에 문제가 있어서 로그인이 제대로 되지 않습니다.",
                3, 1, 1));
        issueList.add(new Issue(4, 4, "상품 결제 오류", "수정요청",
                "상품을 결제할 때 결제 시스템에서 오류가 발생하여 결제가 완료되지 않습니다.",
                1, 4, 1));
        issueList.add(new Issue(5, 5, "상품 정보 누락", "진행예정",
                "일부 상품의 정보가 누락되어 상세 페이지에 정보가 부족합니다.",
                5, 2, 1));
    }

    public ArrayList<Issue> selectIssueByProjectId(int projectId) {
        ArrayList<Issue> issues = new ArrayList<>();

        for (Issue issue : issueList) {
            if (issue.getProjectId() == projectId) {
                issues.add(issue);
            }
        }

        return issues;
    }

    public Issue updateIssue(int projectId, int no, String name, String status, String content, int managerId, int writerId) {

        boolean flag = false;
        Issue target = null;

        for (Issue issue : issueList) {
            if (issue.getProjectId() == projectId) {
                if (flag) {
                    issue.setNo(issue.getNo() - 1);
                } else if (issue.getNo() == no) {
                    flag = true;
                    if (name != null) {
                        issue.setName(name);
                    }
                    if (status != null) {
                        issue.setStatus(status);
                    }
                    if (content != null) {
                        issue.setContent(content);
                    }
                    if (managerId != 0) {
                        issue.setManagerId(managerId);
                    }
                    if (writerId != 0) {
                        issue.setWriterId(writerId);
                    }
                    target = issue;
                }
            }
        }

        return target;
    }

    public Issue insertIssue(int projectId, String name, String status, String content, int managerId, int writerId) {
        int no = 1;

        for (Issue issue : issueList) {
            if (issue.getProjectId() == projectId) {
                no++;
            }
        }

        Issue issue = new Issue(issueList.size() + 1, no, name, status, content, managerId, writerId, projectId);

        issueList.add(issue);

        return issue;
    }
}
