package com.modulith.petrolstats.notifications;

public interface NotificationsService {
    void addListener(CacheUpdatedListener listener);
}
