package com.example.phamducduy.event;


import com.example.phamducduy.entity.UserEntity;

public class MessageEvent {
    public static class Event {
        private UserEntity entity;

        public Event(UserEntity entity) {
            this.entity = entity;
        }

        public UserEntity getEntity() {
            return entity;
        }

        public void setEntity(UserEntity entity) {
            this.entity = entity;
        }
    }
}
