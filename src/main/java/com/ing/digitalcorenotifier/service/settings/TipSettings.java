package com.ing.digitalcorenotifier.service.settings;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.Service;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.util.xmlb.XmlSerializerUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Plugin ayarlarını saklayan ve yöneten sınıf.
 * Bu sınıf IDE yeniden başlatıldığında bile ayarların kalıcı olmasını sağlar.
 */
@State(
        name = "DigitalCoreNotifierSettings",
        storages = {@Storage("DigitalCoreNotifierSettings.xml")}
)
@Service(Service.Level.APP)
public final class TipSettings implements PersistentStateComponent<TipSettings> {

    // Bildirim aralığı (dakika cinsinden)
    public int notificationIntervalMinutes = 1;

    // Bildirimlerin aktif olup olmadığı
    public boolean notificationsEnabled = true;

    // Proje açıldığında otomatik başlatma
    public boolean startOnProjectOpen = true;

    // Singleton instance için static method
    public static TipSettings getInstance() {
        return ApplicationManager.getApplication().getService(TipSettings.class);
    }

    @Override
    public @Nullable TipSettings getState() {
        return this;
    }

    @Override
    public void loadState(@NotNull TipSettings state) {
        XmlSerializerUtil.copyBean(state, this);
    }
}