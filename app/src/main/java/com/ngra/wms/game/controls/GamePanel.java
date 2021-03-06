package com.ngra.wms.game.controls;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.media.MediaPlayer;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.ngra.wms.R;
import com.ngra.wms.game.missiles.Missile_Benz;
import com.ngra.wms.game.missiles.Missile_Bicycle;
import com.ngra.wms.game.missiles.Missile_Corvate;
import com.ngra.wms.game.missiles.Missile_Lambo;
import com.ngra.wms.game.missiles.Missile_Police;
import com.ngra.wms.game.missiles.Missile_Red_Car;
import com.ngra.wms.game.mistake.Mistake_Apple;
import com.ngra.wms.game.mistake.Mistake_BabyDiaper;
import com.ngra.wms.game.mistake.Mistake_Banana;
import com.ngra.wms.game.mistake.Mistake_Chips;
import com.ngra.wms.game.mistake.Mistake_RecyclerBin;
import com.ngra.wms.game.scores.Fuel_Pomp;
import com.ngra.wms.game.scores.ScoreObject;
import com.ngra.wms.game.scores.Score_Bottle;
import com.ngra.wms.game.scores.Score_Coca;
import com.ngra.wms.game.scores.Score_PaperBox;
import com.ngra.wms.views.fragments.Game;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;


public class GamePanel extends SurfaceView implements SurfaceHolder.Callback {


    private int randomMisstake = 0;
    private int randomScore = 0;
    private int randomMissile = 0;
    private int SpeedLow;
    private boolean Fuel;
    private int FuelLevel = 12;
    private int FuelCounter = 0;
    private int GarbageCollection = 0;
    private GameActivity gameActivity;
    boolean SpeedUp = false;
    private int DrawObjectMissile = 0;
    private int DrawObjectScore = 0;
    private int DrawObjectFuel = 0;
    public static int WIDHT = 720;
    public static int HEIGHT = 1280;
    public static int HalfDeviceWidth = 0;
    public static int MOVESPEED = 10;
    private MainThread thread;
    private Background background;
    private Player player;
    private Kilometers kilometers;
    private Fuel_Arrow fuel_arrow;
    private boolean Broken = false;
    private float scaleFactorX;
    private float scaleFactorY;
    private MediaPlayer mediaPlayer = new MediaPlayer();
    private MediaPlayer mediaCoin;
    private MediaPlayer mediaBip;
    private MediaPlayer mediaCollision;
    private int PlayerHeat = 2;

    private ArrayList<Fuel_Pomp> fuelPomps;
    private long fuelStarttime;

    private ArrayList<Missile_Benz> missile_benzs;
    private long missileBenzStartTime;
    private ArrayList<Missile_Red_Car> missile_red_cars;
    private long missileRedCarStartTime;
    private ArrayList<Missile_Corvate> missile_corvates;
    private long missileCorvateStartTime;
    private ArrayList<Missile_Lambo> missile_lambos;
    private long missileLambostartTime;
    private ArrayList<Missile_Police> missile_polices;
    private long missilePoliceStartTime;
    private ArrayList<Missile_Bicycle> missile_bicycles;
    private long missileBicycleStartTime;


    private ArrayList<Score_Coca> score_cocas;
    private long scoreColaStatTime;
    private ArrayList<Score_PaperBox> score_paperBoxes;
    private long scorePaperBoxStartTime;
    private ArrayList<Score_Bottle> score_bottles;
    private long scoreBottleStartTime;


    private ScoreObject scoreObjectCoca;


    private ArrayList<Mistake_RecyclerBin> mistake_recyclerBins;
    private long mistakeRecyclerBinStartTime;
    private ArrayList<Mistake_BabyDiaper> mistake_babyDiapers;
    private long mistakeBabyDiaperStartTime;
    private ArrayList<Mistake_Chips> mistake_chips;
    private long mistakeChipsStartTime;
    private ArrayList<Mistake_Apple> mistake_apples;
    private long mistakeAppleStartTime;
    private ArrayList<Mistake_Banana> mistake_bananas;
    private long mistakeBananStartTime;


    private ArrayList<Coin_Score> coin_scores;


    public GamePanel(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        getHolder().addCallback(this);
        setFocusable(true);
    }

    public GamePanel(Context context, GameActivity gameActivity) {
        super(context);
        this.gameActivity = gameActivity;
        scaleFactorX = getWidth() / (WIDHT * 1.f);
        scaleFactorY = getHeight() / (HEIGHT * 1.f);
        Broken = false;
        getHolder().addCallback(this);
        setFocusable(true);
    }


    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        HalfDeviceWidth = WIDHT / 2;
    }


    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        int counter = 0;
        while (retry && counter < 1000) {
            counter++;
            try {
                thread.setRunning(false);
                thread.join();
                retry = false;
            } catch (InterruptedException e) {
                e.printStackTrace();

            }
        }
    }


    @Override
    public void surfaceCreated(SurfaceHolder holder) {

        thread = new MainThread(getHolder(), this);
        Fuel = true;
        background = new Background(BitmapFactory.decodeResource(getResources(), R.drawable.road2));
        player = new Player(BitmapFactory.decodeResource(getResources(), R.drawable.player),
                BitmapFactory.decodeResource(getResources(), R.drawable.mistake_player),
                110,
                290,
                4,
                getWidth(),
                getHeight());

        kilometers = new Kilometers(getResources()
                , 150, 150, WIDHT / 2, getWidth(), getHeight());

        fuel_arrow = new Fuel_Arrow(getResources()
                , 150, 150, WIDHT / 2, getWidth(), getHeight(), kilometers.getX(),
                kilometers.getWidth());


        if (mediaPlayer == null)
            mediaPlayer = new MediaPlayer();

        if (mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
        }


        if (mediaCoin == null)
            mediaCoin = new MediaPlayer();

        try {
            if (mediaCoin.isPlaying()) {
                mediaCoin.stop();
            }

            mediaCoin = null;
            mediaCoin = new MediaPlayer();
            String fileName = "android.resource://" + gameActivity.getPackageName() + "/" + R.raw.coin_sound;
            mediaCoin.setDataSource(gameActivity, Uri.parse(fileName));
            mediaCoin.prepare();

        } catch (Exception ignored) {
        }


        if (mediaBip == null)
            mediaBip = new MediaPlayer();

        try {
            if (mediaBip.isPlaying()) {
                mediaBip.stop();
            }

            mediaBip = null;
            mediaBip = new MediaPlayer();
            String fileName = "android.resource://" + gameActivity.getPackageName() + "/" + R.raw.bip_sound;
            mediaBip.setDataSource(gameActivity, Uri.parse(fileName));
            mediaBip.prepare();

        } catch (Exception ignored) {
        }


        try {
            mediaCollision = new MediaPlayer();
            String fileName = "android.resource://" + gameActivity.getPackageName() + "/" + R.raw.crash_car;
            mediaCollision.setDataSource(gameActivity, Uri.parse(fileName));
            mediaCollision.prepare();
        } catch (Exception ignored) {
        }

        missile_benzs = new ArrayList<>();
        missileBenzStartTime = System.nanoTime();

        missile_red_cars = new ArrayList<>();
        missileRedCarStartTime = System.nanoTime();

        missile_corvates = new ArrayList<>();
        missileCorvateStartTime = System.nanoTime();

        missile_lambos = new ArrayList<>();
        missileLambostartTime = System.nanoTime();

        missile_bicycles = new ArrayList<>();
        missileBicycleStartTime = System.nanoTime();

        score_cocas = new ArrayList<>();

        scoreColaStatTime = System.nanoTime();

        score_paperBoxes = new ArrayList<>();
        scorePaperBoxStartTime = System.nanoTime();

        score_bottles = new ArrayList<>();
        scoreBottleStartTime = System.nanoTime();

        mistake_recyclerBins = new ArrayList<>();
        mistakeRecyclerBinStartTime = System.nanoTime();

        mistake_babyDiapers = new ArrayList<>();
        mistakeBabyDiaperStartTime = System.nanoTime();

        mistake_chips = new ArrayList<>();
        mistakeChipsStartTime = System.nanoTime();

        mistake_apples = new ArrayList<>();
        mistakeAppleStartTime = System.nanoTime();

        mistake_bananas = new ArrayList<>();
        mistakeBananStartTime = System.nanoTime();

        coin_scores = new ArrayList<>();

        fuelPomps = new ArrayList<>();
        fuelStarttime = System.nanoTime();

        missile_polices = new ArrayList<>();
        missilePoliceStartTime = System.nanoTime();


        thread.setRunning(true);
        thread.start();
    }


    public void StopGame() {

        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer = null;
        }

        if (mediaCollision != null) {
            mediaCollision.stop();
            mediaCollision = null;
        }

        if (mediaBip != null) {
            mediaBip.stop();
            mediaBip = null;
        }

        if (mediaCoin != null) {
            mediaCoin.stop();
            mediaCoin = null;
        }

        thread.setRunning(false);
        player.setPlaying(false);
    }


    private void SetMediaPlayer(Boolean Start) {
        try {
            mediaPlayer = null;
            mediaPlayer = new MediaPlayer();
            String fileName;
            if (Start)
                fileName = "android.resource://" + gameActivity.getPackageName() + "/" + R.raw.truck_sound;
            else {
                fileName = "android.resource://" + gameActivity.getPackageName() + "/" + R.raw.truck_restart;
                mediaPlayer.setLooping(true);
            }
            mediaPlayer.setDataSource(gameActivity, Uri.parse(fileName));
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {


        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (!player.getPlaying() && !Broken) {
                SetMediaPlayer(true);
                player.setPlaying(true);
                if (MOVESPEED > 11)
                    mediaPlayer.seekTo(10 * 1000);
                if (MOVESPEED > 17)
                    mediaPlayer.seekTo(20 * 1000);
                if (MOVESPEED > 25)
                    mediaPlayer.seekTo(40 * 1000);
                if (MOVESPEED > 37)
                    mediaPlayer.seekTo(120 * 1000);
            } else if (Broken) {
                gameActivity.ResetGame(GarbageCollection, player.getScoreForSpeed());
            }
            return true;
        }


        if (event.getAction() == MotionEvent.ACTION_UP) {
            if (Broken) {
                gameActivity.ResetGame(GarbageCollection, player.getScoreForSpeed());
            }
            return true;
        }


        if (event.getAction() == MotionEvent.ACTION_MOVE) {
            if (!Broken) {
                int newX = Math.round(event.getRawX());
                float scaleFactorX = getWidth() / (WIDHT * 1.f);
                newX = Math.round(newX / scaleFactorX);
                player.setNewX(newX, HalfDeviceWidth);
            }
            return true;
        }

        return super.onTouchEvent(event);
    }


    private void CoinSound() {
        mediaCoin.start();
    }


    public void update() {


        if (player.getPlaying()) {
            background.update();
            player.update();


            if (Fuel) {
                int score = player.getScoreForSpeed() % 70;
                if (score < 6) {
                    if (!SpeedUp) {
                        player.setSpeedScore();
                        SpeedUp = true;
                        MOVESPEED = MOVESPEED + 2;
                        int maxSpeed = 80;
                        if (MOVESPEED > maxSpeed) {
                            MOVESPEED = maxSpeed;
                        }
                        background.SetSpeedBackground();
                        player.SetDelayAnimation();
                    }
                } else
                    SpeedUp = false;
            } else {
                SpeedUp = false;
                if (SpeedLow >= 20) {
                    SpeedLow = 0;
                    MOVESPEED = MOVESPEED - 2;
                    player.setLowOfSpeedScore();
                    background.SetSpeedBackground();
                    player.SetLowOfDelayAnimation();
                    if (MOVESPEED < 1) {
                        player.setPlaying(false);
                        PlayerHeat--;
                        FuelLevel = 12;
                        MOVESPEED = 10;
                        background.SetSpeedBackground();
                        for (int i = 0; i < 6; i++)
                            player.setSpeedScore();
                    }

                } else
                    SpeedLow++;
            }


            if (Fuel) {
                if (FuelCounter > 500) {
                    FuelLevel--;
                    FuelCounter = 1;
                    if (FuelLevel == 0) {
                        Fuel = false;
                        SetMediaPlayer(false);
                    }
                } else FuelCounter += Math.round(player.getSpeedScore() * 75 / 100);
            } else {
                if (FuelLevel > 0) {
                    if (FuelCounter > 100) {
                        Fuel = true;
                        FuelCounter = 0;
                        if (MOVESPEED > 11)
                            mediaPlayer.seekTo(10 * 1000);
                        if (MOVESPEED > 17)
                            mediaPlayer.seekTo(20 * 1000);
                        if (MOVESPEED > 25)
                            mediaPlayer.seekTo(40 * 1000);
                        if (MOVESPEED > 37)
                            mediaPlayer.seekTo(120 * 1000);
                    } else
                        FuelCounter += player.getSpeedScore();
                }
            }


            if (MOVESPEED > 1) {

                if (DrawObjectFuel > 1500) {
                    DrawObjectFuel = 0;
                    DrawFuel();
                } else {

                    DrawObjectFuel += player.getSpeedScore();
                }


                if (DrawObjectMissile > 450) {
                    DrawObjectMissile = 0;
                    DrawMissiles();
                    DrawMistakes();
                } else {

                    DrawObjectMissile += player.getSpeedScore();
                }


                if (DrawObjectScore > 130) {
                    DrawObjectScore = 0;
                    DrawGarbage();
                } else {

                    DrawObjectScore += player.getSpeedScore();
                }


                UpdateObjectMissile();
                CollisionMissile();

                UpdateObjectScore();
                CollisionGarbageCollection();


                UpdateObjectMistake();
                MistakeCollisionGarbageCollection();

                UpdateCoin();
            }


        }
    }


    private void UpdateCoin() {
        for (int i = 0; i < coin_scores.size(); i++) {
            coin_scores.get(i).update();
            if (coin_scores.get(i).getCount() > 8)
                coin_scores.remove(i);
        }
    }


    private void GetCoinScore() {
        coin_scores.add(new Coin_Score(
                getResources()
                , HalfDeviceWidth,
                getWidth(),
                getHeight(),
                player.getX(),
                player.getY()
        ));
    }


    private void UpdateObjectMissile() {
        for (int i = 0; i < missile_benzs.size(); i++) {
            missile_benzs.get(i).update();
        }

        for (int i = 0; i < missile_red_cars.size(); i++) {
            missile_red_cars.get(i).update();
        }


        for (int i = 0; i < missile_corvates.size(); i++) {
            missile_corvates.get(i).update();
        }


        for (int i = 0; i < missile_lambos.size(); i++) {
            missile_lambos.get(i).update();
        }


    }


    private void UpdateObjectMistake() {

        for (int i = 0; i < mistake_recyclerBins.size(); i++) {
            mistake_recyclerBins.get(i).update();
        }

    }


    private void UpdateObjectScore() {

        for (int i = 0; i < score_cocas.size(); i++) {
            score_cocas.get(i).update();
        }

        for (int i = 0; i < score_paperBoxes.size(); i++) {
            score_paperBoxes.get(i).update();
        }
    }


    private void CollisionMissile() {

        for (int i = 0; i < missile_benzs.size(); i++) {
            missile_benzs.get(i).update();
            if (collisionMissile(missile_benzs.get(i), player)) {
                missile_benzs.remove(i);
                player.MistakeCollection();
                player.setPlaying(false);
                break;
            }

            if (missile_benzs.get(i).getY() > HEIGHT + 100) {
                missile_benzs.remove(i);
                break;
            }
        }


        for (int i = 0; i < missile_red_cars.size(); i++) {
            missile_red_cars.get(i).update();
            if (collisionMissile(missile_red_cars.get(i), player)) {
                missile_red_cars.remove(i);
                player.MistakeCollection();
                player.setPlaying(false);
                break;
            }

            if (missile_red_cars.get(i).getY() > HEIGHT + 100) {
                missile_red_cars.remove(i);
                break;
            }
        }


        for (int i = 0; i < missile_corvates.size(); i++) {
            missile_corvates.get(i).update();
            if (collisionMissile(missile_corvates.get(i), player)) {
                missile_corvates.remove(i);
                player.MistakeCollection();
                player.setPlaying(false);
                break;
            }

            if (missile_corvates.get(i).getY() > HEIGHT + 100) {
                missile_corvates.remove(i);
                break;
            }
        }


        for (int i = 0; i < missile_polices.size(); i++) {
            missile_polices.get(i).update();
            if (collisionMissile(missile_polices.get(i), player)) {
                missile_polices.remove(i);
                player.MistakeCollection();
                player.setPlaying(false);
                break;
            }

            if (missile_polices.get(i).getY() > HEIGHT + 100) {
                missile_polices.remove(i);
                break;
            }
        }

        for (int i = 0; i < missile_lambos.size(); i++) {
            missile_lambos.get(i).update();
            if (collisionMissile(missile_lambos.get(i), player)) {
                missile_lambos.remove(i);
                player.MistakeCollection();
                player.setPlaying(false);
                break;
            }

            if (missile_lambos.get(i).getY() > HEIGHT + 100) {
                missile_lambos.remove(i);
                break;
            }
        }


        for (int i = 0; i < missile_bicycles.size(); i++) {
            missile_bicycles.get(i).update();
            if (collisionMissile(missile_bicycles.get(i), player)) {
                missile_bicycles.remove(i);
                player.MistakeCollection();
                player.setPlaying(false);
                break;
            }

            if (missile_bicycles.get(i).getY() > HEIGHT + 100) {
                missile_bicycles.remove(i);
                break;
            }
        }
    }


    private void MistakeCollisionGarbageCollection() {

        for (int i = 0; i < mistake_recyclerBins.size(); i++) {
            Mistake_RecyclerBin bin = mistake_recyclerBins.get(i);
            bin.update();
            int score = bin.getScore();
            if (collisionScore(mistake_recyclerBins.get(i), player)) {
                mistake_recyclerBins.remove(i);
                GarbageCollection -= score;
                mediaBip.start();
                player.MistakeCollection();
                break;
            }

            if (bin.getY() > HEIGHT + 100) {
                mistake_recyclerBins.remove(i);
                break;
            }
        }

        for (int i = 0; i < mistake_babyDiapers.size(); i++) {
            Mistake_BabyDiaper babyDiaper = mistake_babyDiapers.get(i);
            babyDiaper.update();
            int score = babyDiaper.getScore();
            if (collisionScore(mistake_babyDiapers.get(i), player)) {
                mistake_babyDiapers.remove(i);
                GarbageCollection -= score;
                mediaBip.start();
                player.MistakeCollection();
                break;
            }

            if (babyDiaper.getY() > HEIGHT + 100) {
                mistake_babyDiapers.remove(i);
                break;
            }
        }

        for (int i = 0; i < mistake_chips.size(); i++) {
            Mistake_Chips chips = mistake_chips.get(i);
            chips.update();
            int score = chips.getScore();
            if (collisionScore(mistake_chips.get(i), player)) {
                mistake_chips.remove(i);
                GarbageCollection -= score;
                mediaBip.start();
                player.MistakeCollection();
                break;
            }

            if (mistake_chips.get(i).getY() > HEIGHT + 100) {
                mistake_chips.remove(i);
                break;
            }
        }

        for (int i = 0; i < mistake_apples.size(); i++) {
            Mistake_Apple apple = mistake_apples.get(i);
            apple.update();
            int score = apple.getScore();
            if (collisionScore(mistake_apples.get(i), player)) {
                mistake_apples.remove(i);
                GarbageCollection -= score;
                mediaBip.start();
                player.MistakeCollection();
                break;
            }

            if (mistake_apples.get(i).getY() > HEIGHT + 100) {
                mistake_apples.remove(i);
                break;
            }
        }

        for (int i = 0; i < mistake_bananas.size(); i++) {
            Mistake_Banana banana = mistake_bananas.get(i);
            banana.update();
            int score = banana.getScore();
            if (collisionScore(mistake_bananas.get(i), player)) {
                mistake_bananas.remove(i);
                GarbageCollection -= score;
                mediaBip.start();
                player.MistakeCollection();
                break;
            }

            if (mistake_bananas.get(i).getY() > HEIGHT + 100) {
                mistake_bananas.remove(i);
                break;
            }
        }

        if (GarbageCollection < 0) {
            if (PlayerHeat <= 0) {
                Broken = true;
                player.setPlaying(false);
                mediaPlayer.stop();
                mediaPlayer.release();
                mediaPlayer = null;
                mediaCollision.start();
            } else {
                player.setPlaying(false);
                PlayerHeat--;
                FuelLevel = 12;
                GarbageCollection = 0;
            }
        }

    }


    private void CollisionGarbageCollection() {

        for (int i = 0; i < fuelPomps.size(); i++) {

            fuelPomps.get(i).update();
            if (collisionScore(fuelPomps.get(i), player)) {
                fuelPomps.remove(i);
                FuelLevel += 3;
                if (FuelLevel > 12)
                    FuelLevel = 12;

                GetCoinScore();
                if (!Fuel) {
                    SetMediaPlayer(true);
                } else {
                    CoinSound();
                }
                break;
            }

            if (fuelPomps.get(i).getY() > HEIGHT + 100) {
                fuelPomps.remove(i);
                break;
            }
        }

        for (int i = 0; i < score_cocas.size(); i++) {
            Score_Coca coca = score_cocas.get(i);
            coca.update();
            int score = coca.getScore();
            if (collisionScore(score_cocas.get(i), player)) {
                score_cocas.remove(i);
                GarbageCollection += score;
                CoinSound();
                GetCoinScore();
                break;
            }

            if (score_cocas.get(i).getY() > HEIGHT + 100) {
                score_cocas.remove(i);
                break;
            }
        }


        for (int i = 0; i < score_paperBoxes.size(); i++) {
            Score_PaperBox box = score_paperBoxes.get(i);
            box.update();
            int score = box.getScore();
            if (collisionScore(score_paperBoxes.get(i), player)) {
                score_paperBoxes.remove(i);
                GarbageCollection += score;
                CoinSound();
                GetCoinScore();
                break;
            }

            if (score_paperBoxes.get(i).getY() > HEIGHT + 100) {
                score_paperBoxes.remove(i);
                break;
            }
        }


        for (int i = 0; i < score_bottles.size(); i++) {
            Score_Bottle bottle = score_bottles.get(i);
            bottle.update();
            int score = bottle.getScore();
            if (collisionScore(score_bottles.get(i), player)) {
                score_bottles.remove(i);
                GarbageCollection += score;
                CoinSound();
                GetCoinScore();
                break;
            }

            if (score_bottles.get(i).getY() > HEIGHT + 100) {
                score_bottles.remove(i);
                break;
            }
        }

    }


    private void DrawFuel() {
        long fuelElapsed = (System.nanoTime() - fuelStarttime) / 1000000;
        if (fuelElapsed > (2000) - player.getScoreForSpeed() / 4) {
            int min = 1;
            int max = WIDHT;
            int random = new Random().nextInt((max - min) + 1) + min;
            fuelPomps.add(
                    new Fuel_Pomp(
                            getResources(),
                            random,
                            -91,
                            80,
                            91,
                            player.getScoreForSpeed(),
                            HalfDeviceWidth,
                            getWidth(),
                            getHeight()
                    )
            );
        }
    }


    private void DrawGarbage() {
        int min = 1;
        int max = 3;
        int random = new Random().nextInt((max - min) + 1) + min;
        while (randomScore == random) {
            random = new Random().nextInt((max - min) + 1) + min;
        }
        randomScore = random;
        if (random == 1) {
            long scoreCocaElapsed = (System.nanoTime() - scoreColaStatTime) / 1000000;
            if (scoreCocaElapsed > (2000) - player.getScoreForSpeed() / 4) {
                min = 1;
                max = WIDHT;
                random = new Random().nextInt((max - min) + 1) + min;
                score_cocas.add(
                        new Score_Coca(
                                getResources(),
                                random,
                                -85,
                                85,
                                85,
                                player.getScoreForSpeed(),
                                HalfDeviceWidth,
                                getWidth(),
                                getHeight(),
                                10
                        )
                );
            }
        } else if (random == 2) {
            long scorePaperElapsed = (System.nanoTime() - scorePaperBoxStartTime) / 1000000;
            if (scorePaperElapsed > (2000) - player.getScoreForSpeed() / 4) {
                min = 1;
                max = WIDHT;
                random = new Random().nextInt((max - min) + 1) + min;
                score_paperBoxes.add(
                        new Score_PaperBox(
                                getResources(),
                                random,
                                -61,
                                80,
                                61,
                                player.getScoreForSpeed(),
                                HalfDeviceWidth,
                                getWidth(),
                                getHeight(),
                                5
                        )
                );
            }
        } else if (random == 3) {
            long scoreBottleElapsed = (System.nanoTime() - scoreBottleStartTime) / 1000000;
            if (scoreBottleElapsed > (2000) - player.getScoreForSpeed() / 4) {
                min = 1;
                max = WIDHT;
                random = new Random().nextInt((max - min) + 1) + min;
                score_bottles.add(
                        new Score_Bottle(
                                getResources(),
                                random,
                                -65,
                                65,
                                65,
                                player.getScoreForSpeed(),
                                HalfDeviceWidth,
                                getWidth(),
                                getHeight(),
                                15
                        )
                );
            }
        }
    }


    private void DrawMistakes() {
        int min = 1;
        int max = 5;
        int random = new Random().nextInt((max - min) + 1) + min;
        while (randomMisstake == random) {
            random = new Random().nextInt((max - min) + 1) + min;
        }
        randomMisstake = random;
        if (random == 1) {
            long mistakeRecyclerElapsed = (System.nanoTime() - mistakeRecyclerBinStartTime) / 1000000;
            if (mistakeRecyclerElapsed > (2000) - player.getScoreForSpeed() / 4) {
                min = 1;
                max = WIDHT;
                random = new Random().nextInt((max - min) + 1) + min;
                mistake_recyclerBins.add(
                        new Mistake_RecyclerBin(
                                getResources(),
                                random,
                                -80,
                                55,
                                80,
                                player.getScoreForSpeed(),
                                HalfDeviceWidth,
                                getWidth(),
                                getHeight(),
                                20
                        )
                );
            }

        } else if (random == 2) {
            long mistakeBabyElapsed = (System.nanoTime() - mistakeBabyDiaperStartTime) / 1000000;
            if (mistakeBabyElapsed > (2000) - player.getScoreForSpeed() / 4) {
                min = 1;
                max = WIDHT;
                random = new Random().nextInt((max - min) + 1) + min;
                mistake_babyDiapers.add(
                        new Mistake_BabyDiaper(
                                getResources(),
                                random,
                                -70,
                                70,
                                70,
                                player.getScoreForSpeed(),
                                HalfDeviceWidth,
                                getWidth(),
                                getHeight(),
                                20
                        )
                );
            }
        } else if (random == 3) {
            long mistakeChipsElapsed = (System.nanoTime() - mistakeChipsStartTime) / 1000000;
            if (mistakeChipsElapsed > (2000) - player.getScoreForSpeed() / 4) {
                min = 1;
                max = WIDHT;
                random = new Random().nextInt((max - min) + 1) + min;
                mistake_chips.add(
                        new Mistake_Chips(
                                getResources(),
                                random,
                                -78,
                                70,
                                78,
                                player.getScoreForSpeed(),
                                HalfDeviceWidth,
                                getWidth(),
                                getHeight(),
                                20
                        )
                );
            }
        } else if (random == 4) {
            long mistakeAppleElapsed = (System.nanoTime() - mistakeAppleStartTime) / 1000000;
            if (mistakeAppleElapsed > (2000) - player.getScoreForSpeed() / 4) {
                min = 1;
                max = WIDHT;
                random = new Random().nextInt((max - min) + 1) + min;
                mistake_apples.add(
                        new Mistake_Apple(
                                getResources(),
                                random,
                                -81,
                                55,
                                81,
                                player.getScoreForSpeed(),
                                HalfDeviceWidth,
                                getWidth(),
                                getHeight(),
                                20
                        )
                );
            }
        } else if (random == 5) {
            long mistakeBananaElapsed = (System.nanoTime() - mistakeBananStartTime) / 1000000;
            if (mistakeBananaElapsed > (2000) - player.getScoreForSpeed() / 4) {
                min = 1;
                max = WIDHT;
                random = new Random().nextInt((max - min) + 1) + min;
                mistake_bananas.add(
                        new Mistake_Banana(
                                getResources(),
                                random,
                                -100,
                                100,
                                100,
                                player.getScoreForSpeed(),
                                HalfDeviceWidth,
                                getWidth(),
                                getHeight(),
                                20
                        )
                );
            }
        }
    }


    private void DrawMissiles() {
        int min = 0;
        int max = 6;
        int random = new Random().nextInt((max - min) + 1) + min;
        while (randomMissile == random) {
            random = new Random().nextInt((max - min) + 1) + min;
        }
        randomMissile = random;
        if (random == 1) {
            long missileBenzElapsed = (System.nanoTime() - missileBenzStartTime) / 1000000;
            if (missileBenzElapsed > (2000) - player.getScoreForSpeed() / 4) {
                min = 1;
                max = WIDHT;
                random = new Random().nextInt((max - min) + 1) + min;
                missile_benzs.add(
                        new Missile_Benz(
                                getResources(),
                                random,
                                -162,
                                80,
                                162,
                                player.getScoreForSpeed(),
                                4,
                                HalfDeviceWidth,
                                getWidth(),
                                getHeight()
                        )
                );
            }

        } else if (random == 2) {

            long missileRedCarElapsed = (System.nanoTime() - missileRedCarStartTime) / 1000000;
            if (missileRedCarElapsed > (2000) - player.getScoreForSpeed() / 4) {
                min = 1;
                max = WIDHT;
                random = new Random().nextInt((max - min) + 1) + min;
                missile_red_cars.add(
                        new Missile_Red_Car(
                                getResources(),
                                random,
                                -171,
                                80,
                                171,
                                player.getScoreForSpeed(),
                                4,
                                HalfDeviceWidth,
                                getWidth(),
                                getHeight()
                        )
                );
            }
        } else if (random == 3) {
            long missileCorvateElapsed = (System.nanoTime() - missileCorvateStartTime) / 1000000;
            if (missileCorvateElapsed > (2000) - player.getScoreForSpeed() / 4) {
                min = 1;
                max = WIDHT;
                random = new Random().nextInt((max - min) + 1) + min;
                missile_corvates.add(
                        new Missile_Corvate(
                                getResources(),
                                random,
                                -162,
                                80,
                                161,
                                player.getScoreForSpeed(),
                                4,
                                HalfDeviceWidth,
                                getWidth(),
                                getHeight()
                        )
                );
            }
        } else if (random == 4) {
            long missileLamboElapsed = (System.nanoTime() - missileLambostartTime) / 1000000;
            if (missileLamboElapsed > (2000) - player.getScoreForSpeed() / 4) {
                min = 1;
                max = WIDHT;
                random = new Random().nextInt((max - min) + 1) + min;
                missile_lambos.add(
                        new Missile_Lambo(
                                getResources(),
                                random,
                                -166,
                                80,
                                166,
                                player.getScoreForSpeed(),
                                4,
                                HalfDeviceWidth,
                                getWidth(),
                                getHeight()
                        )
                );
            }
        } else if (random == 5) {
            long missilePoliceElapsed = (System.nanoTime() - missilePoliceStartTime) / 1000000;
            if (missilePoliceElapsed > (2000) - player.getScoreForSpeed() / 4) {
                min = 1;
                max = WIDHT;
                random = new Random().nextInt((max - min) + 1) + min;
                missile_polices.add(
                        new Missile_Police(
                                getResources(),
                                random,
                                -166,
                                80,
                                160,
                                player.getScoreForSpeed(),
                                4,
                                HalfDeviceWidth,
                                getWidth(),
                                getHeight()
                        )
                );
            }
        } else if (random == 6) {
            long missileBicycleElapsed = (System.nanoTime() - missileBicycleStartTime) / 1000000;
            if (missileBicycleElapsed > (2000) - player.getScoreForSpeed() / 4) {
                min = WIDHT / 2;
                max = WIDHT;
                random = new Random().nextInt((max - min) + 1) + min;
                missile_bicycles.add(
                        new Missile_Bicycle(
                                getResources(),
                                random,
                                -143,
                                80,
                                143,
                                player.getScoreForSpeed(),
                                6,
                                HalfDeviceWidth,
                                getWidth(),
                                getHeight()
                        )
                );
            }
        }
    }


    public boolean collisionMissile(GameObject a, GameObject b) {
        if (Rect.intersects(a.getRectangle(), b.getRectangle())) {
            PlayerHeat--;
            FuelLevel = 12;
            if (PlayerHeat <= 0) {
                player.setPlaying(false);
                Broken = true;
                mediaPlayer.stop();
                mediaPlayer.release();
                mediaPlayer = null;
            } else {
                mediaPlayer.pause();
            }
            mediaCollision.start();
            return true;
        }
        return false;
    }


    public boolean collisionScore(GameObject a, GameObject b) {
        return Rect.intersects(a.getRectangle(), b.getRectangle());
    }


    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);

        if (canvas != null) {
            final int savedState = canvas.save();

            scaleFactorX = getWidth() / (WIDHT * 1.f);
            scaleFactorY = getHeight() / (HEIGHT * 1.f);

            canvas.scale(scaleFactorX, scaleFactorY);
            background.draw(canvas);
            player.draw(canvas);


            for (Missile_Benz benz : missile_benzs)
                benz.draw(canvas);

            for (Missile_Red_Car red_car : missile_red_cars)
                red_car.draw(canvas);


            for (Missile_Corvate corvate : missile_corvates)
                corvate.draw(canvas);

            for (Missile_Lambo lambos : missile_lambos)
                lambos.draw(canvas);

            for (Missile_Bicycle bicycle : missile_bicycles)
                bicycle.draw(canvas);

            for (Score_Coca coca : score_cocas)
                coca.draw(canvas);

            for (Score_PaperBox paperBox : score_paperBoxes)
                paperBox.draw(canvas);

            for (Score_Bottle bottle : score_bottles)
                bottle.draw(canvas);

            for (Mistake_RecyclerBin recyclerBin : mistake_recyclerBins)
                recyclerBin.draw(canvas);

            for (Mistake_BabyDiaper babyDiaper : mistake_babyDiapers)
                babyDiaper.draw(canvas);

            for (Mistake_Chips chips : mistake_chips)
                chips.draw(canvas);

            for (Coin_Score coinScore : coin_scores)
                coinScore.draw(canvas);


            for (Fuel_Pomp fuelPomp : fuelPomps)
                fuelPomp.draw(canvas);

            for (Missile_Police police : missile_polices)
                police.draw(canvas);

            for (Mistake_Apple apple : mistake_apples)
                apple.draw(canvas);

            for (Mistake_Banana banana : mistake_bananas)
                banana.draw(canvas);

            drawScoreText(canvas);
            canvas.restoreToCount(savedState);
        }
    }


    private void drawScoreText(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(Color.LTGRAY);
        paint.setTextSize(40);
        paint.setTypeface(gameActivity.textView.getTypeface());
        paint.setTextAlign(Paint.Align.RIGHT);

        canvas.drawBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.single_coin), WIDHT - 75, 10, null);
        canvas.drawText(String.valueOf(GarbageCollection), WIDHT - 80, 50, paint);


        int left = 30;
        for (int i = 0; i < PlayerHeat; i++) {
            canvas.drawBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.heart), left, 10, null);
            left += 65;
        }

        paint.setTextSize(18);
        paint.setTextAlign(Paint.Align.CENTER);
        kilometers.draw(canvas, player.getSpeedScore(), player.getScoreForSpeed(), paint, player.getPlaying());

        fuel_arrow.draw(canvas, FuelLevel);
//
//
//        paint.setTextAlign(Paint.Align.LEFT);
//        canvas.drawText("مقاومت : " + player.getScore(), 25, 30, paint);
//
//        paint.setTextAlign(Paint.Align.RIGHT);
//        canvas.drawText("سرعت  : " + MOVESPEED + " KM", WIDHT - 25, HEIGHT - 50, paint);

    }

}
