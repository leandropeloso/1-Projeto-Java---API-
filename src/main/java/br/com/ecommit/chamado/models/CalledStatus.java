package br.com.ecommit.chamado.models;

import jakarta.persistence.*;
import org.hibernate.annotations.Type;

import java.util.UUID;

@Entity
@Table(name = "TB_CALLED_STATUS")
public class CalledStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID idCalled;

    @Column(name = "status_description", nullable = false, unique = true)
    private String statusDescription; //(open, in progress, closed)

    public CalledStatus() {
    }
    public CalledStatus(String statusDescription) {
        this.statusDescription = statusDescription;
    }

    public String getStatusDescription() {
        return statusDescription;
    }

    public void setStatusDescription(String statusDescription) {
        this.statusDescription = statusDescription;
    }
}
