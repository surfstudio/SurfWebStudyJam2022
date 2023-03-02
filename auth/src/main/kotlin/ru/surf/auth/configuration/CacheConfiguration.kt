package ru.surf.auth.configuration

import com.github.benmanes.caffeine.cache.Caffeine
import org.springframework.cache.CacheManager
import org.springframework.cache.annotation.CachingConfigurer
import org.springframework.cache.annotation.EnableCaching
import org.springframework.cache.caffeine.CaffeineCacheManager
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.time.Duration


@Configuration
@EnableCaching
class CacheConfiguration : CachingConfigurer {
    @Bean
    override fun cacheManager(): CacheManager {
        return CaffeineCacheManager().apply {
            setCaffeine(Caffeine.newBuilder())
        }
    }

    @Bean
    fun timeoutCacheManager(): CacheManager {
        return CaffeineCacheManager().apply {
            setCaffeine(with(Caffeine.newBuilder()) {
                expireAfterWrite(Duration.ofMinutes(1))
            })
        }
    }
}
