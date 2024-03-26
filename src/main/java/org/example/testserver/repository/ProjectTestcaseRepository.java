package org.example.testserver.repository;

import org.example.testserver.aggregate.entity.ProjectTestcase;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public class ProjectTestcaseRepository {
    private final ArrayList<ProjectTestcase> projectTestcaseList = new ArrayList<>();

    public ProjectTestcaseRepository() {
        projectTestcaseList.add(
                new ProjectTestcase(1, "회원가입",
                        "유효한 이메일과 비밀번호를 입력하고 회원가입 버튼을 클릭했을 때, 회원가입이 성공적으로 이루어져야 함",
                        "회원가입이 성공적으로 완료되었습니다.", "성공",
                        "이메일: test@example.com, 비밀번호: test123", 1));
        projectTestcaseList.add(
                new ProjectTestcase(2, "로그인",
                        "가입한 이메일과 비밀번호를 입력하고 로그인 버튼을 클릭했을 때, 로그인이 성공적으로 이루어져야 함",
                        "로그인이 성공적으로 완료되었습니다.", "실패",
                        "이메일: test@example.com, 비밀번호: test123", 1));
        projectTestcaseList.add(
                new ProjectTestcase(3, "상품 목록 조회",
                        "상품 목록 페이지로 이동하여 상품을 조회했을 때, 모든 상품이 정상적으로 표시되어야 함",
                        "상품 목록이 정상적으로 표시되었습니다.", "성공",
                        "페이지 URL: /products", 1));
        projectTestcaseList.add(
                new ProjectTestcase(4, "상품 상세 조회",
                        "상품 목록에서 특정 상품을 선택하여 상품 상세 페이지로 이동했을 때, 상품의 상세 정보가 정상적으로 표시되어야 함",
                        "상품 상세 정보가 정상적으로 표시되었습니다.", "성공",
                        "상품 ID: 1234", 1));
        projectTestcaseList.add(
                new ProjectTestcase(5, "상품 구매",
                        "상품 상세 페이지에서 구매 버튼을 클릭하여 상품을 구매했을 때, 주문이 정상적으로 완료되어야 함",
                        "주문이 정상적으로 완료되었습니다.", "진행중",
                        "상품 ID: 1234", 1));
    }

    public ArrayList<ProjectTestcase> selectProjectTestcaseByProjectId(int projectId) {
        ArrayList<ProjectTestcase> testcases = new ArrayList<>();

        for (ProjectTestcase testcase : projectTestcaseList) {
            if (testcase.getProjectId() == projectId) {
                testcases.add(testcase);
            }
        }

        return testcases;
    }
}
