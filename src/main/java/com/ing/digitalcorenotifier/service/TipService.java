package com.ing.digitalcorenotifier.service;


import com.ing.digitalcorenotifier.model.Tip;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;


/**
 * İpucu yönetiminin ana arayüzü.
 * TipServiceImpl tarafından implement edilir ve plugin.xml'de service olarak tanımlanır.
 */
public interface TipService {

    /**
     * Bir sonraki ipucunu getirir
     * TipNotifier tarafından çağrılır
     *
     * @return Optional<Tip> - İpucu yoksa boş Optional döner
     */
    @NotNull
    Optional<Tip> getNextTip();

    /**
     * Kullanıcı geri bildirimini kaydeder
     * Bildirim aksiyonları tarafından çağrılır
     *
     * @param tipId İpucu ID'si
     * @param liked true=beğenildi, false=beğenilmedi
     */
    void recordFeedback(@NotNull String tipId, boolean liked);
}