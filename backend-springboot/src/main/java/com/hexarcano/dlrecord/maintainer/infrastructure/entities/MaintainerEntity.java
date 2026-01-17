package com.hexarcano.dlrecord.maintainer.infrastructure.entities;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.hexarcano.dlrecord.maintainer.model.Maintainer;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "maintainer")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class MaintainerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String uuid;

    private String username;

    private String email;

    private String password;

    private Boolean isAdmin = false;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    public MaintainerEntity(String uuid, String username, String email, String password, Boolean isAdmin) {
        this.uuid = uuid;
        this.username = username;
        this.email = email;
        this.password = password;
        this.isAdmin = isAdmin;
    }

    public static MaintainerEntity fromDomainModel(Maintainer maintainer) {
        return new MaintainerEntity(
                maintainer.getUuid(),
                maintainer.getUsername(),
                maintainer.getEmail(),
                maintainer.getPassword(),
                maintainer.getIsAdmin());
    }

    public Maintainer toDomainModel() {
        return new Maintainer(uuid, username, email, password, isAdmin);
    }

}
