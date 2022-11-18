package bricker.main;

import bricker.gameobjects.UserPaddle;
import danogl.GameManager;
import danogl.GameObject;
import danogl.gui.*;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import bricker.gameobjects.Ball;

import java.util.Random;

public class BrickerGameManager extends GameManager{
    private static final float BALL_SPEED = 250;

    public BrickerGameManager(String windowTitle, Vector2 windowDimensions) {
        super(windowTitle, windowDimensions);

    }


    public static void main(String[] args) {
        new BrickerGameManager("Bricker", new Vector2(700, 500)).run();

    }

    @Override
    public void initializeGame(ImageReader imageReader, SoundReader soundReader, UserInputListener inputListener, WindowController windowController) {
        super.initializeGame(imageReader, soundReader, inputListener, windowController);
        Vector2 windowDimensions = windowController.getWindowDimensions();
        // create ball
        createBall(imageReader, soundReader, windowDimensions);


        Renderable paddleImage = imageReader.readImage("assets/paddle.png", false);
        // create user paddle
        createUserPaddle(inputListener, windowDimensions, paddleImage);

        // create AI paddle
        createAIPaddle(windowDimensions, paddleImage);

    }

    private void createBall(ImageReader imageReader, SoundReader soundReader, Vector2 windowDimensions) {
        Renderable ballImage = imageReader.readImage("./assets/ball.png", true);
        Sound collisionSound = soundReader.readSound("assets/blop.wav");
        GameObject ball = new Ball(new Vector2(0, 0), new Vector2(20, 20), ballImage, collisionSound);
        ball.setCenter(windowDimensions.mult(0.5F));
        float ballVelX = BALL_SPEED;
        float ballVelY = BALL_SPEED;
        Random rand = new Random();
        if(rand.nextBoolean()){
            ballVelX *= -1;
        }
        if(rand.nextBoolean()){
            ballVelY *= -1;
        }
        ball.setVelocity(new Vector2(ballVelX, ballVelY));
        gameObjects().addGameObject(ball);
    }

    private void createAIPaddle(Vector2 windowDimensions, Renderable paddleImage) {
        GameObject aiPaddle = new GameObject(Vector2.ZERO, new Vector2(200, 20), paddleImage);
        aiPaddle.setCenter(new Vector2(windowDimensions.x()/2,30));
        gameObjects().addGameObject(aiPaddle);
    }

    private void createUserPaddle(UserInputListener inputListener, Vector2 windowDimensions, Renderable paddleImage) {
        GameObject userPaddle = new UserPaddle(Vector2.ZERO, new Vector2(200, 20), paddleImage,
                inputListener);
        userPaddle.setCenter(new Vector2(windowDimensions.x()/2, windowDimensions.y()-30));
        gameObjects().addGameObject(userPaddle);
    }
}
