package com.parkinglot.demo.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;

@OpenAPIDefinition(
        info = @Info(
                title = "Parking Lot Management API",
                description = "Production-ready parking lot system with slot management, pricing, and persistence.",
                version = "1.0.0",
                contact = @Contact(name = "Parking Lot Team", email = "support@parkinglot.demo"),
                license = @License(name = "Apache 2.0", url = "https://www.apache.org/licenses/LICENSE-2.0")
        )
)
public class OpenApiConfig {
}
