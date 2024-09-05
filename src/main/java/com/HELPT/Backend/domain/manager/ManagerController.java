package com.HELPT.Backend.domain.manager;

import com.HELPT.Backend.global.common.dto.KakaoLoginRequest;
import com.HELPT.Backend.domain.manager.dto.ManagerRequest;
import com.HELPT.Backend.domain.manager.dto.ManagerResponse;
import com.HELPT.Backend.domain.member.Dto.MemberJoinResponse;
import com.HELPT.Backend.global.auth.jwt.JWTResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.HELPT.Backend.global.auth.SecurityUtil.getCurrentUserId;

@RequiredArgsConstructor
@RestController
@RequestMapping("/managers")
@Slf4j
public class ManagerController {

    private final ManagerService managerService;

    @PostMapping("/login")
    public ResponseEntity<JWTResponse> login(@RequestBody KakaoLoginRequest kakaoLoginRequest) {
        return ResponseEntity.ok(managerService.login(kakaoLoginRequest));
    }

    @PostMapping("/register")
    public ResponseEntity<JWTResponse> register(@RequestBody ManagerRequest managerRequest) {
        return ResponseEntity.ok(managerService.register(managerRequest));
    }

    @GetMapping("/{gym_id}")
    public ResponseEntity<ManagerResponse> managerDetails() {
        Long manager_id = getCurrentUserId();
        return ResponseEntity.ok(managerService.findManager(manager_id));
    }

    @GetMapping
    public ResponseEntity<ManagerResponse> managerList() {
        Long id = getCurrentUserId();
        return ResponseEntity.ok(managerService.findManager(id));
    }

    @GetMapping("/members")
    public ResponseEntity<List<MemberJoinResponse>> memberList() {
        Long userId = getCurrentUserId();
        ManagerResponse mr = managerService.findManager(userId);
        Long gymId = mr.getGymId();

        return ResponseEntity.ok(managerService.memberList(gymId));
    }

    @PutMapping("/{gym_id}")
    public ResponseEntity<ManagerResponse> managerModify(@RequestBody ManagerRequest managerRequest) {
        Long manager_id = getCurrentUserId();
        return ResponseEntity.ok(managerService.modifyManager(manager_id, managerRequest));
    }

    @DeleteMapping
    public ResponseEntity<Void> memberRemove() {
        Long userId = getCurrentUserId();
        managerService.removeManager(userId);
        return ResponseEntity.ok().build();
    }
}
