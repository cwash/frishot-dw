package com.example.helloworld.core

import com.google.common.base.Optional

import javax.inject.Inject

import static java.lang.String.format

public class Template {
    final String content
    final String defaultName

    @Inject
    public Template(String content, String defaultName) {
        this.content = content
        this.defaultName = defaultName
    }

    public String render(Optional<String> name) {
        return format(content, name.or(defaultName))
    }
}
