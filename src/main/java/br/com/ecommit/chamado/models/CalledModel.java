package br.com.ecommit.chamado.models;

import br.com.ecommit.chamado.utils.CalledStatusSteady;
import jakarta.persistence.*;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "TB_CALLED")
public class CalledModel extends RepresentationModel<CalledModel> implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private UUID idCalled;
    @Column(name = "name")
    private String name;
    @Column(name = "cause")
    private String cause;
    @Column(name = "description")
    private String description;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "status_Type", referencedColumnName = "idCalled")
    private CalledStatus status; //(open, in progress, closed)

    public CalledModel() {
    }

    public UUID getIdCalled() {
        return idCalled;
    }

    public void setIdCalled(UUID idCalled) {
        this.idCalled = idCalled;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCause() {
        return cause;
    }

    public void setCause(String cause) {
        this.cause = cause;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public CalledStatus getStatus() {
        return status;
    }

    public void setStatus(String status) {
        if (status != null) {
            if (status.equals(CalledStatusSteady.OPEN)) {
                this.status = new CalledStatus(CalledStatusSteady.OPEN);
            } else if (status.equals(CalledStatusSteady.IN_PROGRESS)) {
                this.status = new CalledStatus(CalledStatusSteady.IN_PROGRESS);
            } else if (status.equals(CalledStatusSteady.CLOSED)) {
                this.status = new CalledStatus(CalledStatusSteady.CLOSED);
            } else {
                throw new IllegalArgumentException("Invalid status description.");
            }
            this.status.setStatusDescription(status);
        }
    }
}