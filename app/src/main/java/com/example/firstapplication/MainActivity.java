package com.example.firstapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.ToggleButton;
import android.app.AlertDialog;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class MainActivity extends AppCompatActivity {
    boolean isChecked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        int row = 9;
        int col = 9;

        Set<Integer> randomSet = new HashSet<Integer>();
        while(randomSet.size() < 10) {
            int x = (int) Math.floor(Math.random() * (row * col));
            if(x % 10 == 9) continue;
            else randomSet.add(x);
        }
        //set 배열 -> 오름차순
        List<Integer> list = new ArrayList<>(randomSet);
        Collections.sort(list);
        randomSet = new LinkedHashSet<>(list);
        //System.out.println(randomSet);

        //set 배열 -> 배열 변환
        Integer[] randomArr = randomSet.toArray(new Integer[0]);

        //주변 지뢰 개수 계산
        int[][] arr = new int[9][9];
        for(int i=0; i<9; i++) {
            for(int j=0; j<9; j++) {
                arr[i][j] = i*10 + j;
                for(int k=0; k<randomArr.length; k++) {
                    int x = (int) Math.floor(randomArr[k] / 10);
                    int y = randomArr[k] % 10;
                    if(i == x && j == y) {
                        arr[i][j] = 999;
                        break;
                    }
                }
            }
        }
        int [][] check = new int[9][9];
        for(int i=0; i<9; i++) { //행
            for(int j=0; j<9; j++) { //열
                if(arr[i][j] == 999) {
                    check[i][j] = 888;
                }
                else {
                    int cnt = 0;
                    if(i==0 && j==0) { //왼쪽 상단 모서리
                        if(arr[i][j+1] == 999) cnt++;
                        if(arr[i+1][j] == 999) cnt++;
                        if(arr[i+1][j+1] == 999) cnt++;
                    }
                    else if(i==0 && j!=0 && j!=arr[i].length-1) { //위쪽
                        if(arr[i][j-1] == 999) cnt++;
                        if(arr[i][j+1] == 999) cnt++;
                        if(arr[i+1][j-1] == 999) cnt++;
                        if(arr[i+1][j] == 999) cnt++;
                        if(arr[i+1][j+1] == 999) cnt++;
                    }
                    else if(i==0 && j==arr.length-1){ //오른쪽 상단 모서리
                        if(arr[i][j-1] == 999) cnt++;
                        if(arr[i+1][j-1] == 999) cnt++;
                        if(arr[i+1][j] == 999) cnt++;
                    }
                    else if(j==0 && i!=0 && i!=arr.length-1){ //왼쪽
                        if(arr[i-1][j] == 999) cnt++;
                        if(arr[i-1][j+1] == 999) cnt++;
                        if(arr[i][j+1] == 999) cnt++;
                        if(arr[i+1][j] == 999) cnt++;
                        if(arr[i+1][j+1] == 999) cnt++;
                    }
                    else if(j==0 && i==arr.length-1){ //왼쪽 하단 모서리
                        if(arr[i-1][j] == 999) cnt++;
                        if(arr[i-1][j+1] == 999) cnt++;
                        if(arr[i][j+1] == 999) cnt++;
                    }
                    else if(i==arr.length-1 && j!=0 && j!= arr.length-1){ //아래쪽
                        if(arr[i-1][j-1] == 999) cnt++;
                        if(arr[i-1][j] == 999) cnt++;
                        if(arr[i-1][j+1] == 999) cnt++;
                        if(arr[i][j-1] == 999) cnt++;
                        if(arr[i][j+1] == 999) cnt++;
                    }
                    else if(i==arr.length-1 && j==arr.length-1){ //오른쪽 하단 모서리
                        if(arr[i-1][j-1] == 999) cnt++;
                        if(arr[i-1][j] == 999) cnt++;
                        if(arr[i][j-1] == 999) cnt++;
                    }
                    else if(j==arr.length-1 && i!=0 && i!=arr.length-1){ //오른쪽
                        if(arr[i-1][j-1] == 999) cnt++;
                        if(arr[i-1][j] == 999) cnt++;
                        if(arr[i][j-1] == 999) cnt++;
                        if(arr[i+1][j-1] == 999) cnt++;
                        if(arr[i+1][j] == 999) cnt++;
                    }else{
                        if(arr[i-1][j-1] == 999) cnt++;
                        if(arr[i-1][j] == 999) cnt++;
                        if(arr[i-1][j+1] == 999) cnt++;
                        if(arr[i][j-1] == 999) cnt++;
                        if(arr[i][j+1] == 999) cnt++;
                        if(arr[i+1][j-1] == 999) cnt++;
                        if(arr[i+1][j] == 999) cnt++;
                        if(arr[i+1][j+1] == 999) cnt++;
                    }
                    check[i][j] = cnt;
                }
            }
        }


        TableLayout table = (TableLayout)findViewById(R.id.tableLayout);

        BlockButton[][] buttons = new BlockButton[9][9];

        for (int i = 0; i < 9; i++) {
            TableRow tableRow = new TableRow(this);
            table.addView(tableRow);
            TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(
                    TableRow.LayoutParams.WRAP_CONTENT,
                    TableRow.LayoutParams.WRAP_CONTENT,
                    1.0f);

            for (int j = 0; j < 9; j++) {
                boolean b = false;
                for(int k=0; k<randomArr.length; k++) {
                    int x = (int) Math.floor(randomArr[k] / 10);
                    int y = randomArr[k] % 10;
                    if(i == x && j == y) {
                        b = true;
                        break;
                    }
                }
                int cnt = check[i][j];

                buttons[i][j] = new BlockButton(this, i, j, b, cnt);
                buttons[i][j].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                        BlockButton clicked = (BlockButton) view;
                        if (isChecked) {
                            if(BlockButton.flags > 0 || clicked.flag) {
                                ((BlockButton) view).toggleFlag();
                                TextView textView = (TextView) findViewById(R.id.textView);
                                textView.setText("mines: " + BlockButton.flags + "");
                            }
                            else {
                                builder.setTitle("깃발이 부족합니다!");
                                builder.setMessage("남아있는 깃발이 없습니다!");
                                builder.setNeutralButton("확인", null);
                                builder.create().show();
                            }
                        }
                        else if (!clicked.flag) {
                            if(!clicked.mine) {
                                breakBlock(view);
                            }
                            else{
                                builder.setTitle("지뢰를 밟았습니다!");
                                builder.setMessage("Game Over! 게임을 재시작 할까요?");
                                builder.setPositiveButton("재시작", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        finish();
                                        startActivity(new Intent(MainActivity.this, MainActivity.class));
                                    }
                                });
                                builder.setNegativeButton("종료", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        finishAffinity();
                                        System.runFinalization();
                                        System.exit(0);
                                    }
                                });
                                builder.setCancelable(false);
                                builder.create().show();
                            }
                        }
                        System.out.println(BlockButton.blocks);
                        if (BlockButton.blocks < 11) {
                            builder.setTitle("지뢰를 모두 찾았습니다!");
                            builder.setMessage("WIN! 게임을 재시작 할까요?");
                            builder.setPositiveButton("재시작", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    finish();
                                    startActivity(new Intent(MainActivity.this, MainActivity.class));
                                }
                            });
                            builder.setNegativeButton("종료", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    finishAffinity();
                                    System.runFinalization();
                                    System.exit(0);
                                }
                            });
                            builder.setCancelable(false);
                            builder.create().show();
                        }
                    }
                    public void breakBlock(View view) {
                        int x = ((BlockButton) view).x;
                        int y = ((BlockButton) view).y;
                        int val = ((BlockButton)view).cnt;
                        ((BlockButton) view).breakBlock();
                        for (int i=-1; i<=1; i++) {
                            for (int j=-1; j<=1; j++) {
                                if (x+i>=0 && x+i<9 && y+j>=0 && y+j<9) {
                                    if (val == 0 && !buttons[x+i][y+j].isClicked) {
                                        breakBlock(buttons[x+i][y+j]);
                                    }
                                }
                            }
                        }
                    };
                });

                buttons[i][j].setLayoutParams(layoutParams);
                tableRow.addView(buttons[i][j]);
            }
        }

        ToggleButton toggle = (ToggleButton)findViewById(R.id.toggleButton);
        toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) isChecked = true;
                else isChecked = false;
            }
        });
    }
}