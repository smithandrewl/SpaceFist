// Learn more about F# at http://fsharp.net
// See the 'F# Tutorial' project for more help.
open System

let generateFuzzySet (comment: String, low: float, high: float): Unit =
    let overlap: float = (high / 5.0) * 0.25 
    let offset:  float = (high / 5.0) - overlap

    let gradeFormat        = "return Grade(value, {0}, {1});"
    let reverseGradeFormat = "return ReverseGrade(value, {0}, {1});"
    let trapezoidFormat    = "return Trapezoid(value, {0}, {1}, {2}, {3});"
    let triangleFormat     = "return Triangle(value, {0}, {1}, {2});"
    
    Console.WriteLine("// {0}", comment)
    Console.WriteLine(reverseGradeFormat, low, (low + overlap))
    Console.WriteLine(trapezoidFormat, low, low + overlap, low + offset + overlap, low + offset + (2.0 * overlap))
    Console.WriteLine(trapezoidFormat, low + overlap + offset, low + offset + (2.0 * overlap), low + (2.0 * offset) + (2.0 * overlap), low + (2.0 * offset) + (3.0 * overlap))

    let triangleLower: float  = low + (2.0 * overlap) + (2.0 * offset)
    let triangleUpper: float  = low + (3.0 * overlap) + (3.0 * offset)
    let triangleMiddle: float = triangleLower + ((triangleUpper - triangleLower) / 2.0)

    Console.WriteLine(triangleFormat, triangleLower, triangleUpper, triangleMiddle)
    Console.WriteLine(trapezoidFormat, triangleUpper - overlap, triangleUpper, triangleUpper + offset, triangleUpper + offset + overlap)

    let last: float = triangleUpper + offset + overlap

    Console.WriteLine(trapezoidFormat, last - overlap, last, last + offset, last + offset + overlap)

    let last: float = last + offset + overlap

    Console.WriteLine(gradeFormat, last - overlap, last)


[<EntryPoint>]
let main argv = 
    generateFuzzySet("Health", 0.0, 100.0)
    generateFuzzySet("Acc", 0.0, 1.0)
    generateFuzzySet("Kills", 0.0, 200.0)
    generateFuzzySet("Speed", 0.0, 20.0)
    generateFuzzySet("Distance", 5.0, 300.0)
    Console.ReadLine()
    0 // return an integer exit code



