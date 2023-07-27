package br.com.ecommit.chamado.repositories;

import br.com.ecommit.chamado.models.CalledModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CalledRepository extends JpaRepository<CalledModel, UUID> {
    @Query("SELECT c FROM CalledModel c WHERE c.status.statusDescription = 'Open'")
    List<CalledModel> findOpenCalled();

    @Query("SELECT c FROM CalledModel c WHERE c.status.statusDescription = 'In progress'")
    List<CalledModel> findInProgressCalled();

    @Query("SELECT c FROM CalledModel c WHERE c.status.statusDescription = 'Closed'")
    List<CalledModel> findClosedCalled();
}

