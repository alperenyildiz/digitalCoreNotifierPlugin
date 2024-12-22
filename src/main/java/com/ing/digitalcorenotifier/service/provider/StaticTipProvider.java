package com.ing.digitalcorenotifier.service.provider;


import com.ing.digitalcorenotifier.model.Tip;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

/**
 * Sabit, önceden tanımlanmış ipuçlarını sağlayan provider. Test amaçlı kullanılır.
 */
public class StaticTipProvider implements TipProvider {
    private final List<Tip> tips;
    private final Random random;

    public StaticTipProvider() {
        this.tips = initializeTips();
        this.random = new Random();
    }

    @NotNull
    @Override
    public Optional<Tip> getNextTip() {
        if (tips.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(tips.get(random.nextInt(tips.size())));
    }

    @Override
    public boolean isReady() {
        return !tips.isEmpty();
    }

    private List<Tip> initializeTips() {
        List<Tip> staticTips = new ArrayList<>();

        staticTips.add(new Tip(
                "Optional Best Practice",
                "Prefer Optional.ofNullable() over Optional.of() when the value might be null",
                "AI"
        ));

        staticTips.add(new Tip(
                "Java Records",
                "Use records for immutable data carriers instead of writing boilerplate getter/setter classes",
                "DATABASE"
        ));

        staticTips.add(new Tip(
                "Spring Security - BCrypt",
                "Şifre hashleme için BCryptPasswordEncoder kullanırken strength parametresini projemizin ihtiyacına göre ayarlayabiliriz. Varsayılan değer olan 10, modern sistemler için genellikle yeterlidir. Her +1 artış, hash süresini ikiye katlar.",
                "SYSTEM"
        ));

        staticTips.add(new Tip(
                "Java Optional - OrElseThrow",
                "Optional.orElse() yerine Optional.orElseThrow() kullanmak daha güvenlidir. orElseThrow ile özel exception fırlatabilir ve hata durumlarını daha iyi yönetebilirsiniz: optional.orElseThrow(() -> new EntityNotFoundException(\"Kayıt bulunamadı\"))",
                "SYSTEM"
        ));

        staticTips.add(new Tip(
                "JDK 22 Yenilikleri - String Templates",
                "Java 22 (Mart 2024) ile gelecek String Templates özelliği sayesinde: str`Merhaba ${user.name}, bakiyeniz: ${account.balance}` gibi template literal'lar kullanabileceğiz. Bu özellik şu an preview aşamasında.",
                "SYSTEM"
        ));

        staticTips.add(new Tip(
                "G1GC vs ZGC Karşılaştırması",
                "ZGC (Z Garbage Collector), özellikle büyük heap size (>32GB) ve düşük latency gerektiren uygulamalar için G1GC'ye iyi bir alternatif. -XX:+UseZGC parametresi ile aktif edebilirsiniz.",
                "SYSTEM"
        ));

        staticTips.add(new Tip(
                "Service Mesh - Istio",
                "Microservice mimarinizde trafiği yönetmek için kullandığınız Istio'nun 1.20 sürümü ile Ambient Mesh özelliği stabil hale geldi. Bu sayede sidecar container'lar olmadan da service mesh özelliklerini kullanabilirsiniz.",
                "SYSTEM"
        ));

        staticTips.add(new Tip(
                "Event-Driven Architecture Trend",
                "Microservice'ler arası iletişimde Apache Kafka yerine Redpanda kullanımı artıyor. Kafka ile %100 uyumlu olan Redpanda, daha düşük latency ve daha kolay cluster yönetimi sunuyor. Spring Cloud Stream ile kolayca entegre edilebilir.",
                "SYSTEM"
        ));

        staticTips.add(new Tip(
                "Grafana Tempo ile Trace Analizi",
                "Distributed tracing için Jaeger yerine Grafana Tempo kullanmayı değerlendirin. Tempo, Grafana Labs'in yeni nesil tracing çözümü. Prometheus ve Loki ile tam entegrasyon sunuyor ve OpenTelemetry protokolünü destekliyor.",
                "SYSTEM"
        ));

        staticTips.add(new Tip(
                "JBang - Java Scripting Kolaylığı",
                "Quick POC'ler veya utility scriptler yazmak için JBang kullanabilirsiniz. Maven/Gradle yapılandırması olmadan direkt dependency ekleyip Java kodunuzu çalıştırabilirsiniz. Örnek: //DEPS com.google.code.gson:gson:2.10.1",
                "SYSTEM"
        ));

        staticTips.add(new Tip(
                "API Testleri için REST-assured Yerine Karate",
                "REST-assured'a modern bir alternatif olarak Karate framework'ü kullanabilirsiniz. Gherkin syntax ile daha okunabilir testler yazabilir, API, UI ve performance testlerini tek framework ile yönetebilirsiniz.",
                "SYSTEM"
        ));

        return staticTips;
    }
}