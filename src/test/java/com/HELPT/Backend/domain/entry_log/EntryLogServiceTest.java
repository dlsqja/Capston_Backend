package com.HELPT.Backend.domain.entry_log;

import com.HELPT.Backend.domain.entry_log.repository.EntryLogRepository;
import com.HELPT.Backend.domain.gym.entity.Gym;
import com.HELPT.Backend.domain.gym.repository.GymRepository;
import com.HELPT.Backend.domain.member.Member;
import com.HELPT.Backend.domain.member.MemberRepository;
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
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class EntryLogServiceTest {

    @InjectMocks
    private EntryLogService entryLogService;

    @Mock
    private EntryLogRepository entryLogRepository;

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private GymRepository gymRepository;

    private Member member;
    private Gym gym;
    private EntryLog entryLog;

    @BeforeEach
    void setUp() {
        member = Member.builder()
                .userId(1L)
                .build();

        gym = Gym.builder()
                .id(1L)
                .gymName("Test Gym")
                .build();

        entryLog = EntryLog.builder()
                .member(member)
                .gym(gym)
                .build();
    }

    @Test
    @DisplayName("[Service] 출입 기록 조회 테스트")
    void saveEntryServiceTest() {
        // given
        given(memberRepository.findById(anyLong())).willReturn(Optional.of(member));
        given(gymRepository.findById(anyLong())).willReturn(Optional.of(gym));
        given(entryLogRepository.save(any(EntryLog.class))).willReturn(entryLog);

        // when
        EntryLog result = entryLogService.saveEntry(1L, 1L);

        // then
        verify(memberRepository).findById(anyLong());
        verify(gymRepository).findById(anyLong());
        verify(entryLogRepository).save(any(EntryLog.class));
        assertNotNull(result);
        assertThat(result.getMember().getUserId()).isEqualTo(member.getUserId());
        assertThat(result.getGym().getId()).isEqualTo(gym.getId());
    }
}
