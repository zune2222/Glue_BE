package org.glue.glue_be.invitation.entity;

import jakarta.persistence.*;
import lombok.*;
import org.glue.glue_be.common.BaseEntity;
import org.glue.glue_be.user.entity.User;

import java.time.LocalDateTime;

@Entity
@Table(name = "invitation")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Invitation extends BaseEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "invitation_id")
    private Long invitationId;
    
    @Column(name = "code", nullable = false, unique = true)
    private String code;
    
    @Column(name = "expires_at", nullable = false)
    private LocalDateTime expiresAt;
    
    @Column(name = "max_uses", nullable = false)
    private Integer maxUses;
    
    @Column(name = "used_count", nullable = false)
    private Integer usedCount = 0;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creator_id", nullable = false)
    private User creator;
    
    @Column(name = "status", nullable = false)
    private Integer status = 1; // 1: ACTIVE, 2: EXPIRED, 3: FULLY_USED
    
    @Column(name = "meeting_id", nullable = false)
    private Long meetingId;
    
    @Builder
    private Invitation(String code, LocalDateTime expiresAt, Integer maxUses, User creator, Long meetingId) {
        this.code = code;
        this.expiresAt = expiresAt;
        this.maxUses = maxUses;
        this.creator = creator;
        this.usedCount = 0;
        this.status = 1;
        this.meetingId = meetingId;
    }
    
    public boolean isValid() {
        return status == 1 && LocalDateTime.now().isBefore(expiresAt) && usedCount < maxUses;
    }
    
    public void incrementUsedCount() {
        this.usedCount++;
        if (this.usedCount >= this.maxUses) {
            this.status = 3; // FULLY_USED
        }
    }
    
    public void expire() {
        this.status = 2; // EXPIRED
    }
} 