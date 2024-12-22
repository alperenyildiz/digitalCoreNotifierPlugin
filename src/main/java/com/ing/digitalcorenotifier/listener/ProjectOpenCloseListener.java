package com.ing.digitalcorenotifier.listener;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;
import com.intellij.openapi.startup.ProjectActivity;
import com.ing.digitalcorenotifier.service.notification.TipScheduler;
import com.ing.digitalcorenotifier.service.settings.TipSettings;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


/**
 * Bu sınıf, bir proje açıldığında otomatik olarak çalışan bir startup activity'dir.
 * plugin.xml'de postStartupActivity olarak tanımlanmıştır.
 */
public class ProjectOpenCloseListener implements ProjectActivity {

    /**
     * ProjectActivity interface'inden gelen bu metod, proje açıldığında otomatik olarak çalışır.
     *
     * @param project Açılan projenin referansı
     * @param continuation Kotlin coroutine desteği için gerekli (IntelliJ API gereksinimi)
     * @return Eğer asenkron bir işlem varsa sonucu, yoksa null
     */
    @Nullable
    @Override
    public Object execute(@NotNull Project project, @NotNull Continuation<? super Unit> continuation) {
        System.out.println("ProjectOpenCloseListener execute başladı");

        // Ayarlardan otomatik başlatma aktif mi kontrol et
        if (TipSettings.getInstance().startOnProjectOpen) {
            System.out.println("TipSettings: Otomatik başlatma aktif");

            // Proje servislerinden TipScheduler'ı al
            TipScheduler scheduler = project.getService(TipScheduler.class);

            // Scheduler'ı başlat (bildirim zamanlayıcısını başlatır)
            scheduler.start();
            System.out.println("Scheduler başlatıldı");

        } else {
            System.out.println("TipSettings: Otomatik başlatma devre dışı");
        }

        // Proje kapanma olayını dinlemek için listener ekle
        project.getMessageBus().connect().subscribe(ProjectManager.TOPIC, new com.intellij.openapi.project.ProjectManagerListener() {
            @Override
            public void projectClosing(@NotNull Project closingProject) {
                // Kapanan proje bizim projemiz mi kontrol et
                if (project.equals(closingProject)) {
                    // Scheduler'ı durdur
                    System.out.println("Proje kapanıyor, scheduler durduruluyor");
                    project.getService(TipScheduler.class).stop();
                }
            }
        });
        return null;
    }
}