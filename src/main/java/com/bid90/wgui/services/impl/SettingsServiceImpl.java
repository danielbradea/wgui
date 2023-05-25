package com.bid90.wgui.services.impl;

import com.bid90.wgui.models.Settings;
import com.bid90.wgui.models.dto.WireguardSettingsDTO;
import com.bid90.wgui.repositorys.SettingsRepository;
import com.bid90.wgui.services.SettingsService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

/**
 * Service implementation for managing Wireguard settings.
 */
@Service
@Transactional
public class SettingsServiceImpl implements SettingsService {

    private SettingsRepository settingsRepository;
    private String endpointAddress;
    private String dnsServers;
    private Integer mtu;
    private Integer persistentKeepAlive;
    private String fwMark;

    /**
     * Constructs a new instance of SettingsServiceImpl.
     *
     * @param settingsRepository      The settings repository.
     * @param endpointAddress         The Wireguard endpoint address.
     * @param dnsServers              The Wireguard DNS servers.
     * @param mtu                     The Wireguard MTU (Maximum Transmission Unit).
     * @param persistentKeepAlive     The Wireguard persistent keep-alive interval.
     * @param fwMark                  The Wireguard firewall mark.
     */
    @Autowired
    public SettingsServiceImpl(SettingsRepository settingsRepository,
                               @Value("${wgc.endpointAddress}") String endpointAddress,
                               @Value("${wgc.dnsServers}") String dnsServers,
                               @Value("${wgc.mtu}") Integer mtu,
                               @Value("${wgc.persistentKeepAlive}") Integer persistentKeepAlive,
                               @Value("${wgc.fwMark}") String fwMark) {
        this.settingsRepository = settingsRepository;
        this.endpointAddress = endpointAddress;
        this.dnsServers = dnsServers;
        this.mtu = mtu;
        this.persistentKeepAlive = persistentKeepAlive;
        this.fwMark = fwMark;
    }

    public static final UUID SETTINGS_ID = UUID.fromString("11111111-1111-1111-1111-111111111111");

    /**
     * Retrieves the Wireguard settings.
     * If the settings are not found, a new Settings instance is created and saved with default values.
     *
     * @return The Wireguard settings.
     */
    @Override
    public Settings get() {
        var settings = settingsRepository.findById(SETTINGS_ID);
        if (settings.isPresent()) {
            return settings.get();
        } else {
            var newSettings = new Settings();
            newSettings.setId(SETTINGS_ID);
            newSettings.setEndpointAddress(endpointAddress);
            newSettings.setDnsServers(dnsServers);
            newSettings.setMtu(mtu);
            newSettings.setPersistentKeepAlive(persistentKeepAlive);
            newSettings.setFwMark(fwMark);
            return settingsRepository.save(newSettings);
        }
    }

    /**
     * Updates the Wireguard settings with the provided values.
     *
     * @param wireguardSettingsDTO The DTO containing the Wireguard settings to update.
     * @return The updated Wireguard settings.
     */
    @Override
    public Settings update(WireguardSettingsDTO wireguardSettingsDTO) {
        var settings = get();
        Optional.ofNullable(wireguardSettingsDTO.getEndpointAddress()).ifPresent(settings::setEndpointAddress);
        Optional.ofNullable(wireguardSettingsDTO.getDnsServers()).ifPresent(settings::setDnsServers);
        Optional.ofNullable(wireguardSettingsDTO.getMtu()).ifPresent(settings::setMtu);
        Optional.ofNullable(wireguardSettingsDTO.getPersistentKeepAlive()).ifPresent(settings::setPersistentKeepAlive);
        Optional.ofNullable(wireguardSettingsDTO.getFwMark()).ifPresent(settings::setFwMark);
        return settingsRepository.save(settings);
    }
}