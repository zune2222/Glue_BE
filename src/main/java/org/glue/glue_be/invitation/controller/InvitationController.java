package org.glue.glue_be.invitation.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.glue.glue_be.common.response.BaseResponse;
import org.glue.glue_be.invitation.dto.InvitationDto;
import org.glue.glue_be.invitation.service.InvitationService;
import org.glue.glue_be.meeting.dto.MeetingDto;
import org.glue.glue_be.meeting.service.MeetingService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/invitations")
public class InvitationController {

    private final InvitationService invitationService;
    private final MeetingService meetingService;
    
    /**
     * 모임 초대장 생성 API
     */
    @PostMapping("/meeting/{meetingId}")
    public BaseResponse<InvitationDto.Response> createMeetingInvitation(
            @PathVariable Long meetingId, 
            @Valid @RequestBody MeetingDto.InvitationRequest request) {
        Long currentUserId = getCurrentUserId();
        return new BaseResponse<>(meetingService.createMeetingInvitation(meetingId, request.getInviteeId(), currentUserId));
    }
    
    /**
     * 초대장 수락 API
     */
    @PostMapping("/accept")
    public BaseResponse<Void> acceptInvitation(@RequestBody InvitationDto.AcceptRequest request) {
        Long currentUserId = getCurrentUserId();
        invitationService.acceptInvitation(request.getCode(), currentUserId);
        return new BaseResponse<>();
    }
    
    /**
     * 초대장 목록 조회 API
     */
    @GetMapping
    public BaseResponse<Page<InvitationDto.Response>> getInvitations(Pageable pageable) {
        Long currentUserId = getCurrentUserId();
        return new BaseResponse<>(invitationService.getInvitations(currentUserId, pageable));
    }
    
    /**
     * 현재 로그인한 사용자 ID 가져오기
     * 실제 구현에서는 Spring Security에서 인증 정보를 가져와야 합니다
     */
    private Long getCurrentUserId() {
        // TODO: Spring Security를 통해 현재 인증된 사용자 ID 가져오기
        return 2L; // 임시 값
    }
} 