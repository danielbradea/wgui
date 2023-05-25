package com.bid90.wgui.repositorys;

import com.bid90.wgui.models.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ClientRepository extends JpaRepository<Client, UUID> {
    @Query("SELECT c FROM Client c WHERE c.user.id = ?1")
    List<Client> findClientsByUserId(UUID userId);

    @Query("SELECT c FROM Client c WHERE c.id = ?1 AND c.user.id = ?2")
    Optional<Client> getClientsByIdAndUserId(UUID id, UUID clientId);
}
