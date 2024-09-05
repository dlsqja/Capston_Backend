package com.HELPT.Backend.domain.membership;

import com.HELPT.Backend.domain.member.Member;
import com.HELPT.Backend.domain.member.MemberRepository;
import com.HELPT.Backend.domain.membership.dto.MembershipResponse;
import com.HELPT.Backend.domain.product.Product;
import com.HELPT.Backend.global.error.CustomException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static com.HELPT.Backend.global.error.ErrorCode.NOT_EXIST_DATA;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class MembershipServiceTest {

    @InjectMocks
    private MembershipService membershipService;

    @Mock
    private MembershipRepository membershipRepository;

    @Mock
    private MemberRepository memberRepository;

    private Membership membership;
    private Member member;
    private Product product;

    @BeforeEach
    void setUp() {
        membership = Membership.builder()
                .userId(1L)
                .gymId(1L)
                .build();

        member = Member.builder()
                .userId(1L)
                .gymId(1L)
                .build();

        product = Product.builder()
                .productId(1L)
                .gymId(1L)
                .months(3)
                .build();
    }

    @Test
    @DisplayName("[Service] 회원권 조회 테스트")
    void findMembershipServiceTest() {
        // given
        given(membershipRepository.findByUserId(anyLong())).willReturn(Optional.of(membership));

        // when
        MembershipResponse response = membershipService.findMembership(1L);

        // then
        verify(membershipRepository).findByUserId(anyLong());
        assertNotNull(response);
    }

    @Test
    @DisplayName("[Service] 회원권 삭제 테스트")
    void removeMembershipServiceTest() {
        // given
        given(membershipRepository.findById(anyLong())).willReturn(Optional.of(membership));

        // when
        membershipService.removeMembership(1L);

        // then
        verify(membershipRepository).deleteById(anyLong());
    }

    @Test
    @DisplayName("[Service] 회원권 추가 테스트")
    void addMembershipServiceTest() {
        // given
        given(membershipRepository.findProduct(anyLong())).willReturn(product);
        given(membershipRepository.save(any(Membership.class))).willReturn(membership);
        given(memberRepository.findById(anyLong())).willReturn(Optional.of(member));

        // when
        MembershipResponse response = membershipService.addMembership(1L, 1L);

        // then
        verify(membershipRepository).findProduct(anyLong());
        verify(membershipRepository).save(any(Membership.class));
        verify(memberRepository).findById(anyLong());
        assertNotNull(response);
    }

    @Test
    @DisplayName("[Service] 회원권 연장 테스트")
    void extensionMembershipServiceTest() {
        // given
        LocalDate newEndDate = LocalDate.now().plusMonths(2);
        given(membershipRepository.findById(anyLong())).willReturn(Optional.of(membership));

        // when
        MembershipResponse response = membershipService.extensionMembership(1L, newEndDate);

        // then
        verify(membershipRepository).findById(anyLong());
        assertNotNull(response);
        assertThat(response.getEndDate()).isEqualTo(newEndDate);
    }
}
