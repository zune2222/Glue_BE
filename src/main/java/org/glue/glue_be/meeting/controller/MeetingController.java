package org.glue.glue_be.meeting.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.glue.glue_be.common.response.BaseResponse;
import org.glue.glue_be.meeting.dto.MeetingDto;
import org.glue.glue_be.meeting.service.MeetingService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/meetings")
public class MeetingController {

    private final MeetingService meetingService;

    /**
     * 모임 생성 API
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BaseResponse<MeetingDto.CreateResponse> createMeeting(@Valid @RequestBody MeetingDto.CreateRequest request) {
        Long currentUserId = getCurrentUserId();
        return new BaseResponse<>(meetingService.createMeeting(request, currentUserId));
    }

    /**
     * 모임 참여 API
     */
    @GetMapping("/{meetingId}/join")
    public BaseResponse<Void> joinMeeting(@PathVariable Long meetingId) {
        Long currentUserId = getCurrentUserId();
        meetingService.joinMeeting(meetingId, currentUserId);
        return new BaseResponse<>();
    }

    /**
     * 모임 상세 조회 API
     */
    @GetMapping("/{meetingId}")
    public BaseResponse<MeetingDto.Response> getMeeting(@PathVariable Long meetingId) {
        return new BaseResponse<>(meetingService.getMeeting(meetingId));
    }

    /**
     * 현재 로그인한 사용자 ID 가져오기
     * 실제 구현에서는 Spring Security에서 인증 정보를 가져와야 합니다
     */
    private Long getCurrentUserId() {
        // TODO: Spring Security를 통해 현재 인증된 사용자 ID 가져오기
        return 1L; // 임시 값
    }
}
