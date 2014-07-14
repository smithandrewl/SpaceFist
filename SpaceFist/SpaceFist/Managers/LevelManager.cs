using FuncWorks.XNA.XTiled;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace SpaceFist.Managers
{
    public class LevelManager
    {
        private GameData gameData;

        public LevelManager(GameData gameData)
        {
            this.gameData = gameData;
        }

        public void Init()
        {
            string levelDirectory = gameData.Content.RootDirectory + "/Maps";

            int levelCount = System.IO.Directory.GetFiles(levelDirectory, "*.tmx", System.IO.SearchOption.TopDirectoryOnly).Length;

            gameData.LevelCount = levelCount;
        }

        public void LoadLevel(int id)
        {
            gameData.Level = new Level(gameData.Content.Load<Map>(@"Maps\" + id));
        }
    }
}
