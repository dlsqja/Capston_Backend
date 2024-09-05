package com.HELPT.Backend.domain.qr;

import com.HELPT.Backend.domain.entry_log.EntryLog;
import com.HELPT.Backend.domain.entry_log.repository.EntryLogRepository;
import com.HELPT.Backend.domain.gym.entity.Gym;
import com.HELPT.Backend.domain.gym.repository.GymRepository;
import com.HELPT.Backend.domain.member.Member;
import com.HELPT.Backend.domain.member.MemberRepository;
import com.HELPT.Backend.domain.membership.Membership;
import com.HELPT.Backend.domain.membership.MembershipRepository;
import com.HELPT.Backend.global.auth.jwt.JWTUtil;
import com.HELPT.Backend.global.error.CustomException;
import com.HELPT.Backend.global.error.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

import static com.HELPT.Backend.global.error.ErrorCode.NOT_EXIST_MEMBER;

@Service
@RequiredArgsConstructor
public class QrCodeService {

    private final JWTUtil jwtUtil;
    private final MembershipRepository membershipRepository;
    private final MemberRepository memberRepository;
    private final EntryLogRepository entryLogRepository;
    private final GymRepository gymRepository;

    @Transactional
    public String addQrCode(Long userId){
        return jwtUtil.createQrToken(userId);
    }

    @Transactional
    public QrResponse verifyQrCode(Long userId, Long gymId){
        Optional<Membership> result = membershipRepository.findByUserIdAndGymId(userId,gymId);
        if(result.isEmpty()) {
            throw new CustomException(NOT_EXIST_MEMBER);
        }
        Membership membership = result.get();
        if(!membership.getLastAttendDate().equals(LocalDate.now())){
            membership.attend(membership.getAttendanceDate()+1);
        }
        Optional<Member> findmember = Optional.ofNullable(memberRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_USER)));
        Optional<Gym> findGym = Optional.ofNullable(gymRepository.findById(gymId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_DATA)));
        EntryLog entryLog = EntryLog.builder()
                .member(findmember.get())
                .gym(findGym.get())
                .build();
        entryLogRepository.save(entryLog);
        return QrResponse.builder().userName(entryLog.getMember().getUserName()).build();
    }
}
