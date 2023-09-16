package rgo.tt.user.persistence.storage.entity;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

public class Client implements Serializable {

    @Serial private static final long serialVersionUID = 1L;

    private final Long entityId;
    private final String email;
    private final String password;
    private final LocalDateTime createdDate;
    private final LocalDateTime lastModifiedDate;

    private Client(Builder builder) {
        entityId = builder.entityId;
        email = builder.email;
        password = builder.password;
        createdDate = builder.createdDate;
        lastModifiedDate = builder.lastModifiedDate;
    }

    public Long getEntityId() {
        return entityId;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public LocalDateTime getLastModifiedDate() {
        return lastModifiedDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return Objects.equals(entityId, client.entityId)
                && Objects.equals(email, client.email)
                && Objects.equals(password, client.password)
                && Objects.equals(createdDate, client.createdDate)
                && Objects.equals(lastModifiedDate, client.lastModifiedDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(entityId,
                email,
                password,
                createdDate,
                lastModifiedDate);
    }

    @Override
    public String toString() {
        return "Client{" +
                "entityId=" + entityId +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", createdDate=" + createdDate +
                ", lastModifiedDate=" + lastModifiedDate +
                '}';
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private Long entityId;
        private String email;
        private String password;
        private LocalDateTime createdDate;
        private LocalDateTime lastModifiedDate;

        public Builder setEntityId(Long entityId) {
            this.entityId = entityId;
            return this;
        }

        public Builder setEmail(String email) {
            this.email = email;
            return this;
        }

        public Builder setPassword(String password) {
            this.password = password;
            return this;
        }

        public Builder setCreatedDate(LocalDateTime createdDate) {
            this.createdDate = createdDate;
            return this;
        }

        public Builder setLastModifiedDate(LocalDateTime lastModifiedDate) {
            this.lastModifiedDate = lastModifiedDate;
            return this;
        }

        public Client build() {
            return new Client(this);
        }
    }
}
