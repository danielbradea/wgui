package com.bid90.wgui.services;

import com.bid90.wgui.models.Server;
import com.bid90.wgui.models.dto.ServerDTO;

public interface ServerService {
    Server getServer();
    Server save(Server server);
    ServerDTO toDTO(Server server);
    Server update(ServerDTO serverDTO);
}
