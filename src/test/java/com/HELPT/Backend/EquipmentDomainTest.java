//package com.HELPT.Backend.domain.equipment;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//class EquipmentDomainTest {
//
//    private Equipment equipment;
//
//    @BeforeEach
//    public void setUp() {
//        equipment = Equipment.builder()
//                .equipmentId(1L)
//                .exerciseId(100L)
//                .equipmentName("벤치 프레스")
//                .defaultCount(10)
//                .defaultSet(5)
//                .defaultWeight(25)
//                .build();
//    }
//
//    @Test
//    @DisplayName("[Domain] 기구 생성 테스트")
//    void createEquipment(){
//        // given
//        // when
//        // then
//        assertThat(equipment.getEquipmentName()).isEqualTo("벤치 프레스");
//        assertThat(equipment.getDefaultCount()).isEqualTo(10);
//        assertThat(equipment.getDefaultSet()).isEqualTo(5);
//        assertThat(equipment.getDefaultWeight()).isEqualTo(25);
//    }
//
//    @Test
//    @DisplayName("[Domain] 기구 이름 변경 테스트")
//    void changeEquipmentNameTest(){
//        // given
//
//        // when
//        equipment.updateEquipmentName("스쿼트");
//        // then
//        assertThat(equipment.getEquipmentName()).isEqualTo("스쿼트");
//    }
//
//    @Test
//    @DisplayName("[Domain] 기구 기본 세트당 횟수 변경 테스트")
//    void changeDefaultCountTest(){
//        // given
//
//        // when
//        equipment.updateDefaultCount(15);
//        // then
//        assertThat(equipment.getDefaultCount()).isEqualTo(15);
//    }
//
//    @Test
//    @DisplayName("[Domain] 기구 기본 세트당 횟수 변경 테스트")
//    void changeDefaultSetTest(){
//        // given
//
//        // when
//        equipment.updateDefaultSet(3);
//        // then
//        assertThat(equipment.getDefaultSet()).isEqualTo(3);
//    }
//
//    @Test
//    @DisplayName("[Domain] 기구 기본 세트 수 변경 테스트")
//    void changeDefaultWeightTest(){
//        // given
//
//        // when
//        equipment.updateDefaultWeight(20);
//        // then
//        assertThat(equipment.getDefaultWeight()).isEqualTo(20);
//    }
//
//}