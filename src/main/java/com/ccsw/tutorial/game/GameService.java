package com.ccsw.tutorial.game;

import com.ccsw.tutorial.game.model.Game;
import com.ccsw.tutorial.game.model.GameDto;

import java.util.List;

public interface GameService {

    List<Game> find(String title, Long idCategory);


    void save(Long id, GameDto dto);

}
