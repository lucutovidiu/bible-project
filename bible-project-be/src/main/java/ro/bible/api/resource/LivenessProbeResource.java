package ro.bible.api.resource;

import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Liveness;

@Liveness
public class LivenessProbeResource implements HealthCheck {

    @Override
    public HealthCheckResponse call() {
        return HealthCheckResponse.up("alive");
    }

}
