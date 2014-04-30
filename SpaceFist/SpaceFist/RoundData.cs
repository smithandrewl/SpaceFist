using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace SpaceFist
{
    public class RoundData
    {
        private DateTime roundStart = DateTime.Now;

        private const short PERIOD_IN_SECONDS = 10;

        public int Lives        { get; set; }
        public int Score        { get; set; }
        public int EnemiesShot  { get; set; }
        public int BlocksShot   { get; set; }
        public int BlocksBumped { get; set; }
        public int ShotsFired   { get; set; }

        public float acc
        {
            get
            {
                return (EnemiesShot + BlocksShot) / (float)ShotsFired;
            }
        }

        public TimeSpan TimeElapsed
        {
            get
            {
                return DateTime.Now - roundStart;
            }
        }

        public float ShotsPerPeriod
        {
            get {
                return (float) ShotsFired / (TimeElapsed.Seconds / PERIOD_IN_SECONDS);
            }
        } 

        public float BumpsPerTimePeriod
        {
            get
            {
                return (float) BlocksBumped / (TimeElapsed.Seconds / PERIOD_IN_SECONDS);
            }
        }

        public void Reset()
        {
            BlocksBumped = 0;
            BlocksShot   = 0;
            EnemiesShot  = 0;
            ShotsFired   = 0;

            roundStart = DateTime.Now;
        }
    }
}
