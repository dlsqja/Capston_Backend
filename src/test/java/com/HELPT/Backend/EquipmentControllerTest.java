//package com.HELPT.Backend.domain.equipment;
//
//import com.HELPT.Backend.domain.equipment.dto.EquipmentDto;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.ArgumentMatchers.refEq;
//import static org.mockito.BDDMockito.given;
//import static org.mockito.Mockito.verify;
//import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@WebMvcTest(EquipmentController.class)
//class EquipmentControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockBean
//    private EquipmentService equipmentService;
//
//    @Autowired
//    private ObjectMapper objectMapper;
//
//    public Equipment setUpEquipment(){
//        return Equipment.builder()
//                .equipmentId(1L)
//                .exerciseId(100L)
//                .equipmentName("벤치 프레스")
//                .defaultCount(10)
//                .defaultSet(5)
//                .defaultWeight(25)
//                .build();
//    }
//
//    public EquipmentDto setUpEquipmentDto(){
//        return EquipmentDto.builder()
//                .equipmentId(1L)
//                .exerciseId(100L)
//                .equipmentName("벤치 프레스")
//                .defaultCount(10)
//                .defaultSet(5)
//                .defaultWeight(25)
//                .build();
//    }
//
////    @Test
////    @DisplayName("[Controller] 기구 등록 테스트")
////    public void saveEquipment() throws Exception {
////
////        //given
////        // refEq를 통해 동등성 비교
////        Equipment equipment = setUpEquipment();
////        EquipmentDto equipmentDto = setUpEquipmentDto();
////        given(equipmentService.addEquipment(any(EquipmentDto.class)))
////                .willReturn(equipmentDto);
////
////        // when
////        // restAPI 를 테스트하기 위한 객체
////        mockMvc.perform(post("/equipments")
////                        .contentType(MediaType.APPLICATION_JSON)
////                        .content(new ObjectMapper().writeValueAsString(equipmentDto)))
////                .andExpect(status().isOk())
////                // json의 depth가 깊어지면, .을 추가하여 탐색할 수 있음 (ex: $.productId.productIdName)
////                .andExpect(jsonPath("$.data").value(user1.getEmail()))
////                .andExpect(jsonPath("$.password").isEmpty())
////                .andDo(print());
////
////        // then
////        // 해당 메소드가 실행되었는지를 검증할 수 있다. (eq 메소드로 동등성 비교)
////        verify(authService).signup(refEq(user1));
////    }
//
//}