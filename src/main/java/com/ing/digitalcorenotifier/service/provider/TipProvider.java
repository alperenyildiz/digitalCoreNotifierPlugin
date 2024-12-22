package com.ing.digitalcorenotifier.service.provider;


import com.ing.digitalcorenotifier.model.Tip;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

/**
 * Farklı kaynaklardan (Static, API, AI) ipucu sağlamak için kullanılır
 */
public interface TipProvider {
    /**
     * Bir sonraki ipucunu getirir
     * Her provider kendi mantığına göre implement eder
     *
     * @return Optional<Tip> - İpucu yoksa boş Optional döner
     */
    @NotNull
    Optional<Tip> getNextTip();

    /**
     * Provider'ın hazır olup olmadığını kontrol eder
     * Örneğin: API provider için bağlantı kontrolü
     *
     * @return boolean - Provider kullanıma hazırsa true
     */
    boolean isReady();
}