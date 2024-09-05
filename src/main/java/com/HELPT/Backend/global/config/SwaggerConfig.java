package com.HELPT.Backend.global.config;

import com.HELPT.Backend.global.common.ApiResponse;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.responses.ApiResponses;
import lombok.SneakyThrows;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springdoc.core.customizers.OperationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.HandlerMethod;

import java.lang.reflect.Field;
import java.util.Arrays;

@Configuration
public class SwaggerConfig {

    @Bean
    public OperationCustomizer operationCustomizer() {
        return (operation, handlerMethod) -> {
            this.addResponseBodyWrapperSchemaExample(operation);
            return operation;
        };
    }

    private void addResponseBodyWrapperSchemaExample(Operation operation) {
        final Content content = operation.getResponses().get("200").getContent();
        if (content != null) {
            content.forEach((mediaTypeKey, mediaType) -> {
                Schema<?> originalSchema = mediaType.getSchema();
                Schema<?> wrappedSchema = wrapSchema(originalSchema);
                mediaType.setSchema(wrappedSchema);
            });
        }
    }

    private Schema<?> wrapSchema(Schema<?> originalSchema) {
        final Schema<?> wrapperSchema = new Schema<>();

        wrapperSchema.addProperty("status", new Schema<>().type("integer").example(200));
        wrapperSchema.addProperty("data", originalSchema);

        return wrapperSchema;
    }
}