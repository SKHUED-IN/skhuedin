package com.skhuedin.skhuedin.repository;

import com.skhuedin.skhuedin.domain.File;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<File, Long> {
}