package com.dgd.model.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne(fetch = FetchType.LAZY)
    private SharingApplication sharingApplication;
    private String takerId;
    private String offerId;
    private boolean isOpened;

    public void roomOpen() {
        this.isOpened = true;
    }
}
