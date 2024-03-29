package org.example.testserver.repository;

import org.example.testserver.aggregate.entity.Testcase;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public class TestcaseRepository {
    private final ArrayList<Testcase> testcaseList = new ArrayList<>();

    public TestcaseRepository() {
        testcaseList.add(
                new Testcase(1, "회원가입",
                        "유효한 이메일과 비밀번호를 입력하고 회원가입 버튼을 클릭했을 때, 회원가입이 성공적으로 이루어져야 함",
                        "회원가입이 성공적으로 완료되었습니다.", "성공",
                        "이메일: test@example.com, 비밀번호: test123", 1));
        testcaseList.add(
                new Testcase(2, "로그인",
                        "가입한 이메일과 비밀번호를 입력하고 로그인 버튼을 클릭했을 때, 로그인이 성공적으로 이루어져야 함",
                        "로그인이 성공적으로 완료되었습니다.", "실패",
                        "이메일: test@example.com, 비밀번호: test123", 1));
        testcaseList.add(
                new Testcase(3, "상품 목록 조회",
                        "상품 목록 페이지로 이동하여 상품을 조회했을 때, 모든 상품이 정상적으로 표시되어야 함",
                        "상품 목록이 정상적으로 표시되었습니다.", "성공",
                        "페이지 URL: /products", 1));
        testcaseList.add(
                new Testcase(4, "상품 상세 조회",
                        "상품 목록에서 특정 상품을 선택하여 상품 상세 페이지로 이동했을 때, 상품의 상세 정보가 정상적으로 표시되어야 함",
                        "상품 상세 정보가 정상적으로 표시되었습니다.", "성공",
                        "상품 ID: 1234", 1));
        testcaseList.add(
                new Testcase(5, "상품 구매",
                        "상품 상세 페이지에서 구매 버튼을 클릭하여 상품을 구매했을 때, 주문이 정상적으로 완료되어야 함",
                        "주문이 정상적으로 완료되었습니다.", "진행중",
                        "상품 ID: 1234", 1));
        testcaseList.add(
                new Testcase(6, "주문 내역 조회",
                        "사용자 페이지에서 주문 내역을 조회했을 때, 과거에 주문한 내역이 정상적으로 표시되어야 함",
                        "주문 내역이 정상적으로 표시되었습니다.", "성공",
                        "사용자 ID: user123", 1));
        testcaseList.add(
                new Testcase(7, "배송 조회",
                        "주문 내역 페이지에서 배송 상태를 조회했을 때, 정확한 배송 정보가 표시되어야 함",
                        "배송 정보가 정상적으로 표시되었습니다.", "실패",
                        "주문 번호: order789", 1));
        testcaseList.add(
                new Testcase(8, "상품 리뷰 작성",
                        "상품 상세 페이지에서 리뷰 작성 폼에 내용을 입력하고 제출했을 때, 리뷰가 정상적으로 등록되어야 함",
                        "리뷰가 정상적으로 등록되었습니다.", "성공",
                        "상품 ID: 1234, 리뷰 내용: 좋은 상품입니다.", 1));
        testcaseList.add(
                new Testcase(9, "상품 관리 페이지 접근",
                        "관리자 계정으로 로그인하고 상품 관리 페이지로 이동했을 때, 상품 목록과 관리 기능이 정상적으로 표시되어야 함",
                        "상품 관리 페이지가 정상적으로 표시되었습니다.", "진행중",
                        "관리자 ID: admin123", 1));
        testcaseList.add(
                new Testcase(10, "쿠폰 발급",
                        "관리자 페이지에서 쿠폰 발급 기능을 이용하여 쿠폰을 발급했을 때, 쿠폰이 정상적으로 생성되어야 함",
                        "쿠폰이 정상적으로 발급되었습니다.", "성공",
                        "쿠폰 이름: 신규회원 할인 쿠폰, 할인율: 10%", 1));
    }

    public ArrayList<Testcase> selectProjectTestcaseByProjectId(int projectId) {
        ArrayList<Testcase> testcases = new ArrayList<>();

        for (Testcase testcase : testcaseList) {
            if (testcase.getProjectId() == projectId) {
                testcases.add(testcase);
            }
        }

        return testcases;
    }

    public Testcase insertTestcase(String separate, String content, String expectedValue, String result, String note, int projectId) {

        int no = 1;
        for (Testcase testcase : testcaseList) {
            if (testcase.getProjectId() == projectId) no++;
        }

        Testcase testcase = new Testcase(no, separate, content, expectedValue, result, note, projectId);
        testcaseList.add(testcase);

        return testcase;
    }

    public Testcase updateTestcase(int projectId, int no, String separate, String content, String expectedValue,
                                   String result, String note) throws Exception {

        for (Testcase testcase : testcaseList) {
            if (testcase.getProjectId() == projectId && testcase.getNo() == no) {
                if (separate != null) {
                    testcase.setSeparate(separate);
                }
                if (content != null) {
                    testcase.setContent(content);
                }
                if (expectedValue != null) {
                    testcase.setExpectedValue(expectedValue);
                }
                if (result != null) {
                    if (result.equals("진행전") || result.equals("진행중") || result.equals("성공") || result.equals("실패")) {
                        testcase.setResult(result);
                    } else {
                        throw new Exception("잘못된 요청입니다.");
                    }
                }
                if (note != null) {
                    testcase.setNote(note);
                }

                return testcase;
            }
        }

        throw new Exception("존재하지 않는 테스트케이스입니다.");
    }

    public boolean deleteTestcase(int projectId, int no) {

        boolean flag = false;
        Testcase target = null;

        for (Testcase testcase : testcaseList) {

            if (testcase.getProjectId() == projectId) {
                if (flag) {
                    testcase.setNo(testcase.getNo() - 1);
                } else {
                    if (testcase.getNo() == no) {
                        flag = true;
                        target = testcase;
                    }
                }

            }
        }

        testcaseList.remove(target);

        return flag;
    }
}
