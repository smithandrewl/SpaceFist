using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Microsoft.Xna.Framework;

namespace SpaceFist
{
    public class GameData
    {
        public int finalScore;
        public long second;
        public long minute;


        public GameData(int finalScore=0)
        {
            this.finalScore = finalScore;
            this.second = 0;
            this.minute = 0;
        }

        
        public int FinalScore
        {

            get
            {
                return this.finalScore;
            }

            set
            {
                this.finalScore = value;
            }
        }
        /*
        ConvertToMS() function is used to convert playtime represented by millisecond to 
        seconds.
        */
        public void  ConvertToSecond(long playtime)
        {
            this.second = playtime/1000;
            if (this.second >= 60)
            {
                this.minute = this.second / 60;
                this.second = this.second - this.minute * 60;
            }
            

        }
        
    }


}
