using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using SpaceFist.State.Abstract;

namespace SpaceFist.AI.DummyAI
{
    class DummyAI : EnemyAI
    {
        public State.Abstract.EnemyAIState State { get; set; }

        public DummyAI()
        {
            State = new DummyAIDummyState();
            State.EnteringState();
        }

        public void Update()
        {
            State.Update();
        }
    }
}
