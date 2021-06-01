package com.skhuedin.skhuedin.dto.file;

import com.skhuedin.skhuedin.domain.File;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@ToString
public class FileSaveRequestDto {

    private String originalName;
    private String name;
    private String path;

    @Builder
    public FileSaveRequestDto(String originalName, String name, String path) {
        this.originalName = originalName;
        this.name = name;
        this.path = path;
    }

    public File toEntity() {
        return File.builder()
                .originalName(this.originalName)
                .name(this.name)
                .path(this.path)
                .build();
    }
}