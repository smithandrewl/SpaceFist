package com.spacefist;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.objects.EllipseMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Ellipse;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import org.jetbrains.annotations.NotNull;

public class Level {
    private int     height;
    private int     width;
    private String  title;
    private int     debrisParticleMinScale;
    private int     debrisParticleMaxScale;
    private String  debrisParticleImage;
    private int     debrisParticleCount;
    private String  song;
    private int     blockCount;
    private String  backgroundImage;
    private int     levelId;
    private boolean isLastLevel;

    private Array<SpawnPoint> mines;
    private Array<SpawnZone>  fighters;
    private Array<SpawnZone> freighters;

    public Level(@NotNull TiledMap map)
    {
        mines      = new Array<SpawnPoint>(false, 50);
        fighters   = new Array<SpawnZone>(false, 50);
        freighters = new Array<SpawnZone>(false, 50);

        MapProperties properties  = map.getProperties();
        MapLayer      objectLayer = map.getLayers().get(0);

        int heightInTiles = properties.get("height", Integer.class);
        int tileheight    = properties.get("tileheight", Integer.class);
        int widthInTiles  = properties.get("width", Integer.class);
        int tileWidth     = properties.get("tilewidth", Integer.class);

        height                 = heightInTiles * tileheight;
        width                  = widthInTiles  * tileWidth;

        title                  = properties.get("Level Title", String.class);
        debrisParticleMinScale = Integer.parseInt(properties.get("Debris Particle Min Scale", String.class));
        debrisParticleMaxScale = Integer.parseInt(properties.get("Debris Particle Max Scale", String.class));
        debrisParticleImage    = properties.get("Debris Particle Image", String.class);
        debrisParticleCount    = Integer.parseInt(properties.get("Debris Particle Count", String.class));
        song                   = properties.get("Song", String.class);
        blockCount             = Integer.parseInt(properties.get("Block Count", String.class));
        backgroundImage        = properties.get("Background Image", String.class);
        levelId                = Integer.parseInt(properties.get("Level ID", String.class));
        isLastLevel            = Boolean.parseBoolean(properties.get("Is Last Level", String.class));

        for (MapObject obj :  map.getLayers().get(0).getObjects()) {

            EllipseMapObject object = (EllipseMapObject) obj;

            String objectType = object.getProperties().get("type", String.class);

            int count = 1;

            if (object.getProperties().containsKey("count")) {
                count = Integer.parseInt(object.getProperties().get("count", String.class));
            }

            Ellipse bounds = object.getEllipse();
            Vector2 center = new Vector2(bounds.x + (bounds.width / 2), bounds.y + (bounds.height / 2));

            int left   = (int) bounds.x;
            int right  = (int) (bounds.x + bounds.width);
            int top    = (int) bounds.y;
            int bottom = (int) (bounds.y + bounds.height);

            //TODO: Enable loading of enemies from the map
            if(objectType != null) {
                if (objectType.equals("FighterZone")) {

                    fighters.add(
                            new SpawnZone(
                                    count,
                                    left,
                                    right,
                                    top,
                                    bottom,
                                    center
                            )
                    );
                }
            }
            /*else if (objectType.equals("FreighterZone")) {
                freighters.add(
                    new SpawnZone(
                        count,
                        left,
                        right,
                        top,
                        bottom,
                        center
                    )
                );
            }*/


            /*
            } else if (objectType.equals("Mines")) {
                mines.add(new SpawnPoint((int) center.x, (int) center.y));
            } */
        }
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public String getTitle() {
        return title;
    }

    public int getDebrisParticleMinScale() {
        return debrisParticleMinScale;
    }

    public int getDebrisParticleMaxScale() {
        return debrisParticleMaxScale;
    }

    public String getDebrisParticleImage() {
        return debrisParticleImage;
    }

    public int getDebrisParticleCount() {
        return debrisParticleCount;
    }

    public String getSong() {
        return song;
    }

    public int getBlockCount() {
        return blockCount;
    }

    public String getBackgroundImage() {
        return backgroundImage;
    }

    public int getLevelId() {
        return levelId;
    }

    public boolean isLastLevel() {
        return isLastLevel;
    }

    public Iterable<SpawnPoint> getMines() {
        return mines;
    }

    public Iterable<SpawnZone> getFighters() {
        return fighters;
    }

    public Iterable<SpawnZone> getFreighters() {
        return freighters;
    }
}
