package com.bid90.wgui.repositorys;


import com.bid90.wgui.models.Settings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SettingsRepository extends JpaRepository<Settings, UUID> {

}
