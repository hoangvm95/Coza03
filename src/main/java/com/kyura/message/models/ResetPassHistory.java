package com.kyura.message.models;

import com.kyura.message.common.ACTIVE_STATUS;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "reset_pass_history")
public class ResetPassHistory extends AbstractAuditing implements Serializable {
    /**
     * Generated.
     */
    private static final long serialVersionUID = 1932883370339533518L;

    @Id
    @Column(name = "user_id")
    private Long userId;
    @Column(name = "active_code")
    private String activeCode;
    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 20)
    private ACTIVE_STATUS status;


    public ResetPassHistory(Long userId, String activeCode) {
        this.userId = userId;
        this.activeCode = activeCode;
        this.status = ACTIVE_STATUS.ACTIVE;
    }
}
