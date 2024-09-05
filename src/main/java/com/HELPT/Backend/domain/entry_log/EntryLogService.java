package com.HELPT.Backend.domain.entry_log;

import com.HELPT.Backend.domain.entry_log.repository.EntryLogRepository;
import com.HELPT.Backend.domain.gym.entity.Gym;
import com.HELPT.Backend.domain.gym.repository.GymRepository;
import com.HELPT.Backend.domain.member.Member;
import com.HELPT.Backend.domain.member.MemberRepository;
import com.HELPT.Backend.global.error.CustomException;
import com.HELPT.Backend.global.error.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EntryLogService {

    private final EntryLogRepository entryLogRepository;
    private final MemberRepository memberRepository;
    private final GymRepository gymRepository;

    public List<EntryLogResponse> getEntryLogsByNameAndDate(String name, Long gymId, LocalDate date) {
        return entryLogRepository.findByNameAndGymIdAndEntryDate(name, gymId, date);
    }

    @Transactional
    public EntryLog saveEntry(Long memberId,Long gymId) {
        Optional<Member> findmember = Optional.ofNullable(memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_USER)));
        Optional<Gym> findGym = Optional.ofNullable(gymRepository.findById(gymId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_EXIST_DATA)));
        EntryLog entryLog = EntryLog.builder()
                .member(findmember.get())
                .gym(findGym.get())
                .build();
        entryLogRepository.save(entryLog);
        return entryLog;
    }
}