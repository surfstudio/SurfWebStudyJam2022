package ru.surf.auth.configuration

import com.github.benmanes.caffeine.cache.Caffeine
import org.springframework.cache.CacheManager
import org.springframework.cache.annotation.CachingConfigurerSupport
import org.springframework.cache.annotation.EnableCaching
import org.springframework.cache.caffeine.CaffeineCacheManager
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.time.Duration


@Configuration
@EnableCaching
class CacheConf : CachingConfigurerSupport() {
    @Bean
    override fun cacheManager(): CacheManager {
        val caffeineCacheManager = CaffeineCacheManager()
        caffeineCacheManager.setCaffeine(Caffeine.newBuilder())
        return caffeineCacheManager
    }

    @Bean
    fun timeoutCacheManager(): CacheManager {
        val caffeineCacheManager = CaffeineCacheManager()
        caffeineCacheManager.setCaffeine(Caffeine.newBuilder().expireAfterWrite(Duration.ofMinutes(1)))
        return caffeineCacheManager
    }
}
