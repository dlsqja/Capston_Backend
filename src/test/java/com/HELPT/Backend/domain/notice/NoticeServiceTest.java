package com.HELPT.Backend.domain.notice;

import com.HELPT.Backend.domain.emitter.EmitterService;
import com.HELPT.Backend.domain.fcm.DeviceTokenService;
import com.HELPT.Backend.domain.fcm.FirebaseCloudMessageService;
import com.HELPT.Backend.domain.member.MemberRepository;
import com.HELPT.Backend.domain.notice.dto.NoticeRequest;
import com.HELPT.Backend.domain.notice.dto.NoticeResponse;
import com.HELPT.Backend.global.error.CustomException;
import com.HELPT.Backend.global.error.ErrorCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class NoticeServiceTest {

    @InjectMocks
    private NoticeService noticeService;

    @Mock
    private NoticeRepository noticeRepository;

    private Notice notice;
    private NoticeRequest noticeRequest;

    @BeforeEach
    void setUp() {
        notice = Notice.builder()
                .gymId(1L)
                .title("Test Notice")
                .content("This is a test notice.")
                .createAt(LocalDate.now())
                .build();

        noticeRequest = NoticeRequest.builder()
                .gymId(1L)
                .title("Test Notice")
                .content("This is a test notice.")
                .build();
    }

    @Test
    @DisplayName("[Service] 공지사항 전제 조회 테스트")
    void findNoticeServiceTest() {
        // given
        List<Notice> noticeList = Collections.singletonList(notice);
        given(noticeRepository.findAllByGymId(anyLong())).willReturn(Optional.of(noticeList));

        // when
        List<NoticeResponse> responses = noticeService.findNotice(1L);

        // then
        verify(noticeRepository).findAllByGymId(anyLong());
        assertNotNull(responses);
        assertFalse(responses.isEmpty());
        assertThat(responses.size()).isEqualTo(1);
    }

    @Test
    @DisplayName("[Service] 공지사항 등록 테스트")
    void uploadNoticeServiceTest() {
        // given
        given(noticeRepository.save(any(Notice.class))).willReturn(notice);

        // when
        Boolean result = noticeService.uploadNotice(noticeRequest);

        // then
        verify(noticeRepository).save(any(Notice.class));
        assertTrue(result);
    }

    @Test
    @DisplayName("[Service] 공지사항 삭제 테스트")
    void deleteNoticeServiceTest() {
        // given
        given(noticeRepository.findById(anyLong())).willReturn(Optional.of(notice));

        // when
        Boolean result = noticeService.deleteNotice(1L);

        // then
        verify(noticeRepository).deleteById(anyLong());
        assertTrue(result);
    }

    @Test
    @DisplayName("[Service] 공지사항 수정 테스트")
    void modifyNoticeServiceTest() {
        // given
        given(noticeRepository.findById(anyLong())).willReturn(Optional.of(notice));

        // when
        Boolean result = noticeService.modifyNotice(1L, noticeRequest);

        // then
        verify(noticeRepository).findById(anyLong());
        assertTrue(result);
    }

}
