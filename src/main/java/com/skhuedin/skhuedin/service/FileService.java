package com.skhuedin.skhuedin.service;

import com.skhuedin.skhuedin.domain.File;
import com.skhuedin.skhuedin.dto.file.FileMainResponseDto;
import com.skhuedin.skhuedin.dto.file.FileSaveRequestDto;
import com.skhuedin.skhuedin.repository.FileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FileService {

    private final FileRepository fileRepository;

    @Transactional
    public Long save(FileSaveRequestDto requestDto) {
        return fileRepository.save(requestDto.toEntity()).getId();
    }

    public FileMainResponseDto findById(Long id) {
        File file = getFile(id);

        return new FileMainResponseDto(file);
    }

    @Transactional
    public void delete(Long id) {
        File file = getFile(id);
        fileRepository.delete(file);
    }

    private File getFile(Long id) {
        return fileRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("존재하지 않는 file id 입니다."));
    }
}