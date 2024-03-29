package org.example.testserver.repository;

import org.example.testserver.aggregate.entity.Issue;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public class IssueRepository {
    private final ArrayList<Issue> issueList = new ArrayList<>();

    public IssueRepository() {
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

        issueList.add(new Issue(6, 6, "검색 기능 오류", "진행중",
                "상품 검색 기능에서 오류가 발생하여 검색 결과가 제대로 나오지 않습니다.",
                2, 3, 1));
        issueList.add(new Issue(7, 7, "배송 지연", "수정요청",
                "일부 주문에 대한 배송이 지연되고 있어 고객들이 불만을 제기하고 있습니다.",
                3, 4, 1));
        issueList.add(new Issue(8, 8, "리뷰 시스템 오류", "진행완료",
                "상품 리뷰 시스템에서 오류가 발생하여 리뷰를 작성할 수 없습니다.",
                4, 5, 1));
        issueList.add(new Issue(9, 9, "관리자 페이지 접근 오류", "진행예정",
                "관리자 페이지에 접근할 때 오류가 발생하여 관리자가 페이지를 제대로 이용할 수 없습니다.",
                5, 1, 1));
        issueList.add(new Issue(10, 10, "상품 이미지 업로드 오류", "진행중",
                "상품 이미지를 업로드할 때 오류가 발생하여 이미지가 제대로 업로드되지 않습니다.",
                1, 2, 1));
        issueList.add(new Issue(11, 11, "쿠폰 적용 오류", "수정요청",
                "쿠폰을 적용할 때 오류가 발생하여 쿠폰 할인이 제대로 적용되지 않습니다.",
                2, 3, 1));
        issueList.add(new Issue(12, 12, "주소 입력 오류", "진행완료",
                "주문 시 주소를 입력할 때 오류가 발생하여 주소가 제대로 입력되지 않습니다.",
                3, 4, 1));

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

    public boolean deleteIssue(int projectId, int no) {

        boolean flag = false;
        Issue target = null;

        for (Issue issue : issueList) {
            if (flag) {
                issue.setId(issue.getId() - 1);
            }
            if (issue.getProjectId() == projectId) {
                if (flag) {
                    issue.setNo(issue.getNo() - 1);
                } else if (issue.getNo() == no) {
                    flag = true;
                    target = issue;
                }
            }
        }

        issueList.remove(target);

        return flag;
    }
}
