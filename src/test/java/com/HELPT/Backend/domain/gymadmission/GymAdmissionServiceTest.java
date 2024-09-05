package com.HELPT.Backend.domain.gymadmission;

import com.HELPT.Backend.domain.gym.entity.Gym;
import com.HELPT.Backend.domain.gym.repository.GymRepository;
import com.HELPT.Backend.domain.member.Member;
import com.HELPT.Backend.domain.member.MemberRepository;
import com.HELPT.Backend.domain.membership.Membership;
import com.HELPT.Backend.domain.membership.MembershipRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class GymAdmissionServiceTest {

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private GymRepository gymRepository;

    @Mock
    private GymAdmissionRepository gymAdmissionRepository;

    @Mock
    private MembershipRepository membershipRepository;

    @InjectMocks
    private GymAdmissionService gymAdmissionService;

    private Member member;
    private Gym gym;
    private GymAdmission gymAdmission;
    private Membership membership;
    private LocalDate endDate;

    @BeforeEach
    void setUp() {
        member = Member.builder()
                .userId(1L)
                .userName("이정민")
                .build();
        gym = Gym.builder()
                .id(1L)
                .build();
        gymAdmission = GymAdmission.builder()
                .id(1L)
                .member(member)
                .gym(gym)
                .build();
        endDate = LocalDate.of(2024, 12, 31);
    }

    @Test
    @DisplayName("[Service] 헬스장 등록 요청 테스트")
    void addGymAdmissionTest() {
        // given
        given(memberRepository.findById(anyLong())).willReturn(Optional.of(member));
        given(gymRepository.findById(anyLong())).willReturn(Optional.of(gym));
        given(gymAdmissionRepository.findByGymAndMember(any(Gym.class), any(Member.class))).willReturn(Optional.empty());
        given(gymAdmissionRepository.save(any(GymAdmission.class))).willReturn(gymAdmission);

        // when
        GymAdmission result = gymAdmissionService.addGymAdmission(1L, 1L);

        // then
        verify(memberRepository).findById(anyLong());
        verify(gymRepository).findById(anyLong());
        verify(gymAdmissionRepository).findByGymAndMember(any(Gym.class), any(Member.class));
        verify(gymAdmissionRepository).save(any(GymAdmission.class));
        assertNotNull(result);
        assertEquals(gymAdmission.getId(), result.getId());
    }

    @Test
    @DisplayName("[Service] 헬스장 등록 요청 목록 조회 테스트")
    void findGymAdmissionsTest() {
        // given
        List<GymAdmission> gymAdmissions = Arrays.asList(gymAdmission);
        given(gymAdmissionRepository.findByGymId(anyLong())).willReturn(gymAdmissions);

        // when
        List<GymAdmissionResponse> responses = gymAdmissionService.findGymAdmissions(1L);

        // then
        verify(gymAdmissionRepository).findByGymId(anyLong());
        assertNotNull(responses);
        assertEquals(1, responses.size());
    }

    @Test
    @DisplayName("[Service] 헬스장 등록 요청 승인 테스트")
    void approveGymAdmissionTest() {
        // given
        given(gymAdmissionRepository.findById(anyLong())).willReturn(Optional.of(gymAdmission));
        given(membershipRepository.save(any(Membership.class))).willReturn(membership);
        given(memberRepository.findById(anyLong())).willReturn(Optional.of(member));

        // when
        Membership result = gymAdmissionService.approveGymAdmission(1L, endDate);

        // then
        verify(gymAdmissionRepository).findById(anyLong());
        verify(membershipRepository).save(any(Membership.class));
        verify(memberRepository).findById(anyLong());
        verify(gymAdmissionRepository).deleteById(anyLong());
        assertNotNull(result);
        assertEquals(gym.getId(), result.getGymId());
        assertEquals(member.getUserId(), result.getUserId());
    }

    @Test
    @DisplayName("[Service] 헬스장 등록 요청 거절 테스트")
    void rejectGymAdmissionTest() {
        // given
        doNothing().when(gymAdmissionRepository).deleteById(anyLong());

        // when
        gymAdmissionService.rejectGymAdmission(1L);

        // then
        verify(gymAdmissionRepository).deleteById(anyLong());
    }
}
