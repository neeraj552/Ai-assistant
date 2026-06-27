package com.neeraj.assistant.summary.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import com.neeraj.assistant.summary.entity.SummaryStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SummaryResponse {

    private UUID id;

    private String summary;

    private SummaryStatus status;

    private LocalDateTime createdAt;

}
