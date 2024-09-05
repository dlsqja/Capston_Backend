package com.HELPT.Backend.domain.notice;

import com.HELPT.Backend.domain.notice.dto.NoticeRequest;
import com.HELPT.Backend.domain.notice.dto.NoticeResponse;
import com.HELPT.Backend.domain.product.dto.ProductRequest;
import com.HELPT.Backend.domain.product.dto.ProductResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/notices")
@Slf4j
public class NoticeController {

    private final NoticeService noticeService;

    @GetMapping("")
    public ResponseEntity<List<NoticeResponse>> getNotice(@RequestParam("gymId")Long gymId) {

        return ResponseEntity.ok(noticeService.findNotice(gymId));
    }

    @PostMapping("/upload")
    public ResponseEntity<Boolean> uploadNotice(@RequestBody NoticeRequest noticeRequest) {

        return ResponseEntity.ok(noticeService.uploadNotice(noticeRequest));
    }

    @PostMapping("/{noticeId}/delete")
    public ResponseEntity<Boolean> deleteNotice(@PathVariable("noticeId") Long noticeId) {

        return ResponseEntity.ok(noticeService.deleteNotice(noticeId));
    }

    @PutMapping("/{noticeId}/modify")
    public ResponseEntity<Boolean> modifyNotice(@PathVariable("noticeId") Long noticeId,
                                                @RequestBody NoticeRequest noticeRequest) {

        return ResponseEntity.ok(noticeService.modifyNotice(noticeId,noticeRequest));
    }


}
