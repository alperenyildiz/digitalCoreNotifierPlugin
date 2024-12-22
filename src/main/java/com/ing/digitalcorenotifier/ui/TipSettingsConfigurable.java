package com.ing.digitalcorenotifier.ui;

import com.ing.digitalcorenotifier.service.settings.TipSettings;
import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;
import com.intellij.ui.components.JBLabel;
import com.intellij.util.ui.FormBuilder;
import com.ing.digitalcorenotifier.service.notification.TipScheduler;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

/**
 * IntelliJ ayarlar penceresindeki konfigürasyon sayfasını oluşturur
 * Settings > Tools > Digital Core Notifier Settings
 * Bu sınıf, plugin.xml dosyasında tanımlanmıştır.
 * UI settings dışında farklı yerlerde görünecekse implementasyonu değişir.
 */
public class TipSettingsConfigurable implements Configurable {
    private JPanel mainPanel;
    private JSpinner intervalSpinner;
    private JCheckBox enabledCheckBox;
    private JCheckBox autoStartCheckBox;


    /**
     * Settings menüsünde görünecek başlık
     */
    @Nls(capitalization = Nls.Capitalization.Title)
    @Override
    public String getDisplayName() {
        return "Digital Core Notifier";
    }


    /**
     * Ayarlar sayfasının UI'ını oluşturur
     * Settings açıldığında IntelliJ tarafından çağrılır
     */
    @Override
    public @Nullable JComponent createComponent() {

        // Dakika seçimi için spinner (1-1440 arası, 5'er artarak)
        SpinnerNumberModel spinnerModel = new SpinnerNumberModel(60, 1, 1440, 5);
        intervalSpinner = new JSpinner(spinnerModel);

        enabledCheckBox = new JCheckBox("Bildirimleri Etkinleştir");
        autoStartCheckBox = new JCheckBox("Proje Açıldığında Otomatik Başlat");

        FormBuilder formBuilder = FormBuilder.createFormBuilder()
                .addLabeledComponent(new JBLabel("Bildirim Aralığı (dakika): "), intervalSpinner)
                .addVerticalGap(10)
                .addComponent(enabledCheckBox)
                .addVerticalGap(5)
                .addComponent(autoStartCheckBox);

        mainPanel = formBuilder.addVerticalGap(10).getPanel();
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Mevcut ayarları yükle
        loadSettings();

        return mainPanel;
    }


    /**
     * Ayarlar değiştirildi mi kontrolü
     * Apply butonu bu metoda göre aktif/pasif olur
     */
    @Override
    public boolean isModified() {
        TipSettings settings = TipSettings.getInstance();
        return settings.notificationIntervalMinutes != (Integer) intervalSpinner.getValue() ||
                settings.notificationsEnabled != enabledCheckBox.isSelected() ||
                settings.startOnProjectOpen != autoStartCheckBox.isSelected();
    }

    /**
     * Değişiklikleri kaydet
     * Apply butonuna basıldığında çağrılır
     */
    @Override
    public void apply() {
        TipSettings settings = TipSettings.getInstance();
        boolean wasEnabled = settings.notificationsEnabled;

        // Ayarları güncelle
        settings.notificationIntervalMinutes = (Integer) intervalSpinner.getValue();
        settings.notificationsEnabled = enabledCheckBox.isSelected();
        settings.startOnProjectOpen = autoStartCheckBox.isSelected();

        System.out.println("Ayarlar güncellendi. Bildirimler aktif: " + settings.notificationsEnabled);

        // Açık olan tüm projeler için scheduler'ı güncelle
        for (Project project : ProjectManager.getInstance().getOpenProjects()) {
            TipScheduler scheduler = project.getService(TipScheduler.class);
            if (settings.notificationsEnabled) {
                if (!wasEnabled) {
                    System.out.println("Scheduler yeniden başlatılıyor");
                    scheduler.start();
                } else {
                    System.out.println("Scheduler interval değişikliği için yeniden başlatılıyor");
                    scheduler.stop();
                    scheduler.start();
                }
            } else {
                System.out.println("Scheduler durduruluyor");
                scheduler.stop();
            }
        }
    }

    /**
     * Değişiklikleri iptal et
     * Reset butonuna basıldığında çağrılır
     */
    @Override
    public void reset() {
        loadSettings();
    }

    /**
     * Mevcut ayarları UI'a yükle
     */
    private void loadSettings() {
        TipSettings settings = TipSettings.getInstance();
        intervalSpinner.setValue(settings.notificationIntervalMinutes);
        enabledCheckBox.setSelected(settings.notificationsEnabled);
        autoStartCheckBox.setSelected(settings.startOnProjectOpen);
    }

    /**
     * UI kaynaklarını temizle
     */
    @Override
    public void disposeUIResources() {
        mainPanel = null;
        intervalSpinner = null;
        enabledCheckBox = null;
        autoStartCheckBox = null;
    }
}