package com.HELPT.Backend.domain.manager;

import com.HELPT.Backend.domain.member.Dto.MemberJoinResponse;

import java.util.List;

public interface ManagerRepositoryCustom {
    public List<MemberJoinResponse> MemberList(Long gymId);
}
