using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using SpaceFist.State.Abstract;

namespace SpaceFist.AI
{
    public interface EnemyAI
    {
        EnemyAIState State {get; set;}
        void Update();
    }
}
