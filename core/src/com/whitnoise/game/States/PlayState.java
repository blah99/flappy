package com.whitnoise.game.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.whitnoise.game.FlappyDemo;
import com.whitnoise.game.sprites.Bird;
import com.whitnoise.game.sprites.Tube;

public class PlayState extends State {
    private static final int TUBE_SPACING = 125; //space between tubes
    private static final int TUBE_COUNT = 4; //how many tubes the game stores at any one time
    private static final int GROUND_Y_OFFSET = -50;

    private Bird bird;
    private Texture bg;
    private Texture ground;
    private Vector2 groundPos1, groundPos2;

    private Array<Tube> tubes;

    public PlayState(GameStateManager gsm) {
        super(gsm);
        bird = new Bird(50, 300);
        cam.setToOrtho(false, FlappyDemo.WIDTH / 2, FlappyDemo.HEIGHT / 2);
        System.out.println("before set texture bg ground");
        bg = new Texture("bg.png");
        ground = new Texture("ground.png");
        groundPos1 = new Vector2(cam.position.x - cam.viewportWidth / 2, GROUND_Y_OFFSET);
        groundPos2 = new Vector2((cam.position.x - cam.viewportWidth / 2) + ground.getWidth(), GROUND_Y_OFFSET);

        Tube.setTextures("toptube.png", "bottomtube.png");

        tubes = new Array<Tube>();
        System.out.println("after set texture tube");
        for (int i = 1; i <= TUBE_COUNT; i++) {
            tubes.add(new Tube(i * (TUBE_SPACING) + Tube.TUBE_WIDTH));
        }

    }

    @Override
    protected void handleInput() {
        if(Gdx.input.justTouched())
            bird.jump();
    }

    @Override
    public void update(float dt) {
        handleInput();
        updateGround();
        bird.update(dt);
        //update camera position based on where the bird is
        cam.position.x = bird.getPosition().x + 80;

        //reposition a tube when it gets off the camera viewpoint on the left
        for (Tube tube: tubes) {
            if(cam.position.x - (cam.viewportWidth / 2) > tube.getPosTopTube().x + tube.getTopTube().getWidth()) {
                //tube.reposition(tube.getPosTopTube().x + ((Tube.TUBE_WIDTH + TUBE_SPACING) * TUBE_COUNT));
                tube.reposition(tube.getPosTopTube().x + (TUBE_SPACING * TUBE_COUNT));
            }

            if(tube.collides(bird.getBounds())) {
                gsm.setToPlayState();
                break; //break here because once we have found a collision with a tube we dont need to check all the rest and it causes an error anyway.
            }

        }

        if(bird.getPosition().y <= ground.getHeight() + GROUND_Y_OFFSET) {
            gsm.setToPlayState();
        }

        //need to call update to tell libgdx that the camera has been repositioned
        cam.update();
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(cam.combined);
        sb.begin();
        sb.draw(bg, cam.position.x - cam.viewportWidth / 2, 0);
        sb.draw(bird.getTexture(), bird.getPosition().x, bird.getPosition().y);
        System.out.println(Tube.getTopTube().getHeight() + " ... " + Tube.getBottomTube().toString());

        for (Tube tube : tubes) {
            sb.draw(Tube.getTopTube(), tube.getPosTopTube().x, tube.getPosTopTube().y);
            sb.draw(Tube.getBottomTube(), tube.getPosBotTube().x, tube.getPosBotTube().y);
        }
        sb.draw(ground, groundPos1.x, groundPos1.y);
        sb.draw(ground, groundPos2.x, groundPos2.y);

        sb.end();
    }

    @Override
    public void dispose() {
        Tube.dispose();
        bg.dispose();
        bird.dispose();
        ground.dispose();

        //for(Tube tube : tubes)
        //    tube.dispose();      // cant dispose here because this playstate will only dispose AFTER the new playstate has called its constructor
        //Tube.dispose();
        System.out.println("Play State Disposed");

    }

    private void updateGround() {
        if(cam.position.x - (cam.viewportWidth / 2) > groundPos1.x + ground.getWidth())
            groundPos1.add(ground.getWidth() * 2, 0);
        if(cam.position.x - (cam.viewportWidth / 2) > groundPos2.x + ground.getWidth())
            groundPos2.add(ground.getWidth() * 2, 0);
    }
}

