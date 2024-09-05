//package com.HELPT.Backend.domain.equipment;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//
//import java.util.List;
//import java.util.Optional;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//@DataJpaTest
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//class EquipmentRepositoryTest {
//
//    @Autowired
//    private EquipmentRepository equipmentRepository;
//
//    private Equipment equipment;
//
//    @BeforeEach
//    void setUp() {
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
//    @DisplayName("[Repository] 기구 생성 테스트")
//    void saveEquipment(){
//        // given
//
//        // when
//        Equipment saveEquipment = equipmentRepository.save(equipment);
//
//        // then
//        assertThat(saveEquipment).isNotNull();
//        assertThat(saveEquipment.getEquipmentName()).isEqualTo(equipment.getEquipmentName());
//    }
//
//    @Test
//    @DisplayName("[Repository] 기구 목록 조회 테스트")
//    void findEquipmentList(){
//        // given
//        equipmentRepository.save(equipment);
//
//        // when
//        List<Equipment> equipmentList = equipmentRepository.findAll();
//
//        // then
//        assertThat(equipmentList).isNotNull();
//        assertThat(equipmentList.size()).isEqualTo(2);
//    }
//
//    @Test
//    @DisplayName("[Repository] 기구 조회 테스트")
//    void findEquipment(){
//        // given
//        Equipment saveEquipment = equipmentRepository.save(equipment);
//
//        // when
//        Equipment findEquipment = equipmentRepository.findById(saveEquipment.getEquipmentId()).get();
//
//        // then
//        assertThat(findEquipment).isNotNull();
//        assertThat(findEquipment.getEquipmentName()).isEqualTo(equipment.getEquipmentName());
//    }
//
//    @Test
//    @DisplayName("[Repository] 기구 수정 테스트")
//    void modifyEquipment(){
//        // given
//        Equipment saveEquipment = equipmentRepository.save(equipment);
//
//        // when
//        saveEquipment.updateEquipmentName("스쿼트");
//
//        // then
//        Equipment findEquipment = equipmentRepository.findById(saveEquipment.getEquipmentId()).get();
//        assertThat(findEquipment).isNotNull();
//        assertThat(findEquipment.getEquipmentName()).isEqualTo("스쿼트");
//    }
//
//    @Test
//    @DisplayName("[Repository] 기구 삭제 테스트")
//    void removeEquipment(){
//        // given
//        Equipment saveEquipment = equipmentRepository.save(equipment);
//
//        // when
//        equipmentRepository.deleteById(saveEquipment.getEquipmentId());
//
//        // then
//        Optional<Equipment> findEquipment = equipmentRepository.findById(saveEquipment.getEquipmentId());
//        assertThat(findEquipment).isEmpty();
//    }
//
//}