package com.neeraj.assistant.summary.service;

import java.util.UUID;

import com.neeraj.assistant.summary.dto.SummaryResponse;

public interface SummaryService {

    SummaryResponse generateSummary(UUID fileId);

    SummaryResponse getSummary(UUID fileId);

}
