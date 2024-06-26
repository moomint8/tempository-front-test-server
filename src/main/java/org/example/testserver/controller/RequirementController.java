package org.example.testserver.controller;

import org.example.testserver.aggregate.entity.Requirement;
import org.example.testserver.aggregate.vo.ResponseMessageVO;
import org.example.testserver.aggregate.vo.requirement.RequestRequirementListVO;
import org.example.testserver.aggregate.vo.requirement.RequestRequirementVO;
import org.example.testserver.aggregate.vo.requirement.ResponseRequirementListVO;
import org.example.testserver.aggregate.vo.requirement.ResponseRequirementVO;
import org.example.testserver.service.RequirementService;
import org.example.testserver.service.SessionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/requirement")
public class RequirementController {

    private final RequirementService requirementService;
    private final SessionService sessionService;

    public RequirementController(RequirementService requirementService, SessionService sessionService) {
        this.requirementService = requirementService;
        this.sessionService = sessionService;
    }

    @GetMapping("/{projectId}")
    public ResponseEntity<ResponseRequirementListVO> findRequirementListByProjectId(
            @PathVariable("projectId") String projectId) {

        ResponseRequirementListVO response = new ResponseRequirementListVO();

        if (sessionService.whoAmI() == null) {
            response.setMessage("로그인 이후 이용해주세요.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        ArrayList<Requirement> requirementList = requirementService.findRequirementByProjectId(Integer.parseInt(projectId));
        ArrayList<ResponseRequirementVO> requirementVOS = new ArrayList<>();

        for (Requirement requirement : requirementList) {
            requirementVOS.add(changeRequirementToResponseRequirementVO(requirement));
        }

        response.setMessage("조회 성공");
        response.setRequirementVOList(requirementVOS);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/add/{projectId}")
    public ResponseEntity<ResponseRequirementVO> addRequirement(@PathVariable("projectId") String projectId,
                                                                @RequestBody RequestRequirementVO request) {
        ResponseRequirementVO response = new ResponseRequirementVO();

        if (sessionService.whoAmI() == null) {
            response.setMessage("로그인 이후 이용해주세요.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        response = changeRequirementToResponseRequirementVO(
                requirementService.addRequirement(Integer.parseInt(projectId), request.getSeparate(), request.getName(),
                        request.getContent(), request.getNote()));

        response.setMessage("추가 성공");

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/save/{projectId}")
    public ResponseEntity<ResponseRequirementListVO> saveRequirement(@PathVariable("projectId") String projectId,
                                                                     @RequestBody RequestRequirementListVO request) {
        ResponseRequirementListVO response = new ResponseRequirementListVO();

        if (sessionService.whoAmI() == null) {
            response.setMessage("로그인 이후 이용해주세요.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        ArrayList<Requirement> requirementList = requirementService.findRequirementByProjectId(Integer.parseInt(projectId));

        // 해당 프로젝트 요구사항 전체 삭제
        for (Requirement requirement : requirementList) {
            requirementService.removeRequirement(Integer.parseInt(projectId), requirement.getNo());
        }

        ArrayList<RequestRequirementVO> reqVOList = request.getTarget();
        ArrayList<ResponseRequirementVO> resVOList = new ArrayList<>();

        // 새로운 요구명세서 전체 입력
        for (RequestRequirementVO reqVO : reqVOList) {
            Requirement requirement = requirementService.addRequirement(Integer.parseInt(projectId), reqVO.getSeparate(), reqVO.getName(),
                    reqVO.getContent(), reqVO.getNote());
            resVOList.add(changeRequirementToResponseRequirementVO(requirement));
        }

        response.setMessage("저장 성공");
        response.setRequirementVOList(resVOList);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("/modify/{projectId}")
    public ResponseEntity<ResponseRequirementVO> modifyRequirement(@PathVariable("projectId") String projectId,
                                                                   @RequestBody RequestRequirementVO request) {
        ResponseRequirementVO response = new ResponseRequirementVO();

        if (sessionService.whoAmI() == null) {
            response.setMessage("로그인 이후 이용해주세요.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        try {
            response = changeRequirementToResponseRequirementVO(requirementService.modifyRequirement(
                    Integer.parseInt(projectId), request.getNo(), request.getSeparate(), request.getName(), request.getContent(), request.getNote()
            ));
            response.setMessage("수정 성공");

            return ResponseEntity.status(HttpStatus.OK).body(response);

        } catch (Exception e) {
            response.setMessage("수정 실패");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @DeleteMapping("/remove/{projectId}")
    public ResponseEntity<ResponseMessageVO> removeRequirement(@PathVariable("projectId") String projectId,
                                                               @RequestBody RequestRequirementVO request) {
        ResponseMessageVO response = new ResponseMessageVO();

        if (sessionService.whoAmI() == null) {
            response.setMessage("로그인 이후 이용해주세요.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        if (requirementService.removeRequirement(Integer.parseInt(projectId), request.getNo())) {
            response.setMessage("삭제 성공");
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }

        response.setMessage("삭제 실패");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    private ResponseRequirementVO changeRequirementToResponseRequirementVO(Requirement requirement) {
        ResponseRequirementVO requirementVO = new ResponseRequirementVO();

        requirementVO.setId(requirement.getId());
        requirementVO.setNo(requirement.getNo());
        requirementVO.setSeparate(requirement.getSeparate());
        requirementVO.setName(requirement.getName());
        requirementVO.setContent(requirement.getContent());
        requirementVO.setNote(requirement.getNote());
        requirementVO.setProjectId(requirement.getProjectId());

        return requirementVO;
    }
}
