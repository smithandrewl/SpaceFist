package com.spacefist.state;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.spacefist.GameData;
import com.spacefist.entities.Entity;
import com.spacefist.state.abst.GameState;

import java.util.Date;
import java.util.List;
import java.util.Random;

/*
 * The main state of the game.  All game play occurs in the InPlayState.
 */
public class InPlayState implements GameState {

    // The speed at which the camera scrolls up the map / world
    private static final float ScrollSpeed = 1.5f;


    GameData gameData;

    // TODO: Convert Hud
    //private Hud             hud;

    private Date levelLoadedAt;
    private List<Rectangle> debrisField;

    private boolean titleShown = false;

    // The portion of the world which is currently visible.
    public Rectangle getOnScreenWorld() {
        int screenWidth = (int) gameData.getResolution().getWidth();
        int screenHeight = (int) gameData.getResolution().getHeight();

        return new Rectangle((int) gameData.getCamera().x, (int) gameData.getCamera().y, screenWidth, screenHeight);
    }


    // It is used to measure playtime.
    // TODO: Convert Stopwatch
    //Stopwatch stopwatch = new Stopwatch();

    private Rectangle endOfLevelMarkerPos;

    public Rectangle getEndOfLevelMarkerPos() {
        return endOfLevelMarkerPos;
    }

    public void setEndOfLevelMarkerPos(Rectangle endOfLevelMarkerPos) {
        this.endOfLevelMarkerPos = endOfLevelMarkerPos;
    }

    public InPlayState(GameData gameData) {
        this.gameData = gameData;
    }

    public void LoadContent() {
        Rectangle resolution = gameData.getResolution();
        Rectangle screenRect = new Rectangle(0, 0, resolution.getWidth(), resolution.getHeight());

            /*
            TODO: Convert LevelManager

            gameData.LevelManager.Init();
            gameData.LevelManager.LoadLevel(1);
            */

        //debrisField    = new List<Rectangle>(gameData.getLevel().DebrisParticleCount);
        //gameData.setWorld(new Rectangle(0, 0, gameData.getLevel().getWidth()), gameData.getLevel().getHeight());

        // TODO: Convert Hud
        // hud            = new Hud(gameData, gameData.PlayerManager);
    }

    public void EnteringState() {
        // Reset the round statistics
        gameData.getRoundData().Reset();

        // TODO: Convert music playing code in InPlayState
        // Start playing music on a loop
        // MediaPlayer.Play(gameData.Songs[gameData.Level.Song]);
        // MediaPlayer.IsRepeating = true;

        Rectangle resolution = gameData.getResolution();

        // Position the camera at the bottom of the world
        gameData.setCamera(new Vector2(0, gameData.getWorld().getHeight() - resolution.getHeight()));

        // TODO: Convert PlayerManager, BlockManager, EnemyManager and PickupManager
            /*
            // Tell the ship manager to spawn the ship
            gameData.PlayerManager.Spawn();

            // Since the game states are reused, clear the score and lives
            gameData.PlayerManager.ResetLives();
            gameData.PlayerManager.ResetScore();
            gameData.PlayerManager.ResetWeapon();

            int numBlocks = gameData.Level.BlockCount;

            // Spawn blocks to the world
            gameData.BlockManager.SpawnBlocks(numBlocks);

            gameData.EnemyManager.Clear();
            gameData.EnemyMineManager.Clear();
            gameData.PickUpManager.Reset();

            //Spawn fighters
            foreach (var zone in gameData.Level.Fighters)
            {

                if(zone.Count > 1) {
                    gameData.EnemyManager.SpawnEnemies(
                        zone.Count,
                        zone.Left,
                        zone.Right,
                        zone.Top,
                        zone.Bottom, 
                        position => new EnemyFighter(gameData, position)
                    );
                }
                else
                {
                    gameData.EnemyManager.SpawnEnemy(zone.Center.X, zone.Center.Y, position => new EnemyFighter(gameData, position));
                }
            }

            // Spawn freighters
            foreach (var zone in gameData.Level.Freighters)
            {

                if (zone.Count > 1)
                {
                    gameData.EnemyManager.SpawnEnemies(
                        zone.Count,
                        zone.Left,
                        zone.Right,
                        zone.Top,
                        zone.Bottom,
                        position => new EnemyFreighter(gameData, position)
                    );
                }
                else
                {
                    gameData.EnemyManager.SpawnEnemy(zone.Center.X, zone.Center.Y, position => new EnemyFreighter(gameData, position));
                }
            }

            //Spawn mines
            foreach (var point in gameData.Level.Mines)
            {
                gameData.EnemyMineManager.SpawnEnemyMine(point.X, point.Y);
            }

            // Spawn the players ship
            gameData.PlayerManager.Initialize();

            // Spawn the different pickups to the world
            gameData.PickUpManager.Reset();
            gameData.PickUpManager.SpawnExtraLifePickups(3);
            gameData.PickUpManager.SpawnExamplePickups(4);
            gameData.PickUpManager.SpawnHealthPickups(4);

            */

        /***Dongcai*/
        // gameData.PickUpManager.SpawnLaserbeamPickups(5);
        // gameData.PickUpManager.SpawnMissilePickups(3);
        /**********/

        Random rand = new Random();

        debrisField.clear();

        // TODO: Convert Level and add Level property to GameData
            /*
            int    minScale      = gameData.Level.DebrisParticleMinScale;
            int    maxScale      = gameData.Level.DebrisParticleMaxScale;
            string particleImage = gameData.Level.DebrisParticleImage;
            int    DebrisCount   = gameData.Level.DebrisParticleCount;

            // init debris field
            for (int i = 0; i < DebrisCount; i++)
            {
                var maxX  = gameData.World.Width;
                var maxY  = gameData.World.Height;
                var scale = rand.Next(minScale * 10, maxScale * 10) * .01f;

                Rectangle rect = new Rectangle(
                    rand.Next(0, maxX), 
                    rand.Next(0, maxY), 
                    (int) (gameData.Textures[particleImage].Width  * scale), 
                    (int) (gameData.Textures[particleImage].Height * scale)
                );

                debrisField.Add(rect);
            }

            stopwatch.Reset();
            stopwatch.Start();

            levelLoadedAt = DateTime.Now;
            titleShown    = false;
            */
    }

    public void Update() {
        // TODO: Convert InplayState.Update
            /*
            if (gameData.PlayerManager.Alive)
            {
                KeyboardState keys = Keyboard.GetState();

                if (keys.IsKeyDown(Keys.Q) || keys.IsKeyDown(Keys.Escape))
                {
                    gameData.CurrentState = gameData.MenuState;
                }
                
                KeepOnScreen(gameData.Ship);

                // Tell the entity managers to update
                gameData.ProjectileManager.Update();
                gameData.BlockManager.Update();
                gameData.ExplosionManager.Update();
                gameData.CollisionManager.Update();
                gameData.PlayerManager.Update();
                gameData.EnemyManager.Update();
                gameData.PickUpManager.Update();
                gameData.EnemyMineManager.Update();
 
                // Until the end of the world is reached, move the camera up the world
                if (gameData.Camera.Y >= gameData.World.Y)
                {
                    gameData.Camera = new Vector2(gameData.Camera.X, gameData.Camera.Y - ScrollSpeed);
                }

                // When the ship reaches the end of game marker, switch to the 
                // end of level state.
                if (gameData.Ship.Rectangle.Intersects(EndOfLevelMarkerPos))
                {
                    if (gameData.Level.IsLastLevel)
                    {
                        gameData.CurrentState = gameData.EndOfGameState;
                    }
                    else
                    {
                        gameData.CurrentState = gameData.EndOfLevelState;
                    }
                }
            }
            else
            {
                // If the player has been killed, switch to the game over state
                gameData.CurrentState = gameData.GameOverState;
            }

            var mouse = Mouse.GetState();

            hud.Update();

            */
    }

    public void Draw() {
            /*
            String background    = gameData.Level.BackgroundImage;
            String particleImage = gameData.Level.DebrisParticleImage;

            // Draw the background
            gameData.SpriteBatch.Draw(gameData.Textures[background], gameData.Resolution, Color.White);

            // Draw debris
            foreach(var rect in debrisField)
            {
                gameData.SpriteBatch.Draw(
                    gameData.Textures[particleImage], 
                    new Rectangle(
                        rect.X - (int)gameData.Camera.X, 
                        rect.Y - (int)gameData.Camera.Y, 
                        rect.Width, 
                        rect.Height
                    ), 
                    Color.White
                );
            }

            // Draw the entities
            gameData.ExplosionManager.Draw();
            gameData.BlockManager.Draw();
            gameData.ProjectileManager.Draw();
            gameData.PlayerManager.Draw();
            gameData.EnemyManager.Draw();
            gameData.PickUpManager.Draw();
            gameData.EnemyMineManager.Draw();

            DrawLevelMarkers();

            hud.Draw();
            */
    }

    private void DrawLevelMarkers() {
            /*
            TODO: Convert InPlayState.DrawLevelMarkers
            int halfWidth  = (int)((gameData.World.Width / 2)    - gameData.Camera.X);
            int nearBottom = (int)((gameData.World.Bottom * .98) - gameData.Camera.Y);
            int nearTop    = (int)((gameData.World.Top * .02)    - gameData.Camera.Y);


            if (!titleShown)
            {
                var delta = (DateTime.Now - levelLoadedAt).TotalSeconds;

                // The title takes two seconds to fade in and then
                // is displayed for another second
                Color color = Color.White * ((float)(delta / 2));

                if (delta < 3)
                {
                    gameData.SpriteBatch.DrawString(
                        gameData.TitleFont,
                        gameData.Level.Title,
                        new Vector2(
                            (gameData.Resolution.Width / 2) - (gameData.Level.Title.Length * 10),
                            gameData.Resolution.Height / 2  - 48
                        ),
                        color,
                        0,
                        Vector2.Zero,
                        1f,
                        SpriteEffects.None,
                        0
                    );
                }
                else
                {
                    titleShown = true;
                }

            }
      
            EndOfLevelMarkerPos = new Rectangle(
                (int)halfWidth - (gameData.Textures["LevelEnd"].Width / 2),
                (int)((gameData.Level.Height / 8) - gameData.Camera.Y),
                gameData.Textures["LevelEnd"].Width,
                gameData.Textures["LevelEnd"].Height
            );

            gameData.SpriteBatch.Draw(
                gameData.Textures["LevelEnd"],
                EndOfLevelMarkerPos,
                Color.White
            );

            */
    }

    public void ExitingState() {
        // TODO: Convert InPlayState.ExitingState
        // MediaPlayer.Stop();
    }

    // Keep the player on the screen
    private void KeepOnScreen(Entity obj) {
        Rectangle resolution = gameData.getResolution();

        int farRight   = (int) (gameData.getCamera().x + resolution.getWidth());
        int Bottom     = (int) (gameData.getCamera().y + resolution.getHeight());
        int halfHeight = (int) (obj.getRectangle().getHeight() / 2);

        float velDecrease = .125f;

        boolean offScreenRight  = obj.getX() > farRight;
        boolean offScreenLeft   = obj.getX() < gameData.getCamera().x;
        boolean offscreenTop    = obj.getY() + halfHeight > Bottom;
        boolean offscreenBottom = obj.getY() < gameData.getCamera().y;

        boolean offScreen = offScreenRight ||
            offScreenLeft ||
            offscreenTop ||
            offscreenBottom;

        if (offScreen) {
            if (offScreenRight) {
                obj.setX((int) (farRight - obj.getRectangle().getWidth()));
            } else if (offScreenLeft) {
                obj.setX((int) gameData.getCamera().x);
            } else if (offscreenTop) {
                obj.setY((int) (Bottom - obj.getRectangle().getHeight()));
            } else if (offscreenBottom) {
                obj.setY((int) (gameData.getCamera().y + (obj.getRectangle().getHeight() / 16)));
            }

            float mult = -1 * velDecrease;

            obj.setVelocity(new Vector2(obj.getVelocity().x * mult, obj.getVelocity().y * mult));
        }
    }
}
