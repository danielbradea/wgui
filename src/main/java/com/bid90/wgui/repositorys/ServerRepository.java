package com.bid90.wgui.repositorys;

import com.bid90.wgui.models.Server;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ServerRepository extends JpaRepository<Server, UUID> {
    @Query("SELECT s FROM Server s WHERE s.id = ?1")
    Optional<Server> getServerById(UUID serverId);
}
