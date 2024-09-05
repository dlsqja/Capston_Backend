function openGymDetails(gymId) {
    // 새 창(팝업)의 URL 설정
    var url = '/gyms/details/' + gymId; // 예시 URL, 실제 구현에 맞게 수정 필요
    // 새 창(팝업) 열기
    window.open(url, 'GymDetails', 'width=600,height=400');
}