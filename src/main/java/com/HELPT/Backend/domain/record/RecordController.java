package com.HELPT.Backend.domain.record;

import com.HELPT.Backend.domain.product.dto.ProductResponse;
import com.HELPT.Backend.domain.record.dto.RecordRequest;
import com.HELPT.Backend.domain.record.dto.RecordResponse;
import com.HELPT.Backend.global.s3.S3Uploader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import static com.HELPT.Backend.global.auth.SecurityUtil.getCurrentUserId;

@RequiredArgsConstructor
@RestController
@RequestMapping("/records")
@Slf4j
public class RecordController {

    private final RecordService recordService;
    private final S3Uploader s3Uploader;

    /*
    //임시로 사용
    @PostMapping
    public ResponseEntity<RecordResponse> saveRecord(@RequestBody RecordRequest recordRequest) {

        Long userId = getCurrentUserId();
        return ResponseEntity.ok(recordService.saveRecord(userId,recordRequest));
    }

     */

    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<RecordResponse> saveRecord(@RequestPart("recordRequest") RecordRequest recordRequest,
                                                     @RequestPart("snapshotFile") MultipartFile snapshotFile
                                                     ) {

        Long userId = getCurrentUserId();
        String uploadURL;
        try {
            uploadURL = s3Uploader.upload(snapshotFile, "snapshotFile");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        recordRequest.setSnapshotFile(uploadURL);

        return ResponseEntity.ok(recordService.saveRecord(userId,recordRequest));
    }

    @GetMapping("/detail/{date}")
    public ResponseEntity<List<RecordResponse>> recordDetail(@PathVariable("date") LocalDate monthDate) {

        Long userId = getCurrentUserId();
        return ResponseEntity.ok(recordService.detail(userId,monthDate));
    }

    @PostMapping("/detail/members")
    public ResponseEntity<List<RecordResponse>> recordDetailOfMember(@RequestParam("memberId") Long memberId,
                                                           @RequestParam("date") LocalDate monthDate
    ) {

        return ResponseEntity.ok(recordService.detail(memberId,monthDate));

    }
/*
    @GetMapping("/calender")
    public ResponseEntity<List<RecordResponse>> recordList(@RequestParam("date") LocalDate monthDate) {

        Long userId = getCurrentUserId();

        return ResponseEntity.ok(recordService.recordList(userId,monthDate));

    }
*/




}
