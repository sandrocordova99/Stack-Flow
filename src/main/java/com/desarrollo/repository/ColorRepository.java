package com.desarrollo.repository;

import com.desarrollo.model.ColorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.awt.*;

@Repository
public interface ColorRepository extends JpaRepository<ColorEntity, Long> {

    String findNameById (long id);

}
