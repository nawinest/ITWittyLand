package com.nawinz.itwitty;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import helper.GameManager;
import scenes.GamePlay;
import scenes.MainMenu;

public class GameMain extends Game {
	private SpriteBatch batch;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		setScreen(new MainMenu(this));
        Preferences prefs = Gdx.app.getPreferences("Data");

//        prefs.putInteger("Scorefar", 0);
//        prefs.putInteger("Score", 0);
	}

	@Override
	public void render () {
        super.render();
	}
	
	public SpriteBatch getBatch(){
		return this.batch;
	}
}
