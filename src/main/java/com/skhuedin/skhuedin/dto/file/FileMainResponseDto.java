package com.skhuedin.skhuedin.dto.file;

import com.skhuedin.skhuedin.domain.File;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@ToString
public class FileMainResponseDto {

    private Long id;
    private String originalName;
    private String name;
    private String path;

    public FileMainResponseDto(File file) {
        this.id = file.getId();
        this.originalName = file.getOriginalName();
        this.name = file.getName();
        this.path = file.getPath();
    }
}