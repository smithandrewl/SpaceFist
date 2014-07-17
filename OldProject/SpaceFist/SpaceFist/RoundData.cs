using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace SpaceFist
{
    /// <summary>
    /// Holds information about the current round.
    /// </summary>
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

        /// <summary>
        /// The players accuracy
        /// </summary>
        public float acc
        {
            get
            {
                return (EnemiesShot + BlocksShot) / (float)ShotsFired;
            }
        }
        /// <summary>
        /// The amount of time since the round started
        /// </summary>
        public TimeSpan TimeElapsed
        {
            get
            {
                return DateTime.Now - roundStart;
            }
        }

        /// <summary>
        /// The number of shots that the player fires on average per time period
        /// </summary>
        public float ShotsPerPeriod
        {
            get {
                return (float) ShotsFired / (TimeElapsed.Seconds / PERIOD_IN_SECONDS);
            }
        } 
        
        /// <summary>
        /// The number of bumps that the player has on average per time period
        /// </summary>
        public float BumpsPerTimePeriod
        {
            get
            {
                return (float) BlocksBumped / (TimeElapsed.Seconds / PERIOD_IN_SECONDS);
            }
        }

        /// <summary>
        /// Resets all of the values to their default values.
        /// </summary>
        public void Reset()
        {
            Lives        = 0;
            Score        = 0;
            BlocksBumped = 0;
            BlocksShot   = 0;
            EnemiesShot  = 0;
            ShotsFired   = 0;

            roundStart = DateTime.Now;
        }
    }
}
