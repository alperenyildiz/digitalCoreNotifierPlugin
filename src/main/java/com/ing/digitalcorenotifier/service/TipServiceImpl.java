package com.ing.digitalcorenotifier.service;

import com.intellij.openapi.components.Service;
import com.ing.digitalcorenotifier.model.Tip;
import com.ing.digitalcorenotifier.service.provider.StaticTipProvider;
import com.ing.digitalcorenotifier.service.provider.TipProvider;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

/**
 * TipService'in temel implementasyonu
 * APPLICATION seviyesinde çalışır (tüm projeler için tek instance)
 */
@Service(Service.Level.APP)
public final class TipServiceImpl implements TipService {

    /**
     *  İpucu sağlayıcı - şu an sadece StaticTipProvider kullanıyoruz
     */
    private final TipProvider provider;

    /**
     * Constructor
     * Plugin.xml'de service olarak tanımlandığı için IntelliJ tarafından çağrılır
     */
    public TipServiceImpl() {
        //Şimdilik sadece StaticTipProvider kullanıyoruz
        // İleride API ve AI provider'lar da eklenecek
        this.provider = new StaticTipProvider();
    }

    /**
     * Bir sonraki ipucunu getirir
     * Provider'dan alınan ipucunu cache'e ekler
     */
    @NotNull
    @Override
    public Optional<Tip> getNextTip() {
        return provider.getNextTip();
    }

    /**
     * Kullanıcı geri bildirimini kaydeder
     * Şu an sadece log'a yazıyor, ileride veritabanına kaydedilecek
     */
    @Override
    public void recordFeedback(@NotNull String tipId, boolean liked) {
        if (liked) {
            System.out.println("İpucu beğenildi: " + tipId);
        } else {
            System.out.println("İpucu beğenilmedi: " + tipId);
        }
    }
}

