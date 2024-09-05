package com.HELPT.Backend.domain.gym;

import com.HELPT.Backend.domain.gym.dto.*;
import com.HELPT.Backend.domain.gym.entity.GymRegistration;
import com.HELPT.Backend.domain.gymequipment.GymEquipmentService;
import com.HELPT.Backend.domain.gymequipment.dto.GymEquipmentResponse;
import com.HELPT.Backend.global.s3.S3Uploader;
import com.amazonaws.services.s3.model.ObjectMetadata;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.HELPT.Backend.global.auth.SecurityUtil.getCurrentUserId;

@RequiredArgsConstructor
@RestController
@RequestMapping("/gyms")
@Slf4j
public class GymController {

    private final GymService gymService;
    private final GymEquipmentService gymEquipmentService;
    private final S3Uploader s3Uploader;

    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<GymResponse> registerGym(
            @RequestPart("gymInfo") GymResistrationRequest gymInfo,
            @RequestPart("businessFile") MultipartFile businessFile) {
//        String uploadURL = "/test";
        String uploadURL;
        try {
            uploadURL = s3Uploader.upload(businessFile, "businessFile");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        gymInfo.updateBusinessFile(uploadURL);
        return ResponseEntity.ok(gymService.addGym(gymInfo));
    }

    @PutMapping("/{gymId}/chat-link")
    public ResponseEntity<GymResponse> chatLinkModify(@RequestBody ChatLinkDto chatLinkDto, @PathVariable Long gymId){
        return ResponseEntity.ok(gymService.modifyChatLink(gymId,chatLinkDto.getChatLink()));
    }

    @GetMapping("/{gymId}/chat-link")
    public ResponseEntity<Map<String,String>> findChatLink(@PathVariable Long gymId){
        Map<String, String> map = new HashMap<>();
        map.put("chatLink",gymService.getChatLink(gymId));
        return ResponseEntity.ok(map);
    }

    @DeleteMapping("/{gymId}/chat-link")
    public ResponseEntity<Boolean> removeChatLink(@PathVariable Long gymId){
        return ResponseEntity.ok(gymService.removeChatLink(gymId));
    }

    @GetMapping("/{gymId}")
    public ResponseEntity<GymResponse> gymDetails(@PathVariable Long gymId) {
        return ResponseEntity.ok(gymService.findGym(gymId));
    }

    @GetMapping("/status")
    public ResponseEntity<GymResponse> gymStatus(){
        return ResponseEntity.ok(gymService.findGymStatus(getCurrentUserId()));
    }

    @GetMapping("/{gymId}/gym-registrations")
    public ResponseEntity<GymRegistrationDto> gymRegistrationDetails(@PathVariable Long gymId) {
        return ResponseEntity.ok(gymService.findGymRegistration(gymId));
    }

    @GetMapping("/{gymId}/gym-equipments")
    public ResponseEntity<List<GymEquipmentResponse>> getAllGymEquipments(@PathVariable Long gymId) {
        List<GymEquipmentResponse> equipments = gymEquipmentService.findGymEquipments(gymId);
        return ResponseEntity.ok(equipments);
    }

    @GetMapping
    public ResponseEntity<List<GymResponse>> gymListByName(@RequestParam(name = "name", required = false)String gymName) {
        return ResponseEntity.ok(gymService.findGymsByName(gymName));
    }

    @PutMapping("/{gymId}")
    public ResponseEntity<GymResponse> gymModify(@PathVariable Long gymId, @RequestBody GymRequest gymRequest) {
        return ResponseEntity.ok(gymService.modifyGym(gymId, gymRequest));
    }

    @DeleteMapping("/{gymId}")
    public ResponseEntity<Void> gymRemove(@PathVariable Long gymId) {
        gymService.removeGym(gymId);
        return ResponseEntity.ok().build();
    }
}
