package com.neeraj.assistant.dashboard.service;

import org.springframework.stereotype.Service;

import com.neeraj.assistant.chat.repository.ChatRepository;
import com.neeraj.assistant.common.security.SecurityUtils;
import com.neeraj.assistant.dashboard.dto.DashboardStatsResponse;
import com.neeraj.assistant.file.repository.FileRepository;
import com.neeraj.assistant.summary.repository.SummaryRepository;
import com.neeraj.assistant.user.entity.User;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DashboardServiceImpl implements DashboardService {

    private final FileRepository fileRepository;
    private final ChatRepository chatRepository;
    private final SummaryRepository summaryRepository;

    public DashboardStatsResponse getDashboardStatsResponse(){

        User currentUser = SecurityUtils.getCurrentUser();
        return new DashboardStatsResponse(

            fileRepository.countByUser(currentUser),
            chatRepository.countByUser(currentUser),
            summaryRepository.countByFileUser(currentUser),
            fileRepository.getTotalStorageUsed(currentUser)
        );
    }



}
