package local.oop.view;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane.ScrollPaneStyle;
import local.oop.GameImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class OptionsScreen extends AbstractScreen {

    private Map<TextButton, String> map;

    public OptionsScreen(GameImpl game){
        this.game = game;
    }

    @Override
    public void show() {
        super.show();
        table.setFillParent(true);
        Table scrollTable = new Table();

        final TextButton.TextButtonStyle buttonStyle1 = new TextButton.TextButtonStyle();
        buttonStyle1.up = skin.getDrawable("textbox_01");
        buttonStyle1.checkedFontColor = new Color(0.7f, 0, 0.7f, 1);
        buttonStyle1.font = buttonStyle.font;
        buttonStyle1.fontColor = buttonStyle.fontColor;
        Label.LabelStyle labelStyle = new Label.LabelStyle(buttonStyle.font, new Color(1, 1, 1, 1));

        Label playerOne = new Label("Player One", labelStyle);
        Label lOneUp = new Label("Move up", labelStyle);
        final TextButton bOneUp = new TextButton(settings.oneUp(), buttonStyle1);
        Label lOneDown = new Label("Move down", labelStyle);
        final TextButton bOneDown = new TextButton(settings.oneDown(), buttonStyle1);
        Label lOneLeft = new Label("Move left", labelStyle);
        final TextButton bOneLeft = new TextButton(settings.oneLeft(), buttonStyle1);
        Label lOneRight = new Label("Move right", labelStyle);
        final TextButton bOneRight = new TextButton(settings.oneRight(), buttonStyle1);
        Label lOneBomb = new Label("Plant bomb", labelStyle);
        final TextButton bOneBomb = new TextButton(settings.oneBomb(), buttonStyle1);

        Label playerTwo = new Label("Player Two", labelStyle);
        Label lTwoUp = new Label("Move up", labelStyle);
        final TextButton bTwoUp = new TextButton(settings.twoUp(), buttonStyle1);
        Label lTwoDown = new Label("Move down", labelStyle);
        final TextButton bTwoDown = new TextButton(settings.twoDown(), buttonStyle1);
        Label lTwoLeft = new Label("Move left", labelStyle);
        final TextButton bTwoLeft = new TextButton(settings.twoLeft(), buttonStyle1);
        Label lTwoRight = new Label("Move right", labelStyle);
        final TextButton bTwoRight = new TextButton(settings.twoRight(), buttonStyle1);
        Label lTwoBomb = new Label("Plant bomb", labelStyle);
        final TextButton bTwoBomb = new TextButton(settings.twoBomb(), buttonStyle1);

        map = new HashMap<>();
        map.put(bOneUp, "oneup");
        map.put(bOneDown, "onedown");
        map.put(bOneLeft, "oneleft");
        map.put(bOneRight, "oneright");
        map.put(bOneBomb, "onebomb");
        map.put(bTwoUp, "twoup");
        map.put(bTwoDown, "twodown");
        map.put(bTwoLeft, "twoleft");
        map.put(bTwoRight, "tworight");
        map.put(bTwoBomb, "twobomb");



        final ButtonGroup<TextButton> textButtonButtonGroup = new ButtonGroup<>(bOneUp, bOneDown, bOneLeft, bOneRight, bOneBomb, bTwoUp, bTwoDown, bTwoLeft, bTwoRight, bTwoBomb);
        textButtonButtonGroup.setMaxCheckCount(1);
        textButtonButtonGroup.setMinCheckCount(0);

        InputListener listener = new InputListener(){

            @Override
            public boolean keyUp(InputEvent event, int keycode) {
                TextButton checkedButton = textButtonButtonGroup.getChecked();
                if(checkedButton!=null) {
                    for(TextButton button : textButtonButtonGroup.getButtons()){
                        if(button.getText().toString().equals(Input.Keys.toString(keycode))){
                            button.setText("");
                        }
                    }
                    checkedButton.setText(Input.Keys.toString(keycode));
                    checkedButton.setChecked(false);
                }
                return true;
            }
        };

        stage.addCaptureListener(listener);

        CheckBox.CheckBoxStyle checkBoxStyle = new CheckBox.CheckBoxStyle();
        checkBoxStyle.checkboxOff = skin.getDrawable("checkbox_off");
        checkBoxStyle.checkboxOn = skin.getDrawable("checkbox_on");
        checkBoxStyle.font = font;
        checkBoxStyle.fontColor = new Color(1, 1, 1, 1);
        CheckBox checkBox = new CheckBox("Allow repeated players", checkBoxStyle);
        checkBox.setChecked(settings.isAllowRepeatedPlayers());

        TextButton save = new TextButton("Save settings", buttonStyle);
        save.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y,
                                     int pointer, int button) {
                return true;
            }

            public void touchUp(InputEvent event, float x, float y,
                                int pointer, int button) {
                Map<String, Integer> settingsMap = new HashMap<>();
                boolean displayAlert = false;
                for(TextButton button1 : textButtonButtonGroup.getButtons()){
                    if(button1.getText().toString().isEmpty()){
                        displayAlert = true;
                    }
                    settingsMap.put(map.get(button1), Input.Keys.valueOf(button1.getText().toString()));
                }
                if(displayAlert){
                    displayAlertDialog("Settings must not be empty", "Please correct your settings", new ArrayList<>());
                } else {
                    settings.save(settingsMap);
                    settings.setAllowRepeatedPlayers(checkBox.isChecked());
                    game.setScreen(new StartScreen(game));
                    game.getInputMultiplexer().removeProcessor(stage);
                }
            }
        });

        TextButton goBack = new TextButton("Go back", buttonStyle);
        goBack.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y,
                                     int pointer, int button) {
                return true;
            }

            public void touchUp(InputEvent event, float x, float y,
                                int pointer, int button) {
                game.setScreen(new StartScreen(game));
                game.getInputMultiplexer().removeProcessor(stage);
            }
        });


        ScrollPaneStyle scrollPaneStyle = new ScrollPaneStyle();
        scrollPaneStyle.vScroll = skin.getDrawable("scroll_back_hor");
        scrollPaneStyle.vScrollKnob = skin.getDrawable("knob_04");

        scrollTable.add(playerOne).colspan(2);
        scrollTable.row();
        scrollTable.add(lOneUp).space(LABEL_SPACING).left().expandX();
        scrollTable.add(bOneUp).space(LABEL_SPACING).right().size(TEXT_BOX_WIDTH, TEXT_BOX_HEIGHT);
        scrollTable.row();
        scrollTable.add(lOneDown).space(LABEL_SPACING).left().expandX();
        scrollTable.add(bOneDown).space(LABEL_SPACING).right().size(TEXT_BOX_WIDTH, TEXT_BOX_HEIGHT);
        scrollTable.row();
        scrollTable.add(lOneLeft).space(LABEL_SPACING).left().expandX();
        scrollTable.add(bOneLeft).space(LABEL_SPACING).right().size(TEXT_BOX_WIDTH, TEXT_BOX_HEIGHT);
        scrollTable.row();
        scrollTable.add(lOneRight).space(LABEL_SPACING).left().expandX();
        scrollTable.add(bOneRight).space(LABEL_SPACING).right().size(TEXT_BOX_WIDTH, TEXT_BOX_HEIGHT);
        scrollTable.row();
        scrollTable.add(lOneBomb).space(LABEL_SPACING).left().expandX();
        scrollTable.add(bOneBomb).space(LABEL_SPACING).right().size(TEXT_BOX_WIDTH, TEXT_BOX_HEIGHT);

        scrollTable.row();

        scrollTable.add(playerTwo).colspan(2);
        scrollTable.row();
        scrollTable.add(lTwoUp).space(LABEL_SPACING).left().expandX();
        scrollTable.add(bTwoUp).space(LABEL_SPACING).right().size(TEXT_BOX_WIDTH, TEXT_BOX_HEIGHT);
        scrollTable.row();
        scrollTable.add(lTwoDown).space(LABEL_SPACING).left().expandX();
        scrollTable.add(bTwoDown).space(LABEL_SPACING).right().size(TEXT_BOX_WIDTH, TEXT_BOX_HEIGHT);
        scrollTable.row();
        scrollTable.add(lTwoLeft).space(LABEL_SPACING).left().expandX();
        scrollTable.add(bTwoLeft).space(LABEL_SPACING).right().size(TEXT_BOX_WIDTH, TEXT_BOX_HEIGHT);
        scrollTable.row();
        scrollTable.add(lTwoRight).space(LABEL_SPACING).left().expandX();
        scrollTable.add(bTwoRight).space(LABEL_SPACING).right().size(TEXT_BOX_WIDTH, TEXT_BOX_HEIGHT);
        scrollTable.row();
        scrollTable.add(lTwoBomb).space(LABEL_SPACING).left().expandX();
        scrollTable.add(bTwoBomb).space(LABEL_SPACING).right().size(TEXT_BOX_WIDTH, TEXT_BOX_HEIGHT);
        scrollTable.row();
        scrollTable.add(checkBox).space(LABEL_SPACING).right().expandX();
        scrollTable.pad(50f);

        ScrollPane scrollPane = new ScrollPane(scrollTable, scrollPaneStyle);
        scrollPane.setVariableSizeKnobs(false);

        table.add(imageLogo).center().colspan(2).minHeight(imageLogo.getPrefHeight());
        table.row();
        table.add(scrollPane).colspan(2).fillX();
        table.row();
        table.add(goBack).left().size(300f, 100f).fillX();
        table.add(save).right().size(300f, 100f).fillX();
        table.pad(50f);


        stage.addActor(table);
        stage.setScrollFocus(scrollPane);
    }

}
