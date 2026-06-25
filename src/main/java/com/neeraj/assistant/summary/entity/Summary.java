package com.neeraj.assistant.summary.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import com.neeraj.assistant.file.entity.FileDocument;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "summaries")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Summary {
    @Id
    @Column(nullable = false)
    private UUID id;
    
    @Column(nullable = false)
    @Lob
    private String summaryText;
    
    @Column(nullable = false)
    private String modelUsed;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SummaryStatus status;
    
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "file_id", nullable = false, unique = true)
    private FileDocument file;
    
    @PrePersist
    public void onCreate(){
        createdAt = LocalDateTime.now();

        if(status == null){
            status = SummaryStatus.COMPLETED;
        }
    }

}
