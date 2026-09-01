package company.salesforce;

/*  import com.google.common.cache.*;
  import java.util.*;                                                                                                                                                  
  import java.util.concurrent.*;                            
  import java.util.function.Supplier;*/

  /**
   * Simple Cache Manager using Guava Cache
   * This is a basic implementation with some subtle issues for discussion
   */

  /*public class SimpleCacheManager {

      // Basic Guava Cache with some reasonable defaults
      private static final LoadingCache<String, Object> cache = CacheBuilder.newBuilder()
          .maximumSize(100)
          .expireAfterWrite(5, TimeUnit.MINUTES)
          .build(new CacheLoader<String, Object>() {
              @Override
              public Object load(String key) {
                  throw new UnsupportedOperationException("Use get() with loader");
              }
          });

      *//**
       * Get value from cache or load it
       *//*
      @SuppressWarnings("unchecked")
      public static <T> T get(String key, Supplier<T> loader) {
          try {
              // 'Cache.get() now throws ExecutionException instead of UncheckedExecutionException'
              return (T) cache.get(key, () -> loader.get());
          } catch (UncheckedExecutionException e) {
              throw new RuntimeException("Failed to load: " + key, e);
          } catch (Exception ex) {
               
          }
      }

      *//**
       * Clear specific cache entry
       *//*
      public static void invalidate(String key) {
          cache.invalidate(key);
      }

      *//**
       * Clear all cache
       *//*
      public static void clear() {
          cache.invalidateAll();
      }

      *//**
       * Get cache statistics
       *//*
      public static String getStats() {
          CacheStats stats = cache.stats();
          return String.format("Hits: %d, Misses: %d, Hit Rate: %.2f%%",
              stats.hitCount(),
              stats.missCount(),
              stats.hitRate() * 100);
      }

      // Demo usage
      public static void main(String[] args) {
          // Example 1: Cache a list
          List<String> users = get("users", () -> {
              System.out.println("Loading users from database...");
              return Arrays.asList("Alice", "Bob", "Charlie");
          });
          System.out.println("First call: " + users);

          // Modify the list
          users.add("David");

          // Get from cache again
          List<String> cachedUsers = get("users", () -> {
              return Arrays.asList("Alice", "Bob", "Charlie");
          });
          System.out.println("Second call: " + cachedUsers);
          System.out.println("Size: " + cachedUsers.size()); // What prints here?

          System.out.println("\n" + getStats());
      }


  }
*/

public class SimpleCacheManager {

}