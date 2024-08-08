package com.example.demo.video;

import java.util.UUID;

import com.example.demo.constants.DatabaseConstants;
import com.example.demo.user.User;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = DatabaseConstants.VIDEO)
public class Video {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String title;
    private String path;

    // relations
    @ManyToOne(optional = false)
    @JoinColumn(name = "userId")
    private User uploader;

}
