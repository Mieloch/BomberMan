package local.oop.view;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import local.oop.GameImpl;

import java.util.*;

import static java.util.stream.Collectors.toList;

public class ChoosePlayersScreen extends AbstractScreen {

    private String[] list = {"", "Player 1", "Player 2", "Ernest", "Pawel", "Jacek", "Sebastian"};

    public ChoosePlayersScreen(GameImpl game){
        this.game = game;
    }

    @Override
    public void show() {
        super.show();
        java.util.List<SelectBox<String>> selectBoxList = new ArrayList<>();
        java.util.List<Label> labelList = new ArrayList<>();
        for(int i = 0; i < game.getPresenter().getPlayersInputCache().getNumberOfPlayers(); i++){
            SelectBox<String> selectBox = new SelectBox<>(selectBoxStyle);
            selectBox.setItems(list);
            selectBox.setSelectedIndex(i+1);
            selectBox.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    for(SelectBox<String> box : selectBoxList){

                        String boxSelected = box.getSelected();
                        /**
                         * It is HIGHLY advised not to do unchecked cast
                         * but in that case we can be sure that actor invoking event is of type SelectBox
                         * We cannot check it though, because SelectBox is of generic type
                         */
                        try {
                            @SuppressWarnings("unchecked")
                                SelectBox<String> actorBox = ((SelectBox<String>) actor);

                            String actorSelected = actorBox.getSelected();
                            if(!box.equals(actorBox)) {
                                if (boxSelected.equals(actorSelected)) {
                                    box.setSelected("");
                                }
                            }
                        } catch (ClassCastException e){
                            e.printStackTrace();
                        }
                    }
                }
            });
            selectBoxList.add(selectBox);
            Label player = new Label("Player " + (i+1), labelStyle);
            labelList.add(player);
        }



        TextButton goBack = new TextButton("Go Back", buttonStyle);
        goBack.addListener(new InputListener() {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new PlayersNumberScreen(game));
                game.getInputMultiplexer().removeProcessor(stage);
            }
        });

        TextButton startGame = new TextButton("Start Game", buttonStyle);
        startGame.addListener(new InputListener(){

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                boolean showAlertDialog = false;
                for(SelectBox<String> box : selectBoxList){
                    if(box.getSelected().isEmpty()){
                        showAlertDialog = true;
                    }
                }
                if(showAlertDialog){
                    displayAlertDialog("Player must have selection", "Please correct your settings", null);
                } else {

                    game.setScreen(new GameScreen(game));
                    java.util.List<String> selectedPlayerList = selectBoxList.stream().map(SelectBox::getSelected).collect(toList());
                    game.getPresenter().setPlayers(selectedPlayerList);
                    game.getInputMultiplexer().removeProcessor(stage);
                    game.setPlayerInputProcessor();
                }
            }
        });

        table.add(imageLogo).center().colspan(labelList.size()).expandY().size(imageLogo.getPrefWidth(), imageLogo.getPrefHeight());
        table.row();
        for(Label label : labelList){
            table.add(label).align(Align.center).fillY();
        }
        table.row();
        for(SelectBox<String> selectBox : selectBoxList){
            table.add(selectBox).space(30).fillY();
        }
        table.row();
        Table buttonTable = new Table();
        buttonTable.add(goBack).size(300f, 100f).space(20).align(Align.bottomLeft).expand();
        buttonTable.add(startGame).size(300f, 100f).space(20).align(Align.bottomRight);
        table.add(buttonTable).colspan(labelList.size()).expandY().fill();
        table.setFillParent(true);
        table.pad(50f);
        stage.addActor(table);
    }
}
