package com.landonwoerdeman.server.config;

import io.micronaut.core.annotation.Introspected;
import ofx.message.StatementTransaction;

@Introspected(classes = {
        StatementTransaction.class,
})
public class Config {
}
