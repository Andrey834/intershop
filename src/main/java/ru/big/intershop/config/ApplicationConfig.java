package ru.big.intershop.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import ru.big.intershop.dto.cart.CartShort;


@Configuration
public class ApplicationConfig {

    @Bean
    public ReactiveRedisOperations<String, CartShort> jokeTemplate(LettuceConnectionFactory lettuceConnectionFactory){
        RedisSerializer<CartShort> valueSerializer = new Jackson2JsonRedisSerializer<>(CartShort.class);
        RedisSerializationContext<String, CartShort> serializationContext =
                RedisSerializationContext.<String, CartShort>newSerializationContext(RedisSerializer.string())
                .value(valueSerializer)
                .build();
        return new ReactiveRedisTemplate<>(lettuceConnectionFactory, serializationContext);
    }
}
