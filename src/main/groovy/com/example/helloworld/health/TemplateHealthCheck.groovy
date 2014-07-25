package com.example.helloworld.health

import com.codahale.metrics.health.HealthCheck
import com.example.helloworld.core.Template
import com.google.common.base.Optional
import com.google.inject.Inject

public class TemplateHealthCheck extends HealthCheck {
    final Template template

    @Inject
    public TemplateHealthCheck(Template template) {
        super()
        this.template = template
    }

    @Override
    protected HealthCheck.Result check() throws Exception {
        template.render(Optional.of("woo"))
        template.render(Optional.<String> absent())
        return HealthCheck.Result.healthy()
    }
}
