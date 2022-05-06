package com.example.upload.domain.repository;

import com.example.upload.domain.Item;
import com.example.upload.domain.UploadFile;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class ItemRepository {

    private final Map<Long, Item> store = new ConcurrentHashMap<>();
    private final AtomicLong sequence = new AtomicLong(1L);

    public Long save(Item item) {
        Long id = sequence.getAndAdd(1L);
        store.put(id, new Item(id, item.getItemName(), item.getAttachFile(), item.getImageFiles()));
        return id;
    }

    public Optional<Item> findById(Long id) {
        if(store.get(id) == null) {
            return Optional.empty();
        }
        return Optional.ofNullable(store.get(id));
    }
}
