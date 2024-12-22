package com.ing.digitalcorenotifier.service.notification;

import com.intellij.notification.*;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.Service;
import com.intellij.openapi.project.Project;
import com.ing.digitalcorenotifier.model.Tip;
import com.ing.digitalcorenotifier.service.TipService;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Bu sınıf IntelliJ'nin bildirim sistemiyle entegrasyonu sağlar.
 * Uygulama seviyesinde çalışır (tek instance - APPLICATION level service)
 */
@Service(Service.Level.APP)
public final class TipNotifier {

    public TipNotifier() {
        System.out.println("TipNotifier servisi oluşturuldu");
    }


    /**
     * Yeni bir ipucu bildirimi gösterir
     * TipScheduler'ın timer'ı tarafından periyodik olarak çağrılır
     *
     * @param project Bildirimin hangi projeye ait olduğu
     */
    public void showTip(@Nullable Project project) {
        // TipService'den bir sonraki ipucunu al
        TipService tipService = ApplicationManager.getApplication().getService(TipService.class);
        tipService.getNextTip().ifPresent(tip -> {
            Notification notification = createNotification(tip);
            notification.notify(project);
        });
    }

    /**
     * Bildirimi oluşturur ve aksiyonları (beğen/beğenme) ekler
     *
     * @param tip Gösterilecek ipucu
     * @return Hazırlanmış bildirim nesnesi
     */
    @NotNull
    private Notification createNotification(Tip tip) {
        // TipService'i al (feedback için kullanılacak)
        TipService tipService = ApplicationManager.getApplication().getService(TipService.class);

        // Bildirim grubunu al
        NotificationGroup group = NotificationGroupManager.getInstance()
                .getNotificationGroup("DigitalCoreNotifier");

        // Bildirimi oluştur
        Notification notification = group.createNotification(
                tip.getTitle(),
                tip.getContent(),
                NotificationType.INFORMATION
        );

        // Beğeni/beğenmeme aksiyonları
        notification.addAction(new NotificationAction("👍 Beğendim") {
            @Override
            public void actionPerformed(@NotNull AnActionEvent e, @NotNull Notification notification) {
                tipService.recordFeedback(tip.getId(), true);
                notification.expire();
            }
        });

        notification.addAction(new NotificationAction("👎 Beğenmedim") {
            @Override
            public void actionPerformed(@NotNull AnActionEvent e, @NotNull Notification notification) {
                tipService.recordFeedback(tip.getId(), false);
                notification.expire();
            }
        });

        return notification;
    }
}