package com.ing.digitalcorenotifier.service.notification;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.Service;
import com.intellij.openapi.project.Project;
import com.ing.digitalcorenotifier.service.settings.TipSettings;
import org.jetbrains.annotations.NotNull;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Bu sınıf periyodik bildirimlerin zamanlamasını yöneten servistir.
 * Her proje için ayrı bir instance oluşturulur (PROJECT level service)
 */
@Service(Service.Level.PROJECT)
public final class TipScheduler {

    // Timer nesnesi - Java'nın temel zamanlayıcı sınıfı
    private Timer timer;

    // Projenin referansı - bildirimler için gerekli
    private final Project project;

    /**
     * Constructor - sadece Project parametresi alabilir (Service gereksinimleri)
     * ProjectOpenCloseListener tarafından project.getService() ile çağrılır
     */
    public TipScheduler(@NotNull Project project) {
        this.project = project;
        System.out.println("TipScheduler oluşturuldu");
    }

    /**
     * Bildirim zamanlayıcısını başlatır
     * ProjectOpenCloseListener'dan veya Settings değişikliklerinde çağrılır
     */
    public void start() {
        System.out.println("TipScheduler.start() çağrıldı");
        // Varsa mevcut timer'ı durdur
        stop();

        // Ayarlardan bildirimlerin aktif olup olmadığını kontrol et
        if (!TipSettings.getInstance().notificationsEnabled) {
            System.out.println("Bildirimler devre dışı, scheduler başlatılmadı");
            return;
        }

        // Ayarlardan bildirim aralığını al
        int interval = TipSettings.getInstance().notificationIntervalMinutes;
        System.out.println("Bildirim aralığı: " + interval + " dakika");

        // Yeni bir timer oluştur
        timer = new Timer("DigitalCoreNotifier", true);
        // Periyodik görevi planla
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                System.out.println("Timer tetiklendi, bildirim gönderiliyor");
                // TipNotifier servisini al ve bildirimi göster
                TipNotifier tipNotifier = ApplicationManager.getApplication().getService(TipNotifier.class);
                tipNotifier.showTip(project);
            }
        }, 0, interval * 60 * 1000); // İlk çalışma gecikmesi (0 = hemen başla)

        System.out.println("Timer başlatıldı");
    }

    /**
     * Timer'ı durdurur
     * Settings değişikliklerinde veya proje kapatılırken çağrılır
     */
    public void stop() {
        if (timer != null) {
            System.out.println("Timer durduruluyor");
            timer.cancel();
            timer = null;
        }
    }
}