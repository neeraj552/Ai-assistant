package com.neeraj.assistant.summary.mapper;

import com.neeraj.assistant.summary.dto.SummaryResponse;
import com.neeraj.assistant.summary.entity.Summary;

public class SummaryMapper {

    private SummaryMapper(){}

    public static SummaryResponse toResponse(Summary summary){

        return SummaryResponse.builder()
                 .id(summary.getId())
                 .summary(summary.getSummaryText())
                 .status(summary.getStatus())
                 .createdAt(summary.getCreatedAt())
                 .build();
    }

}
