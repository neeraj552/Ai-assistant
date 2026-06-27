package com.neeraj.assistant.summary.controller;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.neeraj.assistant.summary.dto.SummaryResponse;
import com.neeraj.assistant.summary.service.SummaryService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/v1/summaries")
@RequiredArgsConstructor
public class SummaryController {

    private final SummaryService summaryService;
    
    @PostMapping("/{fileId}")
    public ResponseEntity<SummaryResponse> generateSummary(
            @PathVariable UUID fileId){

                return ResponseEntity.ok(
                    summaryService.generateSummary(fileId));
            }
    @GetMapping("/{fileId}")
    public ResponseEntity<SummaryResponse> getSummary(
                @PathVariable UUID fileId){

                    return ResponseEntity.ok(
                        summaryService.generateSummary(fileId));
                }        

}
