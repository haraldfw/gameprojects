package com.wilhelmsen.exile.chat;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.google.common.base.Splitter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;

/**
 * Created by Harald on 28.02.2017.
 */
public class ChatOverlay {

    private final int maxEntries = 20;
    private final int entriesOnUpdate = 3;
    private final int maxCharactersWidth = 50;
    private final int showNewMessagesForSeconds = 5;
    private final SimpleDateFormat simpleDateFormat;
    private final float xShift = 20;
    private final float yShiftPerLine = 3;
    private LinkedList<String> messages;
    private float secondsSinceLastMessage = Float.POSITIVE_INFINITY;
    private boolean isActive = false;
    private SpriteBatch chatBatch;
    private BitmapFont chatFont;

    public ChatOverlay() {
        this.chatBatch = new SpriteBatch();
        messages = new LinkedList<>();
        simpleDateFormat = new SimpleDateFormat("H:m:s");
        chatFont = new BitmapFont();
        chatFont.setColor(Color.WHITE);
    }

    public void draw(float delta) {
        secondsSinceLastMessage += delta;
        if (isActive) {
            drawMessages(maxEntries);
        } else if (secondsSinceLastMessage < showNewMessagesForSeconds) {
            drawMessages(entriesOnUpdate);
        }
    }

    private void drawMessages(int messageAmount) {
        chatBatch.begin();
        int endIndex = messages.size() - messageAmount;
        int drawShift = 0;
        for (int i = messages.size() - 1; i >= 0 && i >= endIndex; i--) {
            drawShift++;
            String message = messages.get(i);
            chatFont.draw(chatBatch, message, xShift, chatFont.getLineHeight() * (drawShift + yShiftPerLine));
        }
        chatBatch.end();
    }

    public void addMessage(String message, String sender) {
        secondsSinceLastMessage = 0;
        message = simpleDateFormat.format(new Date()).concat(" ").concat(sender).concat(": ").concat(message);
        if (message.length() <= maxCharactersWidth) {
            addToMessageList(message);
        } else {
            Iterable<String> splits = Splitter.fixedLength(maxCharactersWidth).split(message);
            for (String part : splits) {
                addToMessageList(part);
            }
        }
    }

    private void addToMessageList(String message) {
        messages.add(message);
        if (messages.size() > maxEntries) {
            messages.removeFirst();
        }
    }
}
