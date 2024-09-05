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
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class MemberService {

    private final MemberRepository memberRepository;
    private final JWTUtil jwtUtil;
    private final DeviceTokenService deviceTokenService;

    @Transactional
    public JWTResponse login(KakaoLoginRequest kakaoLoginRequest) {
        Optional<Member> member = memberRepository.findByKakaoId(kakaoLoginRequest.getKakaoId());
        if(member.isEmpty()){
            throw new CustomException(ErrorCode.NOT_EXIST_USER);
        }
        JWTToken jwt = jwtUtil.createTokens(member.get().getUserId());
        deviceTokenService.saveDeviceToken(member.get().getUserId(),kakaoLoginRequest.getDeviceToken());
        return JWTResponse.builder().token(jwt).build();
    }

    @Transactional
    public JWTResponse register(MemberDto memberDto) {
        String kakaoId = memberDto.getKakaoId();
        Optional<Member> existMember = memberRepository.findByKakaoId(kakaoId);

        Member member;
        if (existMember.isPresent()) {
            throw new CustomException(ErrorCode.EXIST_MEMBER);
        } else {
            memberRepository.save(memberDto.toEntity());
            member = memberRepository.findByKakaoId(kakaoId).orElseThrow(() -> new RuntimeException("Member could not be retrieved after save."));
        }
        JWTToken jwt = jwtUtil.createTokens(member.getUserId());
        return JWTResponse.builder().token(jwt).build();
    }


    public boolean attendance(Long userId)
    {
        Membership membership =  memberRepository.attendance(userId);
        if(membership==null) return false;
        membership.attend(membership.getAttendanceDate());
        return true;
    }

    @Transactional(readOnly = true)
    public MemberDto findMember(Long userId)
    {
        Member member = memberRepository.findById(userId).orElseThrow(() -> new RuntimeException("Member not found"));
        MemberDto memberDto = MemberDto.toDto(member);
        return memberDto;
    }

    @Transactional(readOnly = true)
    public MemberDetailResponse findMemberDetail(Long memberId) {
        return memberRepository.memberDetail(memberId);
    }

    @Transactional(readOnly = true)
    public List<MemberJoinResponse> searchMembersByGymAndName(Long gymId, String name) {
        return memberRepository.memberList(gymId, name);
    }

    @Transactional
    public MemberDto updateMember(Long userId,MemberDto member)
    {
        Member findMember = memberRepository.findById(userId).orElseThrow(() -> new RuntimeException("Member not found"));
        findMember.setHeight(member.getHeight());
        findMember.setWeight(member.getWeight());
        MemberDto resultDto = MemberDto.toDto(findMember);

        return resultDto;
    }

    @Transactional
    public void removeMember(Long id) {
        Member member = memberRepository.findById(id).orElseThrow(() -> new RuntimeException("Gym not found"));
        memberRepository.delete(member);
    }
}
