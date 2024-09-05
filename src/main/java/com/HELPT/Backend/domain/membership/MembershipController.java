package com.HELPT.Backend.domain.membership;

import com.HELPT.Backend.domain.membership.dto.MembershipResponse;
import com.HELPT.Backend.domain.product.ProductService;
import com.HELPT.Backend.global.kakaomodule.KakaoPayService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import static com.HELPT.Backend.global.auth.SecurityUtil.getCurrentUserId;

@RestController
@RequestMapping("/memberships")
@RequiredArgsConstructor
public class MembershipController {

    private final MembershipService membershipService;

    @GetMapping("/detail")
    public ResponseEntity<MembershipResponse> membershipInfo() {
        Long userId = getCurrentUserId();
        return ResponseEntity.ok(membershipService.findMembership(userId));
    }

    @PostMapping("/{productId}")
    public ResponseEntity<MembershipResponse> addMembership(@PathVariable Long productId) {
        Long userId = getCurrentUserId();
        return ResponseEntity.ok(membershipService.addMembership(userId,productId));
    }

    @PutMapping ("/{membershipId}/extension")
    public ResponseEntity<MembershipResponse> extensionMembership(
            @PathVariable Long membershipId,
            @RequestParam("endDate") LocalDate endDate) {
        return ResponseEntity.ok(membershipService.extensionMembership(membershipId,endDate));
    }

    @DeleteMapping("/{membershipId}")
    public ResponseEntity<Boolean> removeMembership(@PathVariable Long membershipId) {
        membershipService.removeMembership(membershipId);
        return ResponseEntity.ok(true);
    }



}
