package ddururi.bookbookclub.domain.user.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.*;
import java.util.UUID;

@Service
public class ProfileImageService {

    private static final String PROFILE_IMAGE_DIR = "uploads/profile-images";
    private static final String BASE_URL = "/images/profile-images/";

    public String store(MultipartFile file, Long userId) {

        // 확장자 검사
        if (!file.getContentType().startsWith("image/")) {
            throw new IllegalArgumentException("이미지 파일만 업로드 가능합니다.");
        }
        // 파일 크기 제한 (5MB 이하)
        if (file.getSize() > 5 * 1024 * 1024) {
            throw new IllegalArgumentException("파일 용량은 5MB 이하만 가능합니다.");
        }

        String filename = UUID.randomUUID() + "_" + file.getOriginalFilename();

        Path dirPath = Paths.get(PROFILE_IMAGE_DIR);
        try {
            if (!Files.exists(dirPath)) {
                Files.createDirectories(dirPath);
            }
            Path filePath = dirPath.resolve(filename);
            try (InputStream in = file.getInputStream()) {
                Files.copy(in, filePath, StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (IOException e) {
            throw new RuntimeException("파일 저장 실패", e);
        }

        return BASE_URL + filename;
    }
}
