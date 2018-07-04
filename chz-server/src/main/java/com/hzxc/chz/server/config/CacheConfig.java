package com.hzxc.chz.server.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author chz
 * @version create at：2017年3月14日 下午2:17:40
 *
 */
@Configuration
@EnableCaching
public class CacheConfig {
	/**
	 * 缺省最大容量
	 */
	private static final int DEFAULT_MAX_SIZE = 5000;
	/**
	 * 缺省超时时间（秒）
	 */
	private static final int DEFAULT_TTL = 300;

	private static final int TOPIC_SEARCH_MAX_SIZE = 500;
	private static final int TOPIC_SEARCH_TTL = 600;

	/**
	 * 将本地缓存分成多个缓存区
	 */
	public static enum Caches {
		DEFAULT(DEFAULT_TTL, DEFAULT_MAX_SIZE),
		TOPIC_SEARCH(TOPIC_SEARCH_TTL,TOPIC_SEARCH_MAX_SIZE),
		;

		private Caches(int ttl, int maxSize) {
			this.ttl = ttl;
			this.maxSize = maxSize;
		}

		private int ttl;
		private int maxSize;

		public int getTtl() {
			return ttl;
		}

		public int getMaxSize() {
			return maxSize;
		}
	}

	/**
	 * 创建基于Caffeine的Cache Manager
	 * 
	 * @return
	 */
	@Bean(name = "caffeineCacheManager")
	public CacheManager caffeineCacheManager() {
		SimpleCacheManager cacheManager = new SimpleCacheManager();
		ArrayList<CaffeineCache> caches = new ArrayList<CaffeineCache>();
		for (Caches c : Caches.values()) {
			String cacheName = c.name();
			caches.add(new CaffeineCache(cacheName, Caffeine.newBuilder().recordStats()
					.expireAfterWrite(c.getTtl(), TimeUnit.SECONDS).maximumSize(c.getMaxSize()).build()));
		}
		cacheManager.setCaches(caches);
		return cacheManager;
	}
}
