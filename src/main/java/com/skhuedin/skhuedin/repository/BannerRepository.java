package com.skhuedin.skhuedin.repository;

import com.skhuedin.skhuedin.domain.banner.Banner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BannerRepository extends JpaRepository<Banner, Long> {

    @Query("select b " +
            "from Banner b " +
            "order by b.weight DESC ")
    List<Banner> findAll();

    @Query("select b " +
            "from Banner b " +
            "order by b.weight DESC ")
    Page<Banner> findAll(Pageable pageable);
}