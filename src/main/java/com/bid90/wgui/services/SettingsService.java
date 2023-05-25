package com.bid90.wgui.services;

import com.bid90.wgui.models.Settings;
import com.bid90.wgui.models.dto.WireguardSettingsDTO;

public interface SettingsService {
    Settings get();
    Settings update(WireguardSettingsDTO wireguardSettingsDTO);
}
