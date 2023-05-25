package com.bid90.wgui.services;

import com.bid90.wgui.models.Client;
import com.bid90.wgui.models.dto.ClientDTO;
import com.bid90.wgui.models.dto.UpdateClientDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ClientService {
    Client save(Client client);
    List<Client> getAllClients();
    ClientDTO toDTO(Client client);
    List<Client> getClientsByUserId(UUID userId);
    long clientsCount();
    Optional<Client> getClientsByIdAndUserId(UUID clientId, UUID id);
    Optional<Client> getClientsById(UUID clientId);
    Client updateClient(Client client, UpdateClientDTO updateClientDTO);
    void delete(List<Client> clients);
    void delete(Client client);
}
