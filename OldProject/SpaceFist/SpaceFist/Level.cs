using FuncWorks.XNA.XTiled;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace SpaceFist
{
    public class Level
    {
        private int    height;
        private int    width;
        private string title;
        private int    debrisParticleMinScale;
        private int    debrisParticleMaxScale;
        private string debrisParticleImage;
        private int    debrisParticleCount;
        private string song;
        private int    blockCount;
        private string backgroundImage;
        private int    levelId;
        private bool   isLastLevel;

        private List<SpawnPoint> mines;
        private List<SpawnZone>  fighters;
        private List<SpawnZone>  freighters;

        public int    Height                 { get { return height;                 } } 
        public int    Width                  { get { return width;                  } }
        public string Title                  { get { return title;                  } }
        public int    DebrisParticleMinScale { get { return debrisParticleMinScale; } }
        public int    DebrisParticleMaxScale { get { return debrisParticleMaxScale; } }
        public string DebrisParticleImage    { get { return debrisParticleImage;    } }
        public int    DebrisParticleCount    { get { return debrisParticleCount;    } }
        public string Song                   { get { return song;                   } }
        public int    BlockCount             { get { return blockCount;             } }
        public string BackgroundImage        { get { return backgroundImage;        } }
        public int    LevelId                { get { return levelId;                } }
        public bool   IsLastLevel            { get { return isLastLevel;            } }

        public  List<SpawnZone>  Fighters   { get { return fighters;   } }
        public  List<SpawnZone>  Freighters { get { return freighters; } }
        public  List<SpawnPoint> Mines      { get { return mines;      } }

        public Level(Map map)
        {
            mines      = new List<SpawnPoint>();
            fighters   = new List<SpawnZone>();
            freighters = new List<SpawnZone>();

            height                 = map.Height * map.TileHeight;
            width                  = map.Width  * map.TileWidth;
            title                  = map.Properties["Level Title"].Value;
            debrisParticleMinScale = map.Properties["Debris Particle Min Scale"].AsInt32.Value;
            debrisParticleMaxScale = map.Properties["Debris Particle Max Scale"].AsInt32.Value;
            debrisParticleImage    = map.Properties["Debris Particle Image"].Value;
            debrisParticleCount    = map.Properties["Debris Particle Count"].AsInt32.Value;
            song                   = map.Properties["Song"].Value;
            blockCount             = map.Properties["Block Count"].AsInt32.Value;
            backgroundImage        = map.Properties["Background Image"].Value;
            levelId                = map.Properties["Level ID"].AsInt32.Value;
            isLastLevel            = map.Properties["Is Last Level"].AsBoolean.Value;

            foreach (var fighter in map.ObjectLayers[0].MapObjects)
            {
                var bounds = fighter.Bounds;
                var count  = 1;

                if (fighter.Properties.ContainsKey("count"))
                {
                    count = fighter.Properties["count"].AsInt32 ?? 1;
                }

                if (fighter.Type == "FighterZone")
                {
                    Fighters.Add(
                        new SpawnZone(
                            count, 
                            bounds.Left, 
                            bounds.Right, 
                            bounds.Top, 
                            bounds.Bottom,
                            bounds.Center
                        )
                    );
                }
                else if (fighter.Type == "FreighterZone")
                {
                    Freighters.Add(
                        new SpawnZone(
                            count, 
                            bounds.Left, 
                            bounds.Right, 
                            bounds.Top, 
                            bounds.Bottom,
                            bounds.Center
                        )
                    );
                }
                else if (fighter.Type == "Mines")
                {
                    Mines.Add(new SpawnPoint(bounds.Center.X, bounds.Center.Y));
                }
            }
        }
    }
}
