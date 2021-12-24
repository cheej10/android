package com.example.firstapplication;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.util.Log;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class BlockButton extends Button {
    private static final String TAG = "BlockButton";

    boolean mine;   //지뢰인지
    boolean flag;   //깃발 꽂았는지
    int x;
    int y;
    int neighborMines;
    static int flags = 10;
    static int blocks = 81;
    int cnt = 0;
    boolean isClicked;

    public BlockButton(Context context, int x, int y, boolean b, int cnt) {
        super(context);
        mine = b;
        flag = false;
        this.x = x;
        this.y = y;
        neighborMines = 0;
        this.cnt = cnt;
        isClicked = false;
    }

    public void toggleFlag() {
        flag = !flag;
        if (flag) {
            this.setText("\uD83D\uDEA9");
            flags--;
        } else {
            this.setText("");
            flags++;
        }
    }

    public boolean breakBlock() {
        setClickable(false);
        isClicked = true;
        blocks--;
        if (mine) {
            this.setBackgroundColor(Color.LTGRAY);
            this.setTextColor(Color.RED);
            this.setText("\uD83D\uDCA3");
            return true;
        } else {
            //주변 지뢰 개수
            String n = String.valueOf(cnt);
            this.setBackgroundColor(Color.LTGRAY);
            if (!n.equals("0")) this.setText(n); // 사방에 지뢰가 없으면 숫자표시 x
            return false;
        }
    }
}