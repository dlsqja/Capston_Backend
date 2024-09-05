package com.HELPT.Backend.domain.admin;

import com.HELPT.Backend.domain.admin.dto.AdminRequest;
import com.HELPT.Backend.domain.equipment.dto.EquipmentDto;
import com.HELPT.Backend.domain.equipment.dto.EquipmentRequestDto;
import com.HELPT.Backend.domain.equipment.EquipmentService;
import com.HELPT.Backend.domain.exercise.Exercise;
import com.HELPT.Backend.domain.exercise.dto.ExerciseResponseDto;
import com.HELPT.Backend.domain.exercise.ExerciseService;
import com.HELPT.Backend.domain.gym.GymService;
import com.HELPT.Backend.domain.gym.dto.GymRegistrationDto;
import com.HELPT.Backend.domain.gym.entity.Gym;
import com.HELPT.Backend.domain.gym.entity.Status;
import com.HELPT.Backend.global.auth.jwt.JWTResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;
import com.HELPT.Backend.global.s3.S3Uploader;

import java.io.IOException;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
@Slf4j
public class AdminController {

    private final AdminService adminService;
    private final GymService gymService;
    private final EquipmentService equipmentService;
    private final S3Uploader s3Uploader;
    private final ExerciseService exerciseService;

    @GetMapping("/login")
    public String loginView() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute AdminRequest adminRequest, HttpServletResponse response) {
        JWTResponse jwtResponse = adminService.login(adminRequest);
        if (jwtResponse != null) {
            // 쿠키에 토큰 저장
            Cookie cookie = new Cookie("AUTH_TOKEN", jwtResponse.getAccessToken());
            cookie.setHttpOnly(true); // JavaScript를 통한 접근 방지
            cookie.setPath("/"); // 사이트 전역에서 쿠키 접근 가능
            response.addCookie(cookie);
            return "redirect:/admin/gyms/pending";
        } else {
            return "login";
        }
    }

    @GetMapping
    public RedirectView viewAdmin(Model model) {
        return new RedirectView("/admin/gyms/pending");
    }

    @GetMapping("/gyms")
    public String viewGymList(Model model) {
        List<Gym> gymList = gymService.findGymList();
        model.addAttribute("pendingGyms", gymList);
        model.addAttribute("template", "gyms/list");
        return "admin/index";
    }

    @GetMapping("/gyms/pending")
    public String viewGyms(Model model) {
        List<Gym> pendingGyms = gymService.findGymsByStatus(Status.Pending);
        model.addAttribute("pendingGyms", pendingGyms);
        model.addAttribute("template", "gyms/pending");
        return "admin/index";
    }

    @GetMapping("/gyms/gymregistrations")
    public String showGymDetails(@RequestParam Long gymId, Model model) {
        GymRegistrationDto gymDetails = gymService.findGymRegistration(gymId);
        model.addAttribute("gymDetails", gymDetails);
        model.addAttribute("template", "gyms/gymDetail");
        return  "admin/index";
    }

    @PostMapping("/gyms/updateStatus")
    public String updateGymStatus(@RequestParam Long gymId, @RequestParam Status status) {
        gymService.updateGymStatus(gymId, status);
        return "redirect:/admin/gyms/pending";
    }

    @GetMapping("/equipments")
    public String listEquipments(Model model) {
        List<EquipmentDto> equipments = equipmentService.findEquipments();
        model.addAttribute("equipments", equipments);
        model.addAttribute("template", "equipments/list");
        return "admin/index";
    }

    @GetMapping("/equipments/{id}")
    public String showEquipmentDetails(@PathVariable Long id, Model model) {
        EquipmentDto equipment = equipmentService.findEquipment(id);
        ExerciseResponseDto exercise = exerciseService.findExercise(equipment.getExerciseId());
        model.addAttribute("equipment", equipment);
        model.addAttribute("exercise", exercise);
        model.addAttribute("template", "equipments/detail");
        return "admin/index";
    }

    @GetMapping("/equipments/add")
    public String viewAddEquipment(Model model) {
        model.addAttribute("equipment", EquipmentRequestDto.builder().build());
        model.addAttribute("template", "equipments/add");
        return "admin/index";
    }

    @PostMapping("/equipments")
    public String addEquipment(@ModelAttribute EquipmentRequestDto equipmentRequestDto) {
        String uploadTopURL;
        try {
            uploadTopURL = s3Uploader.upload(equipmentRequestDto.getTopImage(), "exerciseFile");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ExerciseResponseDto exerciseResponseDto = exerciseService.uploadExercise(equipmentRequestDto.toExerciseDto(), uploadTopURL);
        equipmentRequestDto.setExerciseId(exerciseResponseDto.getExerciseId());
        equipmentService.addEquipment(equipmentRequestDto.toEquipmentDto());
        return "redirect:/admin/equipments";
    }

    // 기구 수정 페이지로 이동
    @GetMapping("/equipments/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        EquipmentDto equipment = equipmentService.findEquipment(id);
        Exercise exercise = exerciseService.findNonParsingExercise(equipment.getExerciseId());
        model.addAttribute("equipment", equipment);
        model.addAttribute("exercise", exercise);
        model.addAttribute("template", "equipments/edit");
        return "admin/index";
    }

    // 기구 수정
    @PostMapping("/equipments/edit/{id}")
    public String editEquipment(@PathVariable Long id, @ModelAttribute EquipmentRequestDto equipmentRequestDto) {
        String uploadTopURL = equipmentRequestDto.getOriginImage();
        if (!equipmentRequestDto.getTopImage().isEmpty()){
            try {
                s3Uploader.deleteFile(uploadTopURL);
                uploadTopURL = s3Uploader.upload(equipmentRequestDto.getTopImage(), "exerciseFile");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        EquipmentDto equipmentDto = equipmentService.modifyEquipment(id, equipmentRequestDto.toEquipmentDto());
        exerciseService.modifyExercise(equipmentDto.getExerciseId(),equipmentRequestDto.toExerciseDto(),uploadTopURL);
        return "redirect:/admin/equipments";
    }

    // 기구 삭제
    @PostMapping("/equipments/delete/{id}")
    public String deleteEquipment(@PathVariable Long id) {
        equipmentService.removeEquipment(id);
        return "redirect:/admin/equipments";
    }
}
