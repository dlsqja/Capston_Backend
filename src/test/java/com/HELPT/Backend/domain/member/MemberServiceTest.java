package com.HELPT.Backend.domain.member;

import com.HELPT.Backend.domain.fcm.DeviceTokenService;
import com.HELPT.Backend.domain.member.Dto.MemberDetailResponse;
import com.HELPT.Backend.domain.member.Dto.MemberJoinResponse;
import com.HELPT.Backend.domain.member.Dto.MemberDto;
import com.HELPT.Backend.domain.membership.Membership;
import com.HELPT.Backend.global.auth.jwt.JWTResponse;
import com.HELPT.Backend.global.auth.jwt.JWTToken;
import com.HELPT.Backend.global.auth.jwt.JWTUtil;
import com.HELPT.Backend.global.common.dto.KakaoLoginRequest;
import com.HELPT.Backend.global.error.CustomException;
import com.HELPT.Backend.global.error.ErrorCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class MemberServiceTest {

    @InjectMocks
    private MemberService memberService;

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private JWTUtil jwtUtil;

    @Mock
    private DeviceTokenService deviceTokenService;

    private Member member;
    private MemberDto memberDto;
    private JWTToken jwtToken;

    @BeforeEach
    void setUp() {
        member = Member.builder()
                .userId(1L)
                .kakaoId("kakao123")
                .height(180)
                .weight(75)
                .build();

        memberDto = MemberDto.builder()
                .kakaoId("kakao123")
                .height(180)
                .weight(75)
                .build();

        jwtToken = JWTToken.builder()
                .accessToken("access_token")
                .refreshToken("refresh_token")
                .build();
    }

    @Test
    @DisplayName("[Service] 회원 로그인 테스트")
    void loginServiceTest() {
        // given
        KakaoLoginRequest kakaoLoginRequest = KakaoLoginRequest.builder()
                .kakaoId("kakao123")
                .deviceToken("deviceToken")
                .build();
        given(memberRepository.findByKakaoId(anyString())).willReturn(Optional.of(member));
        given(jwtUtil.createTokens(anyLong())).willReturn(jwtToken);

        // when
        JWTResponse response = memberService.login(kakaoLoginRequest);

        // then
        assertNotNull(response);
        assertEquals("access_token", response.getAccessToken());
        verify(memberRepository).findByKakaoId(anyString());
        verify(jwtUtil).createTokens(anyLong());
        verify(deviceTokenService).saveDeviceToken(anyLong(), anyString());
    }

    @Test
    @DisplayName("[Service] 회원 등록 테스트")
    void registerServiceTest() {
        // given
        given(memberRepository.findByKakaoId(anyString())).willReturn(Optional.empty());
        given(memberRepository.save(any(Member.class))).willReturn(member);
        given(memberRepository.findByKakaoId(anyString())).willReturn(Optional.of(member));
        given(jwtUtil.createTokens(anyLong())).willReturn(jwtToken);

        // when
        JWTResponse response = memberService.register(memberDto);

        // then
        assertNotNull(response);
        assertEquals("access_token", response.getAccessToken());
        verify(memberRepository).save(any(Member.class));
        verify(memberRepository).findByKakaoId(anyString());
        verify(jwtUtil).createTokens(anyLong());
    }

    @Test
    @DisplayName("[Service] 출석 체크 테스트")
    void attendanceServiceTest() {
        // given
        Membership membership = Membership.builder()
                .gymId(1L)
                .userId(1L)
                .build();
        given(memberRepository.attendance(anyLong())).willReturn(membership);

        // when
        boolean result = memberService.attendance(1L);

        // then
        assertTrue(result);
        verify(memberRepository).attendance(anyLong());
    }

    @Test
    @DisplayName("[Service] 헬스장에 등록된 회원 검색 테스트")
    void findMemberServiceTest() {
        // given
        given(memberRepository.findById(anyLong())).willReturn(Optional.of(member));

        // when
        MemberDto response = memberService.findMember(1L);

        // then
        assertNotNull(response);
        assertEquals("kakao123", response.getKakaoId());
        verify(memberRepository).findById(anyLong());
    }

    @Test
    @DisplayName("[Service] 헬스장 관리자가 회원 상세 조회 테스트")
    void findMemberDetailServiceTest() {
        // given
        MemberDetailResponse memberDetailResponse = MemberDetailResponse.builder()
                .height(member.getHeight())
                .weight(member.getWeight())
                .build();
        //추가 필요
        given(memberRepository.memberDetail(anyLong())).willReturn(memberDetailResponse);

        // when
        MemberDetailResponse response = memberService.findMemberDetail(1L);

        // then
        assertNotNull(response);
        assertEquals(member.getUserName(), response.getUserName());
        verify(memberRepository).memberDetail(anyLong());
    }

    @Test
    @DisplayName("[Service] 자신의 정보 상세 조회 테스트")
    void searchMembersByGymAndNameServiceTest() {
        // given
        MemberJoinResponse memberJoinResponse = MemberJoinResponse.builder()
                .userName(member.getUserName())
                .build();
        given(memberRepository.memberList(anyLong(), anyString())).willReturn(List.of(memberJoinResponse));

        // when
        List<MemberJoinResponse> responses = memberService.searchMembersByGymAndName(1L, "test");

        // then
        assertNotNull(responses);
        assertFalse(responses.isEmpty());
        verify(memberRepository).memberList(anyLong(), anyString());
    }

    @Test
    @DisplayName("[Service] 회원 정보 수정 테스트")
    void updateMemberServiceTest() {
        // given
        given(memberRepository.findById(anyLong())).willReturn(Optional.of(member));

        // when
        MemberDto response = memberService.updateMember(1L, memberDto);

        // then
        assertNotNull(response);
        assertEquals(180, response.getHeight());
        verify(memberRepository).findById(anyLong());
    }

    @Test
    @DisplayName("[Service] 회원 탈퇴 테스트")
    void removeMemberServiceTest() {
        // given
        given(memberRepository.findById(anyLong())).willReturn(Optional.of(member));

        // when
        memberService.removeMember(1L);

        // then
        verify(memberRepository).findById(anyLong());
        verify(memberRepository).delete(any(Member.class));
    }
}
