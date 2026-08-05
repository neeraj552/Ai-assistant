package com.neeraj.assistant.dashboard.dto;

public record DashboardStatsResponse(
    long totalDocuments,
    long totalChats,
    long totalSummaries,
    long storageUsed

) {

}
