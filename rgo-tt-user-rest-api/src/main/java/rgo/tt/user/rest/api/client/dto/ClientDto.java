package rgo.tt.user.rest.api.client.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

public class ClientDto implements Serializable {

    @Serial private static final long serialVersionUID = 1L;

    private Long entityId;
    private String email;
    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;

    public Long getEntityId() {
        return entityId;
    }

    public void setEntityId(Long entityId) {
        this.entityId = entityId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSSSS")
    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSSSS")
    public LocalDateTime getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(LocalDateTime lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClientDto clientDto = (ClientDto) o;
        return Objects.equals(entityId, clientDto.entityId)
                && Objects.equals(email, clientDto.email)
                && Objects.equals(createdDate, clientDto.createdDate)
                && Objects.equals(lastModifiedDate, clientDto.lastModifiedDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(entityId,
                email,
                createdDate,
                lastModifiedDate);
    }

    @Override
    public String toString() {
        return "ClientDto{" +
                "entityId=" + entityId +
                ", email='" + email + '\'' +
                ", createdDate=" + createdDate +
                ", lastModifiedDate=" + lastModifiedDate +
                '}';
    }
}
