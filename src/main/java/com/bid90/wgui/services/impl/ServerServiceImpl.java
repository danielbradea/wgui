package com.bid90.wgui.services.impl;

import com.bid90.wgui.models.KeyPair;
import com.bid90.wgui.models.Server;
import com.bid90.wgui.models.dto.ServerDTO;
import com.bid90.wgui.repositorys.ServerRepository;
import com.bid90.wgui.services.ServerService;
import com.bid90.wgui.utils.KeyGen;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

/**
 * Service implementation for managing the Wireguard server.
 */
@Service
@Transactional
public class ServerServiceImpl implements ServerService {

    private final ServerRepository serverRepository;
    private final ModelMapper modelMapper;
    private final KeyPairServiceImpl keyPairService;

    public static final UUID SERVER_ID = UUID.fromString("00000000-0000-0000-0000-000000000000");

    private String address;
    private Integer listenPort;
    private String postUp;
    private String postDown;
    private String preUp;
    private String preDown;

    /**
     * Constructs a new instance of ServerServiceImpl.
     *
     * @param serverRepository The server repository.
     * @param modelMapper      The model mapper.
     * @param keyPairService   The key pair service.
     * @param address          The Wireguard server address.
     * @param listenPort       The Wireguard server listen port.
     * @param postUp           The Wireguard server post-up command.
     * @param postDown         The Wireguard server post-down command.
     * @param preUp            The Wireguard server pre-up command.
     * @param preDown          The Wireguard server pre-down command.
     */
    @Autowired
    public ServerServiceImpl(ServerRepository serverRepository,
                             ModelMapper modelMapper,
                             KeyPairServiceImpl keyPairService,
                             @Value("${wgc.address}") String address,
                             @Value("${wgc.listenPort}") Integer listenPort,
                             @Value("${wgc.postUp}") String postUp,
                             @Value("${wgc.postDown}") String postDown,
                             @Value("${wgc.preUp}") String preUp,
                             @Value("${wgc.preDown}") String preDown) {
        this.serverRepository = serverRepository;
        this.modelMapper = modelMapper;
        this.keyPairService = keyPairService;
        this.address = address;
        this.listenPort = listenPort;
        this.postUp = postUp;
        this.postDown = postDown;
        this.preUp = preUp;
        this.preDown = preDown;
    }

    /**
     * Retrieves the Wireguard server.
     * If the server is not found, a new Server instance is created and saved with default values.
     *
     * @return The Wireguard server.
     */
    @Override
    public Server getServer() {
        var server = serverRepository.getServerById(SERVER_ID);
        if (server.isPresent()) {
            return server.get();
        } else {
            var newServer = new Server();
            var keyPair = new KeyPair();
            var keys = KeyGen.generate();
            keyPair.setPublicKey(keys.getPublicKey());
            keyPair.setPrivateKey(keys.getPrivateKey());
            newServer.setKeyPair(keyPairService.save(keyPair));
            newServer.setId(SERVER_ID);
            newServer.setAddress(address);
            newServer.setListenPort(listenPort);
            newServer.setPostUp(postUp);
            newServer.setPostDown(postDown);
            newServer.setPreUp(preUp);
            newServer.setPreDown(preDown);
            return serverRepository.save(newServer);
        }
    }

    /**
     * Saves the Wireguard server.
     *
     * @param server The Wireguard server to save.
     * @return The saved Wireguard server.
     */
    @Override
    public Server save(Server server) {
        server.setId(SERVER_ID);
        return serverRepository.save(server);
    }

    /**
     * Converts a Server entity to a ServerDTO.
     *
     * @param server The Server entity to convert.
     * @return The converted ServerDTO.
     */
    @Override
    public ServerDTO toDTO(Server server) {
        return modelMapper.map(server, ServerDTO.class);
    }

    /**
     * Updates the Wireguard server with the provided values.
     *
     * @param serverDTO The DTO containing the Wireguard server values to update.
     * @return The updated Wireguard server.
     */
    @Override
    public Server update(ServerDTO serverDTO) {
        Server server = getServer();
        Optional.ofNullable(serverDTO.getAddress()).ifPresent(server::setAddress);
        Optional.ofNullable(serverDTO.getListenPort()).ifPresent(server::setListenPort);
        Optional.ofNullable(serverDTO.getPreUp()).ifPresent(server::setPreUp);
        Optional.ofNullable(serverDTO.getPostUp()).ifPresent(server::setPostUp);
        Optional.ofNullable(serverDTO.getPreDown()).ifPresent(server::setPreDown);
        Optional.ofNullable(serverDTO.getPostDown()).ifPresent(server::setPostDown);
        return serverRepository.save(server);
    }
}
