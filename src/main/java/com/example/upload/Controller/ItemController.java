package com.example.upload.Controller;

import com.example.upload.Controller.dto.ItemForm;
import com.example.upload.domain.Item;
import com.example.upload.domain.UploadFile;
import com.example.upload.domain.repository.ItemRepository;
import com.example.upload.util.FileStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriUtils;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Controller

public class ItemController {

    private final Logger log = LoggerFactory.getLogger(ItemController.class);

    private final ItemRepository itemRepository;
    private final FileStore fileStore;

    public ItemController(ItemRepository itemRepository, FileStore fileStore) {
        this.itemRepository = itemRepository;
        this.fileStore = fileStore;
    }

    @GetMapping("/items/new")
    public String newItem() {
        return "item-form";
    }

    @PostMapping("/items/new")
    public String saveItem(@ModelAttribute ItemForm form, RedirectAttributes redirectAttributes) throws IOException {
        UploadFile uploadFile = fileStore.saveFile(form.getAttachFile());
        List<UploadFile> uploadFiles = fileStore.saveFiles(form.getFiles());
        log.info("itemForm={}", form);

        Item item = new Item(null, form.getItemName(), uploadFile, uploadFiles);
        Long id = itemRepository.save(item);

        redirectAttributes.addAttribute("itemId", id);

        return "redirect:/items/{itemId}";
    }

    @GetMapping("/items/{id}")
    public String items(@PathVariable Long id, Model model) {
        Item item = itemRepository.findById(id).orElseThrow();
        model.addAttribute("item", item);

        return "item-view";
    }

    @ResponseBody
    @GetMapping("/images/{filename}")
    public Resource downloadImage(@PathVariable String filename) throws MalformedURLException {
        return new UrlResource("file:" + fileStore.getFullPath(filename));
    }

    @GetMapping("/attach/{itemId}")
    public ResponseEntity<Resource> downloadAttach(@PathVariable Long itemId) throws MalformedURLException {
        Item item = itemRepository.findById(itemId).orElseThrow();
        String storedFilename = item.getAttachFile().getStoredFilename();
        String uploadFilename = item.getAttachFile().getUploadFilename();
        log.info("storedFilename={} uploadFilename={}", storedFilename, uploadFilename);
        UrlResource urlResource = new UrlResource("file:" + fileStore.getFullPath(storedFilename));

        String encodedUploadFilename = UriUtils.encode(uploadFilename, StandardCharsets.UTF_8);
        String contentDisposition = "attachment; filename=\"" + encodedUploadFilename + "\"";

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
                .body(urlResource);
    }
}
