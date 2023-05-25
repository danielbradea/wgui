package com.bid90.wgui.services.impl;

import com.bid90.wgui.models.Client;
import com.bid90.wgui.models.dto.ClientDTO;
import com.bid90.wgui.models.dto.UpdateClientDTO;
import com.bid90.wgui.repositorys.ClientRepository;
import com.bid90.wgui.services.ClientService;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Service implementation for managing clients.
 */
@Service
@Transactional
public class ClientServiceImpl implements ClientService {

    ClientRepository clientRepository;
    ModelMapper modelMapper;

    /**
     * Constructs a new {@link ClientServiceImpl} with the specified dependencies.
     *
     * @param clientRepository The client repository.
     * @param modelMapper      The model mapper.
     */
    @Autowired
    public ClientServiceImpl(ClientRepository clientRepository, ModelMapper modelMapper) {
        this.clientRepository = clientRepository;
        this.modelMapper = modelMapper;
    }

    /**
     * Saves a client.
     *
     * @param client The client to save.
     * @return The saved client.
     */
    @Override
    public Client save(Client client) {
        return clientRepository.save(client);
    }

    /**
     * Retrieves all clients.
     *
     * @return A list of all clients.
     */
    @Override
    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }

    /**
     * Converts a client to a DTO (Data Transfer Object).
     *
     * @param client The client to convert.
     * @return The converted DTO.
     */
    @Override
    public ClientDTO toDTO(Client client) {
        return modelMapper.map(client, ClientDTO.class);
    }

    /**
     * Retrieves all clients associated with a user ID.
     *
     * @param userId The ID of the user.
     * @return A list of clients associated with the user ID.
     */
    @Override
    public List<Client> getClientsByUserId(UUID userId) {
        return clientRepository.findClientsByUserId(userId);
    }

    /**
     * Retrieves the count of all clients.
     *
     * @return The count of clients.
     */
    @Override
    public long clientsCount() {
        return clientRepository.count();
    }

    /**
     * Retrieves a client by its ID and user ID.
     *
     * @param id       The ID of the user.
     * @param clientId The ID of the client.
     * @return An optional containing the client, or an empty optional if not found.
     */
    @Override
    public Optional<Client> getClientsByIdAndUserId(UUID id, UUID clientId) {
        return clientRepository.getClientsByIdAndUserId(id, clientId);
    }

    /**
     * Retrieves a client by its ID.
     *
     * @param clientId The ID of the client.
     * @return An optional containing the client, or an empty optional if not found.
     */
    @Override
    public Optional<Client> getClientsById(UUID clientId) {
        return Optional.of(clientRepository.getReferenceById(clientId));
    }

    /**
     * Updates a client with the specified changes.
     *
     * @param client          The client to update.
     * @param updateClientDTO The DTO containing the update information.
     * @return The updated client.
     */
    @Override
    public Client updateClient(Client client, UpdateClientDTO updateClientDTO) {
        Optional.ofNullable(updateClientDTO.getName()).filter(s -> !s.isBlank()).ifPresent(client::setName);
        Optional.ofNullable(updateClientDTO.getAllowedIPs()).filter(s -> !s.isBlank()).ifPresent(client::setAllowedIPs);
        Optional.ofNullable(updateClientDTO.getEnabled()).ifPresent(client::setEnabled);
        return clientRepository.save(client);
    }

    /**
     * Deletes a list of clients.
     *
     * @param clients The list of clients to delete.
     */
    @Override
    public void delete(List<Client> clients) {
        clientRepository.deleteAll(clients);
    }

    /**
     * Deletes a client.
     *
     * @param client The client to delete.
     */
    @Override
    public void delete(Client client) {
        clientRepository.delete(client);
    }
}
