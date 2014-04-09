// Learn more about F# at http://fsharp.net
// See the 'F# Tutorial' project for more help.
open System

let generateFuzzySet (name: String, low: float, high: float): Unit =
    let med:float = (high - low) / 2.0
    let div:float = (high - low) / 3.0
    let overlap:float = div * 0.25

    let gradeFormat = 
        "public float {0}{1} {{\n" +
         "    get {{\n" +
         "        return Grade(value, {2}, {3});\n" +
         "    }}\n"+
         "}}\n"

    let reverseGradeFormat = 
        "public float {0}{1} {{\n" +
        "    get {{\n" +
        "        return ReverseGrade(value, {2}, {3});\n" +
        "    }}\n" +
        "}}\n" 
    
    let triangleFormat = 
        "public float {0}{1} {{\n" +
        "    get {{\n" +
        "        return Triangle(value, {2}, {3}, {4});\n" +
        "    }}\n" +
        "}}\n"

    Console.WriteLine("// {0}", name)
    
    Console.WriteLine(reverseGradeFormat, "Low", name, low, med);
    Console.WriteLine(triangleFormat, "Medium", name, low, med, high);
    Console.WriteLine(gradeFormat, "High", name, med, high);


[<EntryPoint>]
let main argv = 
    generateFuzzySet("Health", 0.0, 100.0)
    generateFuzzySet("Acc", 0.0, 1.0)
    generateFuzzySet("Kills", 0.0, 200.0)
    generateFuzzySet("Speed", 0.0, 20.0)
    generateFuzzySet("Distance", 5.0, 300.0)
    Console.ReadLine()
    0 // return an integer exit code